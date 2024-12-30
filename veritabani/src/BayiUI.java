import javax.swing.*;
import java.awt.event.ActionEvent;

public class BayiUI {
    private JFrame frame;
    private BayiManager bayiManager;
    private boolean canInsert; // Kullanıcının INSERT yetkisi olup olmadığını kontrol eder
    private boolean canUpdate;
    public BayiUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        bayiManager = new BayiManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Bayi Yönetimi");
        frame.setSize(600, 450);
        frame.setLayout(null);

        JLabel lblBayiID = new JLabel("Bayi ID:");
        lblBayiID.setBounds(20, 20, 100, 25);
        frame.add(lblBayiID);

        JTextField txtBayiID = new JTextField();
        txtBayiID.setBounds(150, 20, 150, 25);
        frame.add(txtBayiID);

        JLabel lblBayiAdi = new JLabel("Bayi Adı:");
        lblBayiAdi.setBounds(20, 60, 100, 25);
        frame.add(lblBayiAdi);

        JTextField txtBayiAdi = new JTextField();
        txtBayiAdi.setBounds(150, 60, 150, 25);
        frame.add(txtBayiAdi);

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
        // ----------------------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 270, 500, 100);
        frame.add(scrollPane);

        // Ekle butonu
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int bayiid = Integer.parseInt(txtBayiID.getText());
                String bayiadi = txtBayiAdi.getText();
                String sehir = txtSehir.getText();
                String adres = txtAdres.getText();
                String telno = txtTelNo.getText();

                bayiManager.insertBayi(bayiid, bayiadi, sehir, adres, telno);
                JOptionPane.showMessageDialog(frame, "Bayi kaydı başarıyla eklendi.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Hata: " + ex.getMessage());
            }
        });

        // Listele butonu
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : bayiManager.getBayiList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE BUTONU ACTION
        btnGuncelle.addActionListener(e -> {
            try {
                int bayiid = Integer.parseInt(txtBayiID.getText());
                String bayiadi = txtBayiAdi.getText();
                String sehir = txtSehir.getText();
                String adres = txtAdres.getText();
                String telno = txtTelNo.getText();

                // Güncelleme için BayiManager'daki updateBayi metodunu çağırıyoruz
                bayiManager.updateBayi(bayiid, bayiadi, sehir, adres, telno);

                JOptionPane.showMessageDialog(frame, "Bayi kaydı başarıyla güncellendi.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Güncelleme hatası: " + ex.getMessage());
            }
        });
        // ----------------------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
