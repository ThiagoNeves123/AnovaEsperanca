package dao;

import java.util.ArrayList;
import util.AvaliacaoAmbiente; // Importa a classe de entidade

public class AvaliacaoAmbienteDAO implements BaseDAO { // Implementa BaseDAO

    // Simula o banco de dados em memória
    private static ArrayList<AvaliacaoAmbiente> avaliacoesAmbienteDB = new ArrayList<>();
    private static int nextIdAvaliacaoAmbiente = 1; // Para simular auto_increment

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoAmbiente) {
            AvaliacaoAmbiente avaliacaoAmbiente = (AvaliacaoAmbiente) obj;
            // Simula o auto_increment do ID
            avaliacaoAmbiente.setIdAvaliacao(nextIdAvaliacaoAmbiente++); // Usa setIdAvaliacao da superclasse Avaliacao
            avaliacoesAmbienteDB.add(avaliacaoAmbiente);
            System.out.println("Avaliação de Ambiente salva (ID: " + avaliacaoAmbiente.getIdAvaliacao() + ", Nota: " + avaliacaoAmbiente.getNotaAmbiente() + ")");
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAmbiente. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando avaliação de ambiente pelo ID: " + id);
        for (AvaliacaoAmbiente avaliacao : avaliacoesAmbienteDB) {
            if (avaliacao.getIdAvaliacao() == id) { // Usa getIdAvaliacao
                System.out.println("Avaliação de Ambiente encontrada: " + avaliacao.getNotaAmbiente());
                return avaliacao;
            }
        }
        System.out.println("Avaliação de Ambiente com ID " + id + " não encontrada.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todas as avaliações de ambiente (Lazy Loading)...");
        ArrayList<Object> listaAvaliacoes = new ArrayList<>();
        for (AvaliacaoAmbiente avaliacao : avaliacoesAmbienteDB) {
            listaAvaliacoes.add(avaliacao);
        }
        System.out.println("Total de avaliações de ambiente listadas: " + listaAvaliacoes.size());
        return listaAvaliacoes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todas as avaliações de ambiente (Eager Loading)...");
        return listarTodosLazyLoading(); // Reutiliza para este exemplo simples
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoAmbiente) {
            AvaliacaoAmbiente avaliacaoAtualizada = (AvaliacaoAmbiente) obj;
            boolean encontrado = false;
            for (int i = 0; i < avaliacoesAmbienteDB.size(); i++) {
                if (avaliacoesAmbienteDB.get(i).getIdAvaliacao() == avaliacaoAtualizada.getIdAvaliacao()) {
                    avaliacoesAmbienteDB.set(i, avaliacaoAtualizada);
                    encontrado = true;
                    System.out.println("Avaliação de Ambiente atualizada (ID: " + avaliacaoAtualizada.getIdAvaliacao() + ", Nova Nota: " + avaliacaoAtualizada.getNotaAmbiente() + ")");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Avaliação de Ambiente com ID " + avaliacaoAtualizada.getIdAvaliacao() + " não encontrada para atualização.");
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAmbiente. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir avaliação de ambiente pelo ID: " + id);
        boolean removido = avaliacoesAmbienteDB.removeIf(avaliacao -> avaliacao.getIdAvaliacao() == id); // Correção do operador de comparação
        if (removido) {
            System.out.println("Avaliação de Ambiente com ID " + id + " excluída com sucesso.");
        } else {
            System.out.println("Avaliação de Ambiente com ID " + id + " não encontrada para exclusão.");
        }
    }
}