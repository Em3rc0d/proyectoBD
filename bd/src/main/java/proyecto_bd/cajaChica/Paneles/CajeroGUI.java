package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.CajeroDAO;
import proyecto_bd.cajaChica.Entidades.Cajero;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CajeroGUI extends JFrame {

    private CajeroDAO cajeroDAO;
    private JTextField txtIdUsuario;
    private JTable tableCajeros;
    private DefaultTableModel model;

    public CajeroGUI(Connection conexion) {
        this.cajeroDAO = new CajeroDAO(conexion);
        initComponents();
        loadData();
        setLocationRelativeTo(null);
        setUndecorated(true); // Hace que la ventana sea undecorable
    }

    private void initComponents() {
        setTitle("Gestión de Cajeros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(2, 2));
        JButton btnSalir = new JButton("Retornar");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retornar();
            }
        });
        add(btnSalir, BorderLayout.BEFORE_LINE_BEGINS);
        panelForm.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField();
        panelForm.add(txtIdUsuario);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCajero();
            }
        });
        panelForm.add(btnAgregar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCajero();
            }
        });
        panelForm.add(btnActualizar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("ID Usuario");

        tableCajeros = new JTable(model);
        add(new JScrollPane(tableCajeros), BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCajero();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<Cajero> cajeros = cajeroDAO.obtenerCajeros();
            for (Cajero cajero : cajeros) {
                model.addRow(new Object[]{cajero.getIdUsuario()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retornar() {
        new PrincipalGUI().setVisible(true);
        setVisible(false);
    }

    private void agregarCajero() {
        int idUsuario = Integer.parseInt(txtIdUsuario.getText());
        Cajero cajero = new Cajero(idUsuario);
        try {
            cajeroDAO.insertar(cajero);
            model.addRow(new Object[]{cajero.getIdUsuario()});
            JOptionPane.showMessageDialog(this, "Cajero agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el cajero.");
        }
    }

    private void actualizarCajero() {
        int selectedRow = tableCajeros.getSelectedRow();
        if (selectedRow != -1) {
            int idUsuario = (int) model.getValueAt(selectedRow, 0);
            Cajero cajero = new Cajero(idUsuario);
            try {
                cajeroDAO.actualizar(cajero);
                model.setValueAt(cajero.getIdUsuario(), selectedRow, 0);
                JOptionPane.showMessageDialog(this, "Cajero actualizado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al actualizar el cajero.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cajero para actualizar.");
        }
    }

    private void eliminarCajero() {
        int selectedRow = tableCajeros.getSelectedRow();
        if (selectedRow != -1) {
            int idUsuario = (int) model.getValueAt(selectedRow, 0);
            try {
                cajeroDAO.eliminar(idUsuario);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Cajero eliminado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el cajero.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cajero para eliminar.");
        }
    }

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.establecerConexion();
        new CajeroGUI(conn).setVisible(true);
    }
}
