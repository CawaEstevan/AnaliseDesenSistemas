/* um sigleton que cria um objeto que representa a conexão com um bd */

public class DBConnection{

    private static DBConnection conexao;//acesso global
    private int statusConexao = 0;

    private DBConnection(){ // Construtor privado
        System.err.println("criando conexão com o banco");
        this.statusConexao = 1;
    }

    public static  DBConnection getInstance(){
        if(conexao == null){
        conexao = new DBConnection();
        }
        return conexao;
    }

    public int getStatusConexao(){
        return this.statusConexao;
    }

}