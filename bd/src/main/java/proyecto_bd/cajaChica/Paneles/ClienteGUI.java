package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.ClienteDAO;
import proyecto_bd.cajaChica.Dao.UsuarioDAO;
import proyecto_bd.cajaChica.Entidades.Cliente;
import proyecto_bd.cajaChica.Entidades.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClienteGUI extends JFrame {

    private ClienteDAO clienteDAO;
    private UsuarioDAO usuarioDAO;
    private JTextField txtIdUsuario;
    private JTable tableClientes;
    private DefaultTableModel model;
    private int selectedIdCliente;

    public ClienteGUI(Connection conexion) {
        this.clienteDAO = new ClienteDAO(conexion);
        this.usuarioDAO = new UsuarioDAO(conexion);
        initComponents();
        loadData();
        setLocationRelativeTo(null);
        setUndecorated(true); // Hace que la ventana sea undecorable
    }

    private void initComponents() {
        setTitle("Gestión de Cliente");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 2));
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

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });
        panelForm.add(btnGuardar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("ID Cliente");
        model.addColumn("Nombre"); // Nombre del usuario
        model.addColumn("Apellido"); // Apellido del usuario
        model.addColumn("Email"); // Email del usuario
        model.addColumn("Teléfono"); // Teléfono del usuario

        tableClientes = new JTable(model);
        add(new JScrollPane(tableClientes), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });
        panelForm.add(btnActualizar);

        tableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableClientes.getSelectedRow();
                txtIdUsuario.setText(model.getValueAt(selectedRow, 1).toString());
                selectedIdCliente = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            for (Cliente cliente : clientes) {
                Usuario usuario = usuarioDAO.obtenerUsuarioPorId(cliente.getIdUsuario());
                model.addRow(new Object[]{
                    cliente.getIdCliente(),
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

    private void guardarCliente() {
        int idUsuario = Integer.parseInt(txtIdUsuario.getText());

        Cliente cliente = new Cliente(idUsuario);
        try {
            clienteDAO.insertar(cliente);
            loadData();
            JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar Cliente.");
        }
    }

    private void actualizarCliente() {
        int idUsuario = Integer.parseInt(txtIdUsuario.getText());

        Cliente cliente = new Cliente(idUsuario);
        cliente.setIdCliente(selectedIdCliente);

        try {
            clienteDAO.actualizar(cliente);
            loadData();
            JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar Cliente.");
        }
    }

    private void eliminarCliente() {
        int selectedRow = tableClientes.getSelectedRow();
        if (selectedRow != -1) {
            int idCliente = (int) model.getValueAt(selectedRow, 0);
            try {
                clienteDAO.eliminar(idCliente);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar Cliente.");
            }
        }
    }

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD(); // obtener conexión;
        Connection conn = conexion.establecerConexion();
        new ClienteGUI(conn).setVisible(true);
    }
}
