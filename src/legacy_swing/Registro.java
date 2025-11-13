package legacy_swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import database.ConexionUVRate;

import java.sql.*;

public class Registro extends JFrame implements ActionListener {

    // Campos de formulario
    JTextField usuarioID = new JTextField();
    JTextField correoTxt = new JTextField();
    JPasswordField password1 = new JPasswordField();
    JPasswordField password2 = new JPasswordField();
    JButton registrarButton = new JButton("Registrar");
    JLabel messageLabel = new JLabel();

    public Registro() {
        // ===== CONFIGURACIÓN BÁSICA DEL FRAME =====
        setTitle("Registro UVRate");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // ===== BARRA SUPERIOR =====
        JPanel barraSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        barraSuperior.setBackground(new Color(240, 240, 240, 0));

        // Botón Volver
        JButton btnVolver = new JButton("← Volver al inicio");
        btnVolver.setFocusPainted(false);
        btnVolver.setBackground(new Color(200, 200, 255));
        btnVolver.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        btnVolver.addActionListener(e -> {
            dispose();
            new Login();
        });

        // Botón Minimizar
        JButton btnMinimizar = new JButton("—");
        btnMinimizar.setFocusPainted(false);
        btnMinimizar.setBorderPainted(false);
        btnMinimizar.setBackground(new Color(180, 180, 180));
        btnMinimizar.setForeground(Color.WHITE);
        btnMinimizar.setPreferredSize(new Dimension(45, 25));
        btnMinimizar.addActionListener(e -> setState(JFrame.ICONIFIED));

        // Botón Cerrar
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setBackground(Color.RED);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setPreferredSize(new Dimension(45, 25));
        btnCerrar.addActionListener(e -> System.exit(0));

        // Añadimos en orden
        barraSuperior.add(btnVolver);
        barraSuperior.add(btnMinimizar);
        barraSuperior.add(btnCerrar);

        add(barraSuperior, BorderLayout.NORTH);

        // ===== PANEL PRINCIPAL =====
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 234, 167),
                        0, getHeight(), new Color(255, 118, 117));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null);

        JLabel title = new JLabel("Crear Cuenta");
        title.setFont(new Font("Verdana", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(150, 20, 300, 40);
        panel.add(title);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 80, 100, 25);
        panel.add(userLabel);
        usuarioID.setBounds(180, 80, 250, 30);
        usuarioID.setBorder(new RoundedBorder(10));
        panel.add(usuarioID);

        JLabel correoLabel = new JLabel("Correo:");
        correoLabel.setForeground(Color.WHITE);
        correoLabel.setBounds(50, 130, 100, 25);
        panel.add(correoLabel);
        correoTxt.setBounds(180, 130, 250, 30);
        correoTxt.setBorder(new RoundedBorder(10));
        panel.add(correoTxt);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 180, 120, 25);
        panel.add(passLabel);
        password1.setBounds(180, 180, 250, 30);
        password1.setBorder(new RoundedBorder(10));
        panel.add(password1);

        JLabel passLabel2 = new JLabel("Confirmar:");
        passLabel2.setForeground(Color.WHITE);
        passLabel2.setBounds(50, 230, 120, 25);
        panel.add(passLabel2);
        password2.setBounds(180, 230, 250, 30);
        password2.setBorder(new RoundedBorder(10));
        panel.add(password2);

        messageLabel.setBounds(50, 280, 380, 25);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel);

        registrarButton.setBounds(180, 320, 120, 35);
        styleButton(registrarButton, new Color(9, 132, 227));
        registrarButton.addActionListener(this);
        panel.add(registrarButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ====== ESTILO DE BOTONES ======
    private void styleButton(JButton btn, Color bg) {
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new RoundedBorder(10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ====== BORDE REDONDEADO ======
    class RoundedBorder extends AbstractBorder {
        private int radius;

        RoundedBorder(int r) {
            radius = r;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // ====== ACCIÓN DE REGISTRO ======
    @Override
    public void actionPerformed(ActionEvent e) {
        String user = usuarioID.getText();
        String correo = correoTxt.getText();
        String pass1Str = String.valueOf(password1.getPassword());
        String pass2Str = String.valueOf(password2.getPassword());

        if (user.isEmpty() || correo.isEmpty() || pass1Str.isEmpty() || pass2Str.isEmpty()) {
            messageLabel.setText("Por favor complete todos los campos");
            messageLabel.setForeground(Color.RED);
            return;
        }

        if (!pass1Str.equals(pass2Str)) {
            messageLabel.setText("Las contraseñas no coinciden");
            messageLabel.setForeground(Color.RED);
            return;
        }

        try (Connection conn = ConexionUVRate.getConnection()) {
            String sql = "INSERT INTO usuario (nombre, correo, contraseña) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, correo);
            stmt.setString(3, pass1Str);
            stmt.executeUpdate();

            messageLabel.setText("¡Registro exitoso!");
            messageLabel.setForeground(Color.GREEN);

            // volver al login después de 1 seg
            Timer timer = new Timer(1000, evt -> {
                dispose();
                new Login();
            });
            timer.setRepeats(false);
            timer.start();

        } catch (SQLException ex) {
            messageLabel.setText("Error: correo ya registrado");
            messageLabel.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Registro();
    }
}
