package util;

public class AvaliacaoAtendimento extends Avaliacao { // Estende Avaliacao
    private float notaAtendimento; //

    public AvaliacaoAtendimento(float notaAtendimento) { //
        super(); //
        this.notaAtendimento = notaAtendimento; //
    }

    // Construtor com ID para uso do DAO
    public AvaliacaoAtendimento(int idAvaliacao, float notaAtendimento) {
        super();
        setIdAvaliacao(idAvaliacao); // Define o ID herdado de Avaliacao
        this.notaAtendimento = notaAtendimento;
    }

    public float getNotaAtendimento() { //
        return notaAtendimento; //
    }

    public void setNotaAtendimento(float notaAtendimento) {
        this.notaAtendimento = notaAtendimento;
    }

    @Override
    protected void comentar() { //
        System.out.println("Comentário sobre o atendimento com nota: " + notaAtendimento);
    }
}