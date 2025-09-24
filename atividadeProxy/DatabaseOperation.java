import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperation implements IDatabaseOperation {

    @Override
    public void execute() {
        String url = "jdbc:mysql://localhost:3306/atividade_proxy";
        String user = "root";         
        String password = "123mudar"; 

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);

            String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "Camisa Proxy");
            stmt.setDouble(2, 99.90);

            stmt.executeUpdate();

            // Simula erro proposital
            if (Math.random() > 0.5) {
                throw new RuntimeException("Erro simulado durante inserção");
            }

            conn.commit();
            System.out.println("Operação concluída com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro detectado: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Rollback realizado.");
                } catch (SQLException ex) {
                    System.out.println("Erro no rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
