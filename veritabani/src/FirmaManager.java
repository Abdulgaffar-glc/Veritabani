import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FirmaManager {

    // Yeni firma kaydı ekleme
    public void insertFirma(int firmaid, String firmaadi, String sehir, String adress, String telno) {
        String query = "INSERT INTO firma (firmaid, firmaadi, sehir, adress, telno) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, firmaid);
            ps.setString(2, firmaadi);
            ps.setString(3, sehir);
            ps.setString(4, adress);
            ps.setString(5, telno);

            ps.executeUpdate();
            System.out.println("Firma kaydı başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Firma eklenirken hata oluştu: " + e.getMessage());
        }
    }

    // Firma verilerini listeleme
    public List<String> getFirmaList() {
        String query = "SELECT * FROM firma";
        List<String> firmaList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "FirmaID: " + rs.getInt("firmaid") +
                        ", Firma Adı: " + rs.getString("firmaadi") +
                        ", Şehir: " + rs.getString("sehir") +
                        ", Adres: " + rs.getString("adress") +
                        ", Tel No: " + rs.getString("telno");
                firmaList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Firma verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return firmaList;
    }

    // Firma kaydını güncelleme (UPDATE)
    public void updateFirma(int firmaid, String firmaadi, String sehir, String adress, String telno) {
        String query = "UPDATE firma "
                + "SET firmaadi = ?, sehir = ?, adress = ?, telno = ? "
                + "WHERE firmaid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, firmaadi);
            ps.setString(2, sehir);
            ps.setString(3, adress);
            ps.setString(4, telno);
            ps.setInt(5, firmaid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Firma kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (firmaid: " + firmaid + ").");
            }

        } catch (SQLException e) {
            System.err.println("Firma güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
