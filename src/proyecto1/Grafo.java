/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementación del grafo no dirigido
 * Utilizando una lista de adyacencia con HashMap
 * Carga CSV con agregar/eliminar
 * BFS e implemento de algoritmo Dijkstra.
 * 
 */

public class Grafo {
    private Map<String, Map<String, Double>> adyacencia;

    public Grafo() {
        adyacencia = new HashMap<>();
    }
    
    
    
    /** Para agregar una arista bidireccional (no dirigido) */
    public void agregarArista(String origen, String destino, double peso) {
        adyacencia.computeIfAbsent(origen, k -> new HashMap<>()).put(destino, peso);
        adyacencia.computeIfAbsent(destino, k -> new HashMap<>()).put(origen, peso);
    }
    
    /** Elimina la proteína y todas sus conexiones*/
    public void eliminarProteina(String proteina) {
        adyacencia.remove(proteina);
        for (Map<String, Double> vecinos : adyacencia.values()) {
            vecinos.remove(proteina);
    }
    
    }   
        
    /** Obtiene vecinos y pesos de una proteína */
    public Map<String, Double> getVecinos(String proteina) {
        Map<String, Double> resultado = adyacencia.get(proteina);
        if (resultado == null) {
            return new HashMap<String, Double>();
        }
        return resultado;
    }
    
    /** Se calcula el grado (que son el número de conexiones) */
    public int grado(String proteina) {
        return getVecinos(proteina).size();
    }

    /** Con esto encuentra el hub principal (el de mayor grado en este caso) */
    public String hubPrincipal() {
        return adyacencia.keySet().stream()
                .max(Comparator.comparingInt(this::grado))
                .orElse(null);
    }
    
    /** Lista todas las proteínas */
    public Set<String> getProteinas() {
        return new HashSet<>(adyacencia.keySet());
    }
    
// ===========================================
// ALGORITMO 1: BFS - Complejos Proteicos
// ===========================================
    /**
     * Aquí se detecta los complejos proteícos (que son los componentes conexos) usando BFS.
     * Luego retorna la lista de grupos de proteínas conectadas.
     */
        public List<Set<String>> detectarComplejos() {
        Set<String> visitados = new HashSet<>();
        List<Set<String>> complejos = new ArrayList<>();

        for (String nodo : adyacencia.keySet()) {
            if (!visitados.contains(nodo)) {
                Set<String> complejo = new HashSet<>();
                Queue<String> cola = new LinkedList<>();
                cola.add(nodo);
                visitados.add(nodo);

                while (!cola.isEmpty()) {
                    String actual = cola.poll();
                    complejo.add(actual);
                    
                    for (String vecino : getVecinos(actual).keySet()) {
                        if (!visitados.contains(vecino)) {
                            visitados.add(vecino);
                            cola.add(vecino);
                        }
                    }
                }
                complejos.add(complejo);
            }
        }
        return complejos;
    }

// ===========================================
// ALGORITMO 2: DIJKSTRA - Ruta Más Corta
// ===========================================
    /**
     * Encuentra ruta metabólica más corta (menor costo total).
     * @return Lista de proteínas en orden, o null si no hay camino.
     */
    
    public List<String> rutaMasCorta(String inicio, String fin) {
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> previos = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(
            Comparator.comparing(distancias::get)
        );

        // Inicializar distancias
        for (String nodo : adyacencia.keySet()) {
            distancias.put(nodo, Double.POSITIVE_INFINITY);
        }
        distancias.put(inicio, 0.0);
        pq.add(inicio);

        while (!pq.isEmpty()) {
            String actual = pq.poll();
            if (actual.equals(fin)) break;

            for (Map.Entry<String, Double> vecino : getVecinos(actual).entrySet()) {
                double nuevaDist = distancias.get(actual) + vecino.getValue();
                if (nuevaDist < distancias.getOrDefault(vecino.getKey(), Double.POSITIVE_INFINITY)) {
                    distancias.put(vecino.getKey(), nuevaDist);
                    previos.put(vecino.getKey(), actual);
                    pq.add(vecino.getKey());
                }
            }
        }

        // Reconstruir camino
        if (distancias.get(fin) == Double.POSITIVE_INFINITY) return null;
        
        List<String> ruta = new ArrayList<>();
        for (String at = fin; at != null; at = previos.get(at)) {
            ruta.add(0, at);
            if (at.equals(inicio)) break;
        }
        return ruta;
    }
    
// ===========================================
// CARGA Y GUARDADO DE ARCHIVOS
// ===========================================
    /** Carga grafo desde CSV: Proteina A, Proteina B y el Costo */
    
    public void cargarDesdeCSV(String rutaArchivo) {
        try (Scanner scanner = new Scanner(new java.io.File(rutaArchivo))) {
            if (scanner.hasNextLine()) scanner.nextLine(); 
            while (scanner.hasNextLine()) {
                String[] partes = scanner.nextLine().split(",");
                if (partes.length == 3) {
                    String origen = partes[0].trim();
                    String destino = partes[1].trim();
                    double peso = Double.parseDouble(partes[2].trim());
                    agregarArista(origen, destino, peso);
                }
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Error al cargar archivo: " + e.getMessage());
        }
    }
    
     /** Esto es para guardar el grafo actual en el archivo CSV */
    public void guardarEnCSV(String rutaArchivo) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(rutaArchivo)) {
            writer.println("ProteinaA,ProteinaB,CostoInteraccion");
            for (String proteina1 : adyacencia.keySet()) {
                for (Map.Entry<String, Double> vecino : adyacencia.get(proteina1).entrySet()) {
                    String proteina2 = vecino.getKey();
                    if (proteina1.compareTo(proteina2) < 0) { // Estoy evitando los duplicados
                        writer.printf("%s,%s,%.2f%n", proteina1, proteina2, vecino.getValue());
                    }
                }
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "Error al guardar: " + e.getMessage());
        }
    }

    /** Estadísticas del grafo */
    public String estadisticas() {
        int totalProteinas = adyacencia.size();
        int totalAristas = 0;
        for (Map<String, Double> vecinos : adyacencia.values()) {
            totalAristas += vecinos.size();
        }
        totalAristas /= 2; // No dirigido
        
        String hub = hubPrincipal();
        return String.format(
            "Proteínas: %d | Aristas: %d | Hub principal: %s (grado %d)",
            totalProteinas, totalAristas, hub, hub != null ? grado(hub) : 0
        );
    }
}

