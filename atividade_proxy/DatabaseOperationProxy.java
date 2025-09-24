public class DatabaseOperationProxy implements DatabaseOperation {
    private DatabaseOperation realOperation;
    private String nome;
    private String email;
    
    public DatabaseOperationProxy(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
    
    private boolean validarDados() {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro de validação: Nome não pode estar vazio");
            return false;
        }
        
        if (email == null || !email.contains("@")) {
            System.out.println("Erro de validação: Email inválido");
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean executeOperation() throws Exception {
        System.out.println("=== PROXY: Iniciando validação ===");
        
  
        if (!validarDados()) {
            throw new Exception("Dados inválidos - operação cancelada");
        }
        
        System.out.println("PROXY: Validação passou, executando operação...");
        
        try {
            // Criar a operação real apenas se validação passou
            this.realOperation = new UserDatabaseOperation(nome, email);
            boolean resultado = realOperation.executeOperation();
            
            System.out.println("PROXY: Operação executada com sucesso");
            return resultado;
            
        } catch (Exception e) {
            System.out.println("PROXY: Erro detectado - realizando rollback");
            System.out.println("Erro: " + e.getMessage());
            
         
            throw new Exception("Operação falhou e foi revertida: " + e.getMessage());
        }
    }
}