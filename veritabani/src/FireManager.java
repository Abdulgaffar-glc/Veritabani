import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FireManager {

    // Yeni fire kaydı ekleme
    public void insertFire(int urunid, String urunadi, int urunadet) {
        String query = "INSERT INTO fire (urunid, urunadi, urunadet) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, urunid);
            ps.setString(2, urunadi);
            ps.setInt(3, urunadet);

            ps.executeUpdate();
            System.out.println("Fire kaydı başarıyla eklendi.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
            System.err.println("ÜrünID: " + urunid + " zaten mevcut. Farklı bir ID deneyin.");
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                System.err.println("ÜrünID: " + urunid + " zaten mevcut. Farklı bir ID deneyin.");
            } else {
                System.err.println("Fire eklenirken bir SQL hatası oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    // Fire verilerini listeleme
    public List<String> getFireList() {
        String query = "SELECT * FROM fire";
        List<String> fireList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "ÜrünID: " + rs.getInt("urunid") +
                        ", Ürün Adı: " + rs.getString("urunadi") +
                        ", Ürün Adet: " + rs.getInt("urunadet");
                fireList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Fire verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return fireList;
    }

    // Fire kaydını güncelleme (UPDATE)
    public void updateFire(int urunid, String yeniUrunAdi, int yeniUrunAdet) {
        String query = "UPDATE fire SET urunadi = ?, urunadet = ? WHERE urunid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Sorgudaki ? parametrelerini sırayla dolduruyoruz
            ps.setString(1, yeniUrunAdi);
            ps.setInt(2, yeniUrunAdet);
            ps.setInt(3, urunid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Fire kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı. (urunid: " + urunid + ")");
            }

        } catch (SQLException e) {
            System.err.println("Fire güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
