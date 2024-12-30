import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StokManager {

    // Stok ekleme işlemi (INSERT)
    public void insertStok(int urunid, String urunadi, int urunadet) {
        String query = "INSERT INTO stok (urunid, urunadi, urunadet) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, urunid);
            ps.setString(2, urunadi);
            ps.setInt(3, urunadet);

            ps.executeUpdate();
            System.out.println("Stok kaydı başarıyla eklendi.");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
            System.err.println("ÜrünID: " + urunid + " zaten mevcut. Farklı bir ID deneyin.");
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("unique constraint")) {
                System.err.println("Tekrarlanan anahtar hatası: " + e.getMessage());
                System.err.println("ÜrünID: " + urunid + " zaten mevcut. Farklı bir ID deneyin.");
            } else {
                System.err.println("Stok eklenirken bir SQL hatası oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    // Stok listeleme işlemi (SELECT)
    public List<String> getStokList() {
        String query = "SELECT * FROM stok";
        List<String> stokList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "ÜrünID: " + rs.getInt("urunid") +
                        ", Ürün Adı: " + rs.getString("urunadi") +
                        ", Ürün Adet: " + rs.getInt("urunadet");
                stokList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Stok verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return stokList;
    }

    // Stok güncelleme işlemi (UPDATE) - EKLENEN METOD
    public void updateStok(int urunid, String urunadi, int urunadet) {
        String query = "UPDATE stok "
                + "SET urunadi = ?, urunadet = ? "
                + "WHERE urunid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, urunadi);
            ps.setInt(2, urunadet);
            ps.setInt(3, urunid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Stok kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (urunid: " + urunid + ").");
            }
        } catch (SQLException e) {
            System.err.println("Stok güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
