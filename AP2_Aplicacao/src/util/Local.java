package util;

public class Local {
    private int idLocal; // Adicionado ID para o DAO
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String cep; // Alterado para String, pois CEPs podem ter hífens e zeros à esquerda

    // Construtor completo
    public Local(int idLocal, String cidade, String bairro, String rua, int numero, String cep) {
        this.idLocal = idLocal;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    // Construtor sem ID (para quando o ID é gerado pelo DAO)
    public Local(String cidade, String bairro, String rua, int numero, String cep) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    // Construtor padrão
    public Local() {
    }

    // Getters e Setters
    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Local [ID=" + idLocal + ", Cidade=" + cidade + ", Bairro=" + bairro + ", Rua=" + rua + ", Número=" + numero + ", CEP=" + cep + "]";
    }
}