package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import proyecto_bd.cajaChica.Conexion.ConexionBD;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasGUI extends JFrame {
    private ConexionBD conexion;

    public ConsultasGUI() {
        // Configuración de la ventana principal
        setTitle("Interfaz de Consultas - Caja Banco");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear instancia de ConexionBD
        conexion = new ConexionBD();

        // Panel de pestañas para distintas secciones
        JTabbedPane tabbedPane = new JTabbedPane();

        // Crear paneles para cada consulta y añadirlos al tabbedPane
        crearPanelesConsultas(tabbedPane);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    private void crearPanelesConsultas(JTabbedPane tabbedPane) {
        // Consultas con inputs
        agregarPanelConsultaConInput(tabbedPane, "Consulta 1",
                "SELECT * FROM Cliente WHERE idUsuario = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 2",
                "SELECT * FROM Cajero WHERE idUsuario = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 3",
                "SELECT * FROM EntregaDinero WHERE idCliente = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 4",
                "SELECT * FROM RendicionDocumento WHERE idCajero = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 5",
                "SELECT * FROM Cliente WHERE nombre LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 6",
                "SELECT * FROM Cajero WHERE nombre LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 7",
                "SELECT * FROM EntregaDinero WHERE motivo LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 8",
                "SELECT * FROM RendicionDocumento WHERE fechaRendicion = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 9",
                "SELECT * FROM Documento WHERE descripcion LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 10",
                "SELECT * FROM DevolucionReposicion WHERE motivo LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 11",
                "SELECT * FROM CajaBanco WHERE fecha = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 12",
                "SELECT * FROM AperturaCajaChica WHERE fecha = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 13",
                "SELECT * FROM Cliente WHERE email LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 14",
                "SELECT * FROM Cajero WHERE email LIKE ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 15",
                "SELECT * FROM EntregaDinero WHERE fechaEntrega = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 16",
                "SELECT * FROM RendicionDocumento WHERE idCliente = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 17",
                "SELECT * FROM Cliente INNER JOIN Cajero ON Cliente.idUsuario = Cajero.idUsuario WHERE Cliente.idUsuario = ?");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 18",
                "SELECT * FROM DevolucionReposicion INNER JOIN Cliente ON DevolucionReposicion.idCliente = Cliente.idCliente WHERE DevolucionReposicion.tipoOperacion = 'Devolución'");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 19",
                "SELECT * FROM EntregaDinero LEFT JOIN Cajero ON EntregaDinero.idCajero = Cajero.idCajero LEFT JOIN Cliente ON EntregaDinero.idCliente = Cliente.idCliente");
        agregarPanelConsultaConInput(tabbedPane, "Consulta 20",
                "SELECT * FROM Documento INNER JOIN RendicionDocumento ON Documento.idRendicionDocumento = RendicionDocumento.idRendDocumento WHERE RendicionDocumento.montoRendido > 1000");
    }

    private void agregarPanelConsultaConInput(JTabbedPane tabbedPane, String nombreConsulta, String consulta) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField txtInput = new JTextField(20);
        JTextArea txtConsulta = new JTextArea(5, 40);
        txtConsulta.setText(consulta);
        txtConsulta.setEditable(false);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Input:"));
        inputPanel.add(txtInput);

        panel.add(new JScrollPane(txtConsulta), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);

        JPanel botonesPanel = new JPanel(new FlowLayout());

        JButton btnConsulta = new JButton(nombreConsulta);
        btnConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsultaConInput(txtConsulta.getText().trim(), txtInput.getText().trim(), nombreConsulta);
            }
        });

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                crearPanelesConsultas((JTabbedPane) getContentPane().getComponent(0));
                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });

        botonesPanel.add(btnConsulta);
        botonesPanel.add(btnRegresar);

        panel.add(botonesPanel, BorderLayout.SOUTH);
        tabbedPane.addTab(nombreConsulta, panel);
    }

    // Método para ejecutar consultas con input y mostrar resultados en una tabla
    private void ejecutarConsultaConInput(String consulta, String input, String nombreConsulta) {
        try {
            Connection conn = conexion.establecerConexion(); // Obtener la conexión
            PreparedStatement stmt = conn.prepareStatement(consulta);
            stmt.setString(1, input);
            ResultSet rs = stmt.executeQuery();

            // Crear modelo de tabla dinámico
            DefaultTableModel model = new DefaultTableModel();
            JTable tablaResultados = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(tablaResultados);

            // Limpiar panel anterior de resultados
            getContentPane().removeAll();
            getContentPane().add(new JScrollPane(scrollPane), BorderLayout.CENTER);

            // Obtener metadatos de columnas
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rs.getMetaData().getColumnName(i));
            }

            // Agregar filas de resultados
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            conexion.cerrarConexion(); // Cerrar la conexión

            // Agregar botón de "Regresar"
            JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnRegresar = new JButton("Regresar");
            btnRegresar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    new ConsultasGUI().setVisible(true);
                    setVisible(false);
                }
            });
            botonesPanel.add(btnRegresar);
            getContentPane().add(botonesPanel, BorderLayout.SOUTH);
            getContentPane().revalidate();
            getContentPane().repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al ejecutar la consulta.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ConsultasGUI interfaz = new ConsultasGUI();
                interfaz.setVisible(true);
            }
        });
    }
}
