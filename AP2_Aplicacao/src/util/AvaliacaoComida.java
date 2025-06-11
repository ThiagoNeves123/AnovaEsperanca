package util;

public class AvaliacaoComida extends Avaliacao {
    private float notaComida;

    public AvaliacaoComida(float notaComida) {
        super();
        this.notaComida = notaComida;
    }

    public AvaliacaoComida(int idAvaliacao, float notaComida) {
        super();
        setIdAvaliacao(idAvaliacao);
        this.notaComida = notaComida;
    }

    public float getNotaComida() {
        return notaComida;
    }

    public void setNotaComida(float notaComida) {
        this.notaComida = notaComida;
    }

    // Método para ser consistente com a chamada em Classificacao.java
    public float getNota() {
        return notaComida;
    }

    @Override
    protected void comentar() {
        System.out.println("Comentário sobre a comida com nota: " + notaComida);
    }
}