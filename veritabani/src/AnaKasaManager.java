import java.sql.*;

public class AnaKasaManager {

    // Ana kasa bakiyesini görüntüleme
    public int getBakiye() {
        String query = "SELECT bakiye FROM anakasa LIMIT 1";
        int bakiye = 0;

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                bakiye = rs.getInt("bakiye");
            }
        } catch (SQLException e) {
            System.err.println("Bakiye verisi alınırken hata oluştu: " + e.getMessage());
        }
        return bakiye;
    }

    // Ana kasa bakiyesini güncelleme
    public void updateBakiye(int yeniBakiye) {
        String query = "UPDATE anakasa SET bakiye = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, yeniBakiye);
            int updatedRows = ps.executeUpdate();

            if (updatedRows > 0) {
                System.out.println("Bakiye başarıyla güncellendi.");
            } else {
                System.out.println("Bakiye güncellenemedi.");
            }
        } catch (SQLException e) {
            System.err.println("Bakiye güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
