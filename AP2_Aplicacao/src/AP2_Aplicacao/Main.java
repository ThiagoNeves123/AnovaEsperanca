package AP2_Aplicacao;

import dao.ClienteDAO;
import util.Cliente;

public class Main {

    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();

        Cliente novoCliente = new Cliente(
                0,
                "243567",
                "Ana Clara",
                "ana.clara@email.com",
                "senhaSegura123"
        );

        Cliente outroCliente = new Cliente(
                1,
                "234566",
                "Bruno Martins",
                "bruno.m@email.com",
                "outraSenhaForte"
        );

        System.out.println("--- Tentando salvar o primeiro cliente ---");
        clienteDAO.salvar(novoCliente);

        System.out.println("\n--- Tentando salvar o segundo cliente ---");
        clienteDAO.salvar(outroCliente);

        System.out.println("\n--- Buscando o primeiro cliente pelo ID ---");
        Cliente clienteRecuperado = (Cliente) clienteDAO.buscarPorId(novoCliente.getIdcliente());
        if (clienteRecuperado != null) {
            System.out.println("Cliente recuperado: " + clienteRecuperado.getNome() + ", Email: " + clienteRecuperado.getEmail());
        }

        System.out.println("\n--- Listando todos os clientes ---");
        for (Object obj : clienteDAO.listarTodosLazyLoading()) {
            Cliente c = (Cliente) obj;
            System.out.println("ID: " + c.getIdcliente() + ", Nome: " + c.getNome() + ", CPF: " + c.getCpf() + ", Email: " + c.getEmail());
        }

        System.out.println("\n--- Atualizando o primeiro cliente ---");
        novoCliente.setNome("Ana Clara Atualizada");
        novoCliente.setEmail("ana.clara.nova@email.com");
        clienteDAO.atualizar(novoCliente);

        System.out.println("\n--- Buscando o cliente atualizado para verificar ---");
        clienteRecuperado = (Cliente) clienteDAO.buscarPorId(novoCliente.getIdcliente());
        if (clienteRecuperado != null) {
            System.out.println("Cliente após atualização: " + clienteRecuperado.getNome() + ", Email: " + clienteRecuperado.getEmail());
        }

        System.out.println("\n--- Excluindo o segundo cliente ---");
        clienteDAO.excluir(outroCliente.getIdcliente());

        System.out.println("\n--- Listando todos os clientes após exclusão ---");
        for (Object obj : clienteDAO.listarTodosLazyLoading()) {
            Cliente c = (Cliente) obj;
            System.out.println("ID: " + c.getIdcliente() + ", Nome: " + c.getNome() + ", CPF: " + c.getCpf() + ", Email: " + c.getEmail());
        }

    }
}