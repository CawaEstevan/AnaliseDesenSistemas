public class Main {
    public static void main(String[] args) {
        // Sistema antigo
        Pagamento pagamentoAntigo = new Pagamento();

        // Adapter conecta o antigo ao novo padrão
        INovoPagamento novoPagamento = new PagamentoAdapter(pagamentoAntigo);

        // Usando o sistema no padrão novo
        novoPagamento.realizarPagamento(150.00);
    }
}

