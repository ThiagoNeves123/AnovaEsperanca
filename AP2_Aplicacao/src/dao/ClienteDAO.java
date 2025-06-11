package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Você pode manter se for usar para listar, mas o foco agora é DB
import util.Cliente;
import bd.ConnectionFactory; // Importa a ConnectionFactory do pacote 'database'

public class ClienteDAO implements BaseDAO {

    // Removemos os atributos estáticos 'clientesDB' e 'nextIdCliente'
    // pois agora o banco de dados real gerenciará os dados e IDs.

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Cliente) {
            Cliente cliente = (Cliente) obj;
            // SQL para inserir um novo cliente
            // As colunas devem corresponder EXATAMENTE às da sua tabela 'cliente' no SQL
            String sql = "INSERT INTO cliente (cpf, nome, email, senha) VALUES (?, ?, ?, ?)";

            // Usamos try-with-resources para garantir que a conexão e o PreparedStatement sejam fechados automaticamente
            try (Connection conn = ConnectionFactory.getConnection(); // Obtém uma conexão do pool
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) { // Importante para pegar o ID gerado

                // Define os valores para os placeholders (?) no SQL
                stmt.setString(1, cliente.getCpf());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getSenha()); // Lembre-se de hash de senhas em produção!

                int affectedRows = stmt.executeUpdate(); // Executa o INSERT

                if (affectedRows > 0) {
                    // Se o INSERT foi bem-sucedido, tenta recuperar o ID gerado pelo banco
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            cliente.setIdcliente(generatedKeys.getInt(1)); // Define o ID gerado no objeto Cliente
                            System.out.println("Cliente salvo com sucesso no DB (ID: " + cliente.getIdcliente() + ", Nome: " + cliente.getNome() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para o cliente.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar o cliente. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar cliente no banco de dados: " + e.getMessage());
                e.printStackTrace(); // Para depuração, em produção pode ser substituído por logging
            }
        } else {
            System.out.println("Objeto não é uma instância de Cliente. Não salvo no DB.");
        }
    }

    // A partir daqui, você precisaria adaptar todos os outros métodos (buscarPorId, listarTodos, etc.)
    // para também interagir com o banco de dados via JDBC.
    // Vou deixar eles como estavam no seu arquivo original para não introduzir muita complexidade de uma vez,
    // mas saiba que eles também precisam ser reescritos.

    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando cliente pelo ID: " + id);
        // Lógica JDBC para buscar o cliente pelo ID no banco de dados
        String sql = "SELECT id_cliente, cpf, nome, email, senha FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Se encontrou, cria e retorna o objeto Cliente
                    Cliente cliente = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                    System.out.println("Cliente encontrado: " + cliente.getNome());
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Cliente com ID " + id + " não encontrado.");
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        ArrayList<Object> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, cpf, nome, email, senha FROM cliente";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Lista de clientes carregada do DB.");
        return clientes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        // Para Cliente, eager e lazy loading seriam os mesmos, a menos que Cliente tivesse objetos aninhados.
        // Mantenho a mesma implementação por simplicidade.
        return listarTodosLazyLoading();
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Cliente) {
            Cliente cliente = (Cliente) obj;
            String sql = "UPDATE cliente SET cpf = ?, nome = ?, email = ?, senha = ? WHERE id_cliente = ?";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cliente.getCpf());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getSenha());
                stmt.setInt(5, cliente.getIdcliente());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Cliente com ID " + cliente.getIdcliente() + " atualizado com sucesso no DB.");
                } else {
                    System.out.println("Cliente com ID " + cliente.getIdcliente() + " não encontrado para atualização no DB.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar cliente no DB: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Cliente. Não atualizado no DB.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Cliente com ID " + id + " excluído com sucesso do DB.");
            } else {
                System.out.println("Cliente com ID " + id + " não encontrado para exclusão no DB.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente do DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método específico para ClienteDAO (adapte-o para JDBC)
    public Cliente buscarPorEmail(String email) {
        System.out.println("Buscando cliente pelo email: " + email);
        String sql = "SELECT id_cliente, cpf, nome, email, senha FROM cliente WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                    System.out.println("Cliente encontrado por email: " + cliente.getNome());
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por email no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Cliente com email '" + email + "' não encontrado.");
        return null;
    }
}