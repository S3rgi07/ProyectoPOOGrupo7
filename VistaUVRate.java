import javax.swing.*;
import java.awt.*;

public class VistaUVRate extends JFrame {

    private JTabbedPane tabbedPane;

    // Componentes para cursos
    private JTable tablaCursos;
    private JTextArea areaDetalleCurso;
    private JButton btnCalificarCurso;

    // Componentes para catedráticos
    private JTable tablaCatedraticos;
    private JTextArea areaDetalleCatedratico;
    private JButton btnCalificarCatedratico;

    // Componentes para metas
    private JComboBox<String> comboMetas;
    private JButton btnGenerarRecomendaciones;
    private JTextArea areaRecomendaciones;

    
public VistaUVRate() {

        //Creación inicial de la ventana de UVRate
        setTitle("UVRate");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
}

}
