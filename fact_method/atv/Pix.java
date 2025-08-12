public class Pix implements IPagamento {

    @Override
    public int processarPagamento(double valor) {
        System.out.println("Gerando chave Pix...");
        System.out.println("Pagamento de R$ " + valor + " via Pix concluído");
        return 1; // 1 = sucesso
    }
}
