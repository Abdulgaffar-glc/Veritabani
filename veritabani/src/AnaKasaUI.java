import javax.swing.*;
import java.awt.event.ActionEvent;

public class AnaKasaUI {
    private JFrame frame;
    private AnaKasaManager anaKasaManager;
    private boolean canInsert;
    private boolean canUpdate;

    public AnaKasaUI(boolean canInsert, boolean canUpdate) {
        // Parametrelerden gelen değerleri saklıyoruz
        this.canInsert = canInsert;   // Ekleme izni
        this.canUpdate = canUpdate;   // Güncelleme izni

        anaKasaManager = new AnaKasaManager();
        initialize();
    }


    private void initialize() {
        frame = new JFrame("Ana Kasa Yönetimi");
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel lblBakiye = new JLabel("Güncel Bakiye:");
        lblBakiye.setBounds(20, 20, 150, 25);
        frame.add(lblBakiye);

        JTextField txtBakiye = new JTextField();
        txtBakiye.setBounds(150, 20, 150, 25);
        txtBakiye.setEditable(false);
        frame.add(txtBakiye);

        JButton btnGoruntule = new JButton("Bakiye Görüntüle");
        btnGoruntule.setBounds(20, 60, 150, 30);
        frame.add(btnGoruntule);

        JLabel lblYeniBakiye = new JLabel("Yeni Bakiye:");
        lblYeniBakiye.setBounds(20, 110, 150, 25);
        frame.add(lblYeniBakiye);

        JTextField txtYeniBakiye = new JTextField();
        txtYeniBakiye.setBounds(150, 110, 150, 25);
        frame.add(txtYeniBakiye);

        JButton btnGuncelle = new JButton("Bakiye Güncelle");
        btnGuncelle.setBounds(20, 150, 150, 30);
        btnGuncelle.setVisible(this.canUpdate);
        frame.add(btnGuncelle);

        // Bakiye görüntüleme
        btnGoruntule.addActionListener((ActionEvent e) -> {
            int bakiye = anaKasaManager.getBakiye();
            txtBakiye.setText(String.valueOf(bakiye));
        });

        // Bakiye güncelleme
        btnGuncelle.addActionListener((ActionEvent e) -> {
            try {
                int yeniBakiye = Integer.parseInt(txtYeniBakiye.getText().trim());
                anaKasaManager.updateBakiye(yeniBakiye);
                JOptionPane.showMessageDialog(frame, "Bakiye başarıyla güncellendi.");
                txtBakiye.setText(String.valueOf(yeniBakiye));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Lütfen geçerli bir sayı girin!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
