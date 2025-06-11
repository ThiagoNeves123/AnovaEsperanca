package dao;

import java.util.ArrayList;
import util.AvaliacaoComida; // Importa a classe de entidade

public class AvaliacaoComidaDAO implements BaseDAO { // Implementa BaseDAO

    private static ArrayList<AvaliacaoComida> avaliacoesComidaDB = new ArrayList<>(); //
    private static int nextIdAvaliacaoComida = 1; //

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoComida) {
            AvaliacaoComida avaliacaoComida = (AvaliacaoComida) obj; //
            avaliacaoComida.setIdAvaliacao(nextIdAvaliacaoComida++); //
            avaliacoesComidaDB.add(avaliacaoComida); //
            System.out.println("Avaliação de Comida salva (ID: " + avaliacaoComida.getIdAvaliacao() + ", Nota: " + avaliacaoComida.getNotaComida() + ")"); //
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoComida. Não salvo."); //
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando avaliação de comida pelo ID: " + id); //
        for (AvaliacaoComida avaliacao : avaliacoesComidaDB) { //
            if (avaliacao.getIdAvaliacao() == id) { //
                System.out.println("Avaliação de Comida encontrada: " + avaliacao.getNotaComida()); //
                return avaliacao; //
            }
        }
        System.out.println("Avaliação de Comida com ID " + id + " não encontrada."); //
        return null; //
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todas as avaliações de comida (Lazy Loading)..."); //
        ArrayList<Object> listaAvaliacoes = new ArrayList<>(); //
        for (AvaliacaoComida avaliacao : avaliacoesComidaDB) { //
            listaAvaliacoes.add(avaliacao); //
        }
        System.out.println("Total de avaliações de comida listadas: " + listaAvaliacoes.size()); //
        return listaAvaliacoes; //
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todas as avaliações de comida (Eager Loading)..."); //
        return listarTodosLazyLoading(); //
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoComida) {
            AvaliacaoComida avaliacaoAtualizada = (AvaliacaoComida) obj; //
            boolean encontrado = false; //
            for (int i = 0; i < avaliacoesComidaDB.size(); i++) { //
                if (avaliacoesComidaDB.get(i).getIdAvaliacao() == avaliacaoAtualizada.getIdAvaliacao()) { //
                    avaliacoesComidaDB.set(i, avaliacaoAtualizada); //
                    encontrado = true; //
                    System.out.println("Avaliação de Comida atualizada (ID: " + avaliacaoAtualizada.getIdAvaliacao() + ", Nova Nota: " + avaliacaoAtualizada.getNotaComida() + ")"); //
                    break; //
                }
            }
            if (!encontrado) { //
                System.out.println("Avaliação de Comida com ID " + avaliacaoAtualizada.getIdAvaliacao() + " não encontrada para atualização."); //
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoComida. Não atualizado."); //
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir avaliação de comida pelo ID: " + id); //
        boolean removido = avaliacoesComidaDB.removeIf(avaliacao -> avaliacao.getIdAvaliacao() == id); //
        if (removido) { //
            System.out.println("Avaliação de Comida com ID " + id + " excluída com sucesso."); //
        } else {
            System.out.println("Avaliação de Comida com ID " + id + " não encontrada para exclusão."); //
        }
    }
}