import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class VistaUVRate extends JFrame {

    private JPanel sidebar;
    private JPanel contentPanel;
    private JButton btnCursos, btnCatedraticos;
    private JTable tabla;
    private JTextArea detalles;
    private JButton btnUpvote;

    private DefaultTableModel cursosModel, catedraticosModel;

    public VistaUVRate() {
        setTitle("UVRate Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ----------------------
        // Sidebar
        // ----------------------
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(150, 0));
        sidebar.setBackground(new Color(40, 44, 52));
        sidebar.setLayout(new GridLayout(10, 1, 0, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        btnCursos = createSidebarButton("Cursos");
        btnCatedraticos = createSidebarButton("Catedráticos");

        sidebar.add(btnCursos);
        sidebar.add(btnCatedraticos);

        add(sidebar, BorderLayout.WEST);

        // ----------------------
        // Content Panel
        // ----------------------
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 245, 245));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(contentPanel, BorderLayout.CENTER);

        // ----------------------
        // Tablas de datos
        // ----------------------
        cursosModel = new DefaultTableModel(
                new Object[][]{
                        {"Curso 1", "Profesor A", 20},
                        {"Curso 2", "Profesor B", 15}
                },
                new Object[]{"Nombre", "Catedrático", "Upvotes"}
        );

        catedraticosModel = new DefaultTableModel(
                new Object[][]{
                        {"Profesor A", "Matemáticas", 20},
                        {"Profesor B", "Historia", 18}
                },
                new Object[]{"Nombre", "Materia", "Upvotes"}
        );

        // Inicializamos con cursos
        showCursos();

        // ----------------------
        // Botones de navegación
        // ----------------------
        btnCursos.addActionListener(e -> showCursos());
        btnCatedraticos.addActionListener(e -> showCatedraticos());

        setVisible(true);
    }

    // ----------------------
    // Funciones para mostrar secciones
    // ----------------------
    private void showCursos() {
        contentPanel.removeAll();

        tabla = new JTable(cursosModel);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(70,130,180));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tabla);

        detalles = new JTextArea("Selecciona un curso para ver detalles...");
        detalles.setEditable(false);
        detalles.setLineWrap(true);
        detalles.setWrapStyleWord(true);
        detalles.setBorder(new RoundedBorder(10));
        detalles.setBackground(Color.WHITE);

        btnUpvote = new JButton("Upvote");
        styleButton(btnUpvote, new Color(0, 168, 150));
        btnUpvote.addActionListener(e -> upvoteSelectedRow(tabla));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, detalles);
        split.setDividerLocation(300);
        split.setResizeWeight(0.7);
        split.setBorder(null);

        contentPanel.add(split, BorderLayout.CENTER);
        contentPanel.add(btnUpvote, BorderLayout.SOUTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCatedraticos() {
        contentPanel.removeAll();

        tabla = new JTable(catedraticosModel);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(70,130,180));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tabla);

        detalles = new JTextArea("Selecciona un catedrático para ver detalles...");
        detalles.setEditable(false);
        detalles.setLineWrap(true);
        detalles.setWrapStyleWord(true);
        detalles.setBorder(new RoundedBorder(10));
        detalles.setBackground(Color.WHITE);

        btnUpvote = new JButton("Upvote");
        styleButton(btnUpvote, new Color(9, 132, 227));
        btnUpvote.addActionListener(e -> upvoteSelectedRow(tabla));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTabla, detalles);
        split.setDividerLocation(300);
        split.setResizeWeight(0.7);
        split.setBorder(null);

        contentPanel.add(split, BorderLayout.CENTER);
        contentPanel.add(btnUpvote, BorderLayout.SOUTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ----------------------
    // Incrementar Upvotes
    // ----------------------
    private void upvoteSelectedRow(JTable tabla) {
        int row = tabla.getSelectedRow();
        if (row != -1) {
            int col = tabla.getColumnCount() - 1; // última columna: Upvotes
            int current = (int) tabla.getValueAt(row, col);
            tabla.setValueAt(current + 1, row, col);
        }
    }

    // ----------------------
    // Estilo moderno para botones
    // ----------------------
    private void styleButton(JButton btn, Color bg) {
        btn.setFocusPainted(false);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setBorder(new RoundedBorder(10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(50,50,60));
        btn.setBorder(new EmptyBorder(10,10,10,10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(70,70,90));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(50,50,60));
            }
        });
        return btn;
    }

    // ----------------------
    // Borde redondeado para textarea y botones
    // ----------------------
    class RoundedBorder extends AbstractBorder {
        private int radius;
        RoundedBorder(int r){ radius = r; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(x,y,width-1,height-1,radius,radius);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VistaUVRate::new);
    }
}
