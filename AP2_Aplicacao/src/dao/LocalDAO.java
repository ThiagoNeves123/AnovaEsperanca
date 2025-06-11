package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Local;
import bd.ConnectionFactory; // Certifique-se de que este import está correto

public class LocalDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Local) {
            Local local = (Local) obj;
            String sql = "INSERT INTO local_restaurante (cidade, bairro, rua, numero, cep) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, local.getCidade());
                stmt.setString(2, local.getBairro());
                stmt.setString(3, local.getRua());
                stmt.setInt(4, local.getNumero());
                stmt.setString(5, local.getCep()); // Assumindo que CEP é String (CHAR(8) no SQL)

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            local.setIdLocal(generatedKeys.getInt(1));
                            System.out.println("Local salvo com sucesso no DB (ID: " + local.getIdLocal() + ", Cidade: " + local.getCidade() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para o local.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar o local. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar local no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Local. Não salvo no DB.");
        }
    }

    // --- Métodos buscarPorId, listarTodosLazyLoading, listarTodosEagerLoading, atualizar, excluir ---
    // Você precisará adaptar estes métodos para JDBC também. Vou dar o exemplo do salvar.
    // O restante dos métodos (buscar, listar, atualizar, excluir) seguirão o mesmo padrão JDBC
    // de obter conexão, preparar statement, executar query/update e mapear resultados.
    // Por exemplo, um buscarPorId:
    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id_local, cidade, bairro, rua, numero, cep FROM local_restaurante WHERE id_local = ?";
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
                    System.out.println("Local encontrado (ID: " + local.getIdLocal() + ")");
                    return local;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar local por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        ArrayList<Object> locais = new ArrayList<>();
        String sql = "SELECT id_local, cidade, bairro, rua, numero, cep FROM local_restaurante";
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
                locais.add(local);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar locais: " + e.getMessage());
            e.printStackTrace();
        }
        return locais;
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
        return listarTodosLazyLoading(); // Para Local, Lazy e Eager são iguais, pois não tem FKs de outros objetos
    }

    @Override
    public void atualizar(Object obj) {
        if (obj instanceof Local) {
            Local local = (Local) obj;
            String sql = "UPDATE local_restaurante SET cidade = ?, bairro = ?, rua = ?, numero = ?, cep = ? WHERE id_local = ?";
            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, local.getCidade());
                stmt.setString(2, local.getBairro());
                stmt.setString(3, local.getRua());
                stmt.setInt(4, local.getNumero());
                stmt.setString(5, local.getCep());
                stmt.setInt(6, local.getIdLocal());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Local atualizado com sucesso no DB (ID: " + local.getIdLocal() + ")");
                } else {
                    System.err.println("Nenhuma linha afetada ao atualizar o local. Local não encontrado ou dados iguais.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar local no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Local. Não atualizado no DB.");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM local_restaurante WHERE id_local = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Local excluído com sucesso do DB (ID: " + id + ")");
            } else {
                System.err.println("Nenhuma linha afetada ao excluir o local. Local não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir local do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}