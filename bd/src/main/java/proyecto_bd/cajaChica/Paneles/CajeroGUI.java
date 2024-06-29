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
import proyecto_bd.cajaChica.Dao.UsuarioDAO;
import proyecto_bd.cajaChica.Entidades.Usuario;

public class CajeroGUI extends JFrame {

    private CajeroDAO cajeroDAO;
    private UsuarioDAO usuarioDAO;
    private JComboBox<Usuario> comboUsuarios; // Cambiar JTextField a JComboBox
    private JTable tableCajeros;
    private DefaultTableModel model;

    public CajeroGUI(Connection conexion) {
        this.cajeroDAO = new CajeroDAO(conexion);
        this.usuarioDAO = new UsuarioDAO(conexion);
        initComponents();
        loadData();
        loadUsuarios();
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
        comboUsuarios = new JComboBox<>();
        panelForm.add(comboUsuarios);

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
        model.addColumn("ID Cajero");
        model.addColumn("Nombre"); // Nombre del usuario
        model.addColumn("Apellido"); // Apellido del usuario
        model.addColumn("Email"); // Email del usuario
        model.addColumn("Teléfono"); // Teléfono del usuario

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

    private void loadUsuarios() {
        comboUsuarios.removeAllItems(); // Limpiar el combo box
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerUsuariosNoAsignadosCajero();
            for (Usuario usuario : usuarios) {
                comboUsuarios.addItem(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void loadData() {
        model.setRowCount(0);
        try {
            List<Cajero> cajeros = cajeroDAO.obtenerCajeros();
            for (Cajero cajero : cajeros) {
                Usuario usuario = usuarioDAO.obtenerUsuarioPorId(cajero.getIdUsuario());
                model.addRow(new Object[]{
                    cajero.getIdCajero(),
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getEmail(),
                    usuario.getTelefono()
                });
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
        Usuario usuarioSeleccionado = (Usuario) comboUsuarios.getSelectedItem();
        if (usuarioSeleccionado != null) {
            int idUsuario = usuarioSeleccionado.getId();
            Cajero cajero = new Cajero(idUsuario);
            try {
                cajeroDAO.insertar(cajero);
                loadData();
                comboUsuarios.removeItem(usuarioSeleccionado); // Remover el usuario del combo box
                JOptionPane.showMessageDialog(this, "Cajero agregado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar el cajero.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario válido.");
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

