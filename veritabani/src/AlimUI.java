import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class AlimUI {
    private boolean canInsert;
    private boolean canUpdate;
    private JFrame frame;
    private AlimManager alimManager;

    public AlimUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        alimManager = new AlimManager();
        initialize();

    }

    private void initialize() {
        frame = new JFrame("Alım Yönetimi");
        frame.setSize(600, 500);
        frame.setLayout(null);

        JLabel lblAlimID = new JLabel("Alım ID:");
        lblAlimID.setBounds(20, 20, 100, 25);
        frame.add(lblAlimID);

        JTextField txtAlimID = new JTextField();
        txtAlimID.setBounds(150, 20, 150, 25);
        frame.add(txtAlimID);

        JLabel lblFirmaID = new JLabel("Firma ID:");
        lblFirmaID.setBounds(20, 60, 100, 25);
        frame.add(lblFirmaID);

        JTextField txtFirmaID = new JTextField();
        txtFirmaID.setBounds(150, 60, 150, 25);
        frame.add(txtFirmaID);

        JLabel lblPreform = new JLabel("Preform:");
        lblPreform.setBounds(20, 100, 100, 25);
        frame.add(lblPreform);

        JTextField txtPreform = new JTextField();
        txtPreform.setBounds(150, 100, 150, 25);
        frame.add(txtPreform);

        JLabel lblKapak = new JLabel("Kapak:");
        lblKapak.setBounds(20, 140, 100, 25);
        frame.add(lblKapak);

        JTextField txtKapak = new JTextField();
        txtKapak.setBounds(150, 140, 150, 25);
        frame.add(txtKapak);

        JLabel lblEtiket = new JLabel("Etiket:");
        lblEtiket.setBounds(20, 180, 100, 25);
        frame.add(lblEtiket);

        JTextField txtEtiket = new JTextField();
        txtEtiket.setBounds(150, 180, 150, 25);
        frame.add(txtEtiket);

        JLabel lblToplamFiyat = new JLabel("Toplam Fiyat:");
        lblToplamFiyat.setBounds(20, 220, 100, 25);
        frame.add(lblToplamFiyat);

        JTextField txtToplamFiyat = new JTextField();
        txtToplamFiyat.setBounds(150, 220, 150, 25);
        frame.add(txtToplamFiyat);

        JLabel lblAlimTarih = new JLabel("Alım Tarihi (YYYY-MM-DD):");
        lblAlimTarih.setBounds(20, 260, 150, 25);
        frame.add(lblAlimTarih);

        JTextField txtAlimTarih = new JTextField();
        txtAlimTarih.setBounds(180, 260, 120, 25);
        frame.add(txtAlimTarih);

        // Yeni olarak urunid ekliyoruz
        JLabel lblUrunID = new JLabel("Ürün ID:");
        lblUrunID.setBounds(20, 300, 100, 25);

        frame.add(lblUrunID);

        JTextField txtUrunID = new JTextField();
        txtUrunID.setBounds(150, 300, 150, 25);
        frame.add(txtUrunID);

        JButton btnEkle = new JButton("Ekle");

        btnEkle.setBounds(20, 340, 100, 30);
        btnEkle.setVisible(this.canInsert);
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 340, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU
        JButton btnGuncelle = new JButton("Güncelle");

        btnGuncelle.setBounds(260, 340, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // ------------------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 380, 500, 100);
        frame.add(scrollPane);

        // EKLE (INSERT) butonu
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                // Kullanıcıdan alınan değerler
                int alimid = Integer.parseInt(txtAlimID.getText());
                int firmaid = Integer.parseInt(txtFirmaID.getText());
                int preform = Integer.parseInt(txtPreform.getText());
                int kapak = Integer.parseInt(txtKapak.getText());
                int etiket = Integer.parseInt(txtEtiket.getText());
                double toplamfiyat = Double.parseDouble(txtToplamFiyat.getText());
                Date alimtarih = Date.valueOf(txtAlimTarih.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());

                // Ekleme işlemi
                alimManager.insertAlim(alimid, firmaid, preform, kapak, etiket, toplamfiyat, alimtarih, urunid);
                JOptionPane.showMessageDialog(frame, "Alım kaydı başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Tarih formatı hatalı! Lütfen YYYY-MM-DD formatında tarih girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        // LİSTELE (SELECT) butonu
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : alimManager.getAlimList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE (UPDATE) butonu
        btnGuncelle.addActionListener(e -> {
            try {
                int alimid = Integer.parseInt(txtAlimID.getText());
                int firmaid = Integer.parseInt(txtFirmaID.getText());
                int preform = Integer.parseInt(txtPreform.getText());
                int kapak = Integer.parseInt(txtKapak.getText());
                int etiket = Integer.parseInt(txtEtiket.getText());
                double toplamfiyat = Double.parseDouble(txtToplamFiyat.getText());
                Date alimtarih = Date.valueOf(txtAlimTarih.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());

                alimManager.updateAlim(alimid, firmaid, preform, kapak, etiket, toplamfiyat, alimtarih, urunid);
                JOptionPane.showMessageDialog(frame, "Alım kaydı başarıyla güncellendi.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Giriş formatı hatalı: Lütfen sayısal değerleri doğru girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Tarih formatı hatalı. Lütfen YYYY-MM-DD formatını kullanın.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ------------------------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
