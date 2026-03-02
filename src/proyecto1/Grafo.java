/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

import proyecto1.Lista;
import proyecto1.Nodo;

import java.io.File;
import java.util.*;


/**
 * Implementación del grafo no dirigido
 * Utilizando una lista de adyacencia con HashMap
 * Carga CSV con agregar/eliminar
 * BFS e implemento de algoritmo Dijkstra.
 * 
 */

public class Grafo {
   
    
    private Lista<Nodo> nodos;  // Lista principal del nodo 
    
    private static final double INF = Double.POSITIVE_INFINITY; // Constante para la arista

    
    public Grafo() {
        nodos = new Lista<>(); // Se crea el grafo vacio
    }
                            // ========== OPERACIONES CRUD ==========
    
    /**
     * Agrega un nodo (proteína) si no existe.
     */
    public void agregarNodo(String nombre) {
        if (buscarNodo(nombre) == null) {
            Nodo nuevoNodo = new Nodo(nombre);
            nodos.agregar(nuevoNodo);
        }
    }
    
    /**
     * Agrega arista bidireccional (no dirigido) entre dos proteínas.
     * @param origen Proteína origen
     * @destino Proteína destino
     * @param peso Costo/resistencia de la interacción
     */
    public void agregarArista(String origen, String destino, double peso) {
        Nodo nodoOrigen = buscarNodo(origen);
        Nodo nodoDestino = buscarNodo(destino);
        
        // Crear nodos si no existen
        if (nodoOrigen == null) {
            nodoOrigen = new Nodo(origen);
            nodos.agregar(nodoOrigen);
        }
        if (nodoDestino == null) {
            nodoDestino = new Nodo(destino);
            nodos.agregar(nodoDestino);
        }
        
        // Agregar aristas bidireccionales
        nodoOrigen.agregarVecino(nodoDestino, peso);
        nodoDestino.agregarVecino(nodoOrigen, peso);
    }
    
    /**
     * Elimina nodo y todas sus conexiones.
     */
    public void eliminarProteina(String nombre) {
        // Eliminar de lista principal
        Nodo nodoEliminar = buscarNodo(nombre);
        if (nodoEliminar != null) {
            nodos.eliminar(nodoEliminar);
        }
        
        // Limpiar referencias cruzadas
        for (int i = 0; i < nodos.getTamaño(); i++) {
            nodos.obtener(i).eliminarVecino(nodoEliminar);
        }
        nodos.eliminar(nodoEliminar);
    }
    
    /**
     * Busca nodo por nombre.
     */
    private Nodo buscarNodo(String nombre) {
        for (int i = 0; i < nodos.getTamaño(); i++) {
            if (nodos.obtener(i).getNombre().equals(nombre)) {
                return nodos.obtener(i);
            }
        }
        return null;
    }
    
     private Nodo obtenerOCrearNodo(String nombre) {
        Nodo nodo = buscarNodo(nombre);
        if (nodo == null) {
            nodo = new Nodo(nombre);
            nodos.agregar(nodo);
        }
        return nodo;
    }
    
    
                        // ========== ANÁLISIS DE CENTRALIDAD ==========
    
    /**
     * Calcula grado de una proteína (número de interacciones).
     * @param nombre Nombre de la proteína
     * @return Grado o 0 si no existe
     */
    public int grado(String nombre) {
        Nodo nodo = buscarNodo(nombre);
        return nodo != null ? nodo.getGrado() : 0;
    }
    
    /**
     * Encuentra hub principal (proteína con mayor grado).
     * @return Nombre del hub o null si el grafo es vacío
     */
    public String hubPrincipal() {
        String hub = null;
        int maxGrado = -1;
        
        for (int i = 0; i < nodos.getTamaño(); i++) {
            Nodo nodo = nodos.obtener(i);
            int gradoActual = nodo.getGrado();
            if (gradoActual > maxGrado) {
                maxGrado = gradoActual;
                hub = nodo.getNombre();
            }
        }
        return hub;
    }
    
    /**
     * Obtiene todos los nombres de proteínas ordenados por grado descendente.
     */
    public List<String> obtenerHubs(int topN) {
        List<String> hubs = new ArrayList<>();
        for (int i = 0; i < nodos.getTamaño(); i++) {
            hubs.add(nodos.obtener(i).getNombre());
        }
        
        hubs.sort((a, b) -> Integer.compare(grado(b), grado(a)));
        return hubs.subList(0, Math.min(topN, hubs.size()));
        
        
    }
    
                    // ========== BFS - COMPLEJOS PROTEICOS ==========
    
