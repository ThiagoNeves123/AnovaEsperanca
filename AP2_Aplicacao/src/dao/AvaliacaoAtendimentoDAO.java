package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.AvaliacaoAtendimento;
import util.Restaurante;
import util.Cliente;
import bd.ConnectionFactory;

public class AvaliacaoAtendimentoDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoAtendimento) {
            AvaliacaoAtendimento avaliacao = (AvaliacaoAtendimento) obj;
            String sql = "INSERT INTO avaliacao_atendimento (nota_atendimento, fk_restaurante, fk_cliente) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setFloat(1, avaliacao.getNotaAtendimento());
                if (avaliacao.getRestaurante() == null || avaliacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("O restaurante ligado à avaliação é nulo ou o ID não é válido .");
                }
                if (avaliacao.getCliente() == null || avaliacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("O cliente ligado à avaliação é nulo ou o ID não é válido.");
                }
                stmt.setInt(2, avaliacao.getRestaurante().getIdrestaurante());
                stmt.setInt(3, avaliacao.getCliente().getIdcliente());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            avaliacao.setIdAvaliacao(generatedKeys.getInt(1));
                            System.out.println("Avaliação de Atendimento salva com sucesso no DB (ID: " + avaliacao.getIdAvaliacao() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para a Avaliação de Atendimento.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar a Avaliação de Atendimento. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar Avaliação de Atendimento no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoAtendimento. Não salvo no DB.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id_avaliacao_atendimento, nota_atendimento, fk_restaurante, fk_cliente FROM avaliacao_atendimento WHERE id_avaliacao_atendimento = ?";
        AvaliacaoAtendimento avaliacao = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idRestaurante = rs.getInt("fk_restaurante");
                    int idCliente = rs.getInt("fk_cliente");

                    RestauranteDAO restauranteDAO = new RestauranteDAO();
                    ClienteDAO clienteDAO = new ClienteDAO();

                    Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(idRestaurante);
                    Cliente cliente = (Cliente) clienteDAO.buscarPorId(idCliente);

                    avaliacao = new AvaliacaoAtendimento(
                            rs.getInt("id_avaliacao_atendimento"),
                            rs.getFloat("nota_atendimento"),
                            restaurante,
                            cliente
                    );
                    System.out.println("Avaliação encontrada: ID " + avaliacao.getIdAvaliacao());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return avaliacao;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        return listarTodosEagerLoading();
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        ArrayList<Object> avaliacoes = new ArrayList<>();
        String sql = "SELECT id_avaliacao_atendimento, nota_atendimento, fk_restaurante, fk_cliente FROM avaliacao_atendimento";

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

                AvaliacaoAtendimento avaliacao = new AvaliacaoAtendimento(
                        rs.getInt("id_avaliacao_atendimento"),
                        rs.getFloat("nota_atendimento"),
                        restaurante,
                        cliente
                );
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        return avaliacoes;
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoAtendimento) {
            AvaliacaoAtendimento avaliacao = (AvaliacaoAtendimento) obj;
            String sql = "UPDATE avaliacao_atendimento SET nota_atendimento = ?, fk_restaurante = ?, fk_cliente = ? WHERE id_avaliacao_atendimento = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setFloat(1, avaliacao.getNotaAtendimento());
                if (avaliacao.getRestaurante() == null || avaliacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("O restaurante ligado à avaliação é nulo ou o ID não é válido.");
                }
                if (avaliacao.getCliente() == null || avaliacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("O cliente ligado à avaliação é nulo ou o ID não é válido.");
                }
                stmt.setInt(2, avaliacao.getRestaurante().getIdrestaurante());
                stmt.setInt(3, avaliacao.getCliente().getIdcliente());
                stmt.setInt(4, avaliacao.getIdAvaliacao());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Avaliação de Atendimento " + avaliacao.getIdAvaliacao() + " atualizada.");
                } else {
                    System.out.println("Avaliação de Atendimento " + avaliacao.getIdAvaliacao() + " não encontrada.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM avaliacao_atendimento WHERE id_avaliacao_atendimento = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Avaliação de Atendimento " + id + " excluída.");
            } else {
                System.out.println("Avaliação de Atendimento " + id + " não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir Avaliação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}