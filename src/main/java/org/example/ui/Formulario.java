package org.example.ui;

import org.example.service.ReporteService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
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

    // Paleta de colores moderna con gradientes
    private static final Color PRIMARY_COLOR = new Color(79, 70, 229); // Indigo 600
    private static final Color PRIMARY_DARK = new Color(67, 56, 202); // Indigo 700
    private static final Color SECONDARY_COLOR = new Color(236, 72, 153); // Pink 500
    private static final Color ACCENT_COLOR = new Color(168, 85, 247); // Purple 500
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252); // Slate 50
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(15, 23, 42); // Slate 900
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139); // Slate 500
    private static final Color BORDER_COLOR = new Color(226, 232, 240); // Slate 200
    private static final Color SUCCESS_COLOR = new Color(34, 197, 94); // Green 500
    private static final Color WARNING_COLOR = new Color(251, 146, 60); // Orange 400

    public Formulario() {
        setTitle("Sistema de Documentación");
        setSize(950, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal con gradiente sutil
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BACKGROUND_COLOR, 0, h, new Color(241, 245, 249));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // Header con diseño mejorado
        JPanel headerPanel = crearHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de contenido con scroll suave
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(25, 0, 25, 0));

        // Cards con animación visual
        JPanel datosCard = crearCardDatosPersonales();
        contentPanel.add(datosCard);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel reportesCard = crearCardReportes();
        contentPanel.add(reportesCard);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Personalizar scrollbar
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(203, 213, 225);
                this.trackColor = BACKGROUND_COLOR;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer moderno
        JPanel footerPanel = crearFooter();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel crearHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Icono decorativo
        JLabel iconLabel = new JLabel("📄");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Generación de Documentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(TEXT_PRIMARY);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Complete la información y seleccione los documentos a generar");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(TEXT_SECONDARY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Badge informativo
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        badgePanel.setOpaque(false);
        JLabel badge = new JLabel(" ✨ Versión 2.0 ");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(PRIMARY_COLOR);
        badge.setOpaque(true);
        badge.setBackground(new Color(238, 242, 255));
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                new EmptyBorder(4, 12, 4, 12)
        ));
        badgePanel.add(badge);

        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(subtitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));
        panel.add(badgePanel);

        return panel;
    }

    private JPanel crearCardDatosPersonales() {
        JPanel card = crearCardConSombra("👤 Información del Colaborador");
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 20, 6, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 1;

        // Sección Personal
        agregarSeparador(card, "Datos Personales", row++, gbc);
        txtColaborador = agregarCampoModerno(card, "Nombre Completo", row++, gbc, true, "👤");
        txtNombres = agregarCampoModerno(card, "Nombres", row++, gbc, true, "📝");
        txtApellidos = agregarCampoModerno(card, "Apellidos", row++, gbc, true, "📝");
        txtCedula = agregarCampoModerno(card, "Cédula", row++, gbc, true, "🆔");
        txtDireccion = agregarCampoModerno(card, "Dirección", row++, gbc, true, "🏠");
        txtCiudad = agregarCampoModerno(card, "Ciudad", row++, gbc, true, "🌆");

        row++;
        agregarSeparador(card, "Información Laboral", row++, gbc);
        txtCargo = agregarCampoModerno(card, "Cargo", row++, gbc, true, "💼");
        txtArea = agregarCampoModerno(card, "Área / Departamento", row++, gbc, true, "🏢");
        txtLugarTrabajo = agregarCampoModerno(card, "Lugar de Trabajo", row++, gbc, true, "📍");
        txtReemplazo = agregarCampoModerno(card, "Reemplazo", row++, gbc, false, "🔄");
        txtRecibidoPor = agregarCampoModerno(card, "Recibido por", row++, gbc, true, "✍️");
        txtResponsableDTH = agregarCampoModerno(card, "Responsable DTH", row++, gbc, true, "👨‍💼");

        row++;
        agregarSeparador(card, "Fecha del Documento", row++, gbc);

        // Panel de fecha con diseño especial
        JPanel fechaPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        fechaPanel.setOpaque(false);

        txtDia = crearCampoFecha();
        txtMes = crearCampoFecha();
        txtAnio = crearCampoFecha();

        fechaPanel.add(crearPanelConLabel(txtDia, "Día"));
        fechaPanel.add(crearPanelConLabel(txtMes, "Mes"));
        fechaPanel.add(crearPanelConLabel(txtAnio, "Año"));

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        card.add(fechaPanel, gbc);
        gbc.gridwidth = 1;

        txtFecha = agregarCampoModerno(card, "Fecha Completa", row++, gbc, false, "📅");
        txtFecha.setEditable(false);
        txtFecha.setBackground(new Color(249, 250, 251));

        // Inicializar fechas
        LocalDate hoy = LocalDate.now();
        txtDia.setText(String.valueOf(hoy.getDayOfMonth()));
        txtMes.setText(hoy.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")));
        txtAnio.setText(String.valueOf(hoy.getYear()));
        txtFecha.setText(hoy.toString());

        return card;
    }

    private void agregarSeparador(JPanel panel, String texto, int row, GridBagConstraints gbc) {
        JPanel separatorPanel = new JPanel(new BorderLayout(10, 0));
        separatorPanel.setOpaque(false);
        separatorPanel.setBorder(new EmptyBorder(15, 0, 10, 0));

        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(PRIMARY_COLOR);

        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);

        separatorPanel.add(label, BorderLayout.WEST);
        separatorPanel.add(separator, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(separatorPanel, gbc);
        gbc.gridwidth = 1;
    }

    private JPanel crearPanelConLabel(JTextField campo, String label) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(TEXT_SECONDARY);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);

        return panel;
    }

    private JTextField crearCampoFecha() {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txt.setHorizontalAlignment(JTextField.CENTER);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
                new EmptyBorder(12, 10, 12, 10)
        ));
        txt.setBackground(Color.WHITE);

        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                        new EmptyBorder(12, 10, 12, 10)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 2, true),
                        new EmptyBorder(12, 10, 12, 10)
                ));
            }
        });

        return txt;
    }

    private JPanel crearCardReportes() {
        JPanel card = crearCardConSombra("📋 Selección de Documentos");
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

        String[] iconos = {"📄", "📋", "📜", "🔐", "🎫", "🎫"};

        JPanel checkboxContainer = new JPanel(new GridLayout(3, 2, 15, 15));
        checkboxContainer.setOpaque(false);
        checkboxContainer.setBorder(new EmptyBorder(10, 20, 20, 20));

        for (int i = 0; i < reportes.length; i++) {
            JCheckBox checkBox = crearCheckBoxModerno(reportes[i], iconos[i]);
            checkBoxReportes.put(archivos[i], checkBox);
            checkboxContainer.add(checkBox);
        }

        card.add(checkboxContainer);

        // Panel de acciones con nuevo diseño
        JPanel accionesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        accionesPanel.setOpaque(false);
        accionesPanel.setBorder(new EmptyBorder(5, 20, 15, 20));

        JButton btnSeleccionarTodos = crearBotonAccion("Seleccionar Todos", SUCCESS_COLOR, "✓");
        JButton btnDeseleccionarTodos = crearBotonAccion("Limpiar Selección", WARNING_COLOR, "✗");

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

    private JPanel crearCardConSombra(String titulo) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Sombra suave
                g2.setColor(new Color(0, 0, 0, 8));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
                g2.setColor(new Color(0, 0, 0, 4));
                g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 20, 20);

                // Fondo del card
                g2.setColor(CARD_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 0, 20, 0));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(TEXT_PRIMARY);
        lblTitulo.setBorder(new EmptyBorder(0, 20, 20, 20));
        card.add(lblTitulo);

        return card;
    }

    private JTextField agregarCampoModerno(JPanel card, String label, int row,
                                           GridBagConstraints gbc, boolean required, String icono) {
        JLabel lbl = new JLabel(icono + " " + label + (required ? " *" : ""));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(TEXT_PRIMARY);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.35;
        card.add(lbl, gbc);

        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(11, 14, 11, 14)
        ));
        txt.setBackground(Color.WHITE);

        // Efectos de interacción mejorados
        txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                        new EmptyBorder(10, 13, 10, 13)
                ));
                txt.setBackground(new Color(249, 250, 255));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        new EmptyBorder(11, 14, 11, 14)
                ));
                txt.setBackground(Color.WHITE);
            }
        });

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        card.add(txt, gbc);

        return txt;
    }

    private JCheckBox crearCheckBoxModerno(String texto, String icono) {
        JCheckBox checkBox = new JCheckBox() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(new Color(243, 244, 246));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        checkBox.setText(" " + icono + "  " + texto);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        checkBox.setOpaque(false);
        checkBox.setForeground(TEXT_PRIMARY);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.setFocusPainted(false);
        checkBox.setSelected(true);
        checkBox.setBorder(new EmptyBorder(12, 12, 12, 12));

        return checkBox;
    }

    private JButton crearBotonAccion(String texto, Color color, String icono) {
        JButton btn = new JButton(icono + "  " + texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        return btn;
    }

    private JPanel crearFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setOpaque(false);

        JButton btnGenerar = new JButton("GENERAR DOCUMENTOS") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradiente para el botón
                GradientPaint gp;
                if (getModel().isPressed()) {
                    gp = new GradientPaint(0, 0, PRIMARY_DARK, getWidth(), 0, ACCENT_COLOR.darker());
                } else if (getModel().isRollover()) {
                    gp = new GradientPaint(0, 0, PRIMARY_COLOR.brighter(), getWidth(), 0, ACCENT_COLOR);
                } else {
                    gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), 0, ACCENT_COLOR);
                }

                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setContentAreaFilled(false);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setBorder(new EmptyBorder(16, 50, 16, 50));
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setFocusPainted(false);
        btnGenerar.addActionListener(e -> generar());

        panel.add(btnGenerar);
        return panel;
    }

    private void generar() {
        if (txtColaborador.getText().trim().isEmpty()
                || txtCedula.getText().trim().isEmpty()
                || txtArea.getText().trim().isEmpty()
                || txtCargo.getText().trim().isEmpty()
                || txtLugarTrabajo.getText().trim().isEmpty()
                || txtRecibidoPor.getText().trim().isEmpty()
                || txtDia.getText().trim().isEmpty()
                || txtMes.getText().trim().isEmpty()
                || txtAnio.getText().trim().isEmpty()) {

            mostrarMensajeModerno(
                    "Por favor complete todos los campos obligatorios marcados con *",
                    "Campos Requeridos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        List<String> reportesSeleccionados = new ArrayList<>();
        checkBoxReportes.forEach((archivo, checkBox) -> {
            if (checkBox.isSelected()) {
                reportesSeleccionados.add(archivo);
            }
        });

        if (reportesSeleccionados.isEmpty()) {
            mostrarMensajeModerno(
                    "Debe seleccionar al menos un documento para generar",
                    "Selección Requerida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

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

        ReporteService.generarDocumentosSeleccionados(reportesSeleccionados, params);

        mostrarMensajeModerno(
                "Se han generado " + reportesSeleccionados.size() + " documento(s) exitosamente ✓",
                "Generación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarMensajeModerno(String mensaje, String titulo, int tipo) {
        UIManager.put("OptionPane.background", CARD_COLOR);
        UIManager.put("Panel.background", CARD_COLOR);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}