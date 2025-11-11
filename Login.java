import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;

public class Login implements ActionListener {

    JFrame frame = new JFrame("Login UVRate");
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton registroButton = new JButton("Registrarse");
    JTextField userID = new JTextField();
    JPasswordField userPassword = new JPasswordField();
    JLabel messageLabel = new JLabel();

    public Login() {
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());

        // Panel principal con degradado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                // Cambié el celeste por un verde claro
                GradientPaint gp = new GradientPaint(0, 0, new Color(144, 238, 144), 0, getHeight(), new Color(0, 184, 148));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);

        // Título
        JLabel title = new JLabel("Bienvenido");
        title.setFont(new Font("Verdana", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(140, 20, 200, 40);
        mainPanel.add(title);

        // Labels y campos
        JLabel userLabel = new JLabel("Correo:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 90, 80, 25);
        mainPanel.add(userLabel);

        userID.setBounds(150, 90, 230, 30);
        userID.setBorder(new RoundedBorder(10));
        mainPanel.add(userID);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 140, 100, 25);
        mainPanel.add(passwordLabel);

        userPassword.setBounds(150, 140, 230, 30);
        userPassword.setBorder(new RoundedBorder(10));
        mainPanel.add(userPassword);

        // Mensaje
        messageLabel.setBounds(50, 190, 330, 25);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setForeground(Color.YELLOW);
        mainPanel.add(messageLabel);

        // Botones
        loginButton.setBounds(50, 230, 100, 35);
        styleButton(loginButton, new Color(0, 168, 150));
        mainPanel.add(loginButton);

        resetButton.setBounds(160, 230, 100, 35);
        styleButton(resetButton, new Color(255, 118, 117));
        mainPanel.add(resetButton);

        registroButton.setBounds(270, 230, 110, 35);
        styleButton(registroButton, new Color(9, 132, 227));
        mainPanel.add(registroButton);

        // Acciones
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        registroButton.addActionListener(this);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Botones redondeados y color
    private void styleButton(JButton btn, Color bg) {
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new RoundedBorder(10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Borde redondeado para campos y botones
    class RoundedBorder extends AbstractBorder {
        private int radius;
        RoundedBorder(int r) { radius = r; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.WHITE);
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registroButton) {
            frame.dispose();
            new Registro();
        }

        if (e.getSource() == resetButton) {
            userID.setText("");
            userPassword.setText("");
            messageLabel.setText("");
        }

        if (e.getSource() == loginButton) {
            String correo = userID.getText();
            String pass = String.valueOf(userPassword.getPassword());

            try (Connection conn = ConexionUVRate.getConnection()) {
                String sql = "SELECT * FROM usuario WHERE correo = ? AND contraseña = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, correo);
                stmt.setString(2, pass);

                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    messageLabel.setText("Inicio exitoso!");
                    messageLabel.setForeground(Color.GREEN);

                    // Creamos estudiante usando datos de BD
                    String nombre = rs.getString("nombre");
                    // Para carnet, si no existe en BD, usar 0
                    int carnet = 0;
                    try { carnet = rs.getInt("carnet"); } catch(Exception ex) {}
                    Estudiante estudiante = new Estudiante(nombre, carnet, correo);

                    estudiante.setId(rs.getInt("id"));


                    // Creamos el controlador con el estudiante
                    UVRate controlador = new UVRate(estudiante);

                    // Abrimos VistaUVRate
                    new VistaUVRate(controlador, estudiante);

                    frame.dispose();
                } else {
                    messageLabel.setText("Datos incorrectos");
                    messageLabel.setForeground(Color.RED);
                }

            } catch (Exception ex) {
                messageLabel.setText("Error de conexión");
                messageLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
