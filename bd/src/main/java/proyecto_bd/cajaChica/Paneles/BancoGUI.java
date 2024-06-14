package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.BancoDAO;
import proyecto_bd.cajaChica.Entidades.Banco;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class BancoGUI extends JFrame {
    private BancoDAO bancoDAO;
    private JTextField txtNombre, txtNumeroCuenta, txtSaldo;
    private JTable tableBancos;
    private DefaultTableModel model;

    public BancoGUI(Connection conexion) {
        this.bancoDAO = new BancoDAO(conexion);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Gestión de Bancos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 2));
        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Número de Cuenta:"));
        txtNumeroCuenta = new JTextField();
        panelForm.add(txtNumeroCuenta);
        panelForm.add(new JLabel("Saldo:"));
        txtSaldo = new JTextField();
        panelForm.add(txtSaldo);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarBanco();
            }
        });
        panelForm.add(btnGuardar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Número de Cuenta");
        model.addColumn("Saldo");

        tableBancos = new JTable(model);
        add(new JScrollPane(tableBancos), BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarBanco();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        try {
            List<Banco> bancos = bancoDAO.obtenerBancos();
            for (Banco banco : bancos) {
                model.addRow(new Object[]{banco.getId(), banco.getNombre(), banco.getNumeroCuenta(), banco.getSaldo()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarBanco() {
        String nombre = txtNombre.getText();
        String numeroCuenta = txtNumeroCuenta.getText();
        double saldo = Double.parseDouble(txtSaldo.getText());

        Banco banco = new Banco(nombre, numeroCuenta, saldo);
        try {
            bancoDAO.insertar(banco);
            model.addRow(new Object[]{banco.getId(), banco.getNombre(), banco.getNumeroCuenta(), banco.getSaldo()});
            JOptionPane.showMessageDialog(this, "Banco guardado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el banco.");
        }
    }

    private void eliminarBanco() {
        int selectedRow = tableBancos.getSelectedRow();
        if (selectedRow != -1) {
            int idBanco = (int) model.getValueAt(selectedRow, 0);
            try {
                bancoDAO.eliminar(idBanco);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Banco eliminado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el banco.");
            }
        }
    }

    public static void main(String[] args) {
        // Aquí debes establecer la conexión a tu base de datos
        ConexionBD conexion = new ConexionBD(); // obtener conexión;
        Connection conn = conexion.establecerConexion();
        new BancoGUI(conn).setVisible(true);
    }
}
