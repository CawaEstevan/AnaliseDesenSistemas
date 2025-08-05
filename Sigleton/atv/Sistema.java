public class Sistema {
    public static void main(String[] args) {
        SessaoUsuario sessao = SessaoUsuario.getInstancia();

        // Tentando acessar painel sem autenticação
        sessao.acessarPainel();

        // Autenticando cliente
        Cliente cliente = new Cliente("Cawa");
        sessao.autenticar(cliente);

        // Acessando painel autenticado
        sessao.acessarPainel();

        // Obtendo dados do cliente
        Cliente c = sessao.getCliente();
        System.out.println("Cliente atual: " + c.getNome());

        // Alterando dados do cliente
        sessao.setInformacaoCliente("Cawa Estevan");

        // Verificando alteração
        System.out.println("Nome atualizado: " + sessao.getCliente().getNome());
    }
}
