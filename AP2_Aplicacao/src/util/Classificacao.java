package util;

// Assumindo que AvaliacaoComida e AvaliacaoLocalizacao existem e estão no pacote 'util'
// import util.AvaliacaoComida;
// import util.AvaliacaoLocalizacao;

public class Classificacao {
    public static AvaliacaoComida avaliacaoComida;
    public static AvaliacaoAmbiente avaliacaoAmbiente;
    public static AvaliacaoAtendimento avaliacaoAtendimento; // CORRIGIDO: Referência para AvaliacaoAtendimento
    public static AvaliacaoLocalizacao avaliacaoLocalizacao;

    public Classificacao(float avaliacaoLocalizacao, float avaliacaoAmbiente, float avaliacaoComida, float avaliacaoAtendimento) {
        this.avaliacaoLocalizacao = new AvaliacaoLocalizacao(avaliacaoLocalizacao);
        this.avaliacaoAmbiente = new AvaliacaoAmbiente(avaliacaoAmbiente);
        this.avaliacaoComida = new AvaliacaoComida(avaliacaoComida);
        this.avaliacaoAtendimento = new AvaliacaoAtendimento(avaliacaoAtendimento); // CORRIGIDO: Instancia AvaliacaoAtendimento
    }

    public static float calcularClassificacao() {
        // CORRIGIDO: Usando getNotaAtendimento() para AvaliacaoAtendimento
        return (avaliacaoLocalizacao.getNota() + avaliacaoAmbiente.getNotaAmbiente() + // Assumindo getNota para Localizacao e Comida, e getNotaAmbiente para Ambiente
                avaliacaoComida.getNota() + avaliacaoAtendimento.getNotaAtendimento()) / 4;
    }
}