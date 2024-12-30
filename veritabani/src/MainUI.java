import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainUI {
    private JFrame frame;
    private final String userRole;
    private final List<String> permissions;

    public MainUI(String userRole, List<String> permissions) {
        this.userRole = userRole;
        this.permissions = permissions;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Yetki Tabanlı Veri Yönetim Sistemi");
        frame.setSize(600, 700);
        frame.setLayout(null);

        JLabel lblWelcome = new JLabel("Hoş Geldiniz! Rolünüz: " + userRole);
        lblWelcome.setBounds(150, 20, 300, 25);
        frame.add(lblWelcome);
        frame = new JFrame("Yetki Tabanlı Veri Yönetim Sistemi");
        frame.setSize(600, 700);
        frame.setLayout(null);

        // Burada "Yalnızca yönetici güncelleme yapsın" mantığını tek satırda belirliyoruz:
        boolean isManager = userRole.equals("yonetici");

        int y = 60;

        // Aşağıdaki tablo if'lerinde eskisi gibi "canInsert" hesaplayın;
        // "isManager" değişkeni de "canUpdate" olarak verilecek.
        if (userRole.equals("yonetici")) {
            addButton("Fonksiyonlar", 220, e -> showFunctionsMenu());
        }


        if (userRole.equals("yonetici")) {
            addButton("Silme İşlemi", 300, e -> showDeleteMenu());
        }

        // ALIM
        if (permissions.contains("alim")) {
            addButton("Alım Yönetimi", y += 40, e -> {
                // Örnek: Alım'a ekleme izni veriyor musunuz?
                boolean canInsert = (userRole.equals("pazarlama") || userRole.equals("yonetici"));
                newAlimUI(canInsert, isManager);
            });
        }

        // SATIŞ
        if (permissions.contains("satis")) {
            addButton("Satış Yönetimi", y += 40, e -> {
                boolean canInsert = (userRole.equals("muhasebe") || isManager);
                newSatisUI(canInsert, isManager);
            });
        }

        // BAYI
        if (permissions.contains("bayi")) {
            addButton("Bayi Yönetimi", y += 40, e -> newBayiUI(isManager, isManager));
        }

        // FIRMA
        if (permissions.contains("firma")) {
            addButton("Firma Yönetimi", y += 40, e -> newFirmaUI(isManager, isManager));
        }

        // HAMMADDE
        if (permissions.contains("hammadde")) {
            addButton("Hammadde Yönetimi", y += 40, e -> newHammaddeUI(isManager, isManager));
        }

        // STOK
        if (permissions.contains("stok")) {
            // Eskisi gibi "yalnızca yönetici ekleyebilsin"
            addButton("Stok Yönetimi", y += 40, e -> newStokUI(isManager, isManager));
        }

        // FIRE
        if (permissions.contains("fire")) {
            boolean canInsert = userRole.equals("stok") || isManager;
            addButton("Fire Yönetimi", y += 40, e -> newFireUI(canInsert, isManager));
        }

        // IADE
        if (permissions.contains("iade")) {
            boolean canInsert = userRole.equals("muhasebe") || isManager;
            addButton("İade Yönetimi", y += 40, e -> newIadeUI(canInsert, isManager));
        }

        // ANAKASA
        if (permissions.contains("anakasa")) {
            addButton("Ana Kasa Yönetimi", y += 40, e -> newAnaKasaUI(isManager));
        }

        // URETIM
        if (permissions.contains("uretim")) {
            boolean canInsert = (userRole.equals("stok") || isManager);
            addButton("Üretim Yönetimi", y += 40, e -> newUretimUI(canInsert, isManager));
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private boolean validateTableAndColumn(String tableName) {
        if (!AllowedTables.TABLE_COLUMN_MAP.containsKey(tableName)) {
            JOptionPane.showMessageDialog(null, "Geçersiz Tablo Adı!", "Hata", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private void showDeleteMenu() {
        JFrame deleteFrame = new JFrame("Silme İşlemi");
        deleteFrame.setSize(800, 500);
        deleteFrame.setLayout(null);

        JLabel lblTable = new JLabel("Tablo Adı:");
        lblTable.setBounds(50, 20, 100, 25);
        deleteFrame.add(lblTable);

        JTextField txtTable = new JTextField();
        txtTable.setBounds(150, 20, 200, 25);
        deleteFrame.add(txtTable);

        JButton btnLoad = new JButton("Tabloyu Listele");
        btnLoad.setBounds(370, 20, 150, 25);
        deleteFrame.add(btnLoad);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 700, 300);
        deleteFrame.add(scrollPane);

        JLabel lblId = new JLabel("Silinecek ID:");
        lblId.setBounds(50, 400, 100, 25);
        deleteFrame.add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(150, 400, 200, 25);
        deleteFrame.add(txtId);

        JButton btnDelete = new JButton("Sil");
        btnDelete.setBounds(370, 400, 100, 30);
        deleteFrame.add(btnDelete);

        // Tabloyu listeleme işlemi
        btnLoad.addActionListener(e -> {
            String tableName = txtTable.getText().trim();

            // Tabloyu doğrula
            if (!AllowedTables.TABLE_COLUMN_MAP.containsKey(tableName)) {
                JOptionPane.showMessageDialog(deleteFrame, "Geçersiz Tablo Adı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tablo verilerini ve sütun isimlerini al
            List<Object[]> data = DBHelper.getTableData(tableName);
            String[] columnNames = DBHelper.getColumnNames(tableName);

            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(deleteFrame, "Tablo Boş veya Hatalı!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // JTable modelini ayarla
            Object[][] tableData = data.toArray(new Object[0][]);
            table.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        });

        // Silme işlemi
        btnDelete.addActionListener(e -> {
            String tableName = txtTable.getText().trim();
            String idText = txtId.getText().trim();

            if (!AllowedTables.TABLE_COLUMN_MAP.containsKey(tableName)) {
                JOptionPane.showMessageDialog(deleteFrame, "Geçersiz Tablo Adı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(deleteFrame, "Silinecek ID'yi girin!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(deleteFrame, "Geçersiz ID formatı! Lütfen bir sayı girin.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String columnName = AllowedTables.TABLE_COLUMN_MAP.get(tableName);

            boolean success = DBHelper.deleteRecord(tableName, columnName, id);
            if (success) {
                JOptionPane.showMessageDialog(deleteFrame, "Kayıt Başarıyla Silindi!");
            } else {
                JOptionPane.showMessageDialog(deleteFrame, "Silme İşlemi Başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteFrame.setVisible(true);
    }



    private void showFunctionsMenu() {
        JFrame functionFrame = new JFrame("Fonksiyonlar");
        functionFrame.setSize(300, 200);
        functionFrame.setLayout(null);

        // Toplam Alım Butonu
        JButton btnTopAlim = new JButton("Toplam Alım");
        btnTopAlim.setBounds(50, 50, 200, 30);
        btnTopAlim.addActionListener(e -> {
            double totalAlim = DBHelper.callFunction("top_alim");
            JOptionPane.showMessageDialog(functionFrame, "Toplam Alım: " + totalAlim);
        });
        functionFrame.add(btnTopAlim);

        // Toplam Satış Butonu
        JButton btnTopSatis = new JButton("Toplam Satış");
        btnTopSatis.setBounds(50, 100, 200, 30);
        btnTopSatis.addActionListener(e -> {
            double totalSatis = DBHelper.callFunction("top_satis");
            JOptionPane.showMessageDialog(functionFrame, "Toplam Satış: " + totalSatis);
        });
        functionFrame.add(btnTopSatis);

        functionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        functionFrame.setVisible(true);
    }


    // Basit bir helper metot: Buton ekle
    private void addButton(String text, int y, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(150, y, 200, 30);
        button.addActionListener(action);
        frame.add(button);
    }

    // ---------------------------------------------------------------------
    // Aşağıdaki metotlarda: "canInsert" tablo bazında, "canUpdate" = isManager
    // ---------------------------------------------------------------------
    private void newAlimUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new AlimUI(canInsert, canUpdate));
    }

    private void newSatisUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new SatisUI(canInsert, canUpdate));
    }

    private void newBayiUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new BayiUI(canInsert, canUpdate));
    }

    private void newFirmaUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new FirmaUI(canInsert, canUpdate));
    }

    private void newHammaddeUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new HammaddeUI(canInsert, canUpdate));
    }

    private void newStokUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new StokUI(canInsert, canUpdate));
    }

    private void newFireUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new FireUI(canInsert, canUpdate));
    }

    private void newIadeUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new IadeUI(canInsert, canUpdate));
    }

    private void newAnaKasaUI(boolean canUpdate) {
        // canInsert konusuna karar verebilirsiniz, diyelim true
        SwingUtilities.invokeLater(() -> new AnaKasaUI(true, canUpdate));
    }

    private void newUretimUI(boolean canInsert, boolean canUpdate) {
        SwingUtilities.invokeLater(() -> new UretimUI(canInsert, canUpdate));
    }

    // ----------------------------------------------------------------------

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Splash ekranını başlat
            AnimatedSplashScreen splash = new AnimatedSplashScreen(() -> {
                // Splash ekranı tamamlandığında kullanıcı girişini başlat
                UserManager userManager = new UserManager();
                String username = JOptionPane.showInputDialog("Kullanıcı Adı:");
                String password = JOptionPane.showInputDialog("Şifre:");

                if (userManager.authenticateUser(username, password)) {
                    new MainUI(userManager.getRole(), userManager.getPermissions());
                } else {
                    JOptionPane.showMessageDialog(null, "Giriş Başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
                    System.exit(0); // Başarısız girişte uygulamayı sonlandır
                }
            });
            splash.setVisible(true);
            splash.startAnimation();
        });
    }


}