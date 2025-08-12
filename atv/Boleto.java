public class Boleto implements IPagamento {

    @Override
    public int processarPagamento(double valor) {
        System.out.println("Gerando boleto no valor de R$ " + valor);
        System.out.println("Enviando boleto por e-mail...");
        return 1; // 1 = sucesso
    }
}
