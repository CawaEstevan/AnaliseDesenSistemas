import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProxy implements DatabaseOperations {
    private DatabaseOperations realObject;
    private List<String> operationLog;
    
    public DatabaseProxy() {
        this.realObject = new DatabaseOperationsImpl();
        this.operationLog = new ArrayList<>();
    }
    
    @Override
    public boolean inserirUsuario(User user) throws Exception {
        Connection conn = null;
        Savepoint savepoint = null;
        
        try {
            // Log da operação
            String operation = "INSERIR_USUARIO: " + user.toString();
            operationLog.add(operation);
            System.out.println("[PROXY] Iniciando: " + operation);
            
            // Configurar transação para rollback
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proxy_db", "root", "password");
            conn.setAutoCommit(false);
            savepoint = conn.setSavepoint("insert_user");
            
            // Executar operação real
            boolean result = realObject.inserirUsuario(user);
            
            if (result) {
                conn.commit();
                System.out.println("[PROXY] Operação concluída com sucesso");
                return true;
            } else {
                throw new Exception("Falha na operação de inserção");
            }
            
        } catch (Exception e) {
            System.err.println("[PROXY] ERRO detectado: " + e.getMessage());
            
            // Rollback da operação
            if (conn != null && savepoint != null) {
                try {
                    conn.rollback(savepoint);
                    System.out.println("[PROXY] ROLLBACK executado com sucesso");
                } catch (SQLException rollbackEx) {
                    System.err.println("[PROXY] Erro no rollback: " + rollbackEx.getMessage());
                }
            }
            
            operationLog.add("ERRO: " + e.getMessage());
            throw e;
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("[PROXY] Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    @Override
    public boolean atualizarUsuario(User user) throws Exception {
        Connection conn = null;
        Savepoint savepoint = null;
        User oldUser = null;
        
        try {
            // Salvar estado anterior para rollback
            oldUser = realObject.buscarUsuario(user.getId());
            
            String operation = "ATUALIZAR_USUARIO: " + user.toString();
            operationLog.add(operation);
            System.out.println("[PROXY] Iniciando: " + operation);
            
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proxy_db", "root", "password");
            conn.setAutoCommit(false);
            savepoint = conn.setSavepoint("update_user");
            
            boolean result = realObject.atualizarUsuario(user);
            
            if (result) {
                conn.commit();
                System.out.println("[PROXY] Operação concluída com sucesso");
                return true;
            } else {
                throw new Exception("Falha na operação de atualização");
            }
            
        } catch (Exception e) {
            System.err.println("[PROXY] ERRO detectado: " + e.getMessage());
            
            if (conn != null && savepoint != null) {
                try {
                    conn.rollback(savepoint);
                    System.out.println("[PROXY] ROLLBACK executado com sucesso");
                } catch (SQLException rollbackEx) {
                    System.err.println("[PROXY] Erro no rollback: " + rollbackEx.getMessage());
                }
            }
            
            operationLog.add("ERRO: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("[PROXY] Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    @Override
    public boolean deletarUsuario(Long id) throws Exception {
        Connection conn = null;
        Savepoint savepoint = null;
        User userToDelete = null;
        
        try {
            // Salvar dados para possível rollback
            userToDelete = realObject.buscarUsuario(id);
            
            String operation = "DELETAR_USUARIO: ID = " + id;
            operationLog.add(operation);
            System.out.println("[PROXY] Iniciando: " + operation);
            
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/proxy_db", "root", "password");
            conn.setAutoCommit(false);
            savepoint = conn.setSavepoint("delete_user");
            
            boolean result = realObject.deletarUsuario(id);
            
            if (result) {
                conn.commit();
                System.out.println("[PROXY] Operação concluída com sucesso");
                return true;
            } else {
                throw new Exception("Falha na operação de exclusão");
            }
            
        } catch (Exception e) {
            System.err.println("[PROXY] ERRO detectado: " + e.getMessage());
            
            if (conn != null && savepoint != null) {
                try {
                    conn.rollback(savepoint);
                    System.out.println("[PROXY] ROLLBACK executado com sucesso");
                } catch (SQLException rollbackEx) {
                    System.err.println("[PROXY] Erro no rollback: " + rollbackEx.getMessage());
                }
            }
            
            operationLog.add("ERRO: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("[PROXY] Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    @Override
    public User buscarUsuario(Long id) throws Exception {
        try {
            String operation = "BUSCAR_USUARIO: ID = " + id;
            operationLog.add(operation);
            System.out.println("[PROXY] Executando: " + operation);
            
            return realObject.buscarUsuario(id);
            
        } catch (Exception e) {
            operationLog.add("ERRO: " + e.getMessage());
            throw e;
        }
    }
    
    public List<String> getOperationLog() {
        return new ArrayList<>(operationLog);
    }
    
    public void clearLog() {
        operationLog.clear();
    }
}