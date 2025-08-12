public class PayPal implements IPagamento {

    @Override
    public int processarPagamento(double valor) {
        System.out.println("Conectando ao PayPal...");
        System.out.println("Processando pagamento de R$ " + valor + " via PayPal");
        return 1; // 1 = sucesso
    }
}
