package com.atividade.proxy;

import java.sql.Connection;

public interface DatabaseOperation {
    void insertUser(String nome, String email) throws Exception;
    Connection getConnection() throws Exception; 
    
}
