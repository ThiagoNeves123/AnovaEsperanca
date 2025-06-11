package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Cliente;
import bd.ConnectionFactory;
public class ClienteDAO implements BaseDAO {


    @Override
    public void salvar(Object obj) {
        if (obj instanceof Cliente) {
            Cliente cliente = (Cliente) obj;
            String sql = "INSERT INTO cliente (cpf, nome, email, senha) VALUES (?, ?, ?, ?)";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, cliente.getCpf());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getSenha());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            cliente.setIdcliente(generatedKeys.getInt(1));
                            System.out.println("Cliente salvo (ID: " + cliente.getIdcliente() + ", Nome: " + cliente.getNome() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar o cliente. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Cliente. Não salvo no DB.");
        }
    }
    @Override
    public Object buscarPorId(int id) {
        System.out.println("Buscando cliente pelo ID: " + id);
        String sql = "SELECT id_cliente, cpf, nome, email, senha FROM cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
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
            System.err.println("Erro ao buscar: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Cliente, ID " + id + " não encontrado.");
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
            System.err.println("Erro ao listar: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Lista de clientes carregada do DB.");
        return clientes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
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
                    System.out.println("Cliente, ID " + cliente.getIdcliente() + " atualizado.");
                } else {
                    System.out.println("Cliente, ID " + cliente.getIdcliente() + " não encontrado.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar: " + e.getMessage());
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
                System.out.println("Cliente, ID " + id + " excluído com sucesso do DB.");
            } else {
                System.out.println("Cliente, ID " + id + " não encontrado para exclusão no DB.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente do DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

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