import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;

public class VistaUVRate extends JFrame {

    private UVRate controlador;
    private Estudiante estudiante;

    private JPanel panelPrincipal;
    private JScrollPane scrollPanel;
    private JTextField searchField;
    private JButton btnCatedraticos, btnCursos;
    private JButton btnOrientador;

    public VistaUVRate(UVRate controlador, Estudiante estudiante) {
        super("UVRate Dashboard");
        this.controlador = controlador;
        this.estudiante = estudiante;

        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel lateral
        JPanel panelLateral = new JPanel();
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBackground(new Color(100, 200, 100));
        panelLateral.setPreferredSize(new Dimension(150, getHeight()));

        btnCatedraticos = new JButton("Catedráticos");
        btnCursos = new JButton("Cursos");
        btnOrientador = new JButton("Orientador");

        btnCatedraticos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCursos.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelLateral.add(Box.createRigidArea(new Dimension(0, 20)));
        panelLateral.add(btnCatedraticos);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelLateral.add(btnCursos);
        panelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelLateral.add(btnOrientador);


        add(panelLateral, BorderLayout.WEST);

        // Panel principal con scroll
        panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(153, 255, 153);
                Color color2 = Color.WHITE;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        scrollPanel = new JScrollPane(panelPrincipal);
        scrollPanel.setBorder(null);
        add(scrollPanel, BorderLayout.CENTER);

        // Barra de búsqueda
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        add(searchField, BorderLayout.NORTH);

        // Listeners
        btnCatedraticos.addActionListener(e -> mostrarCatedraticos());
        btnCursos.addActionListener(e -> mostrarCursos());
        btnOrientador.addActionListener(e -> mostrarOrientador());
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (btnCatedraticos.getBackground() == Color.LIGHT_GRAY) {
                    mostrarCatedraticos();
                } else {
                    mostrarCursos();
                }
            }
        });

        // Inicializa mostrando catedráticos
        btnCatedraticos.doClick();

        setVisible(true);
    }

    // -------------------- Catedráticos --------------------
    private void mostrarCatedraticos() {
        btnCatedraticos.setBackground(Color.LIGHT_GRAY);
        btnCursos.setBackground(null);

        panelPrincipal.removeAll();
        ArrayList<Catedratico> catedraticos = controlador.obtenerTodosCatedraticos();
        String filtro = searchField.getText().toLowerCase();

        for (Catedratico cat : catedraticos) {
            if (cat.getNombre().toLowerCase().contains(filtro)) {
                panelPrincipal.add(crearCardCatedratico(cat));
                panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private JPanel crearCardCatedratico(Catedratico cat) {
        JPanel card = new JPanel();
        card.setBackground(new Color(200, 255, 200));
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(800, 80));
        card.setBorder(BorderFactory.createLineBorder(Color.GREEN.darker(), 2));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel nombreLabel = new JLabel(cat.getNombre() + "   Upvotes: " + cat.getUpvotes().getUpvotes());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nombreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(nombreLabel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPerfilCatedratico(cat);
            }
        });

        return card;
    }

    private void abrirPerfilCatedratico(Catedratico cat) {
        JDialog perfil = new JDialog(this, cat.getNombre(), true);
        perfil.setSize(400, 400);
        perfil.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nombreLabel = new JLabel("Catedrático: " + cat.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(nombreLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel cursosLabel = new JLabel("Cursos que imparte:");
        cursosLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(cursosLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        for (Curso c : cat.getCursos()) {
            JLabel curso = new JLabel("- " + c.getNombre());
            panel.add(curso);
        }

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton upvoteBtn = new JButton(
                controlador.yaVoto(estudiante.getId(), cat.getId()) ? "♥ Upvoted" : "♡ Dar Upvote");
        upvoteBtn.setFont(new Font("Arial", Font.BOLD, 16));
        upvoteBtn.setForeground(Color.RED);
        upvoteBtn.setFocusPainted(false);
        upvoteBtn.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        upvoteBtn.setBackground(new Color(255, 200, 200));
        upvoteBtn.setMaximumSize(new Dimension(200, 40));

        upvoteBtn.addActionListener(e -> {
            controlador.toggleUpvote(estudiante.getId(), cat.getId());
            int nuevosUpvotes = controlador.contarUpvotes(cat.getId());
            boolean ya = controlador.yaVoto(estudiante.getId(), cat.getId());
            upvoteBtn.setText(ya ? "♥ Upvoted" : "♡ Dar Upvote");
            JOptionPane.showMessageDialog(this, "Upvotes actualizados: " + nuevosUpvotes);
            mostrarCatedraticos();
        });

        panel.add(upvoteBtn);
        perfil.add(panel);
        perfil.setVisible(true);
    }

    // -------------------- Cursos --------------------
    private void mostrarCursos() {
        btnCursos.setBackground(Color.LIGHT_GRAY);
        btnCatedraticos.setBackground(null);

        panelPrincipal.removeAll();
        ArrayList<Curso> cursos = controlador.obtenerTodosCursos();
        String filtro = searchField.getText().toLowerCase();

        for (Curso c : cursos) {
            if (c.getNombre().toLowerCase().contains(filtro)) {
                panelPrincipal.add(crearCardCurso(c));
                panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private JPanel crearCardCurso(Curso curso) {
        JPanel card = new JPanel();
        card.setBackground(new Color(200, 200, 255));
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(800, 80));
        card.setBorder(BorderFactory.createLineBorder(Color.BLUE.darker(), 2));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel nombreLabel = new JLabel(curso.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nombreLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        card.add(nombreLabel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPerfilCurso(curso);
            }
        });

        return card;
    }

    private void abrirPerfilCurso(Curso curso) {
        JDialog perfil = new JDialog(this, curso.getNombre(), true);
        perfil.setSize(450, 450);
        perfil.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nombreLabel = new JLabel("Curso: " + curso.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(nombreLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel descLabel = new JLabel("<html><b>Descripción:</b> " + curso.getDescripcion() + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(descLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel catsLabel = new JLabel("Catedráticos que imparten este curso (ordenados por upvotes):");
        catsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(catsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Obtener catedráticos que imparten este curso y ordenarlos
        ArrayList<Catedratico> cats = controlador.obtenerCatedraticosPorCurso(curso.getCodigo());
        cats.sort(Comparator.comparingInt(c -> -c.getUpvotes().getUpvotes()));

        for (Catedratico c : cats) {
            JLabel catLabel = new JLabel("- " + c.getNombre() + " (Upvotes: " + c.getUpvotes().getUpvotes() + ")");
            panel.add(catLabel);
        }

        perfil.add(panel);
        perfil.setVisible(true);
    }
}