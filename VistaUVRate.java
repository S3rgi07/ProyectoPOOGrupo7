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

        //Creación del tabbedPane para categorizar mejor cada opción
        tabbedPane = new JTabbedPane();

        //Tablero 1: Cursos
        JPanel panelCursos = new JPanel(new BorderLayout());
        tablaCursos = new JTable(); // Aquí se cargarán los cursos desde Controller
        areaDetalleCurso = new JTextArea("Detalles del curso...");
        btnCalificarCurso = new JButton("Calificar curso");
        panelCursos.add(new JScrollPane(tablaCursos), BorderLayout.CENTER);
        JPanel panelCursosInferior = new JPanel(new BorderLayout());
        panelCursosInferior.add(new JScrollPane(areaDetalleCurso), BorderLayout.CENTER);
        panelCursosInferior.add(btnCalificarCurso, BorderLayout.SOUTH);
        panelCursos.add(panelCursosInferior, BorderLayout.SOUTH);

        tabbedPane.addTab("Cursos", panelCursos);

        add(tabbedPane);
        setVisible(true);
    }

}
