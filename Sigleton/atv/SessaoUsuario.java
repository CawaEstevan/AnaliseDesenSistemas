public class SessaoUsuario {
    private static SessaoUsuario instancia = null;
    private Cliente clienteAutenticado;

    private SessaoUsuario() {
        // construtor privado
    }

    public static SessaoUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SessaoUsuario(); // cria a sessão uma vez
            System.out.println("Sessão criada.");
        }
        return instancia;
    }

    public void autenticar(Cliente cliente) {
        this.clienteAutenticado = cliente;
        System.out.println("Cliente autenticado: " + cliente.getNome());
    }

    public boolean isAutenticado() { // verifica se o cliente está autenticado 
        return clienteAutenticado != null;
    }

    public Cliente getCliente() {
        return clienteAutenticado;
    }

    public void setInformacaoCliente(String novoNome) {
        if (isAutenticado()) {
            clienteAutenticado.setNome(novoNome);
            System.out.println("Nome atualizado para: " + novoNome);
        } else {
            System.out.println("Acesso negado. Cliente não autenticado.");
        }
    }

    public void acessarPainel() {
        if (isAutenticado()) {
            System.out.println("Bem-vindo ao painel, " + clienteAutenticado.getNome() + "!");
        } else {
            System.out.println("Acesso negado. Faça login primeiro."); // verifica a autenticação
        }
    }
}
