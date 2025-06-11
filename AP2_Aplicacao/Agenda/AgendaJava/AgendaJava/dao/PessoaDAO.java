package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;

import modelo.Pessoa;
import modelo.Telefone;
import modelo.TipoTelefone;

public class PessoaDAO implements BaseDAO{

    private Connection connection;

    public PessoaDAO(Connection connection) {
        this.connection = connection;
    }

    /* Os telefones não precisam estar cadastrados no banco, até porque não faz sentido eles estarem no banco sem uma pessoa relacionada */
    public void salvar(Object objeto) {

        if (!(objeto instanceof Pessoa)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Pessoa.");
        }

        Pessoa pessoa = (Pessoa) objeto;

        try {
            String sql = "INSERT INTO pessoa (nome, cpf, data_nascimento, idade) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstm.setString(1, pessoa.getNome()); //Define o primeiro ?
                pstm.setString(2, pessoa.getCpf()); //Define o segundo ?
                pstm.setObject(3, pessoa.getDataNascimento()); //Define o terceiro ?
                pstm.setInt(4, pessoa.getIdade()); //Define o quarto ?

                pstm.execute();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        pessoa.setId(rst.getInt(1));

                        //Faz a chamada do TelefoneDAO para salvar os telefones da pessoa
                        TelefoneDAO tdao = new TelefoneDAO(connection);
                        for (Telefone telefone : pessoa.getTelefones()) {
                            tdao.salvar(telefone, pessoa);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo ainda não está retornando os telefones da pessoa
    @Override
    public Object buscarPorId(int id) {

        try {
            String sql = "SELECT id, nome, cpf, data_nascimento, idade FROM pessoa WHERE id = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, id);

                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int identificador = rst.getInt("id");
                    String nome = rst.getString("nome");
                    String cpf = rst.getString("cpf");
                    LocalDate data = rst.getObject("data_nascimento", LocalDate.class);
                    int idade = rst.getInt("idade");
                    return new Pessoa(identificador, nome, cpf, data, idade);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Object> listarTodosLazyLoading() {

        ArrayList<Object> pessoas = new ArrayList<>();

        try {
            String sql = "SELECT id, nome, cpf, data_nascimento, idade FROM pessoa";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();
                while (rst.next()) {
                    int id = rst.getInt("id");
                    String nome = rst.getString("nome");
                    String cpf = rst.getString("cpf");
                    LocalDate data = rst.getObject("data_nascimento", LocalDate.class);
                    int idade = rst.getInt("idade");
                    Pessoa p = new Pessoa(id, nome, cpf, data, idade);
                    pessoas.add(p);
                }
            }
            return pessoas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Object> listarTodosEagerLoading() {

        ArrayList<Object> pessoas = new ArrayList<>();
        Pessoa ultima = null;

        try {

            String sql = "SELECT p.id, p.nome, p.cpf, p.data_nascimento, p.idade, t.id, t.tipo, t.codigo_pais, t.codigo_area, t.numero "
                    + "FROM pessoa AS p "
                    + "LEFT JOIN telefone AS t ON p.id = t.fk_pessoa";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        if (ultima == null || ultima.getId() != rst.getInt(1)) {
                            int p_id = rst.getInt(1);
                            String nome = rst.getString(2);
                            String cpf = rst.getString(3);
                            LocalDate data = rst.getObject(4, LocalDate.class);
                            int idade = rst.getInt(5);
                            Pessoa p = new Pessoa(p_id, nome, cpf, data, idade);
                            pessoas.add(p);
                            ultima = p;
                        }

                        if (rst.getInt(6) != 0) {
                            int tel_id = rst.getInt(6);
                            TipoTelefone tipo = TipoTelefone.values()[rst.getInt(7)];
                            int cod_pais = rst.getInt(8);
                            int cod_area = rst.getInt(9);
                            int numero = rst.getInt(10);
                            Telefone t = new Telefone(tel_id, tipo, cod_pais, cod_area, numero);
                            ultima.addTelefone(t);
                        }
                    }
                }
                return pessoas;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo não está excluindo o telefone quando ele não pertence mais a pessoa
    @Override
    public void atualizar(Object objeto) {
        if (!(objeto instanceof Pessoa)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Pessoa.");
        }

        Pessoa pessoa = (Pessoa) objeto;

        String sql = "UPDATE pessoa SET nome = ?, cpf = ?, data_nascimento = ?, idade = ? WHERE id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, pessoa.getNome());
            pstm.setString(2, pessoa.getCpf());
            pstm.setDate(3, java.sql.Date.valueOf(pessoa.getDataNascimento()));
            pstm.setInt(4, pessoa.getIdade());
            pstm.setInt(5, pessoa.getId());

            int linhasAfetadas = pstm.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new SQLException("Falha ao atualizar: nenhuma linha foi afetada.");
            }

            TelefoneDAO tdao = new TelefoneDAO(connection);
            for (Telefone telefone : pessoa.getTelefones()) {
                tdao.atualizar(telefone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar pessoa: " + e.getMessage());
        }
    }

    @Override
    public void excluir(int id) {
        try {
            String sql = "DELETE FROM pessoa WHERE id = ?";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setInt(1, id);

                int linhasAfetadas = pstm.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException("Falha ao deletar: nenhuma linha foi afetada.");
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
