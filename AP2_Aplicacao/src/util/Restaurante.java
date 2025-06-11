package util;

import java.sql.Date;

public class Restaurante {
   private int idrestaurante;
   private String nome;
   private Local local;
   private Date datasql = Date.valueOf("2010-07-03");

   public Restaurante(int idrestaurante, String nome, Local local, Date datasql) {
      this.idrestaurante = idrestaurante;
      this.nome = nome;
      this.local = local;
      this.datasql = datasql;
   }

   public Restaurante() {
   }

   public String getNome() {
      return nome;
   }

   public int getIdrestaurante() {
      return idrestaurante;
   }

   public Local getLocal() {
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

   public void setLocal(Local local) {
      this.local = local;
   }

   public void setDatasql(Date datasql) {
      this.datasql = datasql;
   }
}