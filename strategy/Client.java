public class Client {
 public static void main(String[] args) {
    Carrinho carrinho = new Carrinho();

    double precoOriginal = 100;

    carrinho.setEstrategiaDesconto(new SemDescontoStrategy());

    System.out.println("Preço final do carrinho: "+ carrinho.getPrecoFinal(precoOriginal));
 }   
}
