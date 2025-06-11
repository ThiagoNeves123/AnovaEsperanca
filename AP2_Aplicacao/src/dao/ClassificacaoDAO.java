package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Para java.sql.Date
import java.util.ArrayList;

import util.Classificacao;
import util.Restaurante; // Para buscar Restaurante
import util.Cliente;     // Para buscar Cliente
import bd.ConnectionFactory; // Importa sua ConnectionFactory

public class ClassificacaoDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Classificacao) {
            Classificacao classificacao = (Classificacao) obj;
            // SQL para inserir uma nova classificação final
            // As chaves estrangeiras fk_restaurante e fk_cliente são obrigatórias
            String sql = "INSERT INTO classificacao_final (fk_restaurante, fk_cliente, nota_final, data_classificacao) VALUES (?, ?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                // Garanta que Restaurante e Cliente não são nulos e têm IDs válidos
                if (classificacao.getRestaurante() == null || classificacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("Restaurante associado à Classificacao é nulo ou não tem ID. Salve o Restaurante primeiro.");
                }
                if (classificacao.getCliente() == null || classificacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("Cliente associado à Classificacao é nulo ou não tem ID. Salve o Cliente primeiro.");
                }

                stmt.setInt(1, classificacao.getRestaurante().getIdrestaurante());
                stmt.setInt(2, classificacao.getCliente().getIdcliente());
                stmt.setFloat(3, classificacao.getNotaFinal());
                stmt.setDate(4, classificacao.getDataClassificacao()); // Usa java.sql.Date

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            classificacao.setIdClassificacao(generatedKeys.getInt(1)); // Define o ID gerado no objeto
                            System.out.println("Classificação final salva com sucesso no DB (ID: " + classificacao.getIdClassificacao() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para a Classificação final.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar a Classificação final. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar Classificação final no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Classificacao. Não salvo no DB.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id_classificacao, fk_restaurante, fk_cliente, nota_final, data_classificacao FROM classificacao_final WHERE id_classificacao = ?";
        Classificacao classificacao = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int fkRestaurante = rs.getInt("fk_restaurante");
                    int fkCliente = rs.getInt("fk_cliente");
                    float notaFinal = rs.getFloat("nota_final");
                    Date dataClassificacao = rs.getDate("data_classificacao");

                    // Busca os objetos Restaurante e Cliente completos usando seus DAOs
                    RestauranteDAO restauranteDAO = new RestauranteDAO();
                    Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(fkRestaurante);

                    ClienteDAO clienteDAO = new ClienteDAO(); // Você precisará adaptar ClienteDAO para JDBC também
                    Cliente cliente = (Cliente) clienteDAO.buscarPorId(fkCliente);

                    classificacao = new Classificacao(
                            rs.getInt("id_classificacao"),
                            restaurante,
                            cliente,
                            notaFinal,
                            dataClassificacao
                    );
                    System.out.println("Classificação encontrada: ID " + classificacao.getIdClassificacao() + ", Nota Final: " + classificacao.getNotaFinal());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Classificação final por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return classificacao;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        // Para Classificacao, listarTodos implica buscar os objetos relacionados (Restaurante, Cliente)
        // para serem úteis. Portanto, a implementação será a mesma que eager loading.
        return listarTodosEagerLoading();
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        ArrayList<Object> classificacoes = new ArrayList<>();
        String sql = "SELECT id_classificacao, fk_restaurante, fk_cliente, nota_final, data_classificacao FROM classificacao_final";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int fkRestaurante = rs.getInt("fk_restaurante");
                int fkCliente = rs.getInt("fk_cliente");
                float notaFinal = rs.getFloat("nota_final");
                Date dataClassificacao = rs.getDate("data_classificacao");

                RestauranteDAO restauranteDAO = new RestauranteDAO();
                Restaurante restaurante = (Restaurante) restauranteDAO.buscarPorId(fkRestaurante);

                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = (Cliente) clienteDAO.buscarPorId(fkCliente);

                Classificacao classificacao = new Classificacao(
                        rs.getInt("id_classificacao"),
                        restaurante,
                        cliente,
                        notaFinal,
                        dataClassificacao
                );
                classificacoes.add(classificacao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar Classificações finais: " + e.getMessage());
            e.printStackTrace();
        }
        return classificacoes;
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Classificacao) {
            Classificacao classificacao = (Classificacao) obj;
            String sql = "UPDATE classificacao_final SET fk_restaurante = ?, fk_cliente = ?, nota_final = ?, data_classificacao = ? WHERE id_classificacao = ?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                if (classificacao.getRestaurante() == null || classificacao.getRestaurante().getIdrestaurante() == 0) {
                    throw new SQLException("Restaurante associado à Classificacao é nulo ou não tem ID.");
                }
                if (classificacao.getCliente() == null || classificacao.getCliente().getIdcliente() == 0) {
                    throw new SQLException("Cliente associado à Classificacao é nulo ou não tem ID.");
                }

                stmt.setInt(1, classificacao.getRestaurante().getIdrestaurante());
                stmt.setInt(2, classificacao.getCliente().getIdcliente());
                stmt.setFloat(3, classificacao.getNotaFinal());
                stmt.setDate(4, classificacao.getDataClassificacao());
                stmt.setInt(5, classificacao.getIdClassificacao());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Classificação final atualizada com sucesso no DB (ID: " + classificacao.getIdClassificacao() + ")");
                } else {
                    System.err.println("Nenhuma linha afetada ao atualizar a Classificação final. Classificação não encontrada ou dados iguais.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar Classificação final no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Classificacao. Não atualizado no DB.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM classificacao_final WHERE id_classificacao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Classificação final excluída com sucesso do DB (ID: " + id + ")");
            } else {
                System.err.println("Nenhuma linha afetada ao excluir a Classificação final. Classificação não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir Classificação final do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}