import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
        // Veritabanından tablodaki tüm verileri alır
        public static List<Object[]> getTableData(String tableName) {
            List<Object[]> data = new ArrayList<>();
            String query = "SELECT * FROM " + tableName;

            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet rs = statement.executeQuery()) {

                int columnCount = rs.getMetaData().getColumnCount();

                // Satırları listeye ekle
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i); // Her sütunu bir Object olarak al
                    }
                    data.add(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        // Veritabanından sütun isimlerini alır
        public static String[] getColumnNames(String tableName) {
            String[] columnNames = {};
            String query = "SELECT * FROM " + tableName + " LIMIT 1";

            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet rs = statement.executeQuery()) {

                int columnCount = rs.getMetaData().getColumnCount();
                columnNames = new String[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    columnNames[i - 1] = rs.getMetaData().getColumnName(i); // Sütun isimlerini al
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return columnNames;
        }


    public static boolean deleteRecord(String tableName, String columnName, int value) {
        boolean isDeleted = false;

        // Dinamik sorgu oluştur
        String query = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Parametreyi ayarla
            statement.setInt(1, value);

            // Sorguyu çalıştır
            int rowsAffected = statement.executeUpdate();
            isDeleted = rowsAffected > 0; // Eğer 1 veya daha fazla satır silindiyse başarılı
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }





    public static double callFunction(String functionName) {
        double result = 0.0;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT " + functionName + "()")) {

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1); // Fonksiyonun döndürdüğü değer birinci sütunda
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
