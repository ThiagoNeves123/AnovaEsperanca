package modelo;
public class Telefone{

    private int id;
    private TipoTelefone tipo;
    private int codigoPais;
    private int codigoArea;
    private int numero;


    public Telefone(TipoTelefone tipo, int codigoPais, int codigoArea, int numero) {
        this.tipo = tipo;
        this.codigoPais = codigoPais;
        this.codigoArea = codigoArea;
        this.numero = numero;
    }

    public Telefone(int id, TipoTelefone tipo, int codigoPais, int codigoArea, int numero) {
        this.id = id;
        this.tipo = tipo;
        this.codigoPais = codigoPais;
        this.codigoArea = codigoArea;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoTelefone getTipo() {
        return tipo;
    }

    public int getCodigoPais() {
        return codigoPais;
    }

    public int getCodigoArea() {
        return codigoArea;
    }
    
    public int getNumero() {
        return numero;
    }


    @Override
    public String toString() {
        return "{'telefone':{'id': "+this.id+", 'tipo': '"+this.tipo+"', 'numero': '+"+this.codigoPais+this.codigoArea+this.numero+"'}}";
    }
}