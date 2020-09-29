package com.trabgateoria.codings.unary;
import com.trabgateoria.codings.CodingProtocol;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class UnaryCoding implements CodingProtocol {
    public String pathEncodedFile;
    public String pathDecodedFile;
    public File file;

    public UnaryCoding(File file) {
        this.file = file;
        this.pathEncodedFile = file.getParent() + File.separator + "Unary.cod";
        this.pathDecodedFile = file.getParent() + File.separator + "Unary.dec";
    }

    public String encode() throws IOException {
        ArrayList<Byte> encodedFile = new ArrayList<>();
        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileBytes = Files.readAllBytes(path);
        byte b = 0;
        int position = 0, offset, stopBit;
        
        addHeader(encodedFile);

        for(byte fileByte : fileBytes) {
            if(fileByte > 0){
                offset = fileByte;
            } else {
                offset = 256 + fileByte;
            } 
            
            for(int i = 0; i < offset; i++) {
                if (position >= 8) {
                    encodedFile.add(b);
                    b = 0;
                    position = 0;
                }
                position++;
            }
            if (position >= 8) {
                encodedFile.add(b);
                b = 0;
                position = 0;
            }

            stopBit = (7 - position);
            b = (byte) ( b | ( 1 << stopBit ) );
            position++;
        }

        if (position > 0) {
            encodedFile.add(b);
        }

        File encode = new File(pathEncodedFile);
        FileOutputStream fileOutputStream = new FileOutputStream(encode);
        for(Byte encodedFileByte : encodedFile){
            fileOutputStream.write(encodedFileByte);
        }
        return pathEncodedFile;
    }

    public String decode() throws IOException {
        String decodedFileContent = "";
        ArrayList<Byte> decodedFileBytes = new ArrayList<>();
        int var = 0, asciiChar = 65;
        Path path = Paths.get(file.getAbsolutePath());
        byte[] fileBytes = Files.readAllBytes(path);

        for(int position = 2; position < fileBytes.length; position++) {
            BitSet bits = BitSet.valueOf(
                    new long[] { fileBytes[position] }
                    );

            for(int i = 7; i >= 0; i--){
                if(bits.get(i)){
                    decodedFileBytes.add((byte)var);
                    var = 0;
                } else {
                    var++;
                }
            }
        }

        byte[] decodedBytes = new byte[decodedFileBytes.size()];
        for (int i = 0; i < decodedBytes.length; i++) {
            int ascii = decodedFileBytes.get(i);
            decodedBytes[i] = (byte)ascii;
        }
        byte[] decodedFileByteArray = new byte[decodedFileBytes.size()];
        for(int i = 0; i<decodedFileBytes.size(); i++){
            asciiChar = decodedFileBytes.get(i);
            decodedFileByteArray[i] = (byte)asciiChar;
        }
        decodedFileByteArray = decodedFileContent.getBytes(StandardCharsets.UTF_8);
        decodedFileContent = new String(decodedFileByteArray, StandardCharsets.UTF_8);
        File decodedFile = new File(pathDecodedFile);
        FileWriter writer = new FileWriter(decodedFile);
        writer.write(decodedFileContent);
        writer.close();
        return pathDecodedFile;
    }

    public void addHeader(ArrayList<Byte> encodedFileBytes) {
        encodedFileBytes.add((byte) 3);
        encodedFileBytes.add((byte) 0);
    }
}

