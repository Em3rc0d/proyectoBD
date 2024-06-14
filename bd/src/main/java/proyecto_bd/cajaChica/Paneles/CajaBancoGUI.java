package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.CajaBancoDAO;
import proyecto_bd.cajaChica.Entidades.CajaBanco;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class CajaBancoGUI extends JFrame {

    private CajaBancoDAO cajaBancoDAO;
    private JTextField txtFecha, txtMonto, txtIdCajero;
    private JTable tableCajaBancos;
    private DefaultTableModel model;

    public CajaBancoGUI(Connection conexion) {
        this.cajaBancoDAO = new CajaBancoDAO(conexion);
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
        btnSalir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
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
        panelForm.add(new JLabel("ID Cajero:"));
        txtIdCajero = new JTextField();
        panelForm.add(txtIdCajero);

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
        model.addColumn("Fecha");
        model.addColumn("Monto");
        model.addColumn("ID Cajero");

        tableCajaBancos = new JTable(model);
        add(new JScrollPane(tableCajaBancos), BorderLayout.CENTER);

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
        try {
            List<CajaBanco> cajaBancos = cajaBancoDAO.obtenerCajaBancos();
            for (CajaBanco cajaBanco : cajaBancos) {
                model.addRow(new Object[]{cajaBanco.getFecha(), cajaBanco.getMonto(), cajaBanco.getIdCajero()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retornar(){
        new PrincipalGUI().setVisible(true);
        setVisible(false);
    }
    private void guardarCajaBanco() {
        String fecha = txtFecha.getText();
        double monto = Double.parseDouble(txtMonto.getText());
        int idCajero = Integer.parseInt(txtIdCajero.getText());

        CajaBanco cajaBanco = new CajaBanco(java.sql.Date.valueOf(fecha), monto, idCajero);
        try {
            cajaBancoDAO.insertar(cajaBanco);
            model.addRow(new Object[]{cajaBanco.getFecha(), cajaBanco.getMonto(), cajaBanco.getIdCajero()});
            JOptionPane.showMessageDialog(this, "CajaBanco guardado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar CajaBanco.");
        }
    }

    private void eliminarCajaBanco() {
        int selectedRow = tableCajaBancos.getSelectedRow();
        if (selectedRow != -1) {
            java.sql.Date fecha = (java.sql.Date) model.getValueAt(selectedRow, 0);
            try {
                cajaBancoDAO.eliminar(fecha);
                model.removeRow(selectedRow);
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
