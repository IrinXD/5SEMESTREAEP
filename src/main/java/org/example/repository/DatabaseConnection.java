package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:h2:./lixodb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Usuários
            stmt.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255) NOT NULL UNIQUE," +
                    "senha VARCHAR(255) NOT NULL," +
                    "endereco VARCHAR(255)" +
                    ");");

            // Pontos de Coleta
            stmt.execute("CREATE TABLE IF NOT EXISTS pontos_de_coleta (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "endereco VARCHAR(255) NOT NULL," +
                    "horario_funcionamento VARCHAR(255)" +
                    ");");

            // tipos de lixo aceitos
            stmt.execute("CREATE TABLE IF NOT EXISTS pontos_lixo_aceitos (" +
                    "id_ponto INT NOT NULL," +
                    "tipo_lixo VARCHAR(255) NOT NULL," +
                    "PRIMARY KEY (id_ponto, tipo_lixo)," +
                    "FOREIGN KEY (id_ponto) REFERENCES pontos_de_coleta(id) ON DELETE CASCADE" +
                    ");");

            // Agendamentos
            stmt.execute("CREATE TABLE IF NOT EXISTS agendamentos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "id_usuario INT NOT NULL," +
                    "tipo_lixo VARCHAR(255) NOT NULL," +
                    "volume_lixo VARCHAR(255) NOT NULL," +
                    "data_hora_coleta TIMESTAMP NOT NULL," +
                    "endereco_coleta VARCHAR(255) NOT NULL," +
                    "status VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE" +
                    ");");

            System.out.println("Banco de dados H2 inicializado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}