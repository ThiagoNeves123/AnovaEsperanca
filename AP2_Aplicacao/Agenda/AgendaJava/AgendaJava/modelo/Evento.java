package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {

    private int id;
    private LocalDate data;
    private String descricao;
    private ArrayList<Pessoa> pessoas;


    public Evento(LocalDate data, String descricao) {
        this.data = data;
        this.descricao = descricao;
        this.pessoas = new ArrayList<Pessoa>();
    }

    public Evento(int id, LocalDate data, String descricao) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.pessoas = new ArrayList<Pessoa>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public ArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public void addPessoa(Pessoa pessoa) {
        this.pessoas.add(pessoa);
    }


    @Override
    public String toString() {
        return "{'evento':{'id': " + this.id + ", 'data': '" + this.data + "', 'descricao': '" + this.descricao + "'}}";
    }

}
