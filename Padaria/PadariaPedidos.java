interface ItemPedido {
    String getDescricao();
    double getPreco();
}


class CafeBasico implements ItemPedido {
    @Override
    public String getDescricao() {
        return "Café";
    }
    
    @Override
    public double getPreco() {
        return 3.50;
    }
}


abstract class DecoradorItem implements ItemPedido {
    protected ItemPedido item;
    
    public DecoradorItem(ItemPedido item) {
        this.item = item;
    }
    
    @Override
    public String getDescricao() {
        return item.getDescricao();
    }
    
    @Override
    public double getPreco() {
        return item.getPreco();
    }
}


class Acucar extends DecoradorItem {
    public Acucar(ItemPedido item) {
        super(item);
    }
    
    @Override
    public String getDescricao() {
        return item.getDescricao() + " + Açúcar";
    }
    
    @Override
    public double getPreco() {
        return item.getPreco() + 0.50;
    }
}

class Chocolate extends DecoradorItem {
    public Chocolate(ItemPedido item) {
        super(item);
    }
    
    @Override
    public String getDescricao() {
        return item.getDescricao() + " + Chocolate";
    }
    
    @Override
    public double getPreco() {
        return item.getPreco() + 1.50;
    }
}


class PaoDeQueijo extends DecoradorItem {
    public PaoDeQueijo(ItemPedido item) {
        super(item);
    }
    
    @Override
    public String getDescricao() {
        return item.getDescricao() + " + Pão de Queijo";
    }
    
    @Override
    public double getPreco() {
        return item.getPreco() + 4.00;
    }
}

class Bolo extends DecoradorItem {
    public Bolo(ItemPedido item) {
        super(item);
    }
    
    @Override
    public String getDescricao() {
        return item.getDescricao() + " + Bolo";
    }
    
    @Override
    public double getPreco() {
        return item.getPreco() + 8.50;
    }
}

class Biscoitos extends DecoradorItem {
    public Biscoitos(ItemPedido item) {
        super(item);
    }
    
    @Override
    public String getDescricao() {
        return item.getDescricao() + " + Biscoitos";
    }
    
    @Override
    public double getPreco() {
        return item.getPreco() + 3.20;
    }
}


public class PadariaPedidos {
    public static void main(String[] args) {
        
        
       
        ItemPedido pedido1 = new Chocolate(new Acucar(new CafeBasico()));
        
       
        ItemPedido pedido2 = new Biscoitos(
            new Bolo(
                new PaoDeQueijo(
                    new Chocolate(
                        new Acucar(new CafeBasico())
                    )
                )
            )
        );
        

        System.out.println("=== PEDIDOS DA PADARIA ===");
        System.out.println("Pedido 1: " + pedido1.getDescricao());
        System.out.printf("Preço: R$ %.2f%n", pedido1.getPreco());
        System.out.println();
        
        System.out.println("Pedido 2: " + pedido2.getDescricao());
        System.out.printf("Preço: R$ %.2f%n", pedido2.getPreco());
    }
}