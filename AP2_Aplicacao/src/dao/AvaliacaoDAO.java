package dao;

import java.util.ArrayList;
import util.Avaliacao; // Importa a superclasse Avaliacao

public class AvaliacaoDAO implements BaseDAO { // Implementa BaseDAO

    private static ArrayList<Avaliacao> avaliacoesDB = new ArrayList<>();
    private static int nextIdAvaliacao = 1;

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Avaliacao) {
            Avaliacao avaliacao = (Avaliacao) obj;
            avaliacao.setIdAvaliacao(nextIdAvaliacao++);
            avaliacoesDB.add(avaliacao);
            System.out.println("Avaliação genérica salva (ID: " + avaliacao.getIdAvaliacao() + ")");
        } else {
            System.out.println("Objeto não é uma instância de Avaliacao. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando avaliação genérica pelo ID: " + id);
        for (Avaliacao avaliacao : avaliacoesDB) {
            if (avaliacao.getIdAvaliacao() == id) {
                System.out.println("Avaliação genérica encontrada (ID: " + avaliacao.getIdAvaliacao() + ")");
                return avaliacao;
            }
        }
        System.out.println("Avaliação genérica com ID " + id + " não encontrada.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todas as avaliações genéricas (Lazy Loading)...");
        ArrayList<Object> listaAvaliacoes = new ArrayList<>();
        for (Avaliacao avaliacao : avaliacoesDB) {
            listaAvaliacoes.add(avaliacao);
        }
        System.out.println("Total de avaliações genéricas listadas: " + listaAvaliacoes.size());
        return listaAvaliacoes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todas as avaliações genéricas (Eager Loading)...");
        return listarTodosLazyLoading();
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Avaliacao) {
            Avaliacao avaliacaoAtualizada = (Avaliacao) obj;
            boolean encontrado = false;
            for (int i = 0; i < avaliacoesDB.size(); i++) {
                if (avaliacoesDB.get(i).getIdAvaliacao() == avaliacaoAtualizada.getIdAvaliacao()) {
                    avaliacoesDB.set(i, avaliacaoAtualizada);
                    encontrado = true;
                    System.out.println("Avaliação genérica atualizada (ID: " + avaliacaoAtualizada.getIdAvaliacao() + ")");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Avaliação genérica com ID " + avaliacaoAtualizada.getIdAvaliacao() + " não encontrada para atualização.");
            }
        } else {
            System.out.println("Objeto não é uma instância de Avaliacao. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir avaliação genérica pelo ID: " + id);
        boolean removido = avaliacoesDB.removeIf(avaliacao -> avaliacao.getIdAvaliacao() == id);
        if (removido) {
            System.out.println("Avaliação genérica com ID " + id + " excluída com sucesso.");
        } else {
            System.out.println("Avaliação genérica com ID " + id + " não encontrada para exclusão.");
        }
    }
}