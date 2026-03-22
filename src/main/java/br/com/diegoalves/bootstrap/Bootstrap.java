package br.com.diegoalves.bootstrap;

import java.io.RandomAccessFile;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("data/system/sys_users/sys_users.decaf", "rw");

        raf.writeByte(0);
        raf.writeLong(0);
        raf.writeInt(1);
        raf.writeInt(1);

        String username = "root";
        raf.write(getBytes(username));

        String password = "root";
        raf.write(getBytes(password));

        raf.close();

    }

    private static byte[] getBytes(String word) {
        byte[] wordBytes = new byte[50];
        byte[] raw = word.getBytes();
        System.arraycopy(raw, 0, wordBytes, 0, raw.length);

        for (int i = raw.length; i < wordBytes.length; i++) {
            wordBytes[i] = (byte) ' ';
        }
        return wordBytes;
    }


}
