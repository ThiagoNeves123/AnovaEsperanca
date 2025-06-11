package util;

import dao.AvaliacaoAtendimentoDAO;

public interface ClienteInterface {
    void autenticacao();
    void avaliar(AvaliacaoAtendimentoDAO atendimento, AvaliacaoComida comida,
                 AvaliacaoAmbiente ambiente, AvaliacaoLocalizacao local);
    String getCpf();
    String getNome();
    String getEmail();
    String getSenha();
    void setId(int idcliente);
}
