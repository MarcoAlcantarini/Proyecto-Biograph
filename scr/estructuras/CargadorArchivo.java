/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;


import javax.swing.*;
import java.io.*;

/**
 * Controlador para la carga y guardado de archivos CSV.
 * Proporciona métodos para seleccionar archivos con JFileChooser,
 * procesar el contenido y actualizar el grafo.
 */
public class CargadorArchivo {
    private static File ultimoArchivo;
    private static boolean cambiosPendientes = false;
    
    /**
     * Carga un archivo CSV y construye el grafo.
     * Muestra un diálogo JFileChooser para seleccionar el archivo.
     * 
     * @param grafo El grafo donde cargar los datos
     * @param parent Ventana padre para los diálogos
     * @return true si se cargó correctamente, false en caso contrario
     */
    public static boolean cargar(Grafo grafo, JFrame parent) {
        // Verificar cambios pendientes
        if (cambiosPendientes) {
            int opcion = JOptionPane.showConfirmDialog(parent,
                "Hay cambios sin guardar. ¿Desea guardarlos antes de cargar?",
                "Cambios sin guardar",
                JOptionPane.YES_NO_CANCEL_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                if (!guardar(grafo, parent)) return false;
            } else if (opcion == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }
        
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos CSV (*.csv)", "csv"));
        
        int resultado = fileChooser.showOpenDialog(parent);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            ultimoArchivo = archivo;
            return procesarArchivo(archivo, grafo, parent);
        }
        
        return false;
    }
    
    /**
     * Procesa el archivo y carga los datos en el grafo.
     * 
     * @param archivo Archivo a procesar
     * @param grafo Grafo donde cargar los datos
     * @param parent Ventana padre para los diálogos
     * @return true si se procesó correctamente
     */
    private static boolean procesarArchivo(File archivo, Grafo grafo, JFrame parent) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int lineasLeidas = 0;
            int proteinasAgregadas = 0;
            int interaccionesAgregadas = 0;
            
            grafo.vaciar();
            
            while ((linea = br.readLine()) != null) {
                lineasLeidas++;
                
                if (linea.trim().isEmpty()) continue;
                
                // Saltar encabezados
                if (lineasLeidas == 1 && (linea.contains("Proteina") || 
                    linea.contains("Origen") || linea.contains("Destino"))) {
                    continue;
                }
                
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String protA = partes[0].trim();
                    String protB = partes[1].trim();
                    
                    double peso = 1.0;
                    try {
                        peso = Double.parseDouble(partes[2].trim());
                    } catch (NumberFormatException e) {
                        // Usar peso por defecto
                    }
                    
                    if (!grafo.existeProteina(protA)) {
                        grafo.agregarProteina(protA);
                        proteinasAgregadas++;
                    }
                    if (!grafo.existeProteina(protB)) {
                        grafo.agregarProteina(protB);
                        proteinasAgregadas++;
                    }
                    
                    if (grafo.agregarInteraccion(protA, protB, peso)) {
                        interaccionesAgregadas++;
                    }
                }
            }
            
            cambiosPendientes = false;
            
            JOptionPane.showMessageDialog(parent,
                "Archivo cargado exitosamente:\n" +
                "Archivo: " + archivo.getName() + "\n" +
                "Proteínas: " + grafo.getNumProteinas() + "\n" +
                "Interacciones: " + grafo.getNumInteracciones(),
                "Carga exitosa",
                JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "Error al leer el archivo:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Guarda el grafo actual en un archivo.
     * Muestra un diálogo JFileChooser para seleccionar la ubicación.
     * 
     * @param grafo El grafo a guardar
     * @param parent Ventana padre para los diálogos
     * @return true si se guardó correctamente, false en caso contrario
     */
    public static boolean guardar(Grafo grafo, JFrame parent) {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Guardar archivo CSV");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Archivos CSV (*.csv)", "csv"));
        
        if (ultimoArchivo != null) {
            fileChooser.setSelectedFile(ultimoArchivo);
        }
        
        int resultado = fileChooser.showSaveDialog(parent);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
                archivo = new File(archivo.getAbsolutePath() + ".csv");
            }
            
            return guardarArchivo(archivo, grafo, parent);
        }
        
        return false;
    }
    
    /**
     * Guarda el archivo en disco.
     * 
     * @param archivo Archivo donde guardar
     * @param grafo Grafo a guardar
     * @param parent Ventana padre para los diálogos
     * @return true si se guardó correctamente
     */
    private static boolean guardarArchivo(File archivo, Grafo grafo, JFrame parent) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Proteina_Origen,Proteina_Destino,Peso");
            
            Lista<Proteinas> interacciones = grafo.getInteracciones();
            for (int i = 0; i < interacciones.getTamaño(); i++) {
                Proteinas a = interacciones.obtener(i);
                pw.println(a.getOrigen() + "," + a.getDestino() + "," + a.getPeso());
            }
            
            ultimoArchivo = archivo;
            cambiosPendientes = false;
            
            JOptionPane.showMessageDialog(parent,
                "✅ Archivo guardado exitosamente:\n" + archivo.getName(),
                "Guardado exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                "❌ Error al guardar el archivo:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Carga el archivo por defecto (datos.csv) si existe en la raíz del proyecto.
     * 
     * @param grafo El grafo donde cargar los datos
     * @param parent Ventana padre para los diálogos
     * @return true si se cargó correctamente, false si no existe el archivo
     */
    public static boolean cargarArchivoPorDefecto(Grafo grafo, JFrame parent) {
        File archivo = new File("datos.csv");
        if (archivo.exists()) {
            ultimoArchivo = archivo;
            return procesarArchivo(archivo, grafo, parent);
        }
        return false;
    }
    
    /**
     * Marca que hay cambios pendientes en el grafo.
     */
    public static void marcarCambios() { 
        cambiosPendientes = true; 
    }
    
    /**
     * Verifica si hay cambios sin guardar.
     * 
     * @return true si hay cambios pendientes
     */
    public static boolean hayCambiosPendientes() { 
        return cambiosPendientes; 
    }
    
    /**
     * Obtiene el nombre del último archivo utilizado.
     * 
     * @return Nombre del último archivo, o "Ninguno" si no hay
     */
    public static String getNombreUltimoArchivo() { 
        return ultimoArchivo != null ? ultimoArchivo.getName() : "Ninguno"; 
    }
}
