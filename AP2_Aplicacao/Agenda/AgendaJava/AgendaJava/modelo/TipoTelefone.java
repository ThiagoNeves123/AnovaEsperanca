package modelo;
public enum TipoTelefone{

    Celular(1), Residencial(2), Profissional(3);

    public int id;

    TipoTelefone(int id){
        this.id = id;
    }

} 