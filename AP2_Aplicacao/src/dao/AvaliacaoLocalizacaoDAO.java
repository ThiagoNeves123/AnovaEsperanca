package dao;

import java.util.ArrayList;
import util.AvaliacaoLocalizacao; // Assumindo que você tem esta classe

public class AvaliacaoLocalizacaoDAO implements BaseDAO { // Implementa BaseDAO

    private static ArrayList<AvaliacaoLocalizacao> avaliacoesLocalizacaoDB = new ArrayList<>();
    private static int nextIdAvaliacaoLocalizacao = 1;

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoLocalizacao) {
            AvaliacaoLocalizacao avaliacaoLocalizacao = (AvaliacaoLocalizacao) obj;
            avaliacaoLocalizacao.setIdAvaliacao(nextIdAvaliacaoLocalizacao++);
            avaliacoesLocalizacaoDB.add(avaliacaoLocalizacao);
            System.out.println("Avaliação de Localização salva (ID: " + avaliacaoLocalizacao.getIdAvaliacao() + ", Nota: " + avaliacaoLocalizacao.getNotaLocalizacao() + ")");
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoLocalizacao. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando avaliação de localização pelo ID: " + id);
        for (AvaliacaoLocalizacao avaliacao : avaliacoesLocalizacaoDB) {
            if (avaliacao.getIdAvaliacao() == id) {
                System.out.println("Avaliação de Localização encontrada: " + avaliacao.getNotaLocalizacao());
                return avaliacao;
            }
        }
        System.out.println("Avaliação de Localização com ID " + id + " não encontrada.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todas as avaliações de localização (Lazy Loading)...");
        ArrayList<Object> listaAvaliacoes = new ArrayList<>();
        for (AvaliacaoLocalizacao avaliacao : avaliacoesLocalizacaoDB) {
            listaAvaliacoes.add(avaliacao);
        }
        System.out.println("Total de avaliações de localização listadas: " + listaAvaliacoes.size());
        return listaAvaliacoes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todas as avaliações de localização (Eager Loading)...");
        return listarTodosLazyLoading();
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoLocalizacao) {
            AvaliacaoLocalizacao avaliacaoAtualizada = (AvaliacaoLocalizacao) obj;
            boolean encontrado = false;
            for (int i = 0; i < avaliacoesLocalizacaoDB.size(); i++) {
                if (avaliacoesLocalizacaoDB.get(i).getIdAvaliacao() == avaliacaoAtualizada.getIdAvaliacao()) {
                    avaliacoesLocalizacaoDB.set(i, avaliacaoAtualizada);
                    encontrado = true;
                    System.out.println("Avaliação de Localização atualizada (ID: " + avaliacaoAtualizada.getIdAvaliacao() + ", Nova Nota: " + avaliacaoAtualizada.getNotaLocalizacao() + ")");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Avaliação de Localização com ID " + avaliacaoAtualizada.getIdAvaliacao() + " não encontrada para atualização.");
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoLocalizacao. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir avaliação de localização pelo ID: " + id);
        boolean removido = avaliacoesLocalizacaoDB.removeIf(avaliacao -> avaliacao.getIdAvaliacao() == id);
        if (removido) {
            System.out.println("Avaliação de Localização com ID " + id + " excluída com sucesso.");
        } else {
            System.out.println("Avaliação de Localização com ID " + id + " não encontrada para exclusão.");
        }
    }
}