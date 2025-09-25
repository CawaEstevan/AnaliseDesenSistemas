import java.sql.*;
import java.util.*;
import java.io.*;

public class UserDatabaseOperation implements DatabaseOperation {
    private Connection connection;
    private String nome;
    private String email;
    private PreparedStatement stmt;
    
    // Simulação de banco em memória usando Map (sem drivers externos)
    private static Map<Integer, Map<String, String>> database = new HashMap<>();
    private static Set<String> emailsUsados = new HashSet<>();
    private static int nextId = 1;
    private static List<String> transactionLog = new ArrayList<>();
    
    public UserDatabaseOperation(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
    
    @Override
    public boolean executeOperation() throws Exception {
        System.out.println(" Iniciando operação de inserção...");
        
        try {
            // Simular início de transação
            transactionLog.clear();
            transactionLog.add("BEGIN TRANSACTION");
            
            // Verificar se email já existe (simular UNIQUE constraint)
            if (emailsUsados.contains(email)) {
                transactionLog.add("CONSTRAINT VIOLATION: Email já existe");
                throw new SQLException("Duplicate entry '" + email + "' for key 'email'");
            }
            
            // Criar registro
            Map<String, String> usuario = new HashMap<>();
            usuario.put("id", String.valueOf(nextId));
            usuario.put("nome", nome);
            usuario.put("email", email);
            usuario.put("data_criacao", new java.util.Date().toString());
            
            // Simular inserção
            database.put(nextId, usuario);
            emailsUsados.add(email);
            transactionLog.add("INSERT INTO usuarios VALUES(" + nextId + ", '" + nome + "', '" + email + "')");
            
            // Simular commit
            transactionLog.add("COMMIT");
            System.out.println("✓ Usuário inserido com sucesso: " + nome + " (ID: " + nextId + ")");
            nextId++;
            
            return true;
            
        } catch (SQLException e) {
            // Simular rollback
            transactionLog.add("ROLLBACK");
            System.out.println("✗ Rollback realizado devido ao erro: " + e.getMessage());
            
            // Reverter operações se necessário
            if (database.containsKey(nextId)) {
                database.remove(nextId);
                emailsUsados.remove(email);
            }
            
            throw new Exception("Erro SQL: " + e.getMessage());
        }
    }
    
    // Métodos estáticos para gerenciar o "banco"
    public static void limparBanco() {
        database.clear();
        emailsUsados.clear();
        nextId = 1;
        System.out.println(" Banco de dados limpo");
    }
    
    public static void mostrarUsuarios() {
        System.out.println("\n === USUÁRIOS NO BANCO ===");
        if (database.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            for (Map<String, String> usuario : database.values()) {
                System.out.printf("ID: %s | Nome: %s | Email: %s | Data: %s%n",
                    usuario.get("id"),
                    usuario.get("nome"),
                    usuario.get("email"),
                    usuario.get("data_criacao"));
            }
        }
        System.out.println("========================\n");
    }
    
    public static void mostrarLog() {
        System.out.println("\n === LOG DE TRANSAÇÕES ===");
        for (String log : transactionLog) {
            System.out.println(log);
        }
        System.out.println("========================\n");
    }
}