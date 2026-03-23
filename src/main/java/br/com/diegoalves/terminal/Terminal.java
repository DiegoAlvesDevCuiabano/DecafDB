package br.com.diegoalves.terminal;

import br.com.diegoalves.engine.Engine;

import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        boasVindas();
        Login login = new Login();
        boolean logado = false;
        do {
            logado = login.autenticar(sc);
            if (!logado) {
                System.out.println("Login ou Senha inválidos. Tente novamente");
                System.out.println();
            }
        } while (!logado);
        terminalLoop(sc);
        System.out.println("Goodbye");
        sc.close();
    }

    private static void terminalLoop(Scanner sc) {
        String entrada;
        Engine engine = new Engine();
        do {
            System.out.print("decafdb>");
            entrada = sc.nextLine();
            if (!entrada.equalsIgnoreCase("exit")) {
                engine.start(entrada);
            }
        } while (!entrada.equalsIgnoreCase("exit"));
    }

    private static void boasVindas() {
        System.out.println("\u001B[38;5;130m");
        System.out.println(" ______                          __  ____________ ");
        System.out.println("|  _  \\                        / _| |  _  \\ ___ \\");
        System.out.println("| | | |   ___    ___    __ _  | |_  | | | | |_/ /");
        System.out.println("| | | |  / _ \\  / __|  / _` | |  _| | | | | ___ \\");
        System.out.println("| |/ /  |  __/ | (__  | (_| | | |   | |/ /| |_/ /");
        System.out.println("|___/    \\___|  \\___|  \\__,_| |_|   |___/ \\____/ ");
        System.out.println("\u001B[0m");
        System.out.println(" v0.1 - A didactic database engine");
        System.out.println();
    }
}
