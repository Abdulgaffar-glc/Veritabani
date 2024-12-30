import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class SatisUI {
    private JFrame frame;
    private SatisManager satisManager;
    private boolean canInsert;
    private boolean canUpdate;
    public SatisUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        satisManager = new SatisManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Satış Yönetimi");
        frame.setSize(600, 500);
        frame.setLayout(null);

        JLabel lblSatisID = new JLabel("Satış ID:");
        lblSatisID.setBounds(20, 20, 100, 25);
        frame.add(lblSatisID);

        JTextField txtSatisID = new JTextField();
        txtSatisID.setBounds(150, 20, 150, 25);
        frame.add(txtSatisID);

        JLabel lblUrunID = new JLabel("Ürün ID:");
        lblUrunID.setBounds(20, 60, 100, 25);
        frame.add(lblUrunID);

        JTextField txtUrunID = new JTextField();
        txtUrunID.setBounds(150, 60, 150, 25);
        frame.add(txtUrunID);

        JLabel lblBayiID = new JLabel("Bayi ID:");
        lblBayiID.setBounds(20, 100, 100, 25);
        frame.add(lblBayiID);

        JTextField txtBayiID = new JTextField();
        txtBayiID.setBounds(150, 100, 150, 25);
        frame.add(txtBayiID);

        JLabel lblUrunAdet = new JLabel("Ürün Adet:");
        lblUrunAdet.setBounds(20, 140, 100, 25);
        frame.add(lblUrunAdet);

        JTextField txtUrunAdet = new JTextField();
        txtUrunAdet.setBounds(150, 140, 150, 25);
        frame.add(txtUrunAdet);

        JLabel lblToplamFiyat = new JLabel("Toplam Fiyat:");
        lblToplamFiyat.setBounds(20, 180, 100, 25);
        frame.add(lblToplamFiyat);

        JTextField txtToplamFiyat = new JTextField();
        txtToplamFiyat.setBounds(150, 180, 150, 25);
        frame.add(txtToplamFiyat);

        JLabel lblTarih = new JLabel("Tarih (YYYY-MM-DD):");
        lblTarih.setBounds(20, 220, 150, 25);
        frame.add(lblTarih);

        JTextField txtTarih = new JTextField();
        txtTarih.setBounds(180, 220, 120, 25);
        frame.add(txtTarih);

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(20, 260, 100, 30);
        btnEkle.setVisible(this.canInsert);
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 260, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU EKLENİYOR
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 260, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // -------------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 310, 500, 100);
        frame.add(scrollPane);

        // Ekle (INSERT) butonu
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int satisid = Integer.parseInt(txtSatisID.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());
                int bayiid = Integer.parseInt(txtBayiID.getText());
                int urunadet = Integer.parseInt(txtUrunAdet.getText());
                double toplamfiyat = Double.parseDouble(txtToplamFiyat.getText());
                Date satistarih = Date.valueOf(txtTarih.getText());

                satisManager.insertSatis(satisid, urunid, bayiid, urunadet, toplamfiyat, satistarih);
                JOptionPane.showMessageDialog(frame, "Satış kaydı başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
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
            for (String row : satisManager.getSatisList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE (UPDATE) butonu
        btnGuncelle.addActionListener((ActionEvent e) -> {
            try {
                int satisid = Integer.parseInt(txtSatisID.getText());
                int urunid = Integer.parseInt(txtUrunID.getText());
                int bayiid = Integer.parseInt(txtBayiID.getText());
                int urunadet = Integer.parseInt(txtUrunAdet.getText());
                double toplamfiyat = Double.parseDouble(txtToplamFiyat.getText());
                Date satistarih = Date.valueOf(txtTarih.getText());

                satisManager.updateSatis(satisid, urunid, bayiid, urunadet, toplamfiyat, satistarih);
                JOptionPane.showMessageDialog(frame, "Satış kaydı başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, "Tarih formatı hatalı! Lütfen YYYY-MM-DD formatında tarih girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // -------------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
