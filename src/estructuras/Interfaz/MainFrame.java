/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaz;
import estructuras.Grafo;
import estructuras.CargadorArchivo;
import estructuras.Lista;
import estructuras.Proteinas;
import javax.swing.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import java.awt.*;

/**
 *
 * 
 */
public class MainFrame extends javax.swing.JFrame {
    private Grafo grafo;
    private DefaultComboBoxModel<String> modeloOrigen;
    private DefaultComboBoxModel<String> modeloDestino;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        inicializar();
    }
    
    private void inicializar() {
        this.grafo = new Grafo();
        // Crear modelos SEPARADOS
        modeloOrigen = new DefaultComboBoxModel<>();
        modeloDestino = new DefaultComboBoxModel<>();

        // Asignar modelos DIFERENTES a cada ComboBox
        cmbOrigen.setModel(modeloOrigen);
        cmbDestino.setModel(modeloDestino);

        // Cargar archivo por defecto si existe
        cargarArchivoInicial();
    }
    
    private void cargarArchivoInicial() {
        if (CargadorArchivo.cargarArchivoPorDefecto(grafo, this)) {
            actualizarInfo();
            actualizarCombos();
            txtResultados.append("Archivo por defecto cargado automáticamente.\n");
        }
    }
    
    private void actualizarInfo() {
        lblArchivo.setText("Archivo: " + CargadorArchivo.getNombreUltimoArchivo());
        lblStats.setText("Proteínas: " + grafo.getNumProteinas() + 
                        " | Interacciones: " + grafo.getNumInteracciones());

        setTitle("BioGraph - " + CargadorArchivo.getNombreUltimoArchivo() + 
                 " (" + grafo.getNumProteinas() + " proteínas)");
    }
    
    private void actualizarCombos() {
        //Limpiar ambos modelos
        modeloOrigen.removeAllElements();
        modeloDestino.removeAllElements();
        
        // Obtener proteínas
        Lista<String> proteinas = grafo.getProteinas();
        
        // Llenar AMBOS modelos 
        for (int i = 0; i < proteinas.getTamaño(); i++) {
            String proteina = proteinas.obtener(i);
            modeloOrigen.addElement(proteina);
            modeloDestino.addElement(proteina);
        }
    }
    
    private void agregarInteraccionPara(String proteina) {
        if (grafo.getNumProteinas() < 2) return;

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));

        JComboBox<String> comboDestino = new JComboBox<>();
        JTextField txtPeso = new JTextField("1.0");

        Lista<String> proteinas = grafo.getProteinas();
        for (int i = 0; i < proteinas.getTamaño(); i++) {
            String p = proteinas.obtener(i);
            if (!p.equals(proteina)) {
                comboDestino.addItem(p);
            }
        }

        panel.add(new JLabel("Interacción con:"));
        panel.add(comboDestino);
        panel.add(new JLabel("Peso:"));
        panel.add(txtPeso);

        int opcion = JOptionPane.showConfirmDialog(this,
            panel,
            "Agregar interacción para " + proteina,
            JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String destino = (String) comboDestino.getSelectedItem();
                double peso = Double.parseDouble(txtPeso.getText());

                if (grafo.agregarInteraccion(proteina, destino, peso)) {
                    CargadorArchivo.marcarCambios();
                    actualizarInfo();
                    txtResultados.append("Interacción agregada: " + 
                        proteina + " - " + destino + " (" + peso + ")\n");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Peso inválido");
            }
        }
    }
    
    private double calcularPesoRuta(Lista<String> ruta) {
        double pesoTotal = 0;
    
        for (int i = 0; i < ruta.getTamaño() - 1; i++) {
            String proteina1 = ruta.obtener(i);
            String proteina2 = ruta.obtener(i + 1);

            Lista<Proteinas> interacciones = grafo.getInteracciones();
            for (int j = 0; j < interacciones.getTamaño(); j++) {
                Proteinas a = interacciones.obtener(j);
                if ((a.getOrigen().equals(proteina1) && a.getDestino().equals(proteina2)) ||
                    (a.getOrigen().equals(proteina2) && a.getDestino().equals(proteina1))) {
                    pesoTotal += a.getPeso();
                    break;
                }
            }
        }

        return pesoTotal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblArchivo = new javax.swing.JLabel();
        lblStats = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultados = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnCargar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnComplejos = new javax.swing.JButton();
        btnHubs = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnCalcular = new javax.swing.JButton();
        cmbOrigen = new javax.swing.JComboBox<>();
        cmbDestino = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAgregarProteina = new javax.swing.JButton();
        btnEliminarProteina = new javax.swing.JButton();
        btnAgregarInteraccion = new javax.swing.JButton();
        btnMostrarGrafo = new javax.swing.JButton();
        btnDFS = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblArchivo.setText("Archivo: Ninguno");
        jPanel1.add(lblArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lblStats.setText("Proteínas: 0 | Interacciones: 0");
        jPanel1.add(lblStats, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel3.setText("Proyecto Biograph");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 100));

        txtResultados.setEditable(false);
        txtResultados.setColumns(20);
        txtResultados.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtResultados.setRows(5);
        jScrollPane1.setViewportView(txtResultados);

        jTabbedPane1.addTab("tab1", jScrollPane1);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 600, 260));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCargar.setText("Cargar Archivo");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 140, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, -1));

        btnComplejos.setText("Detectar Complejos");
        btnComplejos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComplejosActionPerformed(evt);
            }
        });
        jPanel2.add(btnComplejos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 140, -1));

        btnHubs.setText("Identificar Hubs");
        btnHubs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHubsActionPerformed(evt);
            }
        });
        jPanel2.add(btnHubs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 140, -1));

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 140, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, 180));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 100));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCalcular.setText("Calcular Ruta");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });
        jPanel3.add(btnCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        cmbOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel3.add(cmbOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        cmbDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDestinoActionPerformed(evt);
            }
        });
        jPanel3.add(cmbDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel1.setText("Origen:");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel2.setText("Destino:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        btnAgregarProteina.setText("Agregar Proteína");
        btnAgregarProteina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProteinaActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregarProteina, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, -1, -1));

        btnEliminarProteina.setText("Eliminar Proteína");
        btnEliminarProteina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProteinaActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarProteina, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, -1, -1));

        btnAgregarInteraccion.setText("Agregar Interacción");
        btnAgregarInteraccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarInteraccionActionPerformed(evt);
            }
        });
        jPanel3.add(btnAgregarInteraccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, -1, -1));

        btnMostrarGrafo.setText("Mostrar Grafo");
        btnMostrarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarGrafoActionPerformed(evt);
            }
        });
        jPanel3.add(btnMostrarGrafo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, -1));

        btnDFS.setText("Buscar por DFS");
        btnDFS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDFSActionPerformed(evt);
            }
        });
        jPanel3.add(btnDFS, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 80, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 430, 180));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        // TODO add your handling code here:
        if (CargadorArchivo.cargar(grafo, this)) {
            actualizarInfo();
            actualizarCombos();
            txtResultados.append("Archivo cargado: " + 
            CargadorArchivo.getNombreUltimoArchivo() + "\n");
        }
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (CargadorArchivo.guardar(grafo, this)) {
            actualizarInfo();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnComplejosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComplejosActionPerformed
        // TODO add your handling code here:
        txtResultados.append("\nDETECTANDO COMPLEJOS PROTEICOS (BFS)\n");
        txtResultados.append("\n");
        Lista<Lista<String>> complejos = grafo.encontrarComplejos();
        if (complejos.getTamaño() == 0) {
            txtResultados.append("No se encontraron complejos proteicos.\n");
        } else {
            txtResultados.append("Se encontraron " + complejos.getTamaño() + " complejos:\n\n");

            for (int i = 0; i < complejos.getTamaño(); i++) {
                Lista<String> complejo = complejos.obtener(i);
                txtResultados.append("Complejo " + (i + 1) + " (" + complejo.getTamaño() + " proteínas):\n");

                for (int j = 0; j < complejo.getTamaño(); j++) {
                    txtResultados.append("  • " + complejo.obtener(j) + "\n");
                }
                txtResultados.append("\n");
            }
        }
        txtResultados.append("\n\n");
    }//GEN-LAST:event_btnComplejosActionPerformed

    private void btnHubsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHubsActionPerformed
        // TODO add your handling code here:
        txtResultados.append("\nIDENTIFICANDO HUBS (TOP 5)\n");
        txtResultados.append("\n");

        Lista<String> hubs = grafo.identificarHubs(5);

        if (hubs.getTamaño() == 0) {
            txtResultados.append("No hay proteínas en el grafo.\n");
        } else {
            for (int i = 0; i < hubs.getTamaño(); i++) {
                txtResultados.append((i + 1) + ". " + hubs.obtener(i) + "\n");
            }
        }
        txtResultados.append("\n\n");
    }//GEN-LAST:event_btnHubsActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        txtResultados.setText("");
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        // TODO add your handling code here:
        String origen = (String) cmbOrigen.getSelectedItem();
        String destino = (String) cmbDestino.getSelectedItem();

        if (origen == null || destino == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Seleccione proteínas de origen y destino.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        txtResultados.append("\nRUTA MÁS CORTA (DIJKSTRA)\n");
        txtResultados.append("\n");
        txtResultados.append("De: " + origen + "\n");
        txtResultados.append("A: " + destino + "\n\n");

        Grafo.ResultadoDijkstra resultado = grafo.rutaMasCorta(origen, destino);

        if (resultado != null && resultado.hayRuta()) {
            txtResultados.append("Distancia total: " + resultado.getDistancia() + "\n");
            txtResultados.append("Ruta: " + resultado.getRuta() + "\n");
        } else {
            txtResultados.append(" No hay ruta disponible entre " + origen + " y " + destino + "\n");
        }
        txtResultados.append("\n\n");
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void cmbDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDestinoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDestinoActionPerformed

    private void btnAgregarProteinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProteinaActionPerformed
        // TODO add your handling code here:
        String nombre = JOptionPane.showInputDialog(this,
            "Ingrese el nombre de la nueva proteína:",
            "Agregar Proteína",
            JOptionPane.QUESTION_MESSAGE);

        if (nombre != null && !nombre.trim().isEmpty()) {
            if (grafo.agregarProteina(nombre)) {
                CargadorArchivo.marcarCambios();
                actualizarCombos();
                actualizarInfo();
                txtResultados.append("Proteína agregada: " + nombre + "\n");

                int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea agregar interacciones para esta proteína?",
                    "Agregar interacciones",
                    JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {
                    agregarInteraccionPara(nombre);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "La proteína ya existe.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarProteinaActionPerformed

    private void btnEliminarProteinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProteinaActionPerformed
        // TODO add your handling code here:
        if (grafo.getNumProteinas() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay proteínas para eliminar.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JComboBox<String> comboProteinas = new JComboBox<>();
        Lista<String> proteinas = grafo.getProteinas();
        for (int i = 0; i < proteinas.getTamaño(); i++) {
            comboProteinas.addItem(proteinas.obtener(i));
        }

        int opcion = JOptionPane.showConfirmDialog(this,
            comboProteinas,
            "Seleccione la proteína a eliminar",
            JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String seleccion = (String) comboProteinas.getSelectedItem();

            int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar la proteína " + seleccion + "?\n" +
                "Se eliminarán todas sus interacciones.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirmar == JOptionPane.YES_OPTION) {
                int interaccionesEliminadas = grafo.getInteraccionesDe(seleccion).getTamaño();
                grafo.eliminarProteina(seleccion);
                CargadorArchivo.marcarCambios();
                actualizarCombos();
                actualizarInfo();
                txtResultados.append("Proteína eliminada: " + seleccion + 
                    " (" + interaccionesEliminadas + " interacciones eliminadas)\n");
            }
        }
    }//GEN-LAST:event_btnEliminarProteinaActionPerformed

    private void btnAgregarInteraccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarInteraccionActionPerformed
        // TODO add your handling code here:
        if (grafo.getNumProteinas() < 2) {
            JOptionPane.showMessageDialog(this,
                "Se necesitan al menos 2 proteínas para crear una interacción.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        JComboBox<String> comboOrigen = new JComboBox<>();
        JComboBox<String> comboDestino = new JComboBox<>();
        JTextField txtPeso = new JTextField("1.0");

        Lista<String> proteinas = grafo.getProteinas();
        for (int i = 0; i < proteinas.getTamaño(); i++) {
            String p = proteinas.obtener(i);
            comboOrigen.addItem(p);
            comboDestino.addItem(p);
        }

        panel.add(new JLabel("Proteína origen:"));
        panel.add(comboOrigen);
        panel.add(new JLabel("Proteína destino:"));
        panel.add(comboDestino);
        panel.add(new JLabel("Peso de interacción:"));
        panel.add(txtPeso);

        int opcion = JOptionPane.showConfirmDialog(this,
            panel,
            "Agregar Interacción",
            JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                String origen = (String) comboOrigen.getSelectedItem();
                String destino = (String) comboDestino.getSelectedItem();
                double peso = Double.parseDouble(txtPeso.getText());

                if (origen.equals(destino)) {
                    JOptionPane.showMessageDialog(this,
                        "Origen y destino no pueden ser la misma proteína.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (grafo.agregarInteraccion(origen, destino, peso)) {
                    CargadorArchivo.marcarCambios();
                    actualizarInfo();
                    txtResultados.append("Interacción agregada: " + 
                        origen + " - " + destino + " (" + peso + ")\n");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "La interacción ya existe o no es válida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "El peso debe ser un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAgregarInteraccionActionPerformed

    private void btnMostrarGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarGrafoActionPerformed
        // TODO add your handling code here:
        if (grafo.getNumProteinas() == 0) {
            JOptionPane.showMessageDialog(this,
                "No hay proteínas para mostrar.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear ventana para el grafo
        JFrame frameGrafo = new JFrame("Visualización del Grafo");
        frameGrafo.setSize(800, 600);
        frameGrafo.setLocationRelativeTo(this);

        // Configurar GraphStream
        System.setProperty("org.graphstream.ui", "swing");

        // Crear grafo
        org.graphstream.graph.Graph graph = new MultiGraph("PPI");

        // Configurar estilos
        String styleSheet = 
            "node {" +
            "   size: 30px;" +
            "   fill-color: #87CEEB;" +
            "   text-mode: normal;" +
            "   text-color: black;" +
            "   text-size: 14;" +
            "   text-style: bold;" +
            "   text-alignment: under;" +
            "}" +
            "node.complejo {" +
            "   fill-color: green;" +
            "}" +
            "edge {" +
            "   shape: line;" +
            "   fill-color: gray;" +
            "   size: 2px;" +
            "   text-size: 12;" +
            "   text-color: blue;" +
            "   text-alignment: along;" +
            "}";

        graph.setAttribute("ui.stylesheet", styleSheet);

        // Identificar complejos
        Lista<Lista<String>> complejos = grafo.encontrarComplejos();

        // Agregar nodos
        Lista<String> proteinas = grafo.getProteinas();
        for (int i = 0; i < proteinas.getTamaño(); i++) {
            String p = proteinas.obtener(i);
            Node node = graph.addNode(p);
            node.setAttribute("ui.label", p);

            for (int j = 0; j < complejos.getTamaño(); j++) {
                Lista<String> complejo = complejos.obtener(j);
                if (complejo.contiene(p)) {
                    node.setAttribute("ui.class", "complejo");
                    break;
                }
            }
        }

        // Agregar aristas
        Lista<Proteinas> interacciones = grafo.getInteracciones();
        for (int i = 0; i < interacciones.getTamaño(); i++) {
            Proteinas a = interacciones.obtener(i);
            String id = a.getOrigen() + "-" + a.getDestino();
            Edge edge = graph.addEdge(id, a.getOrigen(), a.getDestino(), false);
            edge.setAttribute("ui.label", String.valueOf(a.getPeso()));
        }

        // Crear visor
        Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel viewPanel = (ViewPanel) viewer.addDefaultView(false);

        frameGrafo.add(viewPanel);
        frameGrafo.setVisible(true);
    }//GEN-LAST:event_btnMostrarGrafoActionPerformed

    private void btnDFSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDFSActionPerformed
        // TODO add your handling code here:
        String origen = (String) cmbOrigen.getSelectedItem();
        String destino = (String) cmbDestino.getSelectedItem();

        // Validaciones
        if (origen == null || destino == null) {
            JOptionPane.showMessageDialog(this,
                "Seleccione proteínas de origen y destino.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (origen.equals(destino)) {
            JOptionPane.showMessageDialog(this,
                "Origen y destino deben ser diferentes.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        txtResultados.append("\nRUTA POR DFS\n");
        txtResultados.append("\n");
        txtResultados.append("De: " + origen + "\n");
        txtResultados.append("A: " + destino + "\n\n");

        // Ejecutar DFS
        Lista<String> ruta = grafo.rutaDFS(origen, destino);

        if (ruta == null || ruta.getTamaño() == 0) {
            txtResultados.append("No hay ruta disponible entre " + origen + " y " + destino + "\n");
        } else {
            txtResultados.append("Ruta encontrada:\n");
            for (int i = 0; i < ruta.getTamaño(); i++) {
                txtResultados.append("   " + (i+1) + ". " + ruta.obtener(i) + "\n");
            }

            // Calcular el peso total
            double pesoTotal = calcularPesoRuta(ruta);
            txtResultados.append("\nPeso total de la ruta: " + pesoTotal + "\n");

            // Comparar con Dijkstra
            Grafo.ResultadoDijkstra resultadoDijkstra = grafo.rutaMasCorta(origen, destino);
            if (resultadoDijkstra != null && resultadoDijkstra.hayRuta()) {
                txtResultados.append("ℹ️ Dijkstra: " + resultadoDijkstra.getDistancia() + " (más corto)\n");
            }
        }

        txtResultados.append("\n\n");
    }//GEN-LAST:event_btnDFSActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarInteraccion;
    private javax.swing.JButton btnAgregarProteina;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnComplejos;
    private javax.swing.JButton btnDFS;
    private javax.swing.JButton btnEliminarProteina;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHubs;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMostrarGrafo;
    private javax.swing.JComboBox<String> cmbDestino;
    private javax.swing.JComboBox<String> cmbOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblArchivo;
    private javax.swing.JLabel lblStats;
    private javax.swing.JTextArea txtResultados;
    // End of variables declaration//GEN-END:variables
}
