package AP2_Aplicacao;

import dao.*;
import util.*;
import java.sql.Date;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();
        LocalDAO localDAO = new LocalDAO();
        RestauranteDAO restauranteDAO = new RestauranteDAO();
        AvaliacaoComidaDAO avaliacaoComidaDAO = new AvaliacaoComidaDAO();
        AvaliacaoAtendimentoDAO avaliacaoAtendimentoDAO = new AvaliacaoAtendimentoDAO();
        AvaliacaoAmbienteDAO avaliacaoAmbienteDAO = new AvaliacaoAmbienteDAO();
        AvaliacaoLocalizacaoDAO avaliacaoLocalizacaoDAO = new AvaliacaoLocalizacaoDAO();
        ClassificacaoDAO classificacaoDAO = new ClassificacaoDAO();

        System.out.println("CADASTRO CLIENTE:");

        Cliente cliente = new Cliente(
                "111.224.333-44",
                "Carlos Eduardo",
                "carlos.edu@email.com",
                "senhaForte123"
        );
        clienteDAO.salvar(cliente);
        System.out.println("Cliente salvo, ID: " + cliente.getIdcliente());

        System.out.println("\nCADASTRO LOCAL");

        Local local = new Local(
                "Rio de Janeiro",
                "Copacabana",
                "Avenida Atlântica",
                1702,
                "22021-001"
        );
        localDAO.salvar(local);
        System.out.println("Local salvo, ID: " + local.getIdLocal());

        System.out.println("\nCADASTRO RESTAURANTE");

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Cantina do Sabor");
        restaurante.setLocal(local);
        restaurante.setDatasql(new Date(Calendar.getInstance().getTimeInMillis()));
        restauranteDAO.salvar(restaurante);
        System.out.println("Restaurante salvo, ID: " + restaurante.getIdrestaurante());

        System.out.println("\nAVALIAÇÕES RESTAURANTE");

        AvaliacaoComida avaliacaoComida = new AvaliacaoComida(4.5f); // Nota 4.5
        avaliacaoComida.setCliente(cliente);
        avaliacaoComida.setRestaurante(restaurante);
        avaliacaoComidaDAO.salvar(avaliacaoComida);
        System.out.println("Avaliação de Comida salva, ID: " + avaliacaoComida.getIdAvaliacao());

        AvaliacaoAtendimento avaliacaoAtendimento = new AvaliacaoAtendimento(5.0f); // Nota 5.0
        avaliacaoAtendimento.setCliente(cliente);
        avaliacaoAtendimento.setRestaurante(restaurante);
        avaliacaoAtendimentoDAO.salvar(avaliacaoAtendimento);
        System.out.println("Avaliação de Atendimento salva, ID: " + avaliacaoAtendimento.getIdAvaliacao());

        AvaliacaoAmbiente avaliacaoAmbiente = new AvaliacaoAmbiente(4.0f); // Nota 4.0
        avaliacaoAmbiente.setCliente(cliente);
        avaliacaoAmbiente.setRestaurante(restaurante);
        avaliacaoAmbienteDAO.salvar(avaliacaoAmbiente);
        System.out.println("Avaliação de Ambiente salva, ID: " + avaliacaoAmbiente.getIdAvaliacao());

        AvaliacaoLocalizacao avaliacaoLocalizacao = new AvaliacaoLocalizacao(4.8f); // Nota 4.8
        avaliacaoLocalizacao.setCliente(cliente);
        avaliacaoLocalizacao.setRestaurante(restaurante);
        avaliacaoLocalizacaoDAO.salvar(avaliacaoLocalizacao);
        System.out.println("Avaliação de Localização salva, ID: " + avaliacaoLocalizacao.getIdAvaliacao());

        System.out.println("\nCLASSIFICAÇÃO FINAL");

        Classificacao calculadora = new Classificacao(
                restaurante,
                cliente,
                avaliacaoComida,
                avaliacaoAmbiente,
                avaliacaoAtendimento,
                avaliacaoLocalizacao
        );
        float notaFinalCalculada = calculadora.calcularClassificacao();
        System.out.println("Nota final calculada: " + String.format("%.2f", notaFinalCalculada));

        Classificacao classificacaoFinal = new Classificacao();
        classificacaoFinal.setRestaurante(restaurante);
        classificacaoFinal.setCliente(cliente);
        classificacaoFinal.setNotaFinal(notaFinalCalculada);
        classificacaoFinal.setDataClassificacao(new Date(Calendar.getInstance().getTimeInMillis())); // Data de hoje

        classificacaoDAO.salvar(classificacaoFinal);
        System.out.println("Classificação final salva, ID: " + classificacaoFinal.getIdClassificacao());


        System.out.println("\nPROCESSO FINALIZADO");

        System.out.println("\nVerificando classificação");
        Classificacao classificacaoRecuperada = (Classificacao) classificacaoDAO.buscarPorId(classificacaoFinal.getIdClassificacao());
        if (classificacaoRecuperada != null) {
            System.out.println("Recuperado: ID " + classificacaoRecuperada.getIdClassificacao() +
                    ", Restaurante: " + classificacaoRecuperada.getRestaurante().getNome() +
                    ", Cliente: " + classificacaoRecuperada.getCliente().getNome() +
                    ", Nota: " + classificacaoRecuperada.getNotaFinal());
        } else {
            System.out.println("Não foi possível recuperar");
        }
    }
}