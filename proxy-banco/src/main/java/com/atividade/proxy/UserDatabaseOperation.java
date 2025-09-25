package com.atividade.proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserDatabaseOperation implements DatabaseOperation {
    private static final String URL = "jdbc:mysql://localhost:3306/atividade_proxy";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Override
    public void insertUser(String nome, String email) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false); 

            String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();

            conn.commit(); 
            System.out.println("Usuário inserido com sucesso: " + nome);

        } catch (Exception e) {
            if (conn != null) {
                conn.rollback(); 
            }
            throw e; 
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
