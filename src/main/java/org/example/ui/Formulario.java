package org.example.ui;

import org.example.service.ReporteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

public class Formulario extends JFrame {

    private JTextField txtColaborador;
    private JTextField txtCedula;
    private JTextField txtArea;
    private JTextField txtCargo;
    private JTextField txtReemplazo;
    private JTextField txtFecha;
    private JTextField txtDireccion;
    private JTextField txtLugarTrabajo;
    private JTextField txtRecibidoPor;
    private JTextField txtDia;
    private JTextField txtMes;
    private JTextField txtAnio;
    private JTextField txtCiudad;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtResponsableDTH;

    private Map<String, JCheckBox> checkBoxReportes;
    private static final Color PRIMARY_COLOR = new Color(99, 102, 241);
    private static final Color SECONDARY_COLOR = new Color(139, 92, 246);
    private static final Color BACKGROUND_COLOR = new Color(249, 250, 251);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    private static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    private static final Color BORDER_COLOR = new Color(229, 231, 235);

    public Formulario() {
        setTitle("Sistema de Documentación");
        setSize(900, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior con título y descripción
        JPanel headerPanel = crearHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel central con scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Card de datos personales
        JPanel datosCard = crearCardDatosPersonales();
        contentPanel.add(datosCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Card de selección de reportes
        JPanel reportesCard = crearCardReportes();
        contentPanel.add(reportesCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel footerPanel = crearFooter();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel crearHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel titulo = new JLabel("Generación de Documentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(TEXT_PRIMARY);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Complete la información y seleccione los documentos a generar");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(TEXT_SECONDARY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(subtitulo);

        return panel;
    }

    private JPanel crearCardDatosPersonales() {
        JPanel card = crearCard("Datos del Colaborador");
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 1;
        txtColaborador = agregarCampoAlCard(card, "Colaborador", row++, gbc, true);
        txtCedula = agregarCampoAlCard(card, "Cédula", row++, gbc, true);
        txtDireccion = agregarCampoAlCard(card, "Dirección", row++, gbc, true);
        txtLugarTrabajo = agregarCampoAlCard(card, "Lugar de Trabajo", row++, gbc, true);
        txtArea = agregarCampoAlCard(card, "Área / Departamento", row++, gbc, true);
        txtCargo = agregarCampoAlCard(card, "Cargo", row++, gbc, true);
        txtReemplazo = agregarCampoAlCard(card, "Reemplazo", row++, gbc, false);
        txtRecibidoPor = agregarCampoAlCard(card, "Recibido por", row++, gbc, true);
        txtDia  = agregarCampoAlCard(card, "Día", row++, gbc, true);
        txtMes  = agregarCampoAlCard(card, "Mes", row++, gbc, true);
        txtAnio = agregarCampoAlCard(card, "Año", row++, gbc, true);
        txtCiudad = agregarCampoAlCard(card, "Ciudad", row++, gbc, true);
        txtNombres = agregarCampoAlCard(card, "Nombres", row++, gbc, true);
        txtApellidos = agregarCampoAlCard(card, "Apellidos", row++, gbc, true);
        txtResponsableDTH = agregarCampoAlCard(card, "Responsable DTH", row++, gbc, true);

        txtFecha = agregarCampoAlCard(card, "Fecha", row++, gbc, false);
        txtFecha.setText(LocalDate.now().toString());
        txtFecha.setEditable(false);
        txtFecha.setBackground(BACKGROUND_COLOR);

        LocalDate hoy = LocalDate.now();
        txtDia.setText(String.valueOf(hoy.getDayOfMonth()));
        txtMes.setText(hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")));
        txtAnio.setText(String.valueOf(hoy.getYear()));

        return card;
    }

    private JPanel crearCardReportes() {
        JPanel card = crearCard("Documentos a Generar");
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        checkBoxReportes = new LinkedHashMap<>();

        String[] reportes = {
                "Constancia de Entrega de Documentos",
                "Acta de Entrega de Cargo",
                "Acta de Recepción de Reglamento",
                "Autorización de Caución",
                "Carnet con Descuento",
                "Carnet sin Descuento"
        };

        String[] archivos = {
                "ConstanciaEntregaDocumentos.jrxml",
                "ActaEntregaDescriptivoCargo.jrxml",
                "ActaRecepcionReglamento.jrxml",
                "AutorizacionCaucion.jrxml",
                "CarnetIdentificacionConDescuento.jrxml",
                "CarnetIdentificacionSinDescuento.jrxml"
        };

        JPanel checkboxContainer = new JPanel();
        checkboxContainer.setLayout(new GridLayout(0, 2, 15, 10));
        checkboxContainer.setBackground(CARD_COLOR);
        checkboxContainer.setBorder(new EmptyBorder(5, 15, 15, 15));

        for (int i = 0; i < reportes.length; i++) {
            JCheckBox checkBox = crearCheckBoxEstilizado(reportes[i]);
            checkBoxReportes.put(archivos[i], checkBox);
            checkboxContainer.add(checkBox);
        }

        card.add(checkboxContainer);

        // Panel de acciones rápidas
        JPanel accionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        accionesPanel.setBackground(CARD_COLOR);
        accionesPanel.setBorder(new EmptyBorder(0, 15, 10, 15));

        JButton btnSeleccionarTodos = crearBotonSecundario("Seleccionar Todos");
        JButton btnDeseleccionarTodos = crearBotonSecundario("Deseleccionar Todos");

        btnSeleccionarTodos.addActionListener(e ->
                checkBoxReportes.values().forEach(cb -> cb.setSelected(true))
        );

        btnDeseleccionarTodos.addActionListener(e ->
                checkBoxReportes.values().forEach(cb -> cb.setSelected(false))
        );

        accionesPanel.add(btnSeleccionarTodos);
        accionesPanel.add(btnDeseleccionarTodos);
        card.add(accionesPanel);

        return card;
    }

    private JPanel crearCard(String titulo) {
        JPanel card = new JPanel();
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(15, 0, 15, 0)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(TEXT_PRIMARY);
        lblTitulo.setBorder(new EmptyBorder(0, 15, 15, 15));
        card.add(lblTitulo);

        return card;
    }

    private JTextField agregarCampoAlCard(JPanel card, String label, int row,
                                          GridBagConstraints gbc, boolean required) {
        JLabel lbl = new JLabel(label + (required ? " *" : ""));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(TEXT_PRIMARY);
        if (required) {
            lbl.setForeground(TEXT_PRIMARY);
        }

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        card.add(lbl, gbc);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));
        txt.setBackground(Color.WHITE);

        // Efecto hover
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                        new EmptyBorder(9, 11, 9, 11)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(10, 12, 10, 12)
                ));
            }
        });

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        card.add(txt, gbc);

        return txt;
    }

    private JCheckBox crearCheckBoxEstilizado(String texto) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        checkBox.setBackground(CARD_COLOR);
        checkBox.setForeground(TEXT_PRIMARY);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.setFocusPainted(false);
        checkBox.setSelected(true); // Todos seleccionados por defecto

        return checkBox;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(BACKGROUND_COLOR);
        btn.setForeground(TEXT_PRIMARY);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 15, 6, 15)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(243, 244, 246));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(BACKGROUND_COLOR);
            }
        });

        return btn;
    }

    private JPanel crearFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(BACKGROUND_COLOR);

        JButton btnGenerar = new JButton("GENERAR DOCUMENTO");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGenerar.setBackground(PRIMARY_COLOR);
        btnGenerar.setForeground(Color.BLACK);
        btnGenerar.setBorder(new EmptyBorder(14, 40, 14, 40));
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setFocusPainted(false);
        btnGenerar.addActionListener(e -> generar());

        btnGenerar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGenerar.setBackground(new Color(79, 82, 221));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGenerar.setBackground(PRIMARY_COLOR);
            }
        });

        panel.add(btnGenerar);
        return panel;
    }

    private void generar() {
        // Validar campos obligatorios
        if (txtColaborador.getText().trim().isEmpty()
                || txtCedula.getText().trim().isEmpty()
                || txtArea.getText().trim().isEmpty()
                || txtCargo.getText().trim().isEmpty()
                || txtLugarTrabajo.getText().trim().isEmpty()
                || txtRecibidoPor.getText().trim().isEmpty()
                || txtDia.getText().trim().isEmpty()
                || txtMes.getText().trim().isEmpty()
                || txtAnio.getText().trim().isEmpty())
        {
            mostrarMensaje(
                    "Por favor complete todos los campos obligatorios marcados con *",
                    "Campos Requeridos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Validar que al menos un reporte esté seleccionado
        List<String> reportesSeleccionados = new ArrayList<>();
        checkBoxReportes.forEach((archivo, checkBox) -> {
            if (checkBox.isSelected()) {
                reportesSeleccionados.add(archivo);
            }
        });

        if (reportesSeleccionados.isEmpty()) {
            mostrarMensaje(
                    "Debe seleccionar al menos un documento para generar",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Preparar parámetros
        Map<String, Object> params = new HashMap<>();
        params.put("colaborador", txtColaborador.getText().trim());
        params.put("cedula", txtCedula.getText().trim());
        params.put("area_departamento", txtArea.getText().trim());
        params.put("reemplazo", txtReemplazo.getText().trim());
        params.put("fecha_entrega_documentos", txtFecha.getText());
        params.put("direccion", txtDireccion.getText().trim());
        params.put("lugar_trabajo", txtLugarTrabajo.getText().trim());
        params.put("recibido_por", txtRecibidoPor.getText().trim());
        params.put("dia", txtDia.getText().trim());
        params.put("mes", txtMes.getText().trim());
        params.put("anio", txtAnio.getText().trim());
        params.put("cuidad_colaborador", txtCiudad.getText().trim());
        params.put("colaborador_nombres", txtNombres.getText().trim());
        params.put("colaborador_apellidos", txtApellidos.getText().trim());
        params.put("cargo", txtCargo.getText().trim());
        params.put("responsable_DTH", txtResponsableDTH.getText().trim());

        // Generar reportes seleccionados
        ReporteService.generarDocumentosSeleccionados(reportesSeleccionados, params);

        mostrarMensaje(
                "Se han generado " + reportesSeleccionados.size() + " documento(s) exitosamente",
                "Generación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}