package util;

// Não precisamos importar Restaurante e Cliente aqui, pois a Classificacao
// apenas usa as instâncias de Avaliação já completas.

public class Classificacao {

    // Atributos privados para guardar as instâncias das avaliações
    // Eles não devem ser estáticos para que cada objeto Classificacao represente
    // uma classificação específica (ex: de um restaurante, ou de um cliente).
    private AvaliacaoComida avaliacaoComida;
    private AvaliacaoAmbiente avaliacaoAmbiente;
    private AvaliacaoAtendimento avaliacaoAtendimento;
    private AvaliacaoLocalizacao avaliacaoLocalizacao;

    /**
     * Construtor da classe Classificacao.
     * Recebe instâncias das avaliações já criadas e, idealmente, preenchidas com dados do DB.
     * As avaliações podem ser null se não houver uma para uma determinada categoria.
     */
    public Classificacao(AvaliacaoComida avaliacaoComida,
                         AvaliacaoAmbiente avaliacaoAmbiente,
                         AvaliacaoAtendimento avaliacaoAtendimento,
                         AvaliacaoLocalizacao avaliacaoLocalizacao) {
        this.avaliacaoComida = avaliacaoComida;
        this.avaliacaoAmbiente = avaliacaoAmbiente;
        this.avaliacaoAtendimento = avaliacaoAtendimento;
        this.avaliacaoLocalizacao = avaliacaoLocalizacao;
    }

    /**
     * Calcula a classificação média com base nas avaliações fornecidas.
     * Apenas inclui na média as avaliações que não são null.
     *
     * @return A nota média das avaliações, ou 0.0f se nenhuma avaliação for fornecida.
     */
    public float calcularClassificacao() {
        float somaNotas = 0.0f;
        int numeroDeAvaliacoesValidas = 0;

        // Verifica se a avaliação de Localizacao existe e adiciona sua nota
        if (avaliacaoLocalizacao != null) {
            somaNotas += avaliacaoLocalizacao.getNotaLocalizacao(); // Assumindo getNotaLocalizacao()
            numeroDeAvaliacoesValidas++;
        }

        // Verifica se a avaliação de Ambiente existe e adiciona sua nota
        if (avaliacaoAmbiente != null) {
            somaNotas += avaliacaoAmbiente.getNotaAmbiente(); // Assumindo getNotaAmbiente()
            numeroDeAvaliacoesValidas++;
        }

        // Verifica se a avaliação de Comida existe e adiciona sua nota
        if (avaliacaoComida != null) {
            somaNotas += avaliacaoComida.getNotaComida(); // Assumindo getNotaComida()
            numeroDeAvaliacoesValidas++;
        }

        // Verifica se a avaliação de Atendimento existe e adiciona sua nota
        if (avaliacaoAtendimento != null) {
            somaNotas += avaliacaoAtendimento.getNotaAtendimento(); // Assumindo getNotaAtendimento()
            numeroDeAvaliacoesValidas++;
        }

        // Evita divisão por zero
        if (numeroDeAvaliacoesValidas > 0) {
            return somaNotas / numeroDeAvaliacoesValidas;
        } else {
            return 0.0f; // Retorna 0.0 se não houver avaliações para calcular a média
        }
    }

    // --- Métodos Getters para acessar as avaliações individuais (opcional, mas bom para encapsulamento) ---
    public AvaliacaoComida getAvaliacaoComida() {
        return avaliacaoComida;
    }

    public AvaliacaoAmbiente getAvaliacaoAmbiente() {
        return avaliacaoAmbiente;
    }

    public AvaliacaoAtendimento getAvaliacaoAtendimento() {
        return avaliacaoAtendimento;
    }

    public AvaliacaoLocalizacao getAvaliacaoLocalizacao() {
        return avaliacaoLocalizacao;
    }
}