package com.seu_projeto.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // URL do banco de dados, porta padrão 5432
    private static final String URL = "jdbc:postgresql://localhost:5432/lojaonline";

    // Usuário e senha do banco conforme definidos no Docker Compose
    private static final String USER = "admin";  // Ajustado para o usuário correto
    private static final String PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
