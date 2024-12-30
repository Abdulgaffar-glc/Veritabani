import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BayiManager {

    // Yeni bayi kaydı ekleme
    public void insertBayi(int bayiid, String bayiadi, String sehir, String adress, String telno) {
        String query = "INSERT INTO bayi (bayiid, bayiadi, sehir, adress, telno) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, bayiid);
            ps.setString(2, bayiadi);
            ps.setString(3, sehir);
            ps.setString(4, adress);
            ps.setString(5, telno);

            ps.executeUpdate();
            System.out.println("Bayi kaydı başarıyla eklendi.");
        } catch (SQLException e) {
            System.err.println("Bayi eklenirken hata oluştu: " + e.getMessage());
        }
    }

    // Bayi verilerini listeleme
    public List<String> getBayiList() {
        String query = "SELECT * FROM bayi";
        List<String> bayiList = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String row = "BayiID: " + rs.getInt("bayiid") +
                        ", Bayi Adı: " + rs.getString("bayiadi") +
                        ", Şehir: " + rs.getString("sehir") +
                        ", Adres: " + rs.getString("adress") +
                        ", Tel No: " + rs.getString("telno");
                bayiList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Bayi verileri listelenirken hata oluştu: " + e.getMessage());
        }
        return bayiList;
    }

    // Bayi kaydını güncelleme (UPDATE)
    public void updateBayi(int bayiid, String bayiadi, String sehir, String adress, String telno) {
        String query = "UPDATE bayi "
                + "SET bayiadi = ?, sehir = ?, adress = ?, telno = ? "
                + "WHERE bayiid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, bayiadi);
            ps.setString(2, sehir);
            ps.setString(3, adress);
            ps.setString(4, telno);
            ps.setInt(5, bayiid);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Bayi kaydı başarıyla güncellendi.");
            } else {
                System.out.println("Güncellenecek kayıt bulunamadı (bayiid: " + bayiid + ").");
            }

        } catch (SQLException e) {
            System.err.println("Bayi güncellenirken hata oluştu: " + e.getMessage());
        }
    }
}
