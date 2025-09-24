public class DatabaseOperationProxy implements DatabaseOperation {
    private DatabaseOperation realOperation;
    private String nome;
    private String email;
    private boolean operacaoExecutada = false;
    
    public DatabaseOperationProxy(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
    
    private boolean validarDados() {
        System.out.println("🔍 Validando dados...");
        
        // Validação de nome
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("❌ Erro de validação: Nome não pode estar vazio");
            return false;
        }
        
        if (nome.trim().length() < 2) {
            System.out.println("❌ Erro de validação: Nome deve ter pelo menos 2 caracteres");
            return false;
        }
        
        // Validação de email
        if (email == null || email.trim().isEmpty()) {
            System.out.println("❌ Erro de validação: Email não pode estar vazio");
            return false;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("❌ Erro de validação: Email deve conter @ e pelo menos um ponto");
            return false;
        }
        
        // Validação adicional de formato de email
        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        if (!email.matches(emailRegex)) {
            System.out.println("❌ Erro de validação: Formato de email inválido");
            return false;
        }
        
        System.out.println("✅ Dados válidos!");
        return true;
    }
    
    private void logarOperacao(String operacao) {
        System.out.println("📝 LOG: " + operacao + " - " + java.time.LocalDateTime.now());
    }
    
    @Override
    public boolean executeOperation() throws Exception {
        System.out.println("\n=== 🛡️ PROXY: Iniciando operação protegida ===");
        logarOperacao("Início da operação para usuário: " + nome);
        
        // Validação pré-operação
        if (!validarDados()) {
            logarOperacao("Operação cancelada - dados inválidos");
            throw new Exception("❌ Dados inválidos - operação cancelada pelo Proxy");
        }
        
        System.out.println("🚀 PROXY: Validação aprovada, executando operação real...");
        
        try {
            // Criar e executar a operação real
            this.realOperation = new UserDatabaseOperation(nome, email);
            boolean resultado = realOperation.executeOperation();
            
            if (resultado) {
                operacaoExecutada = true;
                logarOperacao("Operação executada com sucesso");
                System.out.println("✅ PROXY: Operação completada com sucesso");
                return true;
            } else {
                logarOperacao("Operação falhou");
                throw new Exception("Operação falhou na camada de dados");
            }
            
        } catch (Exception e) {
            logarOperacao("Erro capturado - " + e.getMessage());
            System.out.println("🔄 PROXY: Erro detectado - sistema de rollback ativado");
            System.out.println("❌ Detalhes do erro: " + e.getMessage());
            
            throw new Exception("🚫 Operação falhou e foi revertida pelo Proxy: " + e.getMessage());
        }
    }
    
    public boolean foiExecutada() {
        return operacaoExecutada;
    }
}