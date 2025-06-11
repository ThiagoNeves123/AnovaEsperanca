package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() throws SQLException {
        String sgbd = "mysql";
        String endereco = "localhost";
        String bd = "restaurante_avaliacao_db";
        String usuario = "root";
        String senha = "Thiago150798";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado. Verifique suas dependências (ex: mysql-connector-java.jar).", e);
        }

        Connection connection = DriverManager.getConnection(
                "jdbc:" + sgbd + "://" + endereco + "/" + bd, usuario, senha);

        return connection;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}