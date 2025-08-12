//Estratégia concreta - Strategy
public class DescontoNatalStrategy implements IDesconto{
    
    @Override
    public double aplicaDesconto(double preco){

        // Aplica desconto de 30%
        return preco * 0.7;

    }

}
