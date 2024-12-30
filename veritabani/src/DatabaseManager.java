import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/a";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static Connection connection = null;

    // Veritabanı bağlantısı oluşturma
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Veritabanına başarıyla bağlandı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanına bağlanırken hata oluştu.");
        }
        return connection;
    }

    // Veritabanı bağlantısını kapatma
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Veritabanı bağlantısı kapatıldı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
