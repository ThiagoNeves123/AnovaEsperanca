package bd; // <--- Certifique-se de que este é o pacote correto da sua ConnectionFactory

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Método principal para obter a conexão
    public static Connection getConnection() throws SQLException {
        String sgbd = "mysql";
        String endereco = "localhost";
        String bd = "restaurante_avaliacao_db"; // <--- Verifique se este nome está correto e o DB existe
        String usuario = "root";
        String senha = "Thiago150798"; // <--- Verifique se esta é a senha correta do seu MySQL

        // Carrega o driver JDBC (boa prática, especialmente para drivers mais antigos)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado. Verifique suas dependências (ex: mysql-connector-java.jar).", e);
        }

        Connection connection = DriverManager.getConnection(
                "jdbc:" + sgbd + "://" + endereco + "/" + bd, usuario, senha);

        return connection;
    }

    // Método para fechar a conexão, para ser usado com try-with-resources ou manualmente
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // O método 'recuperaConexao' original pode ser removido ou alterado para chamar 'getConnection'
    // Ex: public Connection recuperaConexao() { return getConnection(); }
    // Mas é melhor removê-lo para evitar confusão.
}