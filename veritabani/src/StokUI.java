import javax.swing.*;
import java.awt.event.ActionEvent;

public class StokUI {
    private JFrame frame;
    private StokManager stokManager;
    private boolean canInsert;
    private boolean canUpdate;

    public StokUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        stokManager = new StokManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Stok Yönetimi");
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
        btnEkle.setVisible(this.canInsert); // canInsert kontrolü
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 140, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU EKLENDİ
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 140, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // --------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 190, 500, 150);
        frame.add(scrollPane);

        // Ekle butonu (INSERT)
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int urunid = Integer.parseInt(txtUrunID.getText());
                String urunadi = txtUrunAdi.getText();
                int urunadet = Integer.parseInt(txtUrunAdet.getText());

                stokManager.insertStok(urunid, urunadi, urunadet);
                JOptionPane.showMessageDialog(frame, "Stok kaydı başarıyla eklendi.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli değerler giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Hata: " + ex.getMessage());
            }
        });

        // Listele butonu (SELECT)
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : stokManager.getStokList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE (UPDATE) butonu
        btnGuncelle.addActionListener(e -> {
            try {
                int urunid = Integer.parseInt(txtUrunID.getText());
                String urunadi = txtUrunAdi.getText();
                int urunadet = Integer.parseInt(txtUrunAdet.getText());

                // StokManager'daki updateStok metodunu çağırıyoruz
                stokManager.updateStok(urunid, urunadi, urunadet);
                JOptionPane.showMessageDialog(frame, "Stok kaydı başarıyla güncellendi.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli değerler giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Güncelleme hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        // --------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
