package dao;

import java.util.ArrayList;
import util.Classificacao; // Importa a classe Classificacao

public class ClassificacaoDAO implements BaseDAO { // Implementa BaseDAO

    private static ArrayList<Classificacao> classificacoesDB = new ArrayList<>();
    private static int nextIdClassificacao = 1; // Para simular auto_increment para Classificacao

    // Nota: A classe Classificacao não tem um ID intrínseco.
    // Para fins de DAO, vamos gerar um ID para ela aqui.
    // Em um cenário real, talvez a Classificacao não seria salva diretamente,
    // mas sim calculada a partir das avaliações existentes.
    // Para manter a conformidade com BaseDAO, adicionaremos um ID aqui.

    // A classe Classificacao precisa de um setId/getId para ser manipulável por um DAO.
    // Se a Classificacao não tiver ID, o DAO não faz sentido para as operações de buscar/atualizar/excluir por ID.
    // Para simplificar, vou assumir que você pode adicionar um idClassificacao na classe Classificacao
    // ou que este DAO é mais conceitual e não fará CRUD completo com IDs de Classificacao.

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Classificacao) {
            Classificacao classificacao = (Classificacao) obj;
            // Para Classificacao, precisaria de um setter de ID nela
            // classificacao.setIdClassificacao(nextIdClassificacao++);
            classificacoesDB.add(classificacao);
            System.out.println("Classificação salva (simulada).");
        } else {
            System.out.println("Objeto não é uma instância de Classificacao. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando classificação pelo ID: " + id);
        // Para buscar Classificacao por ID, ela precisaria de um campo idClassificacao
        // e um método getIdClassificacao().
        // Como não há no Classificacao.java fornecido, este método seria mais complexo
        // ou precisaria de alteração em Classificacao.java.
        // Simulando apenas...
        if (!classificacoesDB.isEmpty() && id > 0 && id <= classificacoesDB.size()) {
            System.out.println("Classificação encontrada (simulada).");
            return classificacoesDB.get(id - 1); // Apenas um exemplo simples de busca
        }
        System.out.println("Classificação com ID " + id + " não encontrada.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todas as classificações (Lazy Loading)...");
        ArrayList<Object> listaClassificacoes = new ArrayList<>();
        for (Classificacao classificacao : classificacoesDB) {
            listaClassificacoes.add(classificacao);
        }
        System.out.println("Total de classificações listadas: " + listaClassificacoes.size());
        return listaClassificacoes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todas as classificações (Eager Loading)...");
        return listarTodosLazyLoading();
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Classificacao) {
            Classificacao classificacaoAtualizada = (Classificacao) obj;
            // Para atualizar por ID, Classificacao precisaria de um campo de ID.
            // Aqui, apenas um exemplo de atualização sem ID específico.
            System.out.println("Atualizando classificação (simulada).");
        } else {
            System.out.println("Objeto não é uma instância de Classificacao. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir classificação pelo ID: " + id);
        // Excluir por ID também exigiria um campo ID em Classificacao.
        System.out.println("Exclusão de classificação (simulada) para ID: " + id);
    }
}