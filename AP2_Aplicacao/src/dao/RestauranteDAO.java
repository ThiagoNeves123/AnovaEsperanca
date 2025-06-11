package dao;

import java.util.ArrayList;
import util.Restaurante;
import util.Local; // Importa a classe Local

public class RestauranteDAO implements BaseDAO {

    private static ArrayList<Restaurante> restaurantesDB = new ArrayList<>();
    private static int nextIdRestaurante = 1;

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Restaurante) {
            Restaurante restaurante = (Restaurante) obj;
            restaurante.setIdrestaurante(nextIdRestaurante++);
            restaurantesDB.add(restaurante);
            System.out.println("Restaurante salvo (ID: " + restaurante.getIdrestaurante() + ", Nome: " + restaurante.getNome() + ", Local: " + (restaurante.getLocal() != null ? restaurante.getLocal().getCidade() : "N/A") + ")");
        } else {
            System.out.println("Objeto não é uma instância de Restaurante. Não salvo.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando restaurante pelo ID: " + id);
        for (Restaurante restaurante : restaurantesDB) {
            if (restaurante.getIdrestaurante() == id) {
                System.out.println("Restaurante encontrado: " + restaurante.getNome());
                return restaurante;
            }
        }
        System.out.println("Restaurante com ID " + id + " não encontrado.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todos os restaurantes (Lazy Loading)...");
        ArrayList<Object> listaRestaurantes = new ArrayList<>();
        for (Restaurante restaurante : restaurantesDB) {
            listaRestaurantes.add(restaurante);
        }
        System.out.println("Total de restaurantes listados: " + listaRestaurantes.size());
        return listaRestaurantes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todos os restaurantes (Eager Loading)...");
        return listarTodosLazyLoading(); // Reutiliza para este exemplo simples
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Restaurante) {
            Restaurante restauranteAtualizado = (Restaurante) obj;
            boolean encontrado = false;
            for (int i = 0; i < restaurantesDB.size(); i++) {
                if (restaurantesDB.get(i).getIdrestaurante() == restauranteAtualizado.getIdrestaurante()) {
                    restaurantesDB.set(i, restauranteAtualizado);
                    encontrado = true;
                    System.out.println("Restaurante atualizado (ID: " + restauranteAtualizado.getIdrestaurante() + ", Novo Nome: " + restauranteAtualizado.getNome() + ", Novo Local: " + (restauranteAtualizado.getLocal() != null ? restauranteAtualizado.getLocal().getCidade() : "N/A") + ")");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Restaurante com ID " + restauranteAtualizado.getIdrestaurante() + " não encontrado para atualização.");
            }
        } else {
            System.out.println("Objeto não é uma instância de Restaurante. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir restaurante pelo ID: " + id);
        boolean removido = restaurantesDB.removeIf(restaurante -> restaurante.getIdrestaurante() == id);
        if (removido) {
            System.out.println("Restaurante com ID " + id + " excluído com sucesso.");
        } else {
            System.out.println("Restaurante com ID " + id + " não encontrado para exclusão.");
        }
    }

    public Restaurante buscarPorNome(String nome) {
        System.out.println("Buscando restaurante pelo nome: " + nome);
        for (Restaurante restaurante : restaurantesDB) {
            if (restaurante.getNome() != null && restaurante.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Restaurante encontrado por nome: " + restaurante.getNome());
                return restaurante;
            }
        }
        System.out.println("Restaurante com nome '" + nome + "' não encontrado.");
        return null;
    }

    // Método específico para buscar restaurantes por Local (cidade)
    public ArrayList<Restaurante> buscarPorLocalCidade(String cidade) {
        ArrayList<Restaurante> restaurantesEncontrados = new ArrayList<>();
        System.out.println("Buscando restaurantes na cidade: " + cidade);
        for (Restaurante restaurante : restaurantesDB) {
            if (restaurante.getLocal() != null && restaurante.getLocal().getCidade() != null && restaurante.getLocal().getCidade().equalsIgnoreCase(cidade)) {
                restaurantesEncontrados.add(restaurante);
            }
        }
        System.out.println("Total de restaurantes encontrados na cidade '" + cidade + "': " + restaurantesEncontrados.size());
        return restaurantesEncontrados;
    }
}