import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        HashMap<String, PagamentoFactory> pagamentos = new HashMap<>();
        pagamentos.put("paypal", new PayPalFactory());
        pagamentos.put("boleto", new BoletoFactory());
        pagamentos.put("pix", new PixFactory());

        // Simula escolha do usuário
        String metodoEscolhido = "pix";
        double valor = 1050.0;

        int status = pagamentos.get(metodoEscolhido).pagar(valor);

        System.out.println("Status do pagamento: " + (status == 1 ? "Sucesso" : "Falha"));
    }
}
