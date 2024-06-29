package proyecto_bd.cajaChica.Paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.CajeroDAO;
import proyecto_bd.cajaChica.Dao.UsuarioDAO;
import proyecto_bd.cajaChica.Dao.DocumentoDAO;
import proyecto_bd.cajaChica.Dao.RendicionDocumentoDAO;
import proyecto_bd.cajaChica.Entidades.Documento;
import proyecto_bd.cajaChica.Entidades.RendicionDocumento;
import proyecto_bd.cajaChica.Entidades.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DocumentoGUI extends JFrame {
    private CajeroDAO cajeroDAO;
    private UsuarioDAO usuarioDAO;
    private DocumentoDAO documentoDAO;
    private RendicionDocumentoDAO rendicionDocumentoDAO;
    private JTextField txtDescripcion;
    private JComboBox<RendicionDocumento> comboBoxRendicionDocumento;
    private JTable tableDocumentos;
    private DefaultTableModel model;
    private int selectedIdDocumento = -1;

    public DocumentoGUI(Connection conexion) {
        this.cajeroDAO = new CajeroDAO(conexion);
        this.usuarioDAO = new UsuarioDAO(conexion);
        this.documentoDAO = new DocumentoDAO(conexion);
        this.rendicionDocumentoDAO = new RendicionDocumentoDAO(conexion);
        initComponents();
        loadData();
        setLocationRelativeTo(null);
        setUndecorated(true); // Hace que la ventana sea undecorable
    }

    private void initComponents() {
        setTitle("Gestión de Documentos");
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

        panelForm.add(new JLabel("Rendición Documento:"));
        comboBoxRendicionDocumento = new JComboBox<>();
        cargarRendicionesDocumento();
        panelForm.add(comboBoxRendicionDocumento);

        panelForm.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelForm.add(txtDescripcion);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDocumento();
            }
        });
        panelForm.add(btnGuardar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Cliente");
        model.addColumn("Cajero");
        model.addColumn("Fecha Rendición");
        model.addColumn("Monto Rendido");
        model.addColumn("Descripción");

        tableDocumentos = new JTable(model);
        add(new JScrollPane(tableDocumentos), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarDocumento();
            }
        });
        panelForm.add(btnActualizar);

        tableDocumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = tableDocumentos.getSelectedRow();
                if (selectedRow != -1) {
                    RendicionDocumento rendicion = (RendicionDocumento) model.getValueAt(selectedRow, 1);
                    comboBoxRendicionDocumento.setSelectedItem(rendicion);
                    txtDescripcion.setText(model.getValueAt(selectedRow, 2).toString());
                    selectedIdDocumento = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                }
            }
        });

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDocumento();
            }
        });
        add(btnEliminar, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            List<Documento> documentos = documentoDAO.obtenerDocumentos();
            for (Documento documento : documentos) {
                RendicionDocumento rendicion = rendicionDocumentoDAO.obtenerRendicion(documento.getIdRendicionDocumento());
                Usuario cajero = usuarioDAO.obtenerUsuarioPorId(rendicion.getIdCajero());
                Usuario cliente = usuarioDAO.obtenerUsuarioPorId(rendicion.getIdCliente());
                model.addRow(new Object[]{
                    documento.getIdDocumento(),
                    cajero.getNombre() + " " + cajero.getApellido(),
                    cliente.getNombre() + " " + cliente.getApellido(),
                    rendicion.getFechaRendicion(),
                    rendicion.getMontoRendido(),
                    documento.getDescripcion()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarRendicionesDocumento() {
        try {
            List<RendicionDocumento> rendiciones = rendicionDocumentoDAO.obtenerRendiciones();
            for (RendicionDocumento rendicion : rendiciones) {
                Usuario cajero = usuarioDAO.obtenerUsuarioPorId(rendicion.getIdCajero());
                Usuario cliente = usuarioDAO.obtenerUsuarioPorId(rendicion.getIdCliente());
                comboBoxRendicionDocumento.addItem(rendicion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retornar() {
        new PrincipalGUI().setVisible(true);
        setVisible(false);
    }

    private void guardarDocumento() {
        RendicionDocumento rendicion = (RendicionDocumento) comboBoxRendicionDocumento.getSelectedItem();
        int idRendicionDocumento = rendicion.getIdRendicionDocumento();
        String descripcion = txtDescripcion.getText();

        Documento documento = new Documento(0, idRendicionDocumento, descripcion);
        try {
            documentoDAO.insertar(documento);
            loadData();
            JOptionPane.showMessageDialog(this, "Documento guardado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el documento.");
        }
    }

    private void actualizarDocumento() {
        if (selectedIdDocumento == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para actualizar.");
            return;
        }

        RendicionDocumento rendicion = (RendicionDocumento) comboBoxRendicionDocumento.getSelectedItem();
        int idRendicionDocumento = rendicion.getIdRendicionDocumento();
        String descripcion = txtDescripcion.getText();

        Documento documento = new Documento(selectedIdDocumento, idRendicionDocumento, descripcion);

        try {
            documentoDAO.actualizar(documento);
            loadData();
            JOptionPane.showMessageDialog(this, "Documento actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el documento.");
        }
    }

    private void eliminarDocumento() {
        int selectedRow = tableDocumentos.getSelectedRow();
        if (selectedRow != -1) {
            int idDocumento = (int) model.getValueAt(selectedRow, 0);
            try {
                documentoDAO.eliminar(idDocumento);
                loadData();
                JOptionPane.showMessageDialog(this, "Documento eliminado exitosamente.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el documento.");
            }
        }
    }

    public static void main(String[] args) {
        ConexionBD conexion = new ConexionBD(); // obtener conexión;
        Connection conn = conexion.establecerConexion();
        new DocumentoGUI(conn).setVisible(true);
    }
}
