package org.example;

import org.example.repository.DatabaseConnection;
import org.example.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializa o banco de dados (cria tabelas se não existirem)
        DatabaseConnection.initializeDatabase();

        // 2. Inicia a interface do console
        ConsoleView app = new ConsoleView();
        app.iniciarAplicacao();
    }
}