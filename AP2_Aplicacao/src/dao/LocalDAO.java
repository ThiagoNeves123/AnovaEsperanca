package dao;

import java.util.ArrayList;
import util.Local; // Importa a classe Local

public class LocalDAO implements BaseDAO {

    private static ArrayList<Local> locaisDB = new ArrayList<>();
    private static int nextIdLocal = 1;

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Local) {
            Local local = (Local) obj;
            local.setIdLocal(nextIdLocal++);
            locaisDB.add(local);
            System.out.println("Local salvo (ID: " + local.getIdLocal() + ", Cidade: " + local.getCidade() + ")");
        } else {
            System.out.println("Objeto não é uma instância de Local. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando local pelo ID: " + id);
        for (Local local : locaisDB) {
            if (local.getIdLocal() == id) {
                System.out.println("Local encontrado: " + local.getCidade() + ", " + local.getRua());
                return local;
            }
        }
        System.out.println("Local com ID " + id + " não encontrado.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todos os locais (Lazy Loading)...");
        ArrayList<Object> listaLocais = new ArrayList<>();
        for (Local local : locaisDB) {
            listaLocais.add(local);
        }
        System.out.println("Total de locais listados: " + listaLocais.size());
        return listaLocais;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todos os locais (Eager Loading)...");
        return listarTodosLazyLoading(); // Para este exemplo, são iguais
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Local) {
            Local localAtualizado = (Local) obj;
            boolean encontrado = false;
            for (int i = 0; i < locaisDB.size(); i++) {
                if (locaisDB.get(i).getIdLocal() == localAtualizado.getIdLocal()) {
                    locaisDB.set(i, localAtualizado);
                    encontrado = true;
                    System.out.println("Local atualizado (ID: " + localAtualizado.getIdLocal() + ", Nova Cidade: " + localAtualizado.getCidade() + ")");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Local com ID " + localAtualizado.getIdLocal() + " não encontrado para atualização.");
            }
        } else {
            System.out.println("Objeto não é uma instância de Local. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir local pelo ID: " + id);
        boolean removido = locaisDB.removeIf(local -> local.getIdLocal() == id);
        if (removido) {
            System.out.println("Local com ID " + id + " excluído com sucesso.");
        } else {
            System.out.println("Local com ID " + id + " não encontrado para exclusão.");
        }
    }

    // Métodos específicos para LocalDAO (ex: buscar por cidade)
    public ArrayList<Local> buscarPorCidade(String cidade) {
        ArrayList<Local> locaisEncontrados = new ArrayList<>();
        for (Local local : locaisDB) {
            if (local.getCidade() != null && local.getCidade().equalsIgnoreCase(cidade)) {
                locaisEncontrados.add(local);
            }
        }
        System.out.println("Locais encontrados na cidade '" + cidade + "': " + locaisEncontrados.size());
        return locaisEncontrados;
    }
}