import java.util.ArrayList;
import java.util.List;

public class Caixa implements Componente {
    private String nome;
    private List<Componente> itens;

    public Caixa(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
    }

    public void adicionar(Componente c) {
        itens.add(c);
    }

    public void remover(Componente c) {
        itens.remove(c);
    }

    @Override
    public double getPreco() {
        double total = 0.0;
        for (Componente c : itens) {
            total += c.getPreco();
        }
        return total;
    }

    // Função extra: desconto progressivo (5% a mais a cada produto)
    public double getPrecoComDesconto() {
        double total = 0.0;
        int count = 0;
        for (Componente c : itens) {
            count++;
            double desconto = (count - 1) * 0.05; // 5% para o 2º produto, 10% para o 3º, etc.
            if (desconto > 1.0) desconto = 1.0; // limite de 100%
            total += c.getPreco() * (1 - desconto);
        }
        return total;
    }

    @Override
    public String toString() {
        return "Caixa: " + nome + " contém " + itens.size() + " itens";
    }
}
