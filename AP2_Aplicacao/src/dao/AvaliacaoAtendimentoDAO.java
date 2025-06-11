package dao;

import java.util.ArrayList;
import util.AvaliacaoAtendimento; // Importa a classe de entidade

public class AvaliacaoAtendimentoDAO implements BaseDAO { // CORRIGIDO: Implementa BaseDAO

    // Simula o banco de dados em memória
    private static ArrayList<AvaliacaoAtendimento> avaliacoesAtendimentoDB = new ArrayList<>();
    private static int nextIdAvaliacaoAtendimento = 1; // Para simular auto_increment

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoAtendimento) {
            AvaliacaoAtendimento avaliacaoAtendimento = (AvaliacaoAtendimento) obj;
            // Simula o auto_increment do ID
            avaliacaoAtendimento.setIdAvaliacao(nextIdAvaliacaoAtendimento++); // Usa setIdAvaliacao da superclasse Avaliacao
            avaliacoesAtendimentoDB.add(avaliacaoAtendimento);
            System.out.println("Avaliação de Atendimento salva (ID: " + avaliacaoAtendimento.getIdAvaliacao() + ", Nota: " + avaliacaoAtendimento.getNotaAtendimento() + ")");
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAtendimento. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando avaliação de atendimento pelo ID: " + id);
        for (AvaliacaoAtendimento avaliacao : avaliacoesAtendimentoDB) {
            if (avaliacao.getIdAvaliacao() == id) { // Usa getIdAvaliacao
                System.out.println("Avaliação de Atendimento encontrada: " + avaliacao.getNotaAtendimento());
                return avaliacao;
            }
        }
        System.out.println("Avaliação de Atendimento com ID " + id + " não encontrada.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todas as avaliações de atendimento (Lazy Loading)...");
        ArrayList<Object> listaAvaliacoes = new ArrayList<>();
        for (AvaliacaoAtendimento avaliacao : avaliacoesAtendimentoDB) {
            listaAvaliacoes.add(avaliacao);
        }
        System.out.println("Total de avaliações de atendimento listadas: " + listaAvaliacoes.size());
        return listaAvaliacoes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todas as avaliações de atendimento (Eager Loading)...");
        return listarTodosLazyLoading(); // Reutiliza para este exemplo simples
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoAtendimento) {
            AvaliacaoAtendimento avaliacaoAtualizada = (AvaliacaoAtendimento) obj;
            boolean encontrado = false;
            for (int i = 0; i < avaliacoesAtendimentoDB.size(); i++) {
                if (avaliacoesAtendimentoDB.get(i).getIdAvaliacao() == avaliacaoAtualizada.getIdAvaliacao()) {
                    avaliacoesAtendimentoDB.set(i, avaliacaoAtualizada);
                    encontrado = true;
                    System.out.println("Avaliação de Atendimento atualizada (ID: " + avaliacaoAtualizada.getIdAvaliacao() + ", Nova Nota: " + avaliacaoAtualizada.getNotaAtendimento() + ")");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Avaliação de Atendimento com ID " + avaliacaoAtualizada.getIdAvaliacao() + " não encontrada para atualização.");
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAtendimento. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir avaliação de atendimento pelo ID: " + id);
        boolean removido = avaliacoesAtendimentoDB.removeIf(avaliacao -> avaliacao.getIdAvaliacao() == id);
        if (removido) {
            System.out.println("Avaliação de Atendimento com ID " + id + " excluída com sucesso.");
        } else {
            System.out.println("Avaliação de Atendimento com ID " + id + " não encontrada para exclusão.");
        }
    }
}