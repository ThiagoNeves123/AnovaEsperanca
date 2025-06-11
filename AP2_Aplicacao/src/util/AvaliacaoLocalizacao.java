package util;

public class AvaliacaoLocalizacao extends Avaliacao {
    private float notaLocalizacao;

    public AvaliacaoLocalizacao(float notaLocalizacao) {
        super();
        this.notaLocalizacao = notaLocalizacao;
    }

    public AvaliacaoLocalizacao(int idAvaliacao, float notaLocalizacao) {
        super();
        setIdAvaliacao(idAvaliacao);
        this.notaLocalizacao = notaLocalizacao;
    }

    public float getNotaLocalizacao() {
        return notaLocalizacao;
    }

    public void setNotaLocalizacao(float notaLocalizacao) {
        this.notaLocalizacao = notaLocalizacao;
    }

    // Método para ser consistente com a chamada em Classificacao.java
    public float getNota() {
        return notaLocalizacao;
    }

    @Override
    protected void comentar() {
        System.out.println("Comentário sobre a localização com nota: " + notaLocalizacao);
    }
}