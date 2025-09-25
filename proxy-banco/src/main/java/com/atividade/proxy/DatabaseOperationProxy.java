package com.atividade.proxy;

public class DatabaseOperationProxy implements DatabaseOperation {
    private UserDatabaseOperation realOperation;

    public DatabaseOperationProxy(UserDatabaseOperation realOperation) {
        this.realOperation = realOperation;
    }

    @Override
    public void insertUser(String nome, String email) throws Exception {
        try {
         
            if (nome == null || nome.isBlank() || email == null || email.isBlank()) {
                throw new IllegalArgumentException("Nome e email não podem ser vazios.");
            }

            realOperation.insertUser(nome, email);

        } catch (Exception e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            // rollback já é feito dentro do UserDatabaseOperation
        }
    }
}
