package com.atividade.proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificarDados {
    private static final String URL = "jdbc:mysql://localhost:3306/atividade_proxy?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123mudar";

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    VERIFICANDO DADOS NO BANCO MySQL   ");
        System.out.println("========================================");
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Conexão com MySQL estabelecida!");
            
            String sql = "SELECT id, nome, email FROM usuarios ORDER BY id";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                boolean hasData = false;
                System.out.println("\n📋 USUÁRIOS CADASTRADOS:");
                System.out.println("─".repeat(60));
                System.out.printf("%-3s | %-20s | %-25s%n", "ID", "Nome", "Email");
                System.out.println("─".repeat(60));
                
                while (rs.next()) {
                    hasData = true;
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    
                    System.out.printf("%-3d | %-20s | %-25s%n", id, nome, email);
                }
                
                if (!hasData) {
                    System.out.println("❌ NENHUM USUÁRIO ENCONTRADO!");
                    System.out.println("Verifique se o programa Main.java foi executado com sucesso.");
                } else {
                    // Contar total
                    String countSql = "SELECT COUNT(*) as total FROM usuarios";
                    try (PreparedStatement countStmt = conn.prepareStatement(countSql);
                         ResultSet countRs = countStmt.executeQuery()) {
                        if (countRs.next()) {
                            int total = countRs.getInt("total");
                            System.out.println("─".repeat(60));
                            System.out.println("✅ TOTAL DE USUÁRIOS CADASTRADOS: " + total);
                        }
                    }
                }
                
            }
            
        } catch (SQLException e) {
            System.err.println("❌ ERRO AO CONECTAR COM O BANCO:");
            System.err.println("   " + e.getMessage());
            System.err.println("\n🔧 POSSÍVEIS SOLUÇÕES:");
            System.err.println("   1. Verifique se o MySQL está rodando");
            System.err.println("   2. Confirme se o banco 'atividade_proxy' existe");
            System.err.println("   3. Verifique as credenciais (root/123mudar)");
            System.err.println("   4. Confirme se a tabela 'usuarios' foi criada");
        }
        
        System.out.println("\n========================================");
        System.out.println("         VERIFICAÇÃO CONCLUÍDA         ");
        System.out.println("========================================");
    }
}