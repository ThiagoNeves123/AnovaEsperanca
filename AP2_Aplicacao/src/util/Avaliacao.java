package util;

// Esta é uma classe abstrata, pois 'comentar' é abstrato
public abstract class Avaliacao {
    private int idAvaliacao; // <--- Adicionado para suportar os DAOs
    protected float nota;

    // Construtor padrão, ou construtor que inicialize idAvaliacao
    public Avaliacao() {
        // Pode ser deixado vazio ou inicializar algo se necessário
    }

    // Método getter para o ID da avaliação
    public int getIdAvaliacao() { // <--- Adicionado
        return idAvaliacao;
    }

    // Método setter para o ID da avaliação
    public void setIdAvaliacao(int idAvaliacao) { // <--- Adicionado
        this.idAvaliacao = idAvaliacao;
    }

    protected abstract void comentar(); // Mantém o método abstrato

    public float getNota() {
        return nota;
    }
}