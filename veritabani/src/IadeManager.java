import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IadeManager {

    // Yeni iade kaydı ekleme
    public void insertIade(int iadeid, int urunid, int preform, int kapak, int etiket, int toplamfiyat) {
        String query = "INSERT INTO iade (iadeid, urunid, preform, kapak, etiket, toplamfiyat) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, iadeid);
            ps.setInt(2, urunid);
            ps.setInt(3, preform);
            ps.setInt(4, kapak);
            ps.setInt(5, etiket);
            ps.setInt(6, toplamfiyat);

            ps.executeUpdate();
            System.out.println("İade kaydı başarıyla eklendi.");
        }catch (SQLIntegrityConstraintViolationException e) {
                System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                System.err.println("İadeID: " + iadeid + " zaten mevcut. Farklı bir ID deneyin.");
            } catch (SQLException e) {
                if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                    System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                    System.err.println("İadeID: " + iadeid + " zaten mevcut. Farklı bir ID deneyin.");
                } else {
                    System.err.println("İade eklenirken bir SQL hatası oluştu: " + e.getMessage());
                    e.printStackTrace();
                }
            }
    }

    // İade verilerini listeleme
    public List<String> getIadeList() {
        String query = "SELECT * FROM iade";
        List<String> iadeList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "İadeID: " + rs.getInt("iadeid") +
                        ", ÜrünID: " + rs.getInt("urunid") +
                        ", Preform: " + rs.getInt("preform") +
                        ", Kapak: " + rs.getInt("kapak") +
                        ", Etiket: " + rs.getInt("etiket") +
                        ", Toplam Fiyat: " + rs.getInt("toplamfiyat");
                iadeList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("İade verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return iadeList;
    }

    // İade kaydını güncelleme (UPDATE)
    public void updateIade(int iadeid, int urunid, int preform, int kapak, int etiket, int toplamfiyat) {
        String query = "UPDATE iade "
                + "SET urunid = ?, preform = ?, kapak = ?, etiket = ?, toplamfiyat = ? "
                + "WHERE iadeid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // ? parametrelerini sırasıyla dolduruyoruz
            ps.setInt(1, urunid);
            ps.setInt(2, preform);
            ps.setInt(3, kapak);
            ps.setInt(4, etiket);
            ps.setInt(5, toplamfiyat);
            ps.setInt(6, iadeid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("İade kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (iadeid: " + iadeid + ").");
            }

        } catch (SQLException e) {
            System.err.println("İade güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
