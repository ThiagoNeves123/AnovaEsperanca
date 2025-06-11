package dao;

import java.util.ArrayList;
import util.Cliente; // Importa a classe Cliente

public class ClienteDAO implements BaseDAO { // Implementa BaseDAO

    // Simula o banco de dados em memória
    private static ArrayList<Cliente> clientesDB = new ArrayList<>(); //
    private static int nextIdCliente = 1; // Para simular auto_increment

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Cliente) {
            Cliente cliente = (Cliente) obj; //
            // Simula o auto_increment do ID
            cliente.setIdcliente(nextIdCliente++); // Assumindo que setIdcliente existe
            clientesDB.add(cliente); //
            System.out.println("Cliente salvo (ID: " + cliente.getIdcliente() + ", Nome: " + cliente.getNome() + ")"); //
        } else {
            System.out.println("Objeto não é uma instância de Cliente. Não salvo."); //
        }
    }

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando cliente pelo ID: " + id); //
        for (Cliente cliente : clientesDB) { //
            if (cliente.getIdcliente() == id) { //
                System.out.println("Cliente encontrado: " + cliente.getNome()); //
                return cliente; //
            }
        }
        System.out.println("Cliente com ID " + id + " não encontrado."); //
        return null; //
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        System.out.println("Listando todos os clientes (Lazy Loading - apenas id e nome)..."); //
        ArrayList<Object> listaClientes = new ArrayList<>(); //
        for (Cliente cliente : clientesDB) { //
            listaClientes.add(cliente); //
        }
        System.out.println("Total de clientes listados: " + listaClientes.size()); //
        return listaClientes; //
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        System.out.println("Listando todos os clientes (Eager Loading)..."); //
        return listarTodosLazyLoading(); // Reutiliza para este exemplo
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Cliente) {
            Cliente clienteAtualizado = (Cliente) obj; // AQUI ESTÁ A CORREÇÃO: a variável já está definida e tipada corretamente
            boolean encontrado = false; //
            for (int i = 0; i < clientesDB.size(); i++) { //
                if (clientesDB.get(i).getIdcliente() == clienteAtualizado.getIdcliente()) { //
                    clientesDB.set(i, clienteAtualizado); // Use clienteAtualizado diretamente
                    encontrado = true; //
                    System.out.println("Cliente atualizado (ID: " + clienteAtualizado.getIdcliente() + ", Novo Nome: " + clienteAtualizado.getNome() + ")"); //
                    break; //
                }
            }
            if (!encontrado) { //
                System.out.println("Cliente com ID " + clienteAtualizado.getIdcliente() + " não encontrado para atualização."); //
            }
        } else {
            System.out.println("Objeto não é uma instância de Cliente. Não atualizado."); //
        }
    }

    @Override
    public void excluir(int id) {
        System.out.println("Tentando excluir cliente pelo ID: " + id); //
        boolean removido = clientesDB.removeIf(cliente -> cliente.getIdcliente() == id); //
        if (removido) { //
            System.out.println("Cliente com ID " + id + " excluído com sucesso."); //
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado para exclusão."); //
        }
    }

    // Método específico para ClienteDAO
    public Cliente buscarPorEmail(String email) {
        System.out.println("Buscando cliente pelo email: " + email); //
        for (Cliente cliente : clientesDB) { //
            if (cliente.getEmail() != null && cliente.getEmail().equals(email)) { //
                System.out.println("Cliente encontrado por email: " + cliente.getNome()); //
                return cliente; //
            }
        }
        System.out.println("Cliente com email '" + email + "' não encontrado."); //
        return null; //
    }
}