package com.atividade.proxy;

public class Main {
    public static void main(String[] args) {
        UserDatabaseOperation realOp = new UserDatabaseOperation();
        DatabaseOperation proxy = new DatabaseOperationProxy(realOp);

        System.out.println("====================================");
        System.out.println("TESTE DO PADRÃO PROXY COM ROLLBACK");
        System.out.println("====================================\n");

        // TESTE 1: Inserções válidas
        System.out.println("--- TESTE 1: Inserções Válidas ---");
        try {
            proxy.insertUser("Cawa Silva", "cawa@email.com");
            proxy.insertUser("Marcio Santos", "marcio@email.com");
        } catch (Exception e) {
            System.err.println("Erro inesperado no teste 1: " + e.getMessage());
        }

        System.out.println("\n--- TESTE 2: Validação - Nome Vazio ---");
        try {
            proxy.insertUser("", "teste@email.com");
        } catch (Exception e) {
            System.out.println(" Erro esperado capturado: " + e.getMessage());
        }

        System.out.println("\n--- TESTE 3: Validação - Email Inválido ---");
        try {
            proxy.insertUser("Pedro", "email_sem_arroba");
        } catch (Exception e) {
            System.out.println(" Erro esperado capturado: " + e.getMessage());
        }

        System.out.println("\n--- TESTE 4: Rollback - Email Duplicado ---");
        try {
            proxy.insertUser("Teste Rollback", "cawa@email.com"); // Email já existe
        } catch (Exception e) {
            System.out.println(" Rollback executado: " + e.getMessage());
        }

        System.out.println("\n====================================");
        System.out.println("TESTES FINALIZADOS");
        System.out.println("====================================");
    }
}