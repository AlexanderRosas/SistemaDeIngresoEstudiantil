package UserInterface.Form;
import Framework.SieException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDataEntryForm extends JFrame {
    private JTextField txtCodigo;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtAño;
    private JButton btnGuardar;
    private JButton btnConsultar;

    public StudentDataEntryForm() {
        // Configuración de la ventana principal
        setTitle("Ingreso de Datos Estudiantiles");
        setSize(500, 400); // Tamaño de la ventana ampliado
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Crear los componentes
        txtCodigo = createRoundedTextField(20);
        txtNombres = createRoundedTextField(20);
        txtApellidos = createRoundedTextField(20);
        txtAño = createRoundedTextField(4);
        btnGuardar = new JButton("Guardar");
        btnConsultar = new JButton("Consultar Datos");

        // Configurar el layout de la ventana con GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
        gbc.anchor = GridBagConstraints.WEST; // Alinear los componentes a la izquierda

        // Añadir los componentes a la ventana
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Código Único:"), gbc);

        gbc.gridx = 1;
        add(txtCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nombres:"), gbc);

        gbc.gridx = 1;
        add(txtNombres, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Apellidos:"), gbc);

        gbc.gridx = 1;
        add(txtApellidos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Año:"), gbc);

        gbc.gridx = 1;
        add(txtAño, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los botones
        add(btnGuardar, gbc);

        gbc.gridy = 5;
        add(btnConsultar, gbc);

        // Acción del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    validateData(); // Validar los datos
                    // Obtener los datos ingresados
                    String codigo = txtCodigo.getText();
                    String nombres = txtNombres.getText();
                    String apellidos = txtApellidos.getText();
                    String año = txtAño.getText();

                    // Mostrar un mensaje de confirmación
                    JOptionPane.showMessageDialog(null, "Datos guardados:\n" +
                            "Código: " + codigo + "\n" +
                            "Nombres: " + nombres + "\n" +
                            "Apellidos: " + apellidos + "\n" +
                            "Año: " + año);
                } catch (SieException ex) {
                    // Mostrar el mensaje de error
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón Consultar Datos
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la ventana de consulta
                new ConsultarDatosForm().setVisible(true);
            }
        });
    }

    private JTextField createRoundedTextField(int columns) {
        JTextField textField = new JTextField(columns);
        // Crear un borde redondeado para el campo de texto
        Border roundedBorder = BorderFactory.createLineBorder(Color.GRAY, 1, true);
        textField.setBorder(roundedBorder);
        textField.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente más grande
        textField.setPreferredSize(new Dimension(200, 30)); // Tamaño preferido para hacer el campo más alto
        return textField;
    }

    private void validateData() throws SieException {
        String codigo = txtCodigo.getText();
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String año = txtAño.getText();

        // Validar que el código único tenga exactamente 10 dígitos
        if (codigo == null || codigo.length() != 10) {
            throw new SieException("El código único debe tener 10 dígitos", "StudentDataEntryForm", "validateData");
        }

        // Validar que ningún campo esté vacío
        if (nombres == null || nombres.isEmpty() || apellidos == null || apellidos.isEmpty() || año == null || año.isEmpty()) {
            throw new SieException("Todos los campos deben ser completados", "StudentDataEntryForm", "validateData");
        }
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana principal
        SwingUtilities.invokeLater(() -> {
            new StudentDataEntryForm().setVisible(true);
        });
    }
}

class ConsultarDatosForm extends JFrame {
    private JTextField txtBuscarCodigo;
    private JButton btnBuscar;

    public ConsultarDatosForm() {
        // Configuración de la ventana de consulta
        setTitle("Consultar Datos de Estudiante");
        setSize(400, 200); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla

        // Crear los componentes
        txtBuscarCodigo = new JTextField(20);
        btnBuscar = new JButton("Buscar");

        // Configurar el layout de la ventana con GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margen entre componentes
        gbc.anchor = GridBagConstraints.CENTER; // Centrar los componentes

        // Añadir los componentes a la ventana
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Código Único:"), gbc);

        gbc.gridx = 1;
        add(txtBuscarCodigo, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(btnBuscar, gbc);

        // Acción del botón Buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtBuscarCodigo.getText();
                try {
                    String resultado = consultarDatos(codigo);
                    JOptionPane.showMessageDialog(null, resultado, "Resultado de la Consulta", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la Consulta", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private String consultarDatos(String codigo) throws Exception {
        if (codigo == null || codigo.length() != 10) {
            throw new SieException("El código único debe tener 10 dígitos", "ConsultarDatosForm", "consultarDatos");
        }

        // Conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/tu_basedatos";
        String user = "tu_usuario";
        String password = "tu_contraseña";
        String resultado = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT codigo_unico, nombres, apellidos FROM estudiantes WHERE codigo_unico = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, codigo);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombreCompleto = rs.getString("nombres") + " " + rs.getString("apellidos");
                resultado = codigo + "/" + nombreCompleto + "/MATRICULADO-2024-A";
            } else {
                throw new SieException("Estudiante no encontrado", "ConsultarDatosForm", "consultarDatos");
            }
        } catch (Exception ex) {
            throw new SieException("Error al consultar la base de datos", "ConsultarDatosForm", "consultarDatos");
        }

        return resultado;
    }
}
