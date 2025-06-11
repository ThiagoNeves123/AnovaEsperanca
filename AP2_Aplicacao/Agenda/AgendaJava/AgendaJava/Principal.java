import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import modelo.Evento;
import modelo.Pessoa;
import modelo.Telefone;
import modelo.TipoTelefone;
import bd.ConnectionFactory;
import dao.PessoaDAO;

import static java.time.LocalDate.*;

public class Principal {

    public static void main(String[] args) {

        Pessoa pessoa1 = new Pessoa("Lucas Pereira", "00011122200", LocalDate.of(2000, 1, 01));
        Telefone telefone10 = new Telefone(TipoTelefone.Celular, 55, 21, 986596939);
        pessoa1.addTelefone(telefone10);

        Pessoa pessoa2 = new Pessoa("Sofia Almeida", "00011122211", LocalDate.of(2001, 2, 05));
        Telefone telefone20 = new Telefone(TipoTelefone.Celular, 55, 21, 989967451);
        Telefone telefone21 = new Telefone(TipoTelefone.Residencial, 55, 21, 22296547);
        pessoa2.addTelefone(telefone20);
        pessoa2.addTelefone(telefone21);

        Pessoa pessoa3 = new Pessoa("Sofia Almeida", "00011122222", LocalDate.of(2002, 3, 10));
        Telefone telefone30 = new Telefone(TipoTelefone.Celular, 55, 21, 994457835);
        pessoa3.addTelefone(telefone30);

        Pessoa pessoa4 = new Pessoa("Isabella Santos", "00011122233", LocalDate.of(2003, 4, 15));
        Telefone telefone40 = new Telefone(TipoTelefone.Celular, 55, 21, 912365794);
        Telefone telefone41 = new Telefone(TipoTelefone.Celular, 55, 21, 964157679);
        pessoa4.addTelefone(telefone40);
        pessoa4.addTelefone(telefone41);

        Pessoa pessoa5 = new Pessoa("Enzo Medeiros", "00011122244", LocalDate.of(2004, 5, 20));
        Pessoa pessoa6 = new Pessoa("Laura Rodrigues", "00011122255", LocalDate.of(2005, 6, 25));
        Pessoa pessoa7 = new Pessoa("Júlia Gonçalves", "00011122266", LocalDate.of(2000, 7, 30));
        Pessoa pessoa8 = new Pessoa("Miguel Martins", "00011122277", LocalDate.of(2001, 8, 01));
        Pessoa pessoa9 = new Pessoa("Giovana Fernandes", "00011122288", LocalDate.of(2002, 9, 05));

        System.out.println("Comecei a printar os objetos de pessoa criados em memoria\n");
        System.out.println(pessoa1);
        System.out.println(pessoa2);
        System.out.println(pessoa3);
        System.out.println(pessoa4);
        System.out.println(pessoa5);
        System.out.println(pessoa6);
        System.out.println(pessoa7);
        System.out.println(pessoa8);
        System.out.println(pessoa9);
        System.out.println("Acabei de printar os objetos de pessoa em memoria\n\n\n");

        ConnectionFactory fabricaDeConexao = new ConnectionFactory();
        Connection connection = fabricaDeConexao.recuperaConexao();

        PessoaDAO pdao = new PessoaDAO(connection);

        pdao.salvar(pessoa1);
        pdao.salvar(pessoa2);
        pdao.salvar(pessoa3);
        pdao.salvar(pessoa4);
        pdao.salvar(pessoa5);
        pdao.salvar(pessoa6);
        pdao.salvar(pessoa7);
        pdao.salvar(pessoa8);
        pdao.salvar(pessoa9);

        ArrayList<Object> pessoas = pdao.listarTodosEagerLoading();

        System.out.println("Comecei a printar os objetos de pessoa criados a partir dos dados do BD\n");
        for (Object pessoa : pessoas) {
            System.out.println(pessoa);
            for (Telefone telefone : ((Pessoa)pessoa).getTelefones()) {
                System.out.println(telefone);
            }
        }


        Pessoa p = (Pessoa) pdao.buscarPorId(1);
        System.out.println("INICIO ########## Pessoa - ID1 - Antes da Atualização");
        System.out.println(p);
        System.out.println("FIM ########## Pessoa - ID1 - Antes da Atualização");
        p.setNome("Josefa Silva");
        pdao.atualizar(p);
        System.out.println("INICIO ########## Pessoa - ID1 - Depois da Atualização");
        System.out.println(p);
        System.out.println("FIM ########## Pessoa - ID1 - Depois da Atualização");
        pdao.excluir(pessoa9.getId());


        System.out.println("Acabei de printar os objetos de pessoa criados a partir dos dados do BD\n\n\n");



        Evento evento1 = new Evento(of(2023, 11, 10), "Pagar Contas");
        Evento evento2 = new Evento(of(2023, 11, 16), "Reunião de Acompanhamento");
        evento2.addPessoa(pessoa1);
        evento2.addPessoa(pessoa2);
        Evento evento3 = new Evento(of(2023, 11, 16), "Apresentação dos Resultados");
        evento3.addPessoa(pessoa1);
        evento3.addPessoa(pessoa2);
        evento3.addPessoa(pessoa3);

        System.out.println("Comecei a printar os objetos de evento criados em memoria\n");
        System.out.println(evento1);
        System.out.println(evento2);
        System.out.println(evento3);
        System.out.println("Acabei de printar os objetos de evento em memoria\n\n\n");

        EventoDAO edao = new EventoDAO(connection);

        edao.salvar(evento1);
        edao.salvar(evento2);
        edao.salvar(evento3);

        ArrayList<Object> eventos = edao.listarTodosEagerLoading();

        System.out.println("Comecei a printar os objetos de evento criados a partir dos dados do BD\n");
        for (Object evento : eventos) {
            System.out.println(evento);
            for (Pessoa pessoa : ((Evento)evento).getPessoas()) {
                System.out.println(pessoa);
                for (Telefone telefone : pessoa.getTelefones()) {
                    System.out.println(telefone);
                }
            }
        }
        System.out.println("Acabei de printar os objetos de evento criados a partir dos dados do BD\n\n\n");




    }

}