import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HammaddeManager {

    // Yeni hammadde kaydı ekleme
    public void insertHammadde(int urunid, int preform, int kapak, int etiket) {
        String query = "INSERT INTO hammadde (urunid, preform, kapak, etiket) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, urunid);
            ps.setInt(2, preform);
            ps.setInt(3, kapak);
            ps.setInt(4, etiket);

            ps.executeUpdate();
            System.out.println("Hammadde kaydı başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Hammadde eklenirken hata oluştu: " + e.getMessage());
        }
    }

    // Hammadde verilerini listeleme
    public List<String> getHammaddeList() {
        String query = "SELECT * FROM hammadde";
        List<String> hammaddeList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "ÜrünID: " + rs.getInt("urunid") +
                        ", Preform: " + rs.getInt("preform") +
                        ", Kapak: " + rs.getInt("kapak") +
                        ", Etiket: " + rs.getInt("etiket");
                hammaddeList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Hammadde verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return hammaddeList;
    }

    // Hammadde kaydını güncelleme (UPDATE)
    public void updateHammadde(int urunid, int preform, int kapak, int etiket) {
        // urunid’ye göre preform, kapak, etiket alanlarını güncelle
        String query = "UPDATE hammadde "
                + "SET preform = ?, kapak = ?, etiket = ? "
                + "WHERE urunid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, preform);
            ps.setInt(2, kapak);
            ps.setInt(3, etiket);
            ps.setInt(4, urunid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Hammadde kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (urunid: " + urunid + ").");
            }
        } catch (SQLException e) {
            System.err.println("Hammadde güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
