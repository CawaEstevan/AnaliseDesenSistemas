package com.atividade.proxy;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseOperationProxy implements DatabaseOperation {
    private UserDatabaseOperation realOperation;

    public DatabaseOperationProxy(UserDatabaseOperation realOperation) {
        this.realOperation = realOperation;
    }

    @Override
    public Connection getConnection() throws Exception {
        return realOperation.getConnection();
    }

    @Override
    public void insertUser(String nome, String email) throws Exception {
        Connection conn = null;
        
        try {
            System.out.println("=== PROXY: Iniciando validação ===");
            
          
            if (nome == null || nome.isBlank()) {
                throw new IllegalArgumentException("Nome não pode ser vazio ou nulo");
            }
            
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email não pode ser vazio ou nulo");
            }
            
            if (!email.contains("@")) {
                throw new IllegalArgumentException("Email deve conter @");
            }
            
            System.out.println("PROXY: Validação passou - Nome: " + nome + ", Email: " + email);
            
      
            System.out.println("PROXY: Obtendo conexão para gerenciar transação...");
            conn = realOperation.getConnection();
            
            System.out.println("PROXY: Executando operação real...");
            realOperation.insertUser(nome, email);
            

            conn.commit();
            System.out.println("PROXY: COMMIT realizado com sucesso!");
            System.out.println("PROXY: Usuário inserido: " + nome + " (" + email + ")");

        } catch (Exception e) {
            System.err.println("PROXY: Erro detectado: " + e.getMessage());
        
            if (conn != null) {
                try {
                    System.out.println("PROXY: Executando ROLLBACK...");
                    conn.rollback();
                    System.out.println("PROXY: ROLLBACK executado com sucesso!");
                } catch (SQLException rollbackEx) {
                    System.err.println("PROXY: Erro crítico no ROLLBACK: " + rollbackEx.getMessage());
                    throw new RuntimeException("Falha no rollback da transação", rollbackEx);
                }
            }
            
           
            throw new RuntimeException("Operação falhou e foi revertida pelo proxy: " + e.getMessage(), e);
            
        } finally {
            
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("PROXY: Conexão fechada");
                } catch (SQLException e) {
                    System.err.println("PROXY: Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}