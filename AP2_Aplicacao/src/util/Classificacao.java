package util;

import java.sql.Date; // Para a data da classificação

public class Classificacao {

    private int idClassificacao; // ID para persistência no banco de dados
    private Restaurante restaurante;
    private Cliente cliente;
    private float notaFinal; // Atributo para armazenar a nota final calculada
    private Date dataClassificacao; // Data em que a classificação foi realizada

    // Atributos privados para guardar as instâncias das avaliações usadas no cálculo
    private AvaliacaoComida avaliacaoComida;
    private AvaliacaoAmbiente avaliacaoAmbiente;
    private AvaliacaoAtendimento avaliacaoAtendimento;
    private AvaliacaoLocalizacao avaliacaoLocalizacao;

    /**
     * Construtor para criar uma instância de Classificacao para CALCULAR a nota final.
     * Recebe o Restaurante, o Cliente e as instâncias das avaliações.
     * As avaliações podem ser null se não houver uma para uma determinada categoria.
     */
    public Classificacao(Restaurante restaurante,
                         Cliente cliente,
                         AvaliacaoComida avaliacaoComida,
                         AvaliacaoAmbiente avaliacaoAmbiente,
                         AvaliacaoAtendimento avaliacaoAtendimento,
                         AvaliacaoLocalizacao avaliacaoLocalizacao) {
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.avaliacaoComida = avaliacaoComida;
        this.avaliacaoAmbiente = avaliacaoAmbiente;
        this.avaliacaoAtendimento = avaliacaoAtendimento;
        this.avaliacaoLocalizacao = avaliacaoLocalizacao;
        // A notaFinal e dataClassificacao não são definidas aqui, serão calculadas/atribuídas posteriormente.
    }

    /**
     * Construtor para criar uma instância de Classificacao para PERSISTÊNCIA no banco de dados.
     * Recebe todos os dados necessários, incluindo o ID (se já existir) e a nota final calculada.
     */
    public Classificacao(int idClassificacao,
                         Restaurante restaurante,
                         Cliente cliente,
                         float notaFinal,
                         Date dataClassificacao) {
        this.idClassificacao = idClassificacao;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.notaFinal = notaFinal;
        this.dataClassificacao = dataClassificacao;
        // As avaliações individuais (comida, ambiente, etc.) não são necessárias neste construtor
        // pois a notaFinal já foi calculada.
    }

    // Construtor vazio (opcional, mas pode ser útil para frameworks)
    public Classificacao() {
    }

    /**
     * Calcula a classificação média baseada nas avaliações fornecidas.
     * Ignora avaliações que são null.
     * @return A nota média calculada, ou 0.0f se nenhuma avaliação válida for fornecida.
     */
    public float calcularClassificacao() {
        float somaNotas = 0.0f;
        int numeroDeAvaliacoesValidas = 0;

        if (avaliacaoComida != null) {
            somaNotas += avaliacaoComida.getNotaComida();
            numeroDeAvaliacoesValidas++;
        }
        if (avaliacaoAmbiente != null) {
            somaNotas += avaliacaoAmbiente.getNotaAmbiente();
            numeroDeAvaliacoesValidas++;
        }
        if (avaliacaoAtendimento != null) {
            somaNotas += avaliacaoAtendimento.getNotaAtendimento();
            numeroDeAvaliacoesValidas++;
        }
        if (avaliacaoLocalizacao != null) {
            somaNotas += avaliacaoLocalizacao.getNotaLocalizacao();
            numeroDeAvaliacoesValidas++;
        }

        if (numeroDeAvaliacoesValidas > 0) {
            return somaNotas / numeroDeAvaliacoesValidas;
        } else {
            return 0.0f;
        }
    }

    // --- Getters e Setters para todos os atributos ---

    public int getIdClassificacao() {
        return idClassificacao;
    }

    public void setIdClassificacao(int idClassificacao) {
        this.idClassificacao = idClassificacao;
    }

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

    public float getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Date getDataClassificacao() {
        return dataClassificacao;
    }

    public void setDataClassificacao(Date dataClassificacao) {
        this.dataClassificacao = dataClassificacao;
    }

    public AvaliacaoComida getAvaliacaoComida() {
        return avaliacaoComida;
    }

    public void setAvaliacaoComida(AvaliacaoComida avaliacaoComida) {
        this.avaliacaoComida = avaliacaoComida;
    }

    public AvaliacaoAmbiente getAvaliacaoAmbiente() {
        return avaliacaoAmbiente;
    }

    public void setAvaliacaoAmbiente(AvaliacaoAmbiente avaliacaoAmbiente) {
        this.avaliacaoAmbiente = avaliacaoAmbiente;
    }

    public AvaliacaoAtendimento getAvaliacaoAtendimento() {
        return avaliacaoAtendimento;
    }

    public void setAvaliacaoAtendimento(AvaliacaoAtendimento avaliacaoAtendimento) {
        this.avaliacaoAtendimento = avaliacaoAtendimento;
    }

    public AvaliacaoLocalizacao getAvaliacaoLocalizacao() {
        return avaliacaoLocalizacao;
    }

    public void setAvaliacaoLocalizacao(AvaliacaoLocalizacao avaliacaoLocalizacao) {
        this.avaliacaoLocalizacao = avaliacaoLocalizacao;
    }
}