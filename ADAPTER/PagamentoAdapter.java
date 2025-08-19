public class PagamentoAdapter implements INovoPagamento {

    private Pagamento pagamentoAntigo;

    public PagamentoAdapter(Pagamento pagamentoAntigo) {
        this.pagamentoAntigo = pagamentoAntigo;
    }

    @Override
    public void realizarPagamento(double montante) {
        // Adapter traduz o método
        pagamentoAntigo.processarPagamento(montante);
    }
}
