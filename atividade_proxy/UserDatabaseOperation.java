import java.sql.*;

public class UserDatabaseOperation implements DatabaseOperation {
    private Connection connection;
    private String nome;
    private String email;
    
    public UserDatabaseOperation(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
    
    private void conectarBanco() throws SQLException {
     
        String url = "jdbc:sqlite:testdb.db";
        this.connection = DriverManager.getConnection(url);
        this.connection.setAutoCommit(false); 
    }
    
    @Override
    public boolean executeOperation() throws Exception {
        conectarBanco();
        
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.setString(2, email);
        
        int linhasAfetadas = stmt.executeUpdate();
        
        if (linhasAfetadas > 0) {
            connection.commit();
            System.out.println("Usuário inserido com sucesso: " + nome);
            return true;
        } else {
            connection.rollback();
            throw new Exception("Falha ao inserir usuário");
        }
    }
}