import javax.swing.*;

import database.ConexionUVRate;
import legacy_swing.Login;

public class main {
    public static void main(String[] args) {
        ConexionUVRate.getConnection();
        new Login();
    }
}