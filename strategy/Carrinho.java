//Context - Strategy
public class Carrinho {
    
    private IDesconto estrategiaDesconto;

    public void setEstrategiaDesconto(IDesconto estrategia){
        this.estrategiaDesconto = estrategia;
    }

    public double getPrecoFinal(double preco){
        return estrategiaDesconto.aplicaDesconto(preco);
    }

}
