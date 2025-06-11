package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.AvaliacaoComida;
import util.Restaurante; // Para carregar o objeto Restaurante
import util.Cliente;     // Para carregar o objeto Cliente
import bd.ConnectionFactory; // CONFIRME O PACOTE DA SUA ConnectionFactory (era 'bd' em exemplos anteriores)

public class AvaliacaoComidaDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoComida) {
            AvaliacaoComida avaliacao = (AvaliacaoComida) obj;
            // O SQL deve corresponder à sua tabela `avaliacao_comida` e suas colunas
            String sql = "INSERT INTO avaliacao_comida (nota_comida, fk_restaurante, fk_cliente) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setFloat(1, avaliacao.getNotaComida());
                // É crucial que Restaurante e Cliente já tenham sido salvos e tenham IDs válidos no DB
                if (avaliacao.getRestaurante() == null || avaliacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("Restaurante associado à avaliação é nulo ou não tem ID válido.");
                }
                if (avaliacao.getCliente() == null || avaliacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("Cliente associado à avaliação é nulo ou não tem ID válido.");
                }
                stmt.setInt(2, avaliacao.getRestaurante().getIdrestaurante());
                stmt.setInt(3, avaliacao.getCliente().getIdcliente());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            avaliacao.setIdAvaliacao(generatedKeys.getInt(1)); // Define o ID gerado no objeto
                            System.out.println("Avaliação de Comida salva com sucesso no DB (ID: " + avaliacao.getIdAvaliacao() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para a Avaliação de Comida.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar a Avaliação de Comida. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar Avaliação de Comida no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoComida. Não salvo no DB.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id_avaliacao_comida, nota_comida, fk_restaurante, fk_cliente FROM avaliacao_comida WHERE id_avaliacao_comida = ?";
        AvaliacaoComida avaliacao = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idRestaurante = rs.getInt("fk_restaurante");
                    int idCliente = rs.getInt("fk_cliente");

                    // Instancia os DAOs para buscar os objetos relacionados
                    RestauranteDAO restauranteDAO = new RestauranteDAO();
                    ClienteDAO clienteDAO = new ClienteDAO();

                    Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(idRestaurante);
                    Cliente cliente = (Cliente) clienteDAO.buscarPorId(idCliente);

                    // Cria o objeto AvaliacaoComida completo
                    avaliacao = new AvaliacaoComida(
                            rs.getInt("id_avaliacao_comida"),
                            rs.getFloat("nota_comida"),
                            restaurante,
                            cliente
                    );
                    System.out.println("Avaliação de Comida encontrada: ID " + avaliacao.getIdAvaliacao());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Avaliação de Comida por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return avaliacao;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        // Para este exemplo, estamos fazendo um "eager loading" simplificado
        return listarTodosEagerLoading();
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        ArrayList<Object> avaliacoes = new ArrayList<>();
        String sql = "SELECT id_avaliacao_comida, nota_comida, fk_restaurante, fk_cliente FROM avaliacao_comida";

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

                AvaliacaoComida avaliacao = new AvaliacaoComida(
                        rs.getInt("id_avaliacao_comida"),
                        rs.getFloat("nota_comida"),
                        restaurante,
                        cliente
                );
                avaliacoes.add(avaliacao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Avaliações de Comida: " + e.getMessage());
            e.printStackTrace();
        }
        return avaliacoes;
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof AvaliacaoComida) {
            AvaliacaoComida avaliacao = (AvaliacaoComida) obj;
            String sql = "UPDATE avaliacao_comida SET nota_comida = ?, fk_restaurante = ?, fk_cliente = ? WHERE id_avaliacao_comida = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setFloat(1, avaliacao.getNotaComida());
                // Verificações de ID válidos
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
                    System.out.println("Avaliação de Comida com ID " + avaliacao.getIdAvaliacao() + " atualizada com sucesso.");
                } else {
                    System.out.println("Avaliação de Comida com ID " + avaliacao.getIdAvaliacao() + " não encontrada para atualização.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar Avaliação de Comida: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de AvaliacaoComida. Não atualizado.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM avaliacao_comida WHERE id_avaliacao_comida = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Avaliação de Comida com ID " + id + " excluída com sucesso.");
            } else {
                System.out.println("Avaliação de Comida com ID " + id + " não encontrada para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir Avaliação de Comida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}