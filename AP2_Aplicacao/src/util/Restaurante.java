package util;

import java.sql.Date;

public class Restaurante {
   private int idrestaurante;
   private String nome;
   private Local local; // Alterado de String para objeto Local
   private Date datasql = Date.valueOf("2010-07-03");

   // Construtor adicionado para permitir a criação do objeto com ID e Local
   public Restaurante(int idrestaurante, String nome, Local local, Date datasql) {
      this.idrestaurante = idrestaurante;
      this.nome = nome;
      this.local = local; // Atribui o objeto Local
      this.datasql = datasql;
   }

   // Construtor padrão
   public Restaurante() {
   }

   public String getNome() {
      return nome;
   }

   public int getIdrestaurante() {
      return idrestaurante;
   }

   public Local getLocal() { // Retorna um objeto Local
      return local;
   }

   public Date getDatasql() {
      return datasql;
   }

   public void setIdrestaurante(int idrestaurante) {
      this.idrestaurante = idrestaurante;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public void setLocal(Local local) { // Aceita um objeto Local
      this.local = local;
   }

   public void setDatasql(Date datasql) {
      this.datasql = datasql;
   }
}