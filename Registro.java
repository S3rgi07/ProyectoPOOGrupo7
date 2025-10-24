import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Registro implements ActionListener {

    //Componentes principales
    JFrame frame = new JFrame ("Registrar");
    JButton registrarBoton = new JButton("Registrar");
    JTextField usuarioID = new JTextField();
    JPasswordField userpassword = new JPasswordField();
    JPasswordField userpassword2 = new JPasswordField();

    //Etiquetas
    JLabel usuarioLabel = new JLabel("Ingrese nombre de usuario:");
    JLabel passwordLabel = new JLabel("Ingrese clave:");
    JLabel passwordLabel2 = new JLabel("Confirmar clave:");
    JLabel messageLabel = new JLabel();

    public Registro() {

        // Posiciones y tamaños de los componentes (x, y, ancho, alto)
        usuarioLabel.setBounds(50, 80, 200, 25);
        usuarioID.setBounds(220, 80, 150, 25);

        passwordLabel.setBounds(50, 120, 200, 25);
        userpassword.setBounds(220, 120, 150, 25);

        passwordLabel2.setBounds(50, 160, 200, 25);
        userpassword2.setBounds(220, 160, 150, 25);

        registrarBoton.setBounds(150, 210, 100, 30);
        registrarBoton.addActionListener(this); //detecta clicks en el botón

        messageLabel.setBounds(100, 260, 250, 35);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER); //centra el texto horizontalmente dentro del botón

        //gabriel, pon aquí tu código

            // Añadir componentes al frame
        frame.add(usuarioLabel);
        frame.add(usuarioID);
        frame.add(passwordLabel);
        frame.add(userpassword);
        frame.add(passwordLabel2);
        frame.add(userpassword2);
        frame.add(registrarBoton);
        frame.add(messageLabel);

        // Configuración general de la ventana
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registrarBoton) {
            String user = usuarioID.getText();
            String pass1 = String.valueOf(userpassword.getPassword());
            String pass2 = String.valueOf(userpassword2.getPassword());

            if (user.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) {
                messageLabel.setText("Por favor, complete todos los campos");
                messageLabel.setForeground(java.awt.Color.RED);
            } else if (!pass1.equals(pass2)) {
                messageLabel.setText("Las contraseñas no coinciden");
                messageLabel.setForeground(java.awt.Color.RED);
            } else {
                messageLabel.setText("¡Registro exitoso!");
                messageLabel.setForeground(java.awt.Color.GREEN);
            }
    }
    }




    
    
}