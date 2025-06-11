package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.AvaliacaoAmbiente; // Importa a classe específica
import util.Restaurante;       // Para carregar o objeto Restaurante
import util.Cliente;           // Para carregar o objeto Cliente
import bd.ConnectionFactory;   // Sua ConnectionFactory

// Adaptei este DAO para gerenciar AvaliacaoAmbiente.
// Se você tem outros tipos de avaliação (Comida, Atendimento, Localizacao),
// você deve criar DAOs específicos para eles (Ex: AvaliacaoComidaDAO, AvaliacaoAtendimentoDAO).
public class AvaliacaoDAO implements BaseDAO { // Mantendo o nome do arquivo AvaliacaoDAO.java, mas o escopo é AvaliacaoAmbiente

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoAmbiente) {
            AvaliacaoAmbiente avaliacao = (AvaliacaoAmbiente) obj;
            // SQL para inserir uma AvaliacaoAmbiente
            String sql = "INSERT INTO avaliacao_ambiente (nota_ambiente, fk_restaurante, fk_cliente) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setFloat(1, avaliacao.getNotaAmbiente());

                // É CRUCIAL que Restaurante e Cliente já existam e tenham IDs válidos no DB
                if (avaliacao.getRestaurante() == null || avaliacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("Restaurante associado à avaliação é nulo ou não tem ID válido. Salve o Restaurante primeiro.");
                }
                if (avaliacao.getCliente() == null || avaliacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("Cliente associado à avaliação é nulo ou não tem ID válido. Salve o Cliente primeiro.");
                }

                stmt.setInt(2, avaliacao.getRestaurante().getIdrestaurante());
                stmt.setInt(3, avaliacao.getCliente().getIdcliente());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            avaliacao.setIdAvaliacao(generatedKeys.getInt(1)); // Define o ID gerado no objeto
                            System.out.println("Avaliação de Ambiente salva com sucesso no DB (ID: " + avaliacao.getIdAvaliacao() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para a Avaliação de Ambiente.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar a Avaliação de Ambiente. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar Avaliação de Ambiente no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAmbiente. Não salvo no DB.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id_avaliacao_ambiente, nota_ambiente, fk_restaurante, fk_cliente FROM avaliacao_ambiente WHERE id_avaliacao_ambiente = ?";
        AvaliacaoAmbiente avaliacao = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idRestaurante = rs.getInt("fk_restaurante");
                    int idCliente = rs.getInt("fk_cliente");

                    // Instancia os DAOs para buscar os objetos relacionados
                    // Você precisará ter essas classes de DAO implementadas com JDBC
                    RestauranteDAO restauranteDAO = new RestauranteDAO();
                    ClienteDAO clienteDAO = new ClienteDAO();

                    Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(idRestaurante);
                    Cliente cliente = (Cliente) clienteDAO.buscarPorId(idCliente);

                    avaliacao = new AvaliacaoAmbiente(
                            rs.getInt("id_avaliacao_ambiente"),
                            rs.getFloat("nota_ambiente"),
                            restaurante,
                            cliente
                    );
                    System.out.println("Avaliação de Ambiente encontrada: ID " + avaliacao.getIdAvaliacao());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Avaliação de Ambiente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return avaliacao;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        // Para este exemplo, faremos eager loading dos objetos relacionados
        return listarTodosEagerLoading();
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        ArrayList<Object> avaliacoes = new ArrayList<>();
        String sql = "SELECT id_avaliacao_ambiente, nota_ambiente, fk_restaurante, fk_cliente FROM avaliacao_ambiente";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idRestaurante = rs.getInt("fk_restaurante");
                int idCliente = rs.getInt("fk_cliente");

                RestauranteDAO restauranteDAO = new RestauranteDAO();
                ClienteDAO clienteDAO = new ClienteDAO();

                Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(idRestaurante);
                Cliente cliente = (Cliente) clienteDAO.buscarPorId(idCliente);

                AvaliacaoAmbiente avaliacao = new AvaliacaoAmbiente(
                        rs.getInt("id_avaliacao_ambiente"),
                        rs.getFloat("nota_ambiente"),
                        restaurante,
                        cliente
                );
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Avaliações de Ambiente: " + e.getMessage());
            e.printStackTrace();
        }
        return avaliacoes;
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoAmbiente) {
            AvaliacaoAmbiente avaliacao = (AvaliacaoAmbiente) obj;
            String sql = "UPDATE avaliacao_ambiente SET nota_ambiente = ?, fk_restaurante = ?, fk_cliente = ? WHERE id_avaliacao_ambiente = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setFloat(1, avaliacao.getNotaAmbiente());
                if (avaliacao.getRestaurante() == null || avaliacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("Restaurante associado à avaliação é nulo ou não tem ID válido.");
                }
                if (avaliacao.getCliente() == null || avaliacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("Cliente associado à avaliação é nulo ou não tem ID válido.");
                }
                stmt.setInt(2, avaliacao.getRestaurante().getIdrestaurante());
                stmt.setInt(3, avaliacao.getCliente().getIdcliente());
                stmt.setInt(4, avaliacao.getIdAvaliacao()); // ID da avaliação para o WHERE

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Avaliação de Ambiente com ID " + avaliacao.getIdAvaliacao() + " atualizada com sucesso.");
                } else {
                    System.out.println("Avaliação de Ambiente com ID " + avaliacao.getIdAvaliacao() + " não encontrada para atualização.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar Avaliação de Ambiente: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAmbiente. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM avaliacao_ambiente WHERE id_avaliacao_ambiente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Avaliação de Ambiente com ID " + id + " excluída com sucesso.");
            } else {
                System.out.println("Avaliação de Ambiente com ID " + id + " não encontrada para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir Avaliação de Ambiente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}