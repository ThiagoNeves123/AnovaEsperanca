package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.AvaliacaoAmbiente;
import util.Restaurante; // Para carregar o objeto Restaurante
import util.Cliente;     // Para carregar o objeto Cliente
import bd.ConnectionFactory; // Certifique-se de que o pacote está correto

public class AvaliacaoAmbienteDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof AvaliacaoAmbiente) {
            AvaliacaoAmbiente avaliacao = (AvaliacaoAmbiente) obj;
            String sql = "INSERT INTO avaliacao_ambiente (nota_ambiente, fk_restaurante, fk_cliente) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setFloat(1, avaliacao.getNotaAmbiente());
                // Certifique-se de que Restaurante e Cliente já foram salvos e têm IDs válidos do DB
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
                    // Cuidado: Ao buscar uma avaliação, você precisa carregar os objetos Restaurante e Cliente associados.
                    // Isso é feito buscando-os por ID usando seus respectivos DAOs.
                    int idRestaurante = rs.getInt("fk_restaurante");
                    int idCliente = rs.getInt("fk_cliente");

                    // Instancia os DAOs para buscar os objetos relacionados
                    RestauranteDAO restauranteDAO = new RestauranteDAO();
                    ClienteDAO clienteDAO = new ClienteDAO();

                    Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(idRestaurante);
                    Cliente cliente = (Cliente) clienteDAO.buscarPorId(idCliente);

                    // Cria o objeto AvaliacaoAmbiente completo
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
        // Para avaliações, o lazy/eager loading é mais notável ao carregar os objetos Restaurante/Cliente.
        // Lazy loading aqui significaria carregar apenas os IDs das FKs e não os objetos completos.
        // Contudo, para simplicidade e utilidade comum, muitas vezes os DAOs de relacionamento carregam
        // os objetos relacionados por padrão (Eager Loading disfarçado de Lazy).
        // Vamos manter a implementação como a do ClienteDAO, que efetivamente faz um "eager loading"
        // simples para as FKs. Se você tiver muitos dados, pode otimizar.
        return listarTodosEagerLoading(); // Optando por carregar os objetos completos
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