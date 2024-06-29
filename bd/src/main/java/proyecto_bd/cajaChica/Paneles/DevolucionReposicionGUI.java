package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.DevolucionReposicionDAO;
import proyecto_bd.cajaChica.Dao.UsuarioDAO;
import proyecto_bd.cajaChica.Dao.CajeroDAO;
import proyecto_bd.cajaChica.Dao.ClienteDAO;
import proyecto_bd.cajaChica.Entidades.DevolucionReposicion;
import proyecto_bd.cajaChica.Entidades.Usuario;
import proyecto_bd.cajaChica.Entidades.Cajero;
import proyecto_bd.cajaChica.Entidades.Cliente;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DevolucionReposicionGUI extends JFrame {
    private UsuarioDAO usuarioDAO;
    private DevolucionReposicionDAO devolucionReposicionDAO;
    private CajeroDAO cajeroDAO;
    private ClienteDAO clienteDAO;
    private JTextField txtFecha, txtMonto, txtMotivo;
    private JComboBox<String> comboBoxTipoOperacion, comboBoxCajero, comboBoxCliente;
    private JTable tableDevolucionesReposiciones;
    private DefaultTableModel model;
    private int selectedIdDevReposicion;

    public DevolucionReposicionGUI(Connection conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
        this.devolucionReposicionDAO = new DevolucionReposicionDAO(conexion);
        this.cajeroDAO = new CajeroDAO(conexion);
        this.clienteDAO = new ClienteDAO(conexion);
        initComponents();
        loadData();
        setLocationRelativeTo(null);
        setUndecorated(true); 
    }

    private void initComponents() {
        setTitle("Gestión de Devoluciones/Reposiciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(7, 2));
        JButton btnSalir = new JButton("Retornar");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retornar();
            }
        });
        add(btnSalir, BorderLayout.BEFORE_LINE_BEGINS);
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
        String[] tiposOperacion = {"Devolución", "Reposición"};
        comboBoxTipoOperacion = new JComboBox<>(tiposOperacion);
        panelForm.add(comboBoxTipoOperacion);

        panelForm.add(new JLabel("Cajero:"));
        comboBoxCajero = new JComboBox<>();
        cargarCajeros();
        panelForm.add(comboBoxCajero);

        panelForm.add(new JLabel("Cliente:"));
        comboBoxCliente = new JComboBox<>();
        cargarClientes();
        panelForm.add(comboBoxCliente);

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
        model.addColumn("ID");
        model.addColumn("Fecha");
        model.addColumn("Monto");
        model.addColumn("Motivo");
        model.addColumn("Tipo Operación");
        model.addColumn("ID Cajero");
        model.addColumn("ID Cliente");

        tableDevolucionesReposiciones = new JTable(model);
        add(new JScrollPane(tableDevolucionesReposiciones), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDevolucionReposicion();
            }
        });
        panelForm.add(btnActualizar);

        tableDevolucionesReposiciones.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableDevolucionesReposiciones.getSelectedRow();
                if (selectedRow != -1) {
                    txtFecha.setText(model.getValueAt(selectedRow, 1).toString());
                    txtMonto.setText(model.getValueAt(selectedRow, 2).toString());
                    txtMotivo.setText(model.getValueAt(selectedRow, 3).toString());
                    comboBoxTipoOperacion.setSelectedItem(model.getValueAt(selectedRow, 4).toString());
                    comboBoxCajero.setSelectedItem(model.getValueAt(selectedRow, 5).toString());
                    comboBoxCliente.setSelectedItem(model.getValueAt(selectedRow, 6).toString());
                    selectedIdDevReposicion = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                }
            }
        });

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
        model.setRowCount(0);
        try {
            List<DevolucionReposicion> devolucionesReposiciones = devolucionReposicionDAO.obtenerDevolucionesReposicion();
            for (DevolucionReposicion devolucionReposicion : devolucionesReposiciones) {
                model.addRow(new Object[]{
                    devolucionReposicion.getIdDevReposicion(),
                    devolucionReposicion.getFechaDevolucion(),
                    devolucionReposicion.getMontoDevolucion(),
                    devolucionReposicion.getMotivo(),
                    devolucionReposicion.getTipoOperacion(),
                    devolucionReposicion.getIdCajero(),
                    devolucionReposicion.getIdCliente()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarCajeros() {
        try {
            List<Cajero> cajeros = cajeroDAO.obtenerCajeros();
            for (Cajero cajero : cajeros) {
                Usuario usuario = usuarioDAO.obtenerUsuarioPorId(cajero.getIdUsuario());
                comboBoxCajero.addItem(cajero.getIdCajero() + ": " + usuario.getNombre() + " " + usuario.getApellido());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            for (Cliente cliente : clientes) {
                Usuario usuario = usuarioDAO.obtenerUsuarioPorId(cliente.getIdUsuario());
                comboBoxCliente.addItem(cliente.getIdCliente() + ": " + usuario.getNombre() + " " + usuario.getApellido());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retornar() {
        new PrincipalGUI().setVisible(true);
        setVisible(false);
    }

    private void guardarDevolucionReposicion() {
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String motivo = txtMotivo.getText();
        String tipoOperacion = (String) comboBoxTipoOperacion.getSelectedItem();

        String selectedCajeroString = (String) comboBoxCajero.getSelectedItem();
        int idCajero = Integer.parseInt(selectedCajeroString.split(":")[0].trim());

        String selectedClienteString = (String) comboBoxCliente.getSelectedItem();
        int idCliente = Integer.parseInt(selectedClienteString.split(":")[0].trim());

        DevolucionReposicion devolucionReposicion = new DevolucionReposicion(0, java.sql.Date.valueOf(fecha), monto,
                motivo, tipoOperacion, idCajero, idCliente);
        try {
            devolucionReposicionDAO.insertar(devolucionReposicion);
            loadData();
            JOptionPane.showMessageDialog(this, "Devolución/Reposición guardada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la Devolución/Reposición.");
        }
    }

    private void actualizarDevolucionReposicion() {
        if (selectedIdDevReposicion == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para actualizar.");
            return;
        }

        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String motivo = txtMotivo.getText();
        String tipoOperacion = (String) comboBoxTipoOperacion.getSelectedItem();

        String selectedCajeroString = (String) comboBoxCajero.getSelectedItem();
        int idCajero = Integer.parseInt(selectedCajeroString.split(":")[0].trim());

        String selectedClienteString = (String) comboBoxCliente.getSelectedItem();
        int idCliente = Integer.parseInt(selectedClienteString.split(":")[0].trim());

        DevolucionReposicion devolucionReposicion = new DevolucionReposicion(selectedIdDevReposicion, java.sql.Date.valueOf(fecha), monto,
                motivo, tipoOperacion, idCajero, idCliente);

        try {
            devolucionReposicionDAO.actualizar(devolucionReposicion);
            loadData();
            JOptionPane.showMessageDialog(this, "Devolución/Reposición actualizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la Devolución/Reposición.");
        }
    }

    private void eliminarDevolucionReposicion() {
        int selectedRow = tableDevolucionesReposiciones.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            try {
                devolucionReposicionDAO.eliminar(id);
                loadData();
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
