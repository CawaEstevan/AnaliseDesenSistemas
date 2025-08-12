public class PayPalFactory extends PagamentoFactory {
    @Override
    public IPagamento criarPagamento() {
        return new PayPal();
    }
}
