import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        // Configurar ventana
        setTitle("BioGraph - Proyecto Estructuras de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Centrar
        
        // Crear botón principal
        JButton btnCargarArchivo = new JButton("📂 Cargar Archivo CSV");
        btnCargarArchivo.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Acción del botón
        btnCargarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFileChooser();
            }
        });
        
        // Panel para centrar el botón
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(btnCargarArchivo);
        
        // Agregar a la ventana
        add(panel);
    }
    
    private void abrirFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        
        // Filtrar solo archivos CSV
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }
            
            @Override
            public String getDescription() {
                return "Archivos CSV (*.csv)";
            }
        });
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this,
                "✅ Archivo seleccionado:\n" + archivo.getName(),
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}