public class BoletoFactory extends PagamentoFactory {
    @Override
    public IPagamento criarPagamento() {
        return new Boleto();
    }
}
