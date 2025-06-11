package util;

public abstract class Avaliacao {
    private int idAvaliacao; // ID único para cada avaliação no banco de dados

    // Construtor padrão
    public Avaliacao() {
    }

    // Método para obter o ID da avaliação
    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    // Método para definir o ID da avaliação (útil após o DB gerar o ID, por exemplo)
    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    // Método abstrato que subclasses deverão implementar para fornecer um comentário específico
    protected abstract void comentar();

    // Nota: A classe Avaliacao não tem um 'Restaurante' ou 'Cliente' diretamente,
    // pois são as subclasses específicas (AvaliacaoAmbiente, AvaliacaoComida, etc.)
    // que terão esses relacionamentos, já que cada avaliação se refere a um restaurante
    // e foi feita por um cliente. O 'idAvaliacao' é o suficiente aqui.
}