package util;

import util.Cliente;
import util.Restaurante;

public class AvaliacaoAmbiente extends Avaliacao {
    private float notaAmbiente;
    private Restaurante restaurante; // Relacionamento
    private Cliente cliente;         // Relacionamento

    // Construtor para criar uma nova avaliação (ID gerado pelo DB)
    public AvaliacaoAmbiente(float notaAmbiente) {
        super(); // Chama o construtor da superclasse Avaliacao
        this.notaAmbiente = notaAmbiente;

    }

    // Construtor completo com ID (útil para carregar do DB)
    public AvaliacaoAmbiente(int idAvaliacao, float notaAmbiente, Restaurante restaurante, Cliente cliente) {
        super();
        setIdAvaliacao(idAvaliacao); // Define o ID herdado da superclasse
        this.notaAmbiente = notaAmbiente;
        this.restaurante = restaurante;
        this.cliente = cliente;
    }

    // O construtor original 'public AvaliacaoAmbiente(float notaAmbiente)'
    // pode ser removido ou alterado para chamar o construtor completo
    // public AvaliacaoAmbiente(float notaAmbiente) {
    //    this(0, notaAmbiente, null, null); // Exemplo: se precisar de um construtor simples para testes sem FKs
    // }


    public float getNotaAmbiente() {
        return notaAmbiente;
    }

    public void setNotaAmbiente(float notaAmbiente) {
        this.notaAmbiente = notaAmbiente;
    }

    // --- Getters e Setters para os novos atributos de relacionamento ---
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    protected void comentar() {
        System.out.println("Comentário sobre o ambiente com nota: " + notaAmbiente);
        if (restaurante != null) {
            System.out.println("No restaurante: " + restaurante.getNome());
        }
        if (cliente != null) {
            System.out.println("Feito por: " + cliente.getNome());
        }
    }
}