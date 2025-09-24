public interface DatabaseOperations {
    boolean inserirUsuario(User user) throws Exception;
    boolean atualizarUsuario(User user) throws Exception;
    boolean deletarUsuario(Long id) throws Exception;
    User buscarUsuario(Long id) throws Exception;
}

// ===== DatabaseOperationsImpl.java =====
package com.exemplo.proxy;

import java.sql.*;

public class DatabaseOperationsImpl implements DatabaseOperations {
    private static final String URL = "jdbc:mysql://localhost:3306/proxy_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public boolean inserirUsuario(User user) throws Exception {
        String sql = "INSERT INTO usuarios (nome, email, idade) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Simular erro para demonstrar o proxy
            if (user.getIdade() < 0) {
                throw new IllegalArgumentException("Idade não pode ser negativa!");
            }
            
            if (user.getEmail() == null || !user.getEmail().contains("@")) {
                throw new IllegalArgumentException("Email inválido!");
            }
            
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getIdade());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            throw new Exception("Erro ao inserir usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean atualizarUsuario(User user) throws Exception {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, idade = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (user.getIdade() < 0) {
                throw new IllegalArgumentException("Idade não pode ser negativa!");
            }
            
            if (user.getEmail() == null || !user.getEmail().contains("@")) {
                throw new IllegalArgumentException("Email inválido!");
            }
            
            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getIdade());
            stmt.setLong(4, user.getId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deletarUsuario(Long id) throws Exception {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new Exception("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public User buscarUsuario(Long id) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setIdade(rs.getInt("idade"));
                return user;
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar usuário: " + e.getMessage(), e);
        }
    }
}