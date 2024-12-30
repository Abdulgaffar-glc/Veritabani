import javax.swing.*;
import java.awt.*;

public class AnimatedSplashScreen extends JWindow {
    private JProgressBar progressBar;
    private JLabel messageLabel;
    private Runnable onComplete;

    public AnimatedSplashScreen(Runnable onComplete) {
        this.onComplete = onComplete; // Splash tamamlandığında çalışacak kod
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBackground(new Color(30, 30, 30));

        JLabel titleLabel = new JLabel("Hoş Geldiniz!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        messageLabel = new JLabel("Uygulama yükleniyor...", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(Color.LIGHT_GRAY);

        progressBar = new JProgressBar(0, 100);
        progressBar.setForeground(new Color(50, 150, 250));
        progressBar.setBackground(new Color(50, 50, 50));
        progressBar.setStringPainted(true);

        content.add(titleLabel, BorderLayout.NORTH);
        content.add(messageLabel, BorderLayout.CENTER);
        content.add(progressBar, BorderLayout.SOUTH);

        setContentPane(content);
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

    public void startAnimation() {
        Timer timer = new Timer(50, e -> {
            int value = progressBar.getValue();
            if (value < 100) {
                progressBar.setValue(value + 2);
                messageLabel.setText("Yükleniyor... %" + value);
            } else {
                ((Timer) e.getSource()).stop();
                setVisible(false);
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        });
        timer.start();
    }
}
