public abstract class PagamentoFactory {

    // Método fábrica (deve ser implementado pelas subclasses)
    public abstract IPagamento criarPagamento();

    // Método que processa o pagamento usando a implementação criada
    public int pagar(double valor) {
        IPagamento pagamento = criarPagamento();
        return pagamento.processarPagamento(valor);
    }
}
