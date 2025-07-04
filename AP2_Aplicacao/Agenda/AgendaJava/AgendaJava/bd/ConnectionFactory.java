package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

  public Connection recuperaConexao() {
    try {
      String sgbd = "mysql";
      String endereco = "localhost";
      String bd = "agenda";
      String usuario = "root";
      String senha = "mysqlroot";

      Connection connection = DriverManager.getConnection(
          "jdbc:" + sgbd + "://" + endereco + "/" + bd, usuario, senha);

      return connection;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}