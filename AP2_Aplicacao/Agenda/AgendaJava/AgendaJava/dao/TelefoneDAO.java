package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import modelo.Pessoa;
import modelo.Telefone;
import modelo.TipoTelefone;

public class TelefoneDAO implements BaseDAO{

    private Connection connection;

    public TelefoneDAO(Connection connection) {
        this.connection = connection;
    }

    // A pessoa passada como parâmetro já deve ter sido criada no banco. Como o relacionamento de telefone com pessoa não é bidirecional, então temos que passar a pessoa como parâmetro
    public void salvar(Telefone telefone, Pessoa pessoa) {
        try {
            String sql = "INSERT INTO telefone (tipo, codigo_pais, codigo_area, numero, fk_pessoa) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstm.setInt(1, telefone.getTipo().id);
                pstm.setInt(2, telefone.getCodigoPais());
                pstm.setInt(3, telefone.getCodigoArea());
                pstm.setInt(4, telefone.getNumero());
                pstm.setInt(5, pessoa.getId());
                pstm.execute();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        telefone.setId(rst.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void salvar(Object objeto) {

    }

    @Override
    public Object buscarPorId(int id) {
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        return listarTodosEagerLoading();
    }

    public ArrayList<Object> listarTodosEagerLoading() {

        ArrayList<Object> telefones = new ArrayList<Object>();

        try {
            String sql = "SELECT id, tipo, codigo_pais, codigo_area, numero FROM telefone";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int tel_id = rst.getInt("id");
                    TipoTelefone tipo = TipoTelefone.values()[rst.getInt("tipo")];
                    int cod_pais = rst.getInt("codigo_pais");
                    int cod_area = rst.getInt("codigo_area");
                    int numero = rst.getInt("numero");
                    Telefone t = new Telefone(tel_id, tipo, cod_pais, cod_area, numero);
                    telefones.add(t);
                }
            }
            return telefones;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Telefone> listarTodos(Pessoa pessoa) {

        ArrayList<Telefone> telefones = new ArrayList<Telefone>();

        try {
            String sql = "SELECT id, tipo, codigo_pais, codigo_area, numero FROM telefone WHERE fk_pessoa = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, pessoa.getId());
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int tel_id = rst.getInt("id");
                    TipoTelefone tipo = TipoTelefone.values()[rst.getInt("tipo")];
                    int cod_pais = rst.getInt("codigo_pais");
                    int cod_area = rst.getInt("codigo_area");
                    int numero = rst.getInt("numero");
                    Telefone t = new Telefone(tel_id, tipo, cod_pais, cod_area, numero);
                    telefones.add(t);
                }
            }
            return telefones;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(Object objeto) {

    }


    @Override
    public void excluir(int id) {

    }

}
