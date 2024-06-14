package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.DevolucionReposicionDAO;
import proyecto_bd.cajaChica.Entidades.DevolucionReposicion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DevolucionReposicionGUI extends JFrame {
    private DevolucionReposicionDAO devolucionReposicionDAO;
    private JTextField txtFecha, txtMonto, txtMotivo, txtTipoOperacion, txtIdCajero, txtIdCliente;
    private JTable tableDevolucionesReposiciones;
    private DefaultTableModel model;

    public DevolucionReposicionGUI(Connection conexion) {
        this.devolucionReposicionDAO = new DevolucionReposicionDAO(conexion);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Gestión de Devoluciones/Reposiciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(7, 2));
        panelForm.add(new JLabel("Fecha (YYYY-MM-DD):"));
        txtFecha = new JTextField();
        panelForm.add(txtFecha);
        panelForm.add(new JLabel("Monto:"));
        txtMonto = new JTextField();
        panelForm.add(txtMonto);
        panelForm.add(new JLabel("Motivo:"));
        txtMotivo = new JTextField();
        panelForm.add(txtMotivo);
        panelForm.add(new JLabel("Tipo Operación:"));
        txtTipoOperacion = new JTextField();
        panelForm.add(txtTipoOperacion);
        panelForm.add(new JLabel("ID Cajero:"));
        txtIdCajero = new JTextField();
        panelForm.add(txtIdCajero);
        panelForm.add(new JLabel("ID Cliente:"));
        txtIdCliente = new JTextField();
        panelForm.add(txtIdCliente);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDevolucionReposicion();
            }
        });
        panelForm.add(btnGuardar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("Fecha");
        model.addColumn("Monto");
        model.addColumn("Motivo");
        model.addColumn("Tipo Operación");
        model.addColumn("ID Cajero");
        model.addColumn("ID Cliente");

        tableDevolucionesReposiciones = new JTable(model);
        add(new JScrollPane(tableDevolucionesReposiciones), BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDevolucionReposicion();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<DevolucionReposicion> devolucionesReposiciones = devolucionReposicionDAO
                    .obtenerDevolucionesReposicion();
            for (DevolucionReposicion devolucionReposicion : devolucionesReposiciones) {
                model.addRow(new Object[] { devolucionReposicion.getFechaDevolucion(),
                        devolucionReposicion.getMontoDevolucion(), devolucionReposicion.getMotivo(),
                        devolucionReposicion.getTipoOperacion(), devolucionReposicion.getIdCajero(),
                        devolucionReposicion.getIdCliente() });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarDevolucionReposicion() {
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String motivo = txtMotivo.getText();
        String tipoOperacion = txtTipoOperacion.getText();
        int idCajero = Integer.parseInt(txtIdCajero.getText());
        int idCliente = Integer.parseInt(txtIdCliente.getText());

        DevolucionReposicion devolucionReposicion = new DevolucionReposicion(java.sql.Date.valueOf(fecha), monto,
                motivo, tipoOperacion, idCajero, idCliente);
        try {
            devolucionReposicionDAO.insertar(devolucionReposicion);
            model.addRow(
                    new Object[] { devolucionReposicion.getFechaDevolucion(), devolucionReposicion.getMontoDevolucion(),
                            devolucionReposicion.getMotivo(), devolucionReposicion.getTipoOperacion(),
                            devolucionReposicion.getIdCajero(), devolucionReposicion.getIdCliente() });
            JOptionPane.showMessageDialog(this, "Devolución/Reposición guardada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la Devolución/Reposición.");
        }
    }

    private void eliminarDevolucionReposicion() {
        int selectedRow = tableDevolucionesReposiciones.getSelectedRow();
        if (selectedRow != -1) {
            java.sql.Date fecha = (java.sql.Date) model.getValueAt(selectedRow, 0);
            try {
                devolucionReposicionDAO.eliminar(fecha);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Devolución/Reposición eliminada exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar la Devolución/Reposición.");
            }
        }
    }

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD(); // obtener conexión;
        Connection conn = conexion.establecerConexion();
        new DevolucionReposicionGUI(conn).setVisible(true);

    }
}
