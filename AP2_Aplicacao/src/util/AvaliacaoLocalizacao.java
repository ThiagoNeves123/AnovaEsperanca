package util;

// Importa as classes Restaurante e Cliente que serão relacionadas
import util.Restaurante;
import util.Cliente;

public class AvaliacaoLocalizacao extends Avaliacao {
    private float notaLocalizacao;
    private Restaurante restaurante; // Adiciona o objeto Restaurante
    private Cliente cliente;         // Adiciona o objeto Cliente

    // Construtor original (sem Restaurante e Cliente) - pode ser mantido para compatibilidade, mas não recomendado para uso com DB
    public AvaliacaoLocalizacao(float notaLocalizacao) {
        super();
        this.notaLocalizacao = notaLocalizacao;
        // Inicializa os objetos relacionados como null para evitar NullPointerException se não forem definidos
        this.restaurante = null;
        this.cliente = null;
    }

    // Construtor com ID (sem Restaurante e Cliente) - pode ser mantido para compatibilidade, mas não recomendado para uso com DB
    public AvaliacaoLocalizacao(int idAvaliacao, float notaLocalizacao) {
        super();
        setIdAvaliacao(idAvaliacao);
        this.notaLocalizacao = notaLocalizacao;
        this.restaurante = null;
        this.cliente = null;
    }

    // NOVO CONSTRUTOR: Inclui Restaurante e Cliente - Essencial para o JDBC DAO
    public AvaliacaoLocalizacao(int idAvaliacao, float notaLocalizacao, Restaurante restaurante, Cliente cliente) {
        super();
        setIdAvaliacao(idAvaliacao); // Define o ID da avaliação através da superclasse
        this.notaLocalizacao = notaLocalizacao;
        this.restaurante = restaurante;
        this.cliente = cliente;
    }

    // Outro NOVO CONSTRUTOR: Para criar uma nova avaliação antes de ter o ID gerado pelo DB
    public AvaliacaoLocalizacao(float notaLocalizacao, Restaurante restaurante, Cliente cliente) {
        super(); // ID será definido pelo DAO após a inserção
        this.notaLocalizacao = notaLocalizacao;
        this.restaurante = restaurante;
        this.cliente = cliente;
    }

    public float getNotaLocalizacao() {
        return notaLocalizacao;
    }

    public void setNotaLocalizacao(float notaLocalizacao) {
        this.notaLocalizacao = notaLocalizacao;
    }

    // Getter para Restaurante
    public Restaurante getRestaurante() {
        return restaurante;
    }

    // Setter para Restaurante
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    // Getter para Cliente
    public Cliente getCliente() {
        return cliente;
    }

    // Setter para Cliente
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Método para ser consistente com a chamada em Classificacao.java (se ainda for usado lá)
    // Se Classificacao.java estiver usando getNota() sem os novos campos,
    // essa é uma boa forma de manter a compatibilidade.
    public float getNota() {
        return notaLocalizacao;
    }

    @Override
    protected void comentar() {
        // Pode ser útil adicionar detalhes dos objetos relacionados no comentário
        String restauranteNome = (restaurante != null) ? restaurante.getNome() : "Desconhecido";
        String clienteNome = (cliente != null) ? cliente.getNome() : "Desconhecido";
        System.out.println("Comentário sobre a localização com nota: " + notaLocalizacao +
                " (Restaurante: " + restauranteNome + ", Cliente: " + clienteNome + ")");
    }
}