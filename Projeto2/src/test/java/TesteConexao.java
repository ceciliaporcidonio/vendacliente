import com.seu_projeto.conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import static com.seu_projeto.conexao.ConnectionFactory.getConnection;

public class TesteConexao {
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados.");
        }
    }
}
