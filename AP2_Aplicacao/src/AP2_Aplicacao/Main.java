package AP2_Aplicacao; // Ou o pacote principal do seu projeto

import dao.ClienteDAO; // Importa o ClienteDAO
import util.Cliente;   // Importa a classe Cliente

public class Main {

    public static void main(String[] args) {

        // 1. Criar uma instância do DAO
        ClienteDAO clienteDAO = new ClienteDAO();

        // 2. Criar um novo objeto Cliente com os dados que você quer inserir
        // Note que o ID não é passado, pois o banco de dados irá gerá-lo.
        Cliente novoCliente = new Cliente(
                0,
                "243567",// CPF\
                "Ana Clara",    // Nome
                "ana.clara@email.com", // Email
                "senhaSegura123" // Senha (Lembre-se: em produção, use hashing!)
        );

        Cliente outroCliente = new Cliente(
                1,  // CPF
                "234566",
                "Bruno Martins",// Nome
                "bruno.m@email.com", // Email
                "outraSenhaForte" // Senha
        );

        // 3. Chamar o método salvar() do DAO para persistir o objeto no banco de dados
        System.out.println("--- Tentando salvar o primeiro cliente ---");
        clienteDAO.salvar(novoCliente); // Isso irá executar o INSERT no DB

        System.out.println("\n--- Tentando salvar o segundo cliente ---");
        clienteDAO.salvar(outroCliente); // Isso irá executar outro INSERT no DB

        // --- Testando a leitura ---
        System.out.println("\n--- Buscando o primeiro cliente pelo ID ---");
        Cliente clienteRecuperado = (Cliente) clienteDAO.buscarPorId(novoCliente.getIdcliente());
        if (clienteRecuperado != null) {
            System.out.println("Cliente recuperado: " + clienteRecuperado.getNome() + ", Email: " + clienteRecuperado.getEmail());
        }

        System.out.println("\n--- Listando todos os clientes ---");
        // O método listarTodosLazyLoading retorna um ArrayList<Object>, precisamos fazer um cast
        for (Object obj : clienteDAO.listarTodosLazyLoading()) {
            Cliente c = (Cliente) obj;
            System.out.println("ID: " + c.getIdcliente() + ", Nome: " + c.getNome() + ", CPF: " + c.getCpf() + ", Email: " + c.getEmail());
        }

        // --- Exemplo de Atualização ---
        System.out.println("\n--- Atualizando o primeiro cliente ---");
        novoCliente.setNome("Ana Clara Atualizada");
        novoCliente.setEmail("ana.clara.nova@email.com");
        clienteDAO.atualizar(novoCliente);

        System.out.println("\n--- Buscando o cliente atualizado para verificar ---");
        clienteRecuperado = (Cliente) clienteDAO.buscarPorId(novoCliente.getIdcliente());
        if (clienteRecuperado != null) {
            System.out.println("Cliente após atualização: " + clienteRecuperado.getNome() + ", Email: " + clienteRecuperado.getEmail());
        }

        // --- Exemplo de Exclusão ---
        System.out.println("\n--- Excluindo o segundo cliente ---");
        clienteDAO.excluir(outroCliente.getIdcliente());

        System.out.println("\n--- Listando todos os clientes após exclusão ---");
        for (Object obj : clienteDAO.listarTodosLazyLoading()) {
            Cliente c = (Cliente) obj;
            System.out.println("ID: " + c.getIdcliente() + ", Nome: " + c.getNome() + ", CPF: " + c.getCpf() + ", Email: " + c.getEmail());
        }

        // Você deve repetir esse processo (criar DAO, criar objeto, chamar salvar) para Local, Restaurante e as Avaliações.
        // Lembre-se que para Restaurante e Avaliações, você precisará ter o Local e Cliente/Restaurante já salvos no banco.
    }
}