import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaUVRate extends JFrame {

    private UVRate controlador;
    private JPanel panelCatedraticos, panelCursos;

    public VistaUVRate(UVRate controlador) {
        this.controlador = controlador;

        setTitle("UVRate");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel Catedráticos
        panelCatedraticos = new JPanel();
        panelCatedraticos.setLayout(new BoxLayout(panelCatedraticos, BoxLayout.Y_AXIS));
        JScrollPane scrollCat = new JScrollPane(panelCatedraticos);
        tabbedPane.addTab("Catedráticos", scrollCat);

        // Panel Cursos
        panelCursos = new JPanel();
        panelCursos.setLayout(new BoxLayout(panelCursos, BoxLayout.Y_AXIS));
        JScrollPane scrollCur = new JScrollPane(panelCursos);
        tabbedPane.addTab("Cursos", scrollCur);

        add(tabbedPane, BorderLayout.CENTER);

        cargarCatedraticos();
        cargarCursos();

        setVisible(true);
    }

    private void cargarCatedraticos() {
        panelCatedraticos.removeAll();
        List<Catedratico> catedraticos = controlador.obtenerTodosCatedraticos(); // Método en UVRate
        for (Catedratico c : catedraticos) {
            JButton btn = new JButton(c.getNombre() + " (" + controlador.contarUpvotes(c) + " Upvotes)");
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0, 153, 255));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> abrirDetalleCatedratico(c));
            panelCatedraticos.add(Box.createRigidArea(new Dimension(0,10)));
            panelCatedraticos.add(btn);
        }
        panelCatedraticos.revalidate();
        panelCatedraticos.repaint();
    }

    private void cargarCursos() {
        panelCursos.removeAll();
        List<Curso> cursos = controlador.obtenerTodosCursos(); // Método en UVRate
        for (Curso c : cursos) {
            JButton btn = new JButton(c.getNombre());
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(102, 204, 0));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> abrirDetalleCurso(c));
            panelCursos.add(Box.createRigidArea(new Dimension(0,10)));
            panelCursos.add(btn);
        }
        panelCursos.revalidate();
        panelCursos.repaint();
    }

    private void abrirDetalleCatedratico(Catedratico c) {
        JDialog dialog = new JDialog(this, "Detalle del Catedrático", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        JTextArea info = new JTextArea(
                "Nombre: " + c.getNombre() + "\n" +
                        "Descripción: " + c.getDescripcion() + "\n" +
                        "Upvotes: " + controlador.contarUpvotes(c)
        );
        info.setEditable(false);
        dialog.add(info, BorderLayout.CENTER);

        JButton btnUpvote = new JButton(controlador.yaVoto(controlador.getEstudiante(), c) ? "♥ Upvoted" : "♡ Upvote");
        btnUpvote.setBackground(new Color(255, 102, 102));
        btnUpvote.setForeground(Color.WHITE);
        btnUpvote.setFocusPainted(false);
        btnUpvote.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnUpvote.addActionListener(e -> {
            boolean liked = controlador.toggleUpvote(controlador.getEstudiante(), c);
            btnUpvote.setText(liked ? "♥ Upvoted" : "♡ Upvote");
            info.setText(
                    "Nombre: " + c.getNombre() + "\n" +
                            "Descripción: " + c.getDescripcion() + "\n" +
                            "Upvotes: " + controlador.contarUpvotes(c)
            );
        });

        dialog.add(btnUpvote, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void abrirDetalleCurso(Curso c) {
        JDialog dialog = new JDialog(this, "Detalle del Curso", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        JTextArea info = new JTextArea("Nombre: " + c.getNombre() + "\n\n" + "Descripción: " + c.getDescripcion());
        info.setEditable(false);
        dialog.add(info, BorderLayout.NORTH);

        JPanel recPanel = new JPanel();
        recPanel.setLayout(new BoxLayout(recPanel, BoxLayout.Y_AXIS));
        List<Catedratico> recomendaciones = controlador.getRecomendacionesPorCurso(c);
        for (Catedratico cat : recomendaciones) {
            JButton btn = new JButton(cat.getNombre() + " (" + controlador.contarUpvotes(cat) + " Upvotes)");
            btn.setBackground(new Color(0, 153, 255));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> abrirDetalleCatedratico(cat));
            recPanel.add(Box.createRigidArea(new Dimension(0,5)));
            recPanel.add(btn);
        }

        dialog.add(new JScrollPane(recPanel), BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
