import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlimManager {

    // Yeni alım kaydı ekleme
    public void insertAlim(int alimid, int firmaid, int preform, int kapak, int etiket, double toplamfiyat, Date alimtarih, int urunid) {
        String query = "INSERT INTO alim (alimid, firmaid, preform, kapak, etiket, toplamfiyat, alimtarih, urunid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, alimid);
            ps.setInt(2, firmaid);
            ps.setInt(3, preform);
            ps.setInt(4, kapak);
            ps.setInt(5, etiket);
            ps.setDouble(6, toplamfiyat);
            ps.setDate(7, alimtarih);
            ps.setInt(8, urunid);

            ps.executeUpdate();
            System.out.println("Alım kaydı başarıyla eklendi.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
            System.err.println("AlımID: " + alimid + " zaten mevcut. Farklı bir ID deneyin.");
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                System.err.println("AlımID: " + alimid + " zaten mevcut. Farklı bir ID deneyin.");
            } else {
                System.err.println("Alım eklenirken bir SQL hatası oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    // Alım verilerini listeleme
    public List<String> getAlimList() {
        String query = "SELECT * FROM alim";
        List<String> alimList = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "AlımID: " + rs.getInt("alimid") +
                        ", FirmaID: " + rs.getInt("firmaid") +
                        ", Preform: " + rs.getInt("preform") +
                        ", Kapak: " + rs.getInt("kapak") +
                        ", Etiket: " + rs.getInt("etiket") +
                        ", Toplam Fiyat: " + rs.getDouble("toplamfiyat") +
                        ", Tarih: " + rs.getDate("alimtarih") +
                        ", ÜrünID: " + rs.getInt("urunid");
                alimList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Alım verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return alimList;
    }

    // Alım kaydını güncelleme (UPDATE) - EKLENEN METOD
    public void updateAlim(int alimid, int firmaid, int preform, int kapak,
                           int etiket, double toplamfiyat, Date alimtarih, int urunid) {

        String query = "UPDATE alim "
                + "SET firmaid = ?, preform = ?, kapak = ?, etiket = ?, "
                + "    toplamfiyat = ?, alimtarih = ?, urunid = ? "
                + "WHERE alimid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // ? parametrelerini sırasıyla dolduruyoruz
            ps.setInt(1, firmaid);
            ps.setInt(2, preform);
            ps.setInt(3, kapak);
            ps.setInt(4, etiket);
            ps.setDouble(5, toplamfiyat);
            ps.setDate(6, alimtarih);
            ps.setInt(7, urunid);
            ps.setInt(8, alimid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Alım kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (alimid: " + alimid + ").");
            }
        } catch (SQLException e) {
            System.err.println("Alım güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
