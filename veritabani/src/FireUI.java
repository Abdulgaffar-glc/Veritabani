import javax.swing.*;
import java.awt.event.ActionEvent;

public class FireUI {
    private JFrame frame;
    private FireManager fireManager;
    private boolean canInsert; // Kullanıcının INSERT yetkisi olup olmadığını kontrol eder
    private boolean canUpdate;
    public FireUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        fireManager = new FireManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Fire Yönetimi");
        frame.setSize(600, 400);
        frame.setLayout(null);

        JLabel lblUrunID = new JLabel("Ürün ID:");
        lblUrunID.setBounds(20, 20, 100, 25);
        frame.add(lblUrunID);

        JTextField txtUrunID = new JTextField();
        txtUrunID.setBounds(150, 20, 150, 25);
        frame.add(txtUrunID);

        JLabel lblUrunAdi = new JLabel("Ürün Adı:");
        lblUrunAdi.setBounds(20, 60, 100, 25);
        frame.add(lblUrunAdi);

        JTextField txtUrunAdi = new JTextField();
        txtUrunAdi.setBounds(150, 60, 150, 25);
        frame.add(txtUrunAdi);

        JLabel lblUrunAdet = new JLabel("Ürün Adet:");
        lblUrunAdet.setBounds(20, 100, 100, 25);
        frame.add(lblUrunAdet);

        JTextField txtUrunAdet = new JTextField();
        txtUrunAdet.setBounds(150, 100, 150, 25);
        frame.add(txtUrunAdet);

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(20, 140, 100, 30);
        btnEkle.setVisible(this.canUpdate); // INSERT yetkisi yoksa butonu devre dışı bırakır
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 140, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU EKLENİYOR
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 140, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // ------------------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 190, 500, 150);
        frame.add(scrollPane);

        // EKLE butonu - INSERT işlemi
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int urunid = Integer.parseInt(txtUrunID.getText());
                String urunadi = txtUrunAdi.getText();
                int urunadet = Integer.parseInt(txtUrunAdet.getText());

                fireManager.insertFire(urunid, urunadi, urunadet);
                JOptionPane.showMessageDialog(frame, "Fire kaydı başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        // LİSTELE butonu - SELECT işlemi
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : fireManager.getFireList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE butonu - UPDATE işlemi
        btnGuncelle.addActionListener(e -> {
            try {
                int urunid = Integer.parseInt(txtUrunID.getText());
                String urunadi = txtUrunAdi.getText();
                int urunadet = Integer.parseInt(txtUrunAdet.getText());

                fireManager.updateFire(urunid, urunadi, urunadet);

                JOptionPane.showMessageDialog(frame, "Fire kaydı başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // -----------------------------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
