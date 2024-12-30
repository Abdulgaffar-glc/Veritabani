import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresDatabaseApp {
    // JDBC URL, Kullanıcı adı ve Parola bilgileri
    private static final String URL = "jdbc:postgresql://localhost:5432/a";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        // Bağlantı oluşturma
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Bağlantı başarılı!");

            // Veritabanında bir sorgu çalıştırma
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM anakasa");

            // Verileri okuma ve yazdırma
            while (rs.next()) {
                System.out.println("Satır: " + rs.getString("bakiye"));
            }

        } catch (SQLException e) {
            System.out.println("Bağlantı sırasında hata oluştu: " + e.getMessage());
        }
    }
}
