import java.awt.Font; //Yo
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*; //Importa toda la libreria

public class Login implements ActionListener { //el action Listener "escucha" lo que el usuario hace, lo vamos a usar para los botones
	
	JFrame frame = new JFrame();
	JButton loginboton = new JButton("Login");
	JButton resetbuton = new JButton("Reset");
	JButton registroboton = new JButton("Registrate");
	JTextField userID = new JTextField();
	JPasswordField userpassword = new JPasswordField();
	JLabel userLabel = new JLabel("userID: ");
	JLabel passwordLabel = new JLabel("password: ");
	JLabel messageLabel = new JLabel();
	
}


