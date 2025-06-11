package util;

public class Local {
    private int idLocal;
    private String cidade;
    private String bairro;
    private String rua;
    private int numero;
    private String cep; // Alterado para String para melhor compatibilidade com CEPs

    // Construtor completo com ID (para quando se recupera do DB)
    public Local(int idLocal, String cidade, String bairro, String rua, int numero, String cep) {
        this.idLocal = idLocal;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    // Construtor sem ID (para quando se cria um novo objeto para ser salvo no DB)
    public Local(String cidade, String bairro, String rua, int numero, String cep) {
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
    }

    // Construtor padrão (pode ser útil para frameworks ou inicialização básica)
    public Local() {
    }

    // Getters
    public int getIdLocal() {
        return idLocal;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    // Setters
    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "Local{" +
                "idLocal=" + idLocal +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numero=" + numero +
                ", cep='" + cep + '\'' +
                '}';
    }
}