package util;

public class AvaliacaoAmbiente extends Avaliacao {
    private float notaAmbiente;

    public AvaliacaoAmbiente(float notaAmbiente) {
        super();
        this.notaAmbiente = notaAmbiente;
    }

    public AvaliacaoAmbiente(int idAvaliacao, float notaAmbiente) {
        super();
        setIdAvaliacao(idAvaliacao);
        this.notaAmbiente = notaAmbiente;
    }

    public float getNotaAmbiente() {
        return notaAmbiente;
    }

    public void setNotaAmbiente(float notaAmbiente) {
        this.notaAmbiente = notaAmbiente;
    }

    @Override
    protected void comentar() { //
        System.out.println("Comentário sobre o ambiente com nota: " + notaAmbiente);
    }
}