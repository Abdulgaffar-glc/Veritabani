import javax.swing.*;
import java.awt.event.ActionEvent;

public class FirmaUI {
    private JFrame frame;
    private FirmaManager firmaManager;
    private boolean canInsert; // Kullanıcının INSERT yetkisi olup olmadığını kontrol eder
    private boolean canUpdate;
    public FirmaUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        firmaManager = new FirmaManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Firma Yönetimi");
        frame.setSize(600, 450);
        frame.setLayout(null);

        JLabel lblFirmaID = new JLabel("Firma ID:");
        lblFirmaID.setBounds(20, 20, 100, 25);
        frame.add(lblFirmaID);

        JTextField txtFirmaID = new JTextField();
        txtFirmaID.setBounds(150, 20, 150, 25);
        frame.add(txtFirmaID);

        JLabel lblFirmaAdi = new JLabel("Firma Adı:");
        lblFirmaAdi.setBounds(20, 60, 100, 25);
        frame.add(lblFirmaAdi);

        JTextField txtFirmaAdi = new JTextField();
        txtFirmaAdi.setBounds(150, 60, 150, 25);
        frame.add(txtFirmaAdi);

        JLabel lblSehir = new JLabel("Şehir:");
        lblSehir.setBounds(20, 100, 100, 25);
        frame.add(lblSehir);

        JTextField txtSehir = new JTextField();
        txtSehir.setBounds(150, 100, 150, 25);
        frame.add(txtSehir);

        JLabel lblAdres = new JLabel("Adres:");
        lblAdres.setBounds(20, 140, 100, 25);
        frame.add(lblAdres);

        JTextField txtAdres = new JTextField();
        txtAdres.setBounds(150, 140, 150, 25);
        frame.add(txtAdres);

        JLabel lblTelNo = new JLabel("Telefon No:");
        lblTelNo.setBounds(20, 180, 100, 25);
        frame.add(lblTelNo);

        JTextField txtTelNo = new JTextField();
        txtTelNo.setBounds(150, 180, 150, 25);
        frame.add(txtTelNo);

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(20, 220, 100, 30);
        btnEkle.setVisible(this.canInsert);
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 220, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU EKLENDİ
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 220, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // ---------------------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 270, 500, 100);
        frame.add(scrollPane);

        // Ekle butonu (INSERT)
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int firmaid = Integer.parseInt(txtFirmaID.getText());
                String firmaadi = txtFirmaAdi.getText();
                String sehir = txtSehir.getText();
                String adres = txtAdres.getText();
                String telno = txtTelNo.getText();

                firmaManager.insertFirma(firmaid, firmaadi, sehir, adres, telno);
                JOptionPane.showMessageDialog(frame, "Firma kaydı başarıyla eklendi.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Hata: " + ex.getMessage());
            }
        });

        // Listele butonu (SELECT)
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : firmaManager.getFirmaList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE BUTONU ACTION (UPDATE)
        btnGuncelle.addActionListener(e -> {
            try {
                int firmaid = Integer.parseInt(txtFirmaID.getText());
                String firmaadi = txtFirmaAdi.getText();
                String sehir = txtSehir.getText();
                String adres = txtAdres.getText();
                String telno = txtTelNo.getText();

                // Güncelleme için FirmaManager'daki updateFirma metodunu çağırıyoruz
                firmaManager.updateFirma(firmaid, firmaadi, sehir, adres, telno);

                JOptionPane.showMessageDialog(frame, "Firma kaydı başarıyla güncellendi.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Güncelleme hatası: " + ex.getMessage());
            }
        });
        // ---------------------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
