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
}