public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 === SISTEMA PROXY PARA OPERAÇÕES DE BANCO ===");
        System.out.println("💡 Usando banco em memória (sem drivers externos)\n");
        
        // Inicializar "banco"
        UserDatabaseOperation.limparBanco();
        
        // Testes do padrão Proxy
        System.out.println("🧪 === INICIANDO TESTES ===\n");
        
        System.out.println("=== TESTE 1: Dados válidos ===");
        testarOperacao("João Silva", "joao@email.com");
        
        System.out.println("\n=== TESTE 2: Email inválido ===");
        testarOperacao("Maria Santos", "email-invalido");
        
        System.out.println("\n=== TESTE 3: Nome vazio ===");
        testarOperacao("", "pedro@email.com");
        
        System.out.println("\n=== TESTE 4: Email sem ponto ===");
        testarOperacao("Cawa", "cawa@email");
        
        System.out.println("\n=== TESTE 5: Nome muito curto ===");
        testarOperacao("A", "ana@email.com");
        
        System.out.println("\n=== TESTE 6: Dados válidos 2 ===");
        testarOperacao("Ana Costa", "ana@email.com");
        
        System.out.println("\n=== TESTE 7: Email duplicado (deve falhar) ===");
        testarOperacao("João Santos", "joao@email.com"); // Email já existe
        
        System.out.println("\n=== TESTE 8: Dados válidos 3 ===");
        testarOperacao("Pedro Oliveira", "pedro@empresa.com.br");
        
       
        UserDatabaseOperation.mostrarUsuarios();
        UserDatabaseOperation.mostrarLog();
        
        
    }
    
    private static void testarOperacao(String nome, String email) {
        System.out.println("📤 Testando: Nome='" + nome + "', Email='" + email + "'");
        
        DatabaseOperationProxy proxy = new DatabaseOperationProxy(nome, email);
        
        try {
            boolean sucesso = proxy.executeOperation();
            System.out.println("🎯 Resultado: " + (sucesso ? "✅ SUCESSO" : "❌ FALHA"));
            
        } catch (Exception e) {
            System.out.println("⚠️ Exceção capturada pelo sistema: " + e.getMessage());
        }
        
        System.out.println("─".repeat(50));
    }
}