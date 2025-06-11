package util;

public class AvaliacaoAmbiente extends Avaliacao { // Estende Avaliacao
    private float notaAmbiente; //

    public AvaliacaoAmbiente(float notaAmbiente) { //
        super(); //
        this.notaAmbiente = notaAmbiente; //
    }

    // Construtor com ID para uso do DAO
    public AvaliacaoAmbiente(int idAvaliacao, float notaAmbiente) {
        super();
        setIdAvaliacao(idAvaliacao); // Define o ID herdado de Avaliacao
        this.notaAmbiente = notaAmbiente;
    }

    public float getNotaAmbiente() { //
        return notaAmbiente; //
    }

    public void setNotaAmbiente(float notaAmbiente) {
        this.notaAmbiente = notaAmbiente;
    }

    @Override
    protected void comentar() { //
        System.out.println("Comentário sobre o ambiente com nota: " + notaAmbiente);
    }
}