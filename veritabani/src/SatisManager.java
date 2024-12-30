import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SatisManager {

    // Yeni satış kaydı ekleme
    public void insertSatis(int satisid, int urunid, int bayiid, int urunadet, double toplamfiyat, Date satistarih) {
        String query = "INSERT INTO satis (satisid, urunid, bayiid, urunadet, toplamfiyat, satistarih) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, satisid);
            ps.setInt(2, urunid);
            ps.setInt(3, bayiid);
            ps.setInt(4, urunadet);
            ps.setDouble(5, toplamfiyat);
            ps.setDate(6, satistarih);

            ps.executeUpdate();
            System.out.println("Satış kaydı başarıyla eklendi.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
            System.err.println("SatışID: " + satisid + " zaten mevcut. Farklı bir ID deneyin.");
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                System.err.println("SatışID: " + satisid + " zaten mevcut. Farklı bir ID deneyin.");
            } else {
                System.err.println("Satış eklenirken bir SQL hatası oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    // Satış verilerini listeleme
    public List<String> getSatisList() {
        String query = "SELECT * FROM satis";
        List<String> satisList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "SatışID: " + rs.getInt("satisid") +
                        ", ÜrünID: " + rs.getInt("urunid") +
                        ", BayiID: " + rs.getInt("bayiid") +
                        ", Ürün Adet: " + rs.getInt("urunadet") +
                        ", Toplam Fiyat: " + rs.getDouble("toplamfiyat") +
                        ", Satış Tarihi: " + rs.getDate("satistarih");
                satisList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Satış verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return satisList;
    }

    // Satış kaydını güncelleme (UPDATE)
    public void updateSatis(int satisid, int urunid, int bayiid, int urunadet, double toplamfiyat, Date satistarih) {
        String query = "UPDATE satis "
                + "SET urunid = ?, bayiid = ?, urunadet = ?, toplamfiyat = ?, satistarih = ? "
                + "WHERE satisid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // ? parametrelerini sırasıyla dolduruyoruz
            ps.setInt(1, urunid);
            ps.setInt(2, bayiid);
            ps.setInt(3, urunadet);
            ps.setDouble(4, toplamfiyat);
            ps.setDate(5, satistarih);
            ps.setInt(6, satisid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Satış kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (satisid: " + satisid + ").");
            }

        } catch (SQLException e) {
            System.err.println("Satış güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
