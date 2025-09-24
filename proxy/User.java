package com.exemplo.proxy;

public class User {
    private Long id;
    private String nome;
    private String email;
    private int idade;

    public User() {}

    public User(String nome, String email, int idade) {
        this.nome = nome;
        this.email = email;
        this.idade = idade;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    @Override
    public String toString() {
        return "User{id=" + id + ", nome='" + nome + "', email='" + email + "', idade=" + idade + "}";
    }
}