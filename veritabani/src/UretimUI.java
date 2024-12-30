import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class UretimUI {
    private JFrame frame;
    private UretimManager uretimManager;
    private boolean canInsert; // Kullanıcının INSERT yetkisi olup olmadığını kontrol eder
    private boolean canUpdate;
    public UretimUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        uretimManager = new UretimManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Üretim Yönetimi");
        frame.setSize(600, 400);
        frame.setLayout(null);

        JLabel lblUretimID = new JLabel("Üretim ID:");
        lblUretimID.setBounds(20, 20, 100, 25);
        frame.add(lblUretimID);

        JTextField txtUretimID = new JTextField();
        txtUretimID.setBounds(150, 20, 150, 25);
        frame.add(txtUretimID);

        JLabel lblUrunID = new JLabel("Ürün ID:");
        lblUrunID.setBounds(20, 60, 100, 25);
        frame.add(lblUrunID);

        JTextField txtUrunID = new JTextField();
        txtUrunID.setBounds(150, 60, 150, 25);
        frame.add(txtUrunID);

        JLabel lblUrunAdet = new JLabel("Ürün Adet:");
        lblUrunAdet.setBounds(20, 100, 100, 25);
        frame.add(lblUrunAdet);

        JTextField txtUrunAdet = new JTextField();
        txtUrunAdet.setBounds(150, 100, 150, 25);
        frame.add(txtUrunAdet);

        JLabel lblTarih = new JLabel("Tarih (YYYY-MM-DD):");
        lblTarih.setBounds(20, 140, 150, 25);
        frame.add(lblTarih);

        JTextField txtTarih = new JTextField();
        txtTarih.setBounds(180, 140, 120, 25);
        frame.add(txtTarih);

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(20, 180, 100, 30);
        btnEkle.setVisible(this.canInsert);
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 180, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 180, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // ---------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 230, 500, 100);
        frame.add(scrollPane);

        // Ekle (INSERT) butonu
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int uretimid = Integer.parseInt(txtUretimID.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());
                int urunadet = Integer.parseInt(txtUrunAdet.getText());
                Date uretimtarih = Date.valueOf(txtTarih.getText());

                uretimManager.insertUretim(uretimid, urunid, urunadet, uretimtarih);
                JOptionPane.showMessageDialog(frame, "Üretim kaydı başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Tarih formatı hatalı! Lütfen YYYY-MM-DD formatında tarih girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Listele (SELECT) butonu
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : uretimManager.getUretimList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE (UPDATE) butonu
        btnGuncelle.addActionListener((ActionEvent e) -> {
            try {
                int uretimid = Integer.parseInt(txtUretimID.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());
                int urunadet = Integer.parseInt(txtUrunAdet.getText());
                Date uretimtarih = Date.valueOf(txtTarih.getText());

                uretimManager.updateUretim(uretimid, urunid, urunadet, uretimtarih);
                JOptionPane.showMessageDialog(frame, "Üretim kaydı başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Tarih formatı hatalı! Lütfen YYYY-MM-DD formatında tarih girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // -----------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
