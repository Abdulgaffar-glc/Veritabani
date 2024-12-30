import javax.swing.*;
import java.awt.event.ActionEvent;

public class HammaddeUI {
    private JFrame frame;
    private HammaddeManager hammaddeManager;
    private boolean canInsert; // Kullanıcının INSERT yetkisi olup olmadığını kontrol eder
    private boolean canUpdate;
    public HammaddeUI(boolean canInsert, boolean canUpdate) {
        this.canInsert = canInsert;
        this.canUpdate = canUpdate;
        hammaddeManager = new HammaddeManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Hammadde Yönetimi");
        frame.setSize(600, 400);
        frame.setLayout(null);

        JLabel lblUrunID = new JLabel("Ürün ID:");
        lblUrunID.setBounds(20, 20, 100, 25);
        frame.add(lblUrunID);

        JTextField txtUrunID = new JTextField();
        txtUrunID.setBounds(150, 20, 150, 25);
        frame.add(txtUrunID);

        JLabel lblPreform = new JLabel("Preform:");
        lblPreform.setBounds(20, 60, 100, 25);
        frame.add(lblPreform);

        JTextField txtPreform = new JTextField();
        txtPreform.setBounds(150, 60, 150, 25);
        frame.add(txtPreform);

        JLabel lblKapak = new JLabel("Kapak:");
        lblKapak.setBounds(20, 100, 100, 25);
        frame.add(lblKapak);

        JTextField txtKapak = new JTextField();
        txtKapak.setBounds(150, 100, 150, 25);
        frame.add(txtKapak);

        JLabel lblEtiket = new JLabel("Etiket:");
        lblEtiket.setBounds(20, 140, 100, 25);
        frame.add(lblEtiket);

        JTextField txtEtiket = new JTextField();
        txtEtiket.setBounds(150, 140, 150, 25);
        frame.add(txtEtiket);

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(20, 180, 100, 30);
        btnEkle.setVisible(this.canInsert);  // ya da setEnabled(canInsert);
        frame.add(btnEkle);

        JButton btnListele = new JButton("Listele");
        btnListele.setBounds(140, 180, 100, 30);
        frame.add(btnListele);

        // <-- GÜNCELLE BUTONU EKLENİYOR
        JButton btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(260, 180, 100, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);
        // --------------------------------

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 230, 500, 100);
        frame.add(scrollPane);

        // EKLE (INSERT) butonu
        btnEkle.addActionListener((ActionEvent e) -> {
            try {
                int urunid = Integer.parseInt(txtUrunID.getText());
                int preform = Integer.parseInt(txtPreform.getText());
                int kapak = Integer.parseInt(txtKapak.getText());
                int etiket = Integer.parseInt(txtEtiket.getText());

                hammaddeManager.insertHammadde(urunid, preform, kapak, etiket);
                JOptionPane.showMessageDialog(frame, "Hammadde kaydı başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        // LİSTELE (SELECT) butonu
        btnListele.addActionListener(e -> {
            textArea.setText("");
            for (String row : hammaddeManager.getHammaddeList()) {
                textArea.append(row + "\n");
            }
        });

        // <-- GÜNCELLE (UPDATE) butonu
        btnGuncelle.addActionListener((ActionEvent e) -> {
            try {
                int urunid = Integer.parseInt(txtUrunID.getText());
                int preform = Integer.parseInt(txtPreform.getText());
                int kapak = Integer.parseInt(txtKapak.getText());
                int etiket = Integer.parseInt(txtEtiket.getText());

                hammaddeManager.updateHammadde(urunid, preform, kapak, etiket);
                JOptionPane.showMessageDialog(frame, "Hammadde kaydı başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Sayısal değer formatı hatalı! Lütfen doğru değerler girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Bilinmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --------------------------------

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
