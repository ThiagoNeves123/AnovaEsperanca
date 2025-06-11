package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Restaurante;
import util.Local; // Importa a classe Local
import bd.ConnectionFactory;

public class RestauranteDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Restaurante) {
            Restaurante restaurante = (Restaurante) obj;
            // O INSERT precisa do ID do Local, não do objeto Local diretamente
            String sql = "INSERT INTO restaurante (nome, id_local, data_criacao) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, restaurante.getNome());
                // Garanta que o objeto Local não é nulo e tem um ID.
                // O Local deve ser salvo ANTES do Restaurante.
                if (restaurante.getLocal() != null && restaurante.getLocal().getIdLocal() != 0) {
                    stmt.setInt(2, restaurante.getLocal().getIdLocal());
                } else {
                    throw new SQLException("Local associado ao restaurante é nulo ou não tem ID. Salve o Local primeiro.");
                }
                stmt.setDate(3, restaurante.getDatasql());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            restaurante.setIdrestaurante(generatedKeys.getInt(1));
                            System.out.println("Restaurante salvo com sucesso no DB (ID: " + restaurante.getIdrestaurante() + ", Nome: " + restaurante.getNome() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para o restaurante.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar o restaurante. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar restaurante no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Restaurante. Não salvo no DB.");
        }
    }

    // --- Métodos buscarPorId, listarTodosLazyLoading, listarTodosEagerLoading, atualizar, excluir ---
    // Para Restaurante, o Eager Loading será importante para carregar o objeto Local associado.

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT r.id_restaurante, r.nome, r.data_criacao, " +
                "l.id_local, l.cidade, l.bairro, l.rua, l.numero, l.cep " +
                "FROM restaurante r JOIN local_restaurante l ON r.id_local = l.id_local WHERE r.id_restaurante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Local local = new Local(
                            rs.getInt("id_local"),
                            rs.getString("cidade"),
                            rs.getString("bairro"),
                            rs.getString("rua"),
                            rs.getInt("numero"),
                            rs.getString("cep")
                    );
                    Restaurante restaurante = new Restaurante(
                            rs.getInt("id_restaurante"),
                            rs.getString("nome"),
                            local, // Associa o objeto Local recuperado
                            rs.getDate("data_criacao")
                    );
                    System.out.println("Restaurante encontrado (ID: " + restaurante.getIdrestaurante() + ")");
                    return restaurante;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar restaurante por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        ArrayList<Object> restaurantes = new ArrayList<>();
        // Lazy loading: apenas carrega o ID do Local, não o objeto completo.
        String sql = "SELECT id_restaurante, nome, id_local, data_criacao FROM restaurante";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Aqui, apenas criamos um objeto Local com o ID, sem buscar os detalhes.
                Local localDummy = new Local(rs.getInt("id_local"), null, null, null, 0, null);
                Restaurante restaurante = new Restaurante(
                        rs.getInt("id_restaurante"),
                        rs.getString("nome"),
                        localDummy,
                        rs.getDate("data_criacao")
                );
                restaurantes.add(restaurante);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar restaurantes (Lazy): " + e.getMessage());
            e.printStackTrace();
        }
        return restaurantes;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        ArrayList<Object> restaurantes = new ArrayList<>();
        // Eager loading: Carrega os dados do Local junto com o Restaurante.
        String sql = "SELECT r.id_restaurante, r.nome, r.data_criacao, " +
                "l.id_local, l.cidade, l.bairro, l.rua, l.numero, l.cep " +
                "FROM restaurante r JOIN local_restaurante l ON r.id_local = l.id_local";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Local local = new Local(
                        rs.getInt("id_local"),
                        rs.getString("cidade"),
                        rs.getString("bairro"),
                        rs.getString("rua"),
                        rs.getInt("numero"),
                        rs.getString("cep")
                );
                Restaurante restaurante = new Restaurante(
                        rs.getInt("id_restaurante"),
                        rs.getString("nome"),
                        local,
                        rs.getDate("data_criacao")
                );
                restaurantes.add(restaurante);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar restaurantes (Eager): " + e.getMessage());
            e.printStackTrace();
        }
        return restaurantes;
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Restaurante) {
            Restaurante restaurante = (Restaurante) obj;
            // Se o local também for atualizado, ele deve ser salvo pelo LocalDAO antes
            String sql = "UPDATE restaurante SET nome = ?, id_local = ?, data_criacao = ? WHERE id_restaurante = ?";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, restaurante.getNome());
                if (restaurante.getLocal() != null && restaurante.getLocal().getIdLocal() != 0) {
                    stmt.setInt(2, restaurante.getLocal().getIdLocal());
                } else {
                    throw new SQLException("Local associado ao restaurante é nulo ou não tem ID.");
                }
                stmt.setDate(3, restaurante.getDatasql());
                stmt.setInt(4, restaurante.getIdrestaurante());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Restaurante atualizado com sucesso no DB (ID: " + restaurante.getIdrestaurante() + ")");
                } else {
                    System.err.println("Nenhuma linha afetada ao atualizar o restaurante. Restaurante não encontrado ou dados iguais.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar restaurante no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Restaurante. Não atualizado no DB.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM restaurante WHERE id_restaurante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Restaurante excluído com sucesso do DB (ID: " + id + ")");
            } else {
                System.err.println("Nenhuma linha afetada ao excluir o restaurante. Restaurante não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir restaurante do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}