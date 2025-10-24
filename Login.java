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
	

	Login(){
		//Configuracion de las propiedades
		messageLabel.setBounds(125, 250, 250, 35); //El mensaje al final
		messageLabel.setFont(new Font("Arial", Font.ITALIC, 20)); 
		
		userLabel.setBounds(50, 100, 75, 25); //Salen las etiquetas de userID y clave
		passwordLabel.setBounds(50, 150, 75, 25);
		
		userID.setBounds(125, 100, 200, 25); //Cajita de textos
		userpassword.setBounds(125, 150, 200, 25);

        loginboton.setBounds(125, 200, 100, 25); //Boton de login
		loginboton.addActionListener(this); //El actionListener
		
		resetbuton.setBounds(225, 200, 100, 25); //Boton de reset
		resetbuton.addActionListener(this); //El actionListener

		registroboton.setBounds(150, 300, 100, 25); //Boton de registrar
		registroboton.addActionListener(this); //El actionListener
		
        //Lo agrega a la vista
		frame.add(loginboton);
		frame.add(registroboton);
		frame.add(resetbuton);
		frame.add(userLabel);
		frame.add(passwordLabel);
		frame.add(userID);
		frame.add(userpassword);
		frame.add(messageLabel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para que se cierre la ventana
		frame.setSize(420, 420);
		frame.setLayout(null); //No queremos layout
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//Que pasa si se apacha alguno de los botones
		
		if(e.getSource() == registroboton) {
			// Si presionan "Registrate", se abre la ventana de registro
			frame.dispose(); // Cierra la ventana actual
			new Registro(); // Abre la ventana de registro
		}
		if(e.getSource()==resetbuton) {
			userID.setText("");
			userpassword.setText("");
		}
		//Falta un segundo "if" aca, que es que pasa cuando el log in es correcto, pero para eso necesito la base de datos.
	}

	
}


