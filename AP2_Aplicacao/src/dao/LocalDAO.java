package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Local;
import bd.ConnectionFactory;

public class LocalDAO implements BaseDAO {

    @Override
    public void salvar(Object obj) {
        if (obj instanceof Local) {
            Local local = (Local) obj;
            String sql = "INSERT INTO local_restaurante (cidade, bairro, rua, numero, cep) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection(); // Obtém uma conexão
                 PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, local.getCidade());
                stmt.setString(2, local.getBairro());
                stmt.setString(3, local.getRua());
                stmt.setInt(4, local.getNumero());
                stmt.setString(5, local.getCep());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            local.setIdLocal(generatedKeys.getInt(1)); // Define o ID gerado no objeto
                            System.out.println("Local salvo com sucesso no DB (ID: " + local.getIdLocal() + ")");
                        } else {
                            System.err.println("Falha ao obter o ID gerado para o Local.");
                        }
                    }
                } else {
                    System.err.println("Nenhuma linha afetada ao salvar o Local. Possível erro.");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao salvar Local no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Local. Não salvo no DB.");
        }
    }

    @Override
    public Object buscarPorId(int id) {
        String sql = "SELECT id_local, cidade, bairro, rua, numero, cep FROM local_restaurante WHERE id_local = ?";
        Local local = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    local = new Local(
                            rs.getInt("id_local"),
                            rs.getString("cidade"),
                            rs.getString("bairro"),
                            rs.getString("rua"),
                            rs.getInt("numero"),
                            rs.getString("cep")
                    );
                    System.out.println("Local encontrado: ID " + local.getIdLocal() + ", Cidade: " + local.getCidade());
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Local por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return local;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        // Para Local, Lazy Loading pode não ser tão aplicável sem objetos relacionados
        // diretamente aqui. Implementamos o eager loading.
        return listarTodosEagerLoading();
    }

    @Override
    public ArrayList<Object> listarTodosEagerLoading() {
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
            System.err.println("Erro ao listar Locais: " + e.getMessage());
            e.printStackTrace();
        }
        return locais;
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
                    System.out.println("Local com ID " + local.getIdLocal() + " atualizado com sucesso.");
                } else {
                    System.out.println("Local com ID " + local.getIdLocal() + " não encontrado para atualização.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar Local: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Objeto não é uma instância de Local. Não atualizado.");
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
                System.out.println("Local com ID " + id + " excluído com sucesso.");
            } else {
                System.out.println("Local com ID " + id + " não encontrado para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir Local: " + e.getMessage());
            e.printStackTrace();
        }
    }
}