package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.UsuarioDAO;
import proyecto_bd.cajaChica.Entidades.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioGUI extends JFrame {
    private UsuarioDAO usuarioDAO;
    private JTextField txtNombre, txtApellido, txtEmail, txtTelefono, txtFechaIngreso;
    private JTable tableUsuarios;
    private DefaultTableModel model;
    private int selectedIdUsuario;

    public UsuarioGUI(Connection conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
        initComponents();
        loadData();
        setLocationRelativeTo(null);
        setUndecorated(true); // Hace que la ventana sea undecorable
    }

    private void initComponents() {
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(6, 2));
        JButton btnSalir = new JButton("Retornar");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retornar();
            }
        });
        add(btnSalir, BorderLayout.BEFORE_LINE_BEGINS);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelForm.add(txtApellido);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Fecha de Ingreso (YYYY-MM-DD):"));
        txtFechaIngreso = new JTextField();
        panelForm.add(txtFechaIngreso);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarUsuario();
            }
        });
        panelForm.add(btnGuardar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Email");
        model.addColumn("Teléfono");
        model.addColumn("Fecha de Ingreso");

        tableUsuarios = new JTable(model);
        add(new JScrollPane(tableUsuarios), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUsuario();
            }
        });
        panelForm.add(btnActualizar);

        tableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableUsuarios.getSelectedRow();
                txtNombre.setText(model.getValueAt(selectedRow, 1).toString());
                txtApellido.setText(model.getValueAt(selectedRow, 2).toString());
                txtEmail.setText(model.getValueAt(selectedRow, 3).toString());
                txtTelefono.setText(model.getValueAt(selectedRow, 4).toString());
                txtFechaIngreso.setText(model.getValueAt(selectedRow, 5).toString());
                selectedIdUsuario = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuario();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();
            for (Usuario usuario : usuarios) {
                model.addRow(new Object[]{
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellido(),
                        usuario.getEmail(),
                        usuario.getTelefono(),
                        usuario.getFechaIngreso()
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

    private void guardarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String fechaIngresoStr = txtFechaIngreso.getText();

        // Validación de campos
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || fechaIngresoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son requeridos.");
            return;
        }

        // Validación de formato de fecha
        if (!isValidDate(fechaIngresoStr)) {
            JOptionPane.showMessageDialog(this, "Fecha de Ingreso inválida. Debe estar en formato YYYY-MM-DD.");
            return;
        }

        Usuario usuario = new Usuario(nombre, apellido, email, telefono, java.sql.Date.valueOf(fechaIngresoStr));
        try {
            usuarioDAO.agregarUsuario(usuario);
            loadData();
            JOptionPane.showMessageDialog(this, "Usuario guardado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar Usuario.");
        }
    }

    private void actualizarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        String fechaIngresoStr = txtFechaIngreso.getText();

        // Validación de campos
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || fechaIngresoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son requeridos.");
            return;
        }

        // Validación de formato de fecha
        if (!isValidDate(fechaIngresoStr)) {
            JOptionPane.showMessageDialog(this, "Fecha de Ingreso inválida. Debe estar en formato YYYY-MM-DD.");
            return;
        }

        Usuario usuario = new Usuario(nombre, apellido, email, telefono, java.sql.Date.valueOf(fechaIngresoStr));
        usuario.setId(selectedIdUsuario);

        try {
            usuarioDAO.actualizarUsuario(usuario);
            loadData();
            JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar Usuario.");
        }
    }

    private void eliminarUsuario() {
        int selectedRow = tableUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            int idUsuario = (int) model.getValueAt(selectedRow, 0);
            try {
                usuarioDAO.eliminarUsuario(idUsuario);
                loadData();
                JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar Usuario.");
            }
        }
    }

    private boolean isValidDate(String dateStr) {
        try {
            java.sql.Date.valueOf(dateStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.establecerConexion();
        new UsuarioGUI(conn).setVisible(true);
    }
}
