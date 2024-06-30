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
        setTitle("Interfaz de Consultas - Banco");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear instancia de ConexionBD
        conexion = new ConexionBD();

        // Panel de pestañas para distintas secciones
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel para Consulta 1: Obtener todos los documentos de un cliente específico
        JPanel panelConsulta1 = new JPanel(new BorderLayout());
        JTextArea txtConsulta1 = new JTextArea(5, 40);
        txtConsulta1.setText("SELECT d.idDocumento, d.descripcion, rd.fechaRendicion\n" +
                "FROM Documento d\n" +
                "JOIN RendicionDocumento rd ON d.idRendicionDocumento = rd.idRendDocumento\n" +
                "JOIN Cliente c ON rd.idCliente = c.idCliente\n" +
                "WHERE c.idCliente = ?");
        panelConsulta1.add(new JScrollPane(txtConsulta1), BorderLayout.CENTER);
        JButton btnConsulta1 = new JButton("Obtener todos los documentos de un cliente");
        btnConsulta1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta(txtConsulta1.getText().trim(), "Obtener todos los documentos de un cliente");
            }
        });
        panelConsulta1.add(btnConsulta1, BorderLayout.SOUTH);
        tabbedPane.addTab("Consulta 1", panelConsulta1);

        // Panel para Consulta 2: Listar entregas de dinero junto con el nombre del cliente y nombre del cajero
        JPanel panelConsulta2 = new JPanel(new BorderLayout());
        JTextArea txtConsulta2 = new JTextArea(5, 40);
        txtConsulta2.setText("SELECT ed.idEntregaDinero, ed.fechaEntrega, ed.monto, ed.motivo, e.tipoEstado, u.nombre AS NombreCajero, c.nombre AS NombreCliente\n" +
                "FROM EntregaDinero ed\n" +
                "JOIN Cajero cj ON ed.idCajero = cj.idCajero\n" +
                "JOIN Usuario u ON cj.idUsuario = u.idPersonal\n" +
                "JOIN Cliente cl ON ed.idCliente = cl.idCliente\n" +
                "JOIN Usuario c ON cl.idUsuario = c.idPersonal\n" +
                "JOIN Estado e ON ed.idEstado = e.idEstado");
        panelConsulta2.add(new JScrollPane(txtConsulta2), BorderLayout.CENTER);
        JButton btnConsulta2 = new JButton("Listar entregas de dinero junto con nombres de cliente y cajero");
        btnConsulta2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta(txtConsulta2.getText().trim(), "Listar entregas de dinero junto con nombres de cliente y cajero");
            }
        });
        panelConsulta2.add(btnConsulta2, BorderLayout.SOUTH);
        tabbedPane.addTab("Consulta 2", panelConsulta2);

        // Panel para Consulta 3: Detalle de rendiciones de documentos con sus respectivas devoluciones/reposiciones
        JPanel panelConsulta3 = new JPanel(new BorderLayout());
        JTextArea txtConsulta3 = new JTextArea(5, 40);
        txtConsulta3.setText("SELECT rd.idRendDocumento, rd.fechaRendicion, rd.montoRendido, dr.fechaDevolucion, dr.montoDevolucion, dr.motivo\n" +
                "FROM RendicionDocumento rd\n" +
                "LEFT JOIN DevolucionReposicion dr ON rd.idCajero = dr.idCajero AND rd.idCliente = dr.idCliente");
        panelConsulta3.add(new JScrollPane(txtConsulta3), BorderLayout.CENTER);
        JButton btnConsulta3 = new JButton("Detalle de rendiciones de documentos con devoluciones/reposiciones");
        btnConsulta3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta(txtConsulta3.getText().trim(), "Detalle de rendiciones de documentos con devoluciones/reposiciones");
            }
        });
        panelConsulta3.add(btnConsulta3, BorderLayout.SOUTH);
        tabbedPane.addTab("Consulta 3", panelConsulta3);

        // Panel para Consulta 4: Información de aperturas de caja chica junto con el nombre del cajero
        JPanel panelConsulta4 = new JPanel(new BorderLayout());
        JTextArea txtConsulta4 = new JTextArea(5, 40);
        txtConsulta4.setText("SELECT ac.idApertura, ac.fecha, ac.montoInicial, ac.estado, u.nombre AS NombreCajero\n" +
                "FROM AperturaCajaChica ac\n" +
                "JOIN Cajero cj ON ac.idCajero = cj.idCajero\n" +
                "JOIN Usuario u ON cj.idUsuario = u.idPersonal");
        panelConsulta4.add(new JScrollPane(txtConsulta4), BorderLayout.CENTER);
        JButton btnConsulta4 = new JButton("Información de aperturas de caja chica junto con el nombre del cajero");
        btnConsulta4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta(txtConsulta4.getText().trim(), "Información de aperturas de caja chica junto con el nombre del cajero");
            }
        });
        panelConsulta4.add(btnConsulta4, BorderLayout.SOUTH);
        tabbedPane.addTab("Consulta 4", panelConsulta4);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    // Método para ejecutar consultas y mostrar resultados en una tabla
    private void ejecutarConsulta(String consulta, String nombreConsulta) {
        try {
            Connection conn = conexion.establecerConexion(); // Obtener la conexión
            PreparedStatement stmt = conn.prepareStatement(consulta);
            ResultSet rs = stmt.executeQuery();

            // Crear modelo de tabla dinámico
            DefaultTableModel model = new DefaultTableModel();
            JTable tablaResultados = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(tablaResultados);

            // Limpiar panel anterior de resultados
            getContentPane().removeAll();
            getContentPane().add(new JScrollPane(scrollPane), BorderLayout.CENTER);
            getContentPane().revalidate();
            getContentPane().repaint();

            // Agregar título de consulta como primer columna
            model.addColumn("Consulta Realizada: " + nombreConsulta);

            // Obtener metadatos de columnas
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(rs.getMetaData().getColumnName(i));
            }

            // Agregar filas de resultados
            while (rs.next()) {
                Object[] row = new Object[columnCount + 1];
                row[0] = nombreConsulta;
                for (int i = 1; i <= columnCount; i++) {
                    row[i] = rs.getObject(i);
                }
                model.addRow(row);
            }

            rs.close();
            stmt.close();
            conexion.cerrarConexion(); // Cerrar la conexión
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
