package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Evento;
import modelo.Pessoa;
import modelo.Telefone;
import modelo.TipoTelefone;

public class EventoDAO implements BaseDAO{

    private Connection connection;

    public EventoDAO(Connection connection) {
        this.connection = connection;
    }

    // As pessoas já devem estar cadastradas no banco, até porque não faz sentido o evento ser o responsável de criar as pessoas
    public void salvar(Object objeto) {

        if (!(objeto instanceof Evento)) {
            throw new IllegalArgumentException("Objeto deve ser do tipo Evento.");
        }

        Evento evento = (Evento) objeto;

        try {
            String sql = "INSERT INTO evento (data_evento, descricao) VALUES (?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstm.setObject(1, evento.getData());
                pstm.setString(2, evento.getDescricao());

                pstm.execute();

                try (ResultSet rst = pstm.getGeneratedKeys()) {
                    while (rst.next()) {
                        evento.setId(rst.getInt(1));
                        for (Pessoa pessoa : evento.getPessoas()) {
                            salvarParticipacao(evento, pessoa);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Tanto o evento quanto a pessoa já m estar cadastrados no banco
    public void salvarParticipacao(Evento evento, Pessoa pessoa) {
        try {
            String sql = "INSERT INTO participacao (fk_evento, fk_pessoa) VALUES (?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {

                pstm.setInt(1, evento.getId());
                pstm.setInt(2, pessoa.getId());

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object buscarPorId(int id) {
        return null;
    }

    @Override
    public ArrayList<Object> listarTodosLazyLoading() {
        return null;
    }

    public ArrayList<Object> listarTodosEagerLoading() {

        ArrayList<Object> eventos = new ArrayList<>();
        Evento ultimoEvento = null;
        Pessoa ultimaPessoa = null;

        try {

            String sql = "SELECT e.id, e.data_evento, e.descricao, p.id, p.nome, p.cpf, p.data_nascimento, p.idade, t.id, t.tipo, t.codigo_pais, t.codigo_area, t.numero "
                    + "FROM evento AS e "
                    + "LEFT JOIN participacao AS ep ON ep.fk_evento = e.id "
                    + "LEFT JOIN pessoa AS p ON ep.fk_pessoa = p.id "
                    + "LEFT JOIN telefone AS t ON p.id = t.fk_pessoa";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();

                try (ResultSet rst = pstm.getResultSet()) {
                    while (rst.next()) {
                        if (ultimoEvento == null || ultimoEvento.getId() != rst.getInt(1)) {
                            int e_id = rst.getInt(1);
                            LocalDate e_data = rst.getObject(2, LocalDate.class);
                            String e_descricao = rst.getString(3);
                            Evento e = new Evento(e_id, e_data, e_descricao);
                            eventos.add(e);
                            ultimoEvento = e;
                        }

                        if ((rst.getInt(4) != 0) && (ultimaPessoa == null || ultimaPessoa.getId() != rst.getInt(4))) {
                            int p_id = rst.getInt(4);
                            String nome = rst.getString(5);
                            String cpf = rst.getString(6);
                            LocalDate p_data = rst.getObject(7, LocalDate.class);
                            int idade = rst.getInt(8);
                            Pessoa p = new Pessoa(p_id, nome, cpf, p_data, idade);
                            ultimoEvento.addPessoa(p);
                            ultimaPessoa = p;
                        }

                        if (rst.getInt(9) != 0) {
                            int tel_id = rst.getInt(9);
                            TipoTelefone tipo = TipoTelefone.values()[rst.getInt(10)];
                            int cod_pais = rst.getInt(11);
                            int cod_area = rst.getInt(12);
                            int numero = rst.getInt(13);
                            Telefone t = new Telefone(tel_id, tipo, cod_pais, cod_area, numero);
                            ultimaPessoa.addTelefone(t);
                        }
                    }
                }
                return eventos;
            }
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
