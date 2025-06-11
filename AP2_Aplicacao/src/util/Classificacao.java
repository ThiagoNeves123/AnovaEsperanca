package util;

public class Classificacao {
    public static AvaliacaoComida avaliacaoComida;
    public static AvaliacaoAmbiente avaliacaoAmbiente;
    public static AvaliacaoAtendimento avaliacaoAtendimento;
    public static AvaliacaoLocalizacao avaliacaoLocalizacao;

    public Classificacao(float avaliacaoLocalizacao, float avaliacaoAmbiente, float avaliacaoComida, float avaliacaoAtendimento) {
        this.avaliacaoLocalizacao = new AvaliacaoLocalizacao(avaliacaoLocalizacao);
        this.avaliacaoAmbiente = new AvaliacaoAmbiente(avaliacaoAmbiente);
        this.avaliacaoComida = new AvaliacaoComida(avaliacaoComida);
        this.avaliacaoAtendimento = new AvaliacaoAtendimento(avaliacaoAtendimento);
    }

    public static float calcularClassificacao() {
        return (avaliacaoLocalizacao.getNota() + avaliacaoAmbiente.getNotaAmbiente() + avaliacaoComida.getNota() + avaliacaoAtendimento.getNotaAtendimento()) / 4;
    }
}