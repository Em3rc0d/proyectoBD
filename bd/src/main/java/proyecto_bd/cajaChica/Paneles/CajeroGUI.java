package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;

import proyecto_bd.cajaChica.Dao.CajeroDAO;
import proyecto_bd.cajaChica.Entidades.Cajero;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import proyecto_bd.cajaChica.Conexion.ConexionBD;

public class CajeroGUI extends JFrame {
    private CajeroDAO cajeroDAO;
    
    public CajeroGUI() {
        // Configuración de la conexión a la base de datos
        try {
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_basedatos", "usuario", "contraseña");
            cajeroDAO = new CajeroDAO(conexion);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        
        setTitle("Gestión de Cajeros");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Tabla para mostrar los datos de Cajero
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botones CRUD
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Agregar");
        JButton updateButton = new JButton("Actualizar");
        JButton deleteButton = new JButton("Eliminar");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Acción de agregar
        addButton.addActionListener(e -> {
            Cajero cajero = new Cajero(1);  // Asumiendo que ID de usuario es 1
            try {
                cajeroDAO.insertar(cajero);
                actualizarTabla(table);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        // Acción de actualizar
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Cajero cajero = new Cajero(2);  // Asumiendo que ID de usuario es 2
                try {
                    cajeroDAO.actualizar(cajero);
                    actualizarTabla(table);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Acción de eliminar
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    cajeroDAO.eliminar(selectedRow + 1);  // Asumiendo que ID es el índice de la fila
                    actualizarTabla(table);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        actualizarTabla(table);
        add(panel);
    }
    
    private void actualizarTabla(JTable table) {
        try {
            List<Cajero> cajeros = cajeroDAO.obtenerCajeros();
            String[] columnNames = {"ID Usuario"};
            Object[][] data = new Object[cajeros.size()][1];
            for (int i = 0; i < cajeros.size(); i++) {
                data[i][0] = cajeros.get(i).getIdUsuario();
            }
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.establecerConexion();
        new CajeroGUI().setVisible(true);
    }
}
