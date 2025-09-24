import java.sql.*;

public class Main {
    public static void main(String[] args) {
      
        criarTabela();
        
        System.out.println("=== TESTE 1: Dados válidos ===");
        testarOperacao("João Silva", "joao@email.com");
        
        System.out.println("\n=== TESTE 2: Email inválido ===");
        testarOperacao("Maria Santos", "email-invalido");
        
        System.out.println("\n=== TESTE 3: Nome vazio ===");
        testarOperacao("", "pedro@email.com");
        
        System.out.println("\n=== TESTE 4: Dados válidos 2 ===");
        testarOperacao("Ana Costa", "ana@email.com");
    }
    
    private static void testarOperacao(String nome, String email) {
        DatabaseOperation proxy = new DatabaseOperationProxy(nome, email);
        
        try {
            boolean sucesso = proxy.executeOperation();
            System.out.println("Resultado: " + (sucesso ? "SUCESSO" : "FALHA"));
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }
    }
    
    private static void criarTabela() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:testdb.db");
            
            String createTable = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTable);
            System.out.println("Tabela 'usuarios' criada/verificada com sucesso");
            
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
}