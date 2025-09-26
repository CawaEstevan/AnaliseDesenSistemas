package com.atividade.proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDatabaseOperation implements DatabaseOperation {
    
    private static final String URL = "jdbc:mysql://localhost:3306/atividade_proxy?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Override
    public void insertUser(String nome, String email) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            System.out.println("Tentando conectar ao banco...");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão estabelecida!");
            
            conn.setAutoCommit(false); 
            System.out.println("AutoCommit desabilitado");

            String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + rowsAffected);

            conn.commit(); 
            System.out.println("Commit realizado com sucesso!");
            System.out.println("Usuário inserido com sucesso: " + nome);

        } catch (SQLException e) {
            System.out.println("Erro SQL capturado: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Rollback realizado");
                } catch (SQLException rollbackEx) {
                    System.out.println("Erro no rollback: " + rollbackEx.getMessage());
                }
            }
            throw e; 
        } catch (Exception e) {
            System.out.println("Erro geral capturado: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Rollback realizado");
                } catch (SQLException rollbackEx) {
                    System.out.println("Erro no rollback: " + rollbackEx.getMessage());
                }
            }
            throw e;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    System.out.println("PreparedStatement fechado");
                }
                if (conn != null) {
                    conn.close();
                    System.out.println("Conexão fechada");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
}