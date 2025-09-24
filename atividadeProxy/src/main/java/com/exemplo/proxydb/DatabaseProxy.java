package com.exemplo.proxydb;

public class DatabaseProxy implements IDatabaseOperation {
    private final DatabaseOperation realOperation;

    public DatabaseProxy() {
        this.realOperation = new DatabaseOperation();
    }

    @Override
    public void execute() {
        System.out.println("Proxy: iniciando operação...");
        realOperation.execute();
        System.out.println("Proxy: operação finalizada.");
    }
}
