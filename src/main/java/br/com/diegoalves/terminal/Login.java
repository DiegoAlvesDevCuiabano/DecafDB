package br.com.diegoalves.terminal;

import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class Login {
    public boolean autenticar(Scanner sc) throws Exception {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("data/system/sys_users/sys_users.decaf", "r");
            byte deleted = raf.readByte();
            long deletedAt = raf.readLong();
            int rowid = raf.readInt();
            int id = raf.readInt();

            byte[] usernameBytes = new byte[50];
            raf.read(usernameBytes);

            byte[] passwordBytes = new byte[50];
            raf.read(passwordBytes);

            System.out.print("Informe o seu login: ");
            String login = sc.nextLine();
            System.out.print("Informe sua senha: ");
            String senha = sc.nextLine();

            String usernameArquivo = new String(usernameBytes).trim();
            String passwordArquivo = new String(passwordBytes).trim();

            if (login.equals(usernameArquivo) && senha.equals(passwordArquivo)) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (raf != null) {
                raf.close();
            }
        }
        return false;
    }
}
