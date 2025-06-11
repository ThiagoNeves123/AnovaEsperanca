package util;

public abstract class Avaliacao {
    private int idAvaliacao;
    protected float nota;

    public Avaliacao() {
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    // Adicionado o método setNota para permitir que as subclasses definam a nota
    public void setNota(float nota) {
        this.nota = nota;
    }

    protected abstract void comentar();

    public float getNota() {
        return nota;
    }
}