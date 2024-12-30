import javax.swing.*;
import java.awt.event.ActionEvent;

public class IadeUI {
    private JFrame frame;
    private IadeManager iadeManager;

    // <-- EKLENDİ: canInsert ve canUpdate değişkenleri
    private boolean canInsert;
    private boolean canUpdate;

    public IadeUI(boolean canInsert, boolean canUpdate) {
        // Parametrelerden gelen değerleri saklıyoruz
        this.canInsert = canInsert;   // Ekleme izni
        this.canUpdate = canUpdate;   // Güncelleme izni

        iadeManager = new IadeManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("İade Yönetimi");
        frame.setSize(600, 400);
        frame.setLayout(null);

        JLabel lblIadeID = new JLabel("İade ID:");
        lblIadeID.setBounds(20, 20, 100, 25);
        frame.add(lblIadeID);

        JTextField txtIadeID = new JTextField();
        txtIadeID.setBounds(150, 20, 150, 25);
        frame.add(txtIadeID);

        JLabel lblUrunID = new JLabel("Ürün ID:");
        lblUrunID.setBounds(20, 60, 100, 25);
        frame.add(lblUrunID);

        JTextField txtUrunID = new JTextField();
        txtUrunID.setBounds(150, 60, 150, 25);
        frame.add(txtUrunID);

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

        // "Ekle" butonu
        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(20, 260, 100, 30);

        // <-- EKLENDİ: Ekle butonunun görünürlük/etkinliği
        btnEkle.setVisible(this.canInsert);  // ya da setEnabled(canInsert);
        // -----------------------------------------
        frame.add(btnEkle);

        // "Listele" butonu
        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 260, 100, 30);
        frame.add(btnListele);

        // "Güncelle" butonu
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 260, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 300, 500, 100);
        frame.add(scrollPane);

        // Ekle (INSERT) butonunun Action
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int iadeid = Integer.parseInt(txtIadeID.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());
                int preform = Integer.parseInt(txtPreform.getText());
                int kapak = Integer.parseInt(txtKapak.getText());
                int etiket = Integer.parseInt(txtEtiket.getText());
                int toplamfiyat = Integer.parseInt(txtToplamFiyat.getText());

                iadeManager.insertIade(iadeid, urunid, preform, kapak, etiket, toplamfiyat);
                JOptionPane.showMessageDialog(frame, "İade kaydı başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Listele (SELECT) butonunun Action
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : iadeManager.getIadeList()) {
                textArea.append(row + "\n");
            }
        });

        // Güncelle (UPDATE) butonunun Action
        btnGuncelle.addActionListener(e -> {
            try {
                // canUpdate kontrolü
                if (!this.canUpdate) {
                    JOptionPane.showMessageDialog(frame, "Güncelleme yetkiniz yok!", "Yetki Hatası", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int iadeid = Integer.parseInt(txtIadeID.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());
                int preform = Integer.parseInt(txtPreform.getText());
                int kapak = Integer.parseInt(txtKapak.getText());
                int etiket = Integer.parseInt(txtEtiket.getText());
                int toplamfiyat = Integer.parseInt(txtToplamFiyat.getText());

                iadeManager.updateIade(iadeid, urunid, preform, kapak, etiket, toplamfiyat);
                JOptionPane.showMessageDialog(frame, "İade kaydı başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
