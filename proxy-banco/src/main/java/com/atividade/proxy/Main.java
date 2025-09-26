package com.atividade.proxy;

public class Main {
    public static void main(String[] args) {
        UserDatabaseOperation realOp = new UserDatabaseOperation();
        DatabaseOperation proxy = new DatabaseOperationProxy(realOp);

        try {
            proxy.insertUser("Marcelo", "marcelo@example.com");
            proxy.insertUser("Lucas", "lucas@example.com");

            // Forçando erro para testar rollback
            proxy.insertUser("Erro", "marcelo@example.com"); // email repetido

        } catch (Exception e) {
            System.err.println("Exceção capturada no Main: " + e.getMessage());
        }
    }
}
