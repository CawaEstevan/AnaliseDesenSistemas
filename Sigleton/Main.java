package Sigleton;
public class Main{

    public static void main(String[] args) {

        DBConnection conexao1 = DBConnection.getInstance();
        System.out.println(conexao1.getStatusConexao());

        DBConnection conexao2 = DBConnection.getInstance();
        System.out.println(conexao2.getStatusConexao());

    }
}