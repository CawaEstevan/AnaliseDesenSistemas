package com.atividade.proxy;

public class Main {
    public static void main(String[] args) {
        UserDatabaseOperation realOp = new UserDatabaseOperation();
        DatabaseOperation proxy = new DatabaseOperationProxy(realOp);

        try {
            proxy.insertUser("João Silva", "joao@example.com");
            proxy.insertUser("Maria Souza", "maria@example.com");

            // Forçando erro para testar rollback
            proxy.insertUser("Erro", "joao@example.com"); // email repetido

        } catch (Exception e) {
            System.err.println("Exceção capturada no Main: " + e.getMessage());
        }
    }
}
