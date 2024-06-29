package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.CajaBancoDAO;
import proyecto_bd.cajaChica.Dao.CajeroDAO;
import proyecto_bd.cajaChica.Dao.UsuarioDAO;
import proyecto_bd.cajaChica.Entidades.CajaBanco;
import proyecto_bd.cajaChica.Entidades.Cajero;
import proyecto_bd.cajaChica.Entidades.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CajaBancoGUI extends JFrame {
    private UsuarioDAO usuarioDAO;
    private CajaBancoDAO cajaBancoDAO;
    private CajeroDAO cajeroDAO;
    private JTextField txtFecha, txtMonto;
    private JComboBox<String> comboBoxIdCajero;
    private JTable tableCajaBancos;
    private DefaultTableModel model;
    private int selectedIdCajaBanco;

    public CajaBancoGUI(Connection conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
        this.cajaBancoDAO = new CajaBancoDAO(conexion);
        this.cajeroDAO = new CajeroDAO(conexion);
        initComponents();
        loadData();
        setLocationRelativeTo(null);
        setUndecorated(true); // Hace que la ventana sea undecorable
    }

    private void initComponents() {
        setTitle("Gestión de CajaBanco");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 2));
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
        panelForm.add(new JLabel("Cajero:"));
        comboBoxIdCajero = new JComboBox<>();
        cargarCajeros();
        panelForm.add(comboBoxIdCajero);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCajaBanco();
            }
        });
        panelForm.add(btnGuardar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Fecha");
        model.addColumn("Monto");
        model.addColumn("Cajero");

        tableCajaBancos = new JTable(model);
        add(new JScrollPane(tableCajaBancos), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCajaBanco();
            }
        });
        panelForm.add(btnActualizar);

        tableCajaBancos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableCajaBancos.getSelectedRow();
                txtFecha.setText(model.getValueAt(selectedRow, 1).toString());
                txtMonto.setText(model.getValueAt(selectedRow, 2).toString());
                comboBoxIdCajero.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
                selectedIdCajaBanco = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCajaBanco();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<CajaBanco> cajaBancos = cajaBancoDAO.obtenerCajaBancos();
            for (CajaBanco cajaBanco : cajaBancos) {
                model.addRow(new Object[]{cajaBanco.getIdCajaBanco(), cajaBanco.getFecha(), cajaBanco.getMonto(), cajaBanco.getIdCajero()});
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
                comboBoxIdCajero.addItem(cajero.getIdCajero() + ": " + usuario.getNombre() + " " + usuario.getApellido());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retornar() {
        new PrincipalGUI().setVisible(true);
        setVisible(false);
    }

    private void guardarCajaBanco() {
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String selectedCajeroString = (String) comboBoxIdCajero.getSelectedItem();
        int idCajero = Integer.parseInt(selectedCajeroString.split(":")[0].trim()); // Asumiendo que el toString() de Cajero es "id: nombre apellido"

        CajaBanco cajaBanco = new CajaBanco(java.sql.Date.valueOf(fecha), monto, idCajero);
        try {
            cajaBancoDAO.insertar(cajaBanco);
            loadData();
            JOptionPane.showMessageDialog(this, "CajaBanco guardado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar CajaBanco.");
        }
    }

    private void actualizarCajaBanco() {
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        String selectedCajeroString = (String) comboBoxIdCajero.getSelectedItem();
        int idCajero = Integer.parseInt(selectedCajeroString.split(":")[0].trim()); // Asumiendo que el toString() de Cajero es "id: nombre apellido"

        CajaBanco cajaBanco = new CajaBanco(java.sql.Date.valueOf(fecha), monto, idCajero);
        cajaBanco.setIdCajaBanco(selectedIdCajaBanco); // Establece el ID del registro a actualizar

        try {
            cajaBancoDAO.actualizar(cajaBanco);
            loadData();
            JOptionPane.showMessageDialog(this, "CajaBanco actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar CajaBanco.");
        }
    }

    private void eliminarCajaBanco() {
        int selectedRow = tableCajaBancos.getSelectedRow();
        if (selectedRow != -1) {
            int idCajaBanco = (int) model.getValueAt(selectedRow, 0);
            try {
                cajaBancoDAO.eliminar(idCajaBanco);
                loadData();
                JOptionPane.showMessageDialog(this, "CajaBanco eliminado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar CajaBanco.");
            }
        }
    }

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD(); // obtener conexión;
        Connection conn = conexion.establecerConexion();
        new CajaBancoGUI(conn).setVisible(true);
    }
}
