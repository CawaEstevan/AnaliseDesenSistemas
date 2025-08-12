public class PixFactory extends PagamentoFactory {
    @Override
    public IPagamento criarPagamento() {
        return new Pix();
    }
}
