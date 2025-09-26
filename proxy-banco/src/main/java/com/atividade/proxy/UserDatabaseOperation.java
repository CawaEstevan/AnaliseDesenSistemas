package com.atividade.proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDatabaseOperation implements DatabaseOperation {
    
    private static final String URL = "jdbc:mysql://localhost:3306/atividade_proxy?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123mudar";

    @Override
    public Connection getConnection() throws Exception {
        System.out.println("Estabelecendo conexão com o banco...");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        conn.setAutoCommit(false); 
        System.out.println("Conexão estabelecida - AutoCommit desabilitado");
        return conn;
    }

    @Override
    public void insertUser(String nome, String email) throws Exception {
      
        Connection conn = getConnection();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Operação executada - Linhas afetadas: " + rowsAffected);
        

        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                    System.out.println("PreparedStatement fechado");
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }
           
        }
    }
}