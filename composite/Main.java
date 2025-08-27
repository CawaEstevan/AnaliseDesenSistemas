public class Main {
    public static void main(String[] args) {
        Produto p1 = new Produto("Camisa", 100.0);
        Produto p2 = new Produto("Calça", 200.0);
        Produto p3 = new Produto("Tênis", 300.0);

        Caixa caixa1 = new Caixa("Caixa 1");
        caixa1.adicionar(p1);
        caixa1.adicionar(p2);

        Caixa caixa2 = new Caixa("Caixa 2");
        caixa2.adicionar(p3);
        caixa2.adicionar(caixa1); // caixa dentro de caixa

        System.out.println("Preço total da " + caixa1 + " = R$ " + caixa1.getPreco());
        System.out.println("Preço total da " + caixa2 + " = R$ " + caixa2.getPreco());

        System.out.println("Preço com desconto progressivo da " + caixa1 + " = R$ " + caixa1.getPrecoComDesconto());
        System.out.println("Preço com desconto progressivo da " + caixa2 + " = R$ " + caixa2.getPrecoComDesconto());
    }
}
