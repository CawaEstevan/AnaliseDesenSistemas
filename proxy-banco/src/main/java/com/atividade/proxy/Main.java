package com.atividade.proxy;

public class Main {
    public static void main(String[] args) {
        UserDatabaseOperation realOp = new UserDatabaseOperation();
        DatabaseOperation proxy = new DatabaseOperationProxy(realOp);

        try {
            proxy.insertUser("Marcos", "marcos@example.com");
            proxy.insertUser("Pedro", "pedro@example.com");

            // Forçando erro para testar rollback
            proxy.insertUser("Erro", "marcos@example.com"); // email repetido

        } catch (Exception e) {
            System.err.println("Exceção capturada no Main: " + e.getMessage());
        }
    }
}
