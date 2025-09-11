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

        //Tablero 2: Catedráticos
        JPanel panelCatedraticos = new JPanel(new BorderLayout());
        tablaCatedraticos = new JTable();
        areaDetalleCatedratico = new JTextArea("Detalles del catedrático...");
        btnCalificarCatedratico = new JButton("Calificar catedrático");
        panelCatedraticos.add(new JScrollPane(tablaCatedraticos), BorderLayout.CENTER);
        JPanel panelCatInferior = new JPanel(new BorderLayout());
        panelCatInferior.add(new JScrollPane(areaDetalleCatedratico), BorderLayout.CENTER);
        panelCatInferior.add(btnCalificarCatedratico, BorderLayout.SOUTH);
        panelCatedraticos.add(panelCatInferior, BorderLayout.SOUTH);

        tabbedPane.addTab("Catedráticos", panelCatedraticos);

        add(tabbedPane);
        setVisible(true);
    }

}
