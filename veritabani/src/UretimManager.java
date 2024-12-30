import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UretimManager {
        // Yeni üretim kaydı ekleme
        public void insertUretim(int uretimid, int urunid, int urunadet, Date uretimtarih) {
            String query = "INSERT INTO uretim (uretimid, urunid, urunadet, uretimtarih) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setInt(1, uretimid);
                ps.setInt(2, urunid);
                ps.setInt(3, urunadet);
                ps.setDate(4, uretimtarih);

                ps.executeUpdate();
                System.out.println("Üretim kaydı başarıyla eklendi.");
            } catch (SQLIntegrityConstraintViolationException e) {
                System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                System.err.println("ÜretimID: " + uretimid + " zaten mevcut. Farklı bir ID deneyin.");
            } catch (SQLException e) {
                if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                    System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                    System.err.println("ÜretimID: " + uretimid + " zaten mevcut. Farklı bir ID deneyin.");
                } else {
                    System.err.println("Üretim eklenirken bir SQL hatası oluştu: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    // Üretim verilerini listeleme
    public List<String> getUretimList() {
        String query = "SELECT * FROM uretim";
        List<String> uretimList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "ÜretimID: " + rs.getInt("uretimid") +
                        ", ÜrünID: " + rs.getInt("urunid") +
                        ", Ürün Adet: " + rs.getInt("urunadet") +
                        ", Üretim Tarihi: " + rs.getDate("uretimtarih");
                uretimList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Üretim verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return uretimList;
    }

    // Üretim kaydını güncelleme (UPDATE) - EKLENEN METOD
    public void updateUretim(int uretimid, int urunid, int urunadet, Date uretimtarih) {
        String query = "UPDATE uretim "
                + "SET urunid = ?, urunadet = ?, uretimtarih = ? "
                + "WHERE uretimid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // ? parametrelerini sırayla doldur
            ps.setInt(1, urunid);
            ps.setInt(2, urunadet);
            ps.setDate(3, uretimtarih);
            ps.setInt(4, uretimid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Üretim kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (uretimid: " + uretimid + ").");
            }
        } catch (SQLException e) {
            System.err.println("Üretim güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