    /**
     * Detecta complejos proteicos (componentes conexos) usando BFS.
     * Requerimiento E del proyecto.
     */
    public List<Set<String>> detectarComplejos() {
        List<Set<String>> complejos = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        
        for (int i = 0; i < nodos.getTamaño(); i++) {
            Nodo inicio = nodos.obtener(i);
            String nombreInicio = inicio.getNombre();
            
            if (!visitados.contains(nombreInicio)) {
                Set<String> complejo = new HashSet<>();
                Queue<String> cola = new LinkedList<>();
                
                cola.add(nombreInicio);
                visitados.add(nombreInicio);
                
                while (!cola.isEmpty()) {
                    String actual = cola.poll();
                    complejo.add(actual);
                    
                    Nodo nodoActual = buscarNodo(actual);
                    for (int j = 0; j < nodoActual.getVecinos().getTamaño(); j++) {
                        
                        Arista arista = nodoActual.getVecinos().obtener(j);
                        String vecino = arista.getDestino().getNombre();
                        
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
    
                             // ========== DIJKSTRA - RUTA MÁS CORTA ==========
    
    /**
     * Encuentra ruta metabólica más corta entre dos proteínas.
     * Requerimiento F del proyecto.
     */
    public List<String> rutaMasCorta(String inicio, String fin) {
        if (buscarNodo(inicio) == null || buscarNodo(fin) == null) {
            return null;
        }
        
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> previos = new HashMap<>();
        
        // Inicializar distancias
        for (int i = 0; i < nodos.getTamaño(); i++) {
            distancias.put(nodos.obtener(i).getNombre(), INF);
        }
        distancias.put(inicio, 0.0);
        
        // Dijkstra 
        List<String> pendientes = new ArrayList<>();
        for (int i = 0; i < nodos.getTamaño(); i++) {
            pendientes.add(nodos.obtener(i).getNombre());
        }
        
        while (!pendientes.isEmpty()) {
            // Nodo con menor distancia
            String nodoMin = null;
            double minDist = INF;
            for (String nodo : pendientes) {
                double dist = distancias.getOrDefault(nodo, INF);
                if (dist < minDist) {
                    minDist = dist;
                    nodoMin = nodo;
                }
            }
            
            if (nodoMin == null || nodoMin.equals(fin)) break;
            pendientes.remove(nodoMin);
            
            // Relajar las aristas
            Nodo nodoActual = buscarNodo(nodoMin);
            for (int j = 0; j < nodoActual.getVecinos().getTamaño(); j++) {
                Arista arista = nodoActual.getVecinos().obtener(j);
                String vecino = arista.getDestino().getNombre();
                double nuevaDist = distancias.get(nodoMin) + arista.getPeso();
                
                if (nuevaDist < distancias.getOrDefault(vecino, INF)) {
                    distancias.put(vecino, nuevaDist);
                    previos.put(vecino, nodoMin);
                }
            }
        }
        
        // Reconstruir ruta
        if (!distancias.containsKey(fin) || distancias.get(fin) == INF) {
            return null;
        }
        
        List<String> ruta = new ArrayList<>();
        for (String at = fin; at != null; at = previos.get(at)) {
            ruta.add(0, at);
        }
        return ruta;
    }
    
      // ========== REQUERIMIENTOS A,C: PERSISTENCIA ==========
    
    /**
     * Carga desde CSV: ProteinaA,ProteinaB,CostoInteraccion
     */
    public boolean cargarDesdeCSV(String rutaArchivo) {
        try (java.util.Scanner scanner = new java.util.Scanner(new File(rutaArchivo))) {
            // Limpiar grafo actual
            while (!nodos.estaVacia()) {
                nodos.eliminar(nodos.obtener(0));
            }
            
            if (scanner.hasNextLine()) scanner.nextLine(); // Header
            
            int lineas = 0;
            while (scanner.hasNextLine()) {
                String[] linea = scanner.nextLine().split(",");
                if (linea.length == 3) {
                    String origen = linea[0].trim();
                    String destino = linea[1].trim();
                    double peso = Double.parseDouble(linea[2].trim());
                    agregarArista(origen, destino, peso);
                    lineas++;
                }
            }
            System.out.println("Cargadas " + lineas + " interacciones");
            return true;
        } catch (Exception e) {
            System.err.println("Error CSV: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Guarda grafo actual en CSV.
     */
    public boolean guardarEnCSV(String rutaArchivo) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(rutaArchivo)) {
            writer.println("ProteinaA,ProteinaB,CostoInteraccion");
            
            for (int i = 0; i < nodos.getTamaño(); i++) {
                Nodo nodo1 = nodos.obtener(i);
                for (int j = 0; j < nodo1.getVecinos().getTamaño(); j++) {
                    Arista arista = nodo1.getVecinos().obtener(j);
                    Nodo nodo2 = arista.getDestino();
                    
                    // Evitar duplicados (solo nodo1 < nodo2)
                    if (nodo1.getNombre().compareTo(nodo2.getNombre()) < 0) {
                        writer.printf("%s,%s,%.2f%n",
                            nodo1.getNombre(), nodo2.getNombre(), arista.getPeso());
                    }
                }
            }
            System.out.println("Grafo guardado: " + rutaArchivo);
            return true;
        } catch (Exception e) {
            System.err.println("Error guardado: " + e.getMessage());
            return false;
        }
    }
   
    
    // ========== CONSULTAS ==========
    
    /**
     * Estadísticas del grafo.
     */
    public String estadisticas() {
        int totalAristas = 0;
        
        for (int i = 0; i <nodos.getTamaño(); i++) {
            totalAristas += nodos.obtener(i).getGrado();
        }
        totalAristas /= 2; // No dirigido
        
        String hub = hubPrincipal();
        return String.format(" ESTADÍSTICAS DEL GRAFO: | Proteínas: %d | Interacciones: %d | Hub principal: %s",
            nodos.getTamaño(), totalAristas, hub != null ? hub : "N/A");
    }
        

    
    /**
     * Lista de todos los nombres de proteínas.
     */
    public List<String> getProteinas() {
        List<String> nombres = new ArrayList<>();
        for (int i = 0; i < nodos.getTamaño(); i++) {
            nombres.add(nodos.obtener(i).getNombre());
        }
        return nombres;
    }
    
        public int numeroProteinas() {
        return nodos.getTamaño();
        }
}
   
