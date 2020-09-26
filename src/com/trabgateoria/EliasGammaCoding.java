package com.trabgateoria;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class EliasGammaCoding {

    public String pathEncodedFile;
    public String pathDictionaryFile;
    public String pathDecodedFile;
    public File file;

    public EliasGammaCoding(File file) {
        this.file = file;
        this.pathEncodedFile = file.getParent() + File.separator + "Elias-Gamma-Encoded";
        this.pathDictionaryFile = file.getParent() + File.separator + "dicionario";
        this.pathDecodedFile = file.getParent() + File.separator + "Elias-Gamma-Decoded";
    }

    public void encode() throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        String fileData = readFile(path);

        int i, d, t, p = 0;
        BitSet bsD = new BitSet(), bsCoded = new BitSet();

        Map<Integer, Integer> alphabet = createAlphabet(fileData);

        for (i = 0; i < fileData.length(); i++) {
            Integer k = (int) fileData.charAt(i);
            d = alphabet.get(k);

            bsD = egEncode(d);
            t = egSize(d);
            for (int j = 0; j < t; j++) {
                bsCoded.set(p, bsD.get(j));
                p++;
            }
        }

        File encode = new File(pathEncodedFile);
        FileOutputStream in = new FileOutputStream(encode);
        in.write(bsCoded.toByteArray());
        /* cria um arquivo dicionario com o alfabeto passado por parametro */
        encodeDicionario(alphabet);
    }

    public void decode() throws IOException {
        Map<Integer, Integer> alphabet = decodeDicionario();
        byte[] ans = Files.readAllBytes(Paths.get(pathEncodedFile));

        BitSet newBS = BitSet.valueOf(ans);
        int q = 0, j;
        int t = 0;
        String decodedString = "";
        while (q < newBS.length()) {
            BitSet bsD = new BitSet();
            j = newBS.nextSetBit(q) - q;
            if (j == 0) {
                t = 2;
            } else {
                t = j * 2 + 1;
            }
            for (int k = 0; k < t; k++) {
                bsD.set(k, newBS.get(q + k));
            }
            q += t;
            int d = egDecode(bsD);

            for (Map.Entry<Integer, Integer> entry : alphabet.entrySet()) {
                int k = entry.getKey();
                Integer v = entry.getValue();
                if (v == d) {
                    decodedString += (char) k;
                    break;
                }
            }
        }
        createDecodedFile(decodedString);
    }

    private void createDecodedFile(String decodedString) throws IOException {
        File decodedFile = new File(pathDecodedFile);
        FileWriter writer = new FileWriter(decodedFile);
        writer.write(decodedString);
        writer.close();
    }

    private String readFile(Path path) throws IOException {
        Stream<String> linhas = Files.lines(path);
        String fileData = "";
        for (Object linha : linhas.toArray()) {
            fileData += linha.toString();
        }
        return fileData;
    }

    private Map<Integer, Integer> decodeDicionario() throws IOException {
        File dictionary = new File(pathDictionaryFile);
        byte[] ans = Files.readAllBytes(Paths.get(dictionary.getAbsolutePath()));
        Files.deleteIfExists(Paths.get(dictionary.getAbsolutePath()));
        BitSet newBS = BitSet.valueOf(ans);
        /* percorrer bitset adicionando no dicionario primeiro o valor, depois a chave */
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        int q = 0, j, t = 0;
        Integer value = null, keyValue = null;
        while (q < newBS.length()) {
            BitSet bsD = new BitSet();
            j = newBS.nextSetBit(q) - q;
            if (j == 0) {
                t = 2;
            } else {
                t = j * 2 + 1;
            }
            for (int k = 0; k < t; k++) {
                bsD.set(k, newBS.get(q + k));
            }
            q += t;
            if (keyValue == null) {
                keyValue = egDecode(bsD);
                continue;
            }

            if (value == null) {
                value = egDecode(bsD);
            }
            if (value != null && keyValue != null) {
                result.put(keyValue, value);
                value = null;
                keyValue = null;
            }
        }
        return result;
    }

    private void encodeDicionario(Map<Integer, Integer> dictionary) throws FileNotFoundException, IOException {
        FileOutputStream inD = new FileOutputStream(new File(pathDictionaryFile));
        BitSet bsDic = new BitSet();
        int t = 0, p = 0;
        for (Map.Entry<Integer, Integer> entry : dictionary.entrySet()) {
            Integer k = entry.getKey();
            Integer v = entry.getValue();

            /*encode key*/
            BitSet bsD = egEncode(k);
            t = egSize(k);
            for (int j = 0; j < t; j++) {
                bsDic.set(p, bsD.get(j));
                p++;
            }
            /*encode value*/
            bsD = egEncode(v);
            t = egSize(v);
            for (int j = 0; j < t; j++) {
                bsDic.set(p, bsD.get(j));
                p++;
            }
        }
        inD.write(bsDic.toByteArray());
    }

    private static Map createAlphabet(String chars) {
        Map<Integer, Integer> alfabeto = new HashMap<Integer, Integer>();
        int d;
        for (int i = 0; i < chars.length(); i++) {
            d = ((int) chars.charAt(i));
            int cont = 1;
            if (alfabeto.containsKey(d)) {
                cont = alfabeto.get(d) + 1;
            }
            alfabeto.put(d, cont);
        }
        ValueComparator alf = new ValueComparator(alfabeto);
        TreeMap<Integer, Integer> sorted_map = new TreeMap<Integer,Integer>(alf);
        sorted_map.putAll(alfabeto);

        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        int representation = 0;

        for (Map.Entry<Integer, Integer> entry : sorted_map.entrySet()) {
            Integer k = entry.getKey();
            result.put(k, representation);
            representation++;
        }
        return result;
    }

    //MARK: - Helpers
    public int egSize(int n) {
        if (n == 0 || n == 1) {
            return 2;
        }
        return (int) (Math.log(n) / Math.log(2)) * 2 + 1;
    }

    public BitSet egEncode(int n) {
        BitSet code;
        if (n == 0 || n == 1) {
            code = new BitSet(2);
            code.set(0);
            if (n == 1) {
                code.set(1);
            }
            return code;
        }

        int p1 = (int) (Math.log(n) / Math.log(2));
        int p2 = n - (int) Math.pow(2, p1);
        code = new BitSet(p1 * 2 + 1);

        int i;
        for (i = 0; i < p1; i++) {
            code.clear(i);
        }

        code.set(p1);
        int k = 1 << (p1 - 1);
        for (i = p1 + 1; i < p1 * 2 + 1; i++) {
            code.set(i, (p2 & k) != 0);
            k >>= 1;
        }
        return code;
    }

    public int egDecode(BitSet codigo) {
        int separator = codigo.nextSetBit(0);
        if (separator == 0) {
            if (codigo.get(1)) {
                return 1;
            } else {
                return 0;
            }
        }

        int n = (int) Math.pow(2, separator);
        int i = separator + 1;
        int pot = separator - 1;
        while (pot >= 0) {
            if (codigo.get(i)) {
                n += (int) Math.pow(2, pot);
            }
            i++;
            pot--;
        }
        return n;
    }
}

