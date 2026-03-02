package estructuras;

/**
 * Grafo de interacciones proteína-proteína (PPI).
 * Implementa un grafo no dirigido usando listas de adyacencia.
 * Proporciona algoritmos para análisis de redes biológicas: BFS, Dijkstra y centralidad de grado.
 * 
 */
public class Grafo {
    private Lista<String> proteinas;
    private Lista<Proteinas> interacciones;
    
    /**
     * Constructor que crea un grafo vacío.
     */
    public Grafo() {
        this.proteinas = new Lista<>();
        this.interacciones = new Lista<>();
    }
    
    
    /**
     * Agrega una nueva proteína al grafo.
     * 
     * @param nombre Nombre de la proteína a agregar
     * @return true si se agregó correctamente, false si ya existía
     */
    public boolean agregarProteina(String nombre) {
        if (!proteinas.contiene(nombre)) {
            proteinas.agregar(nombre);
            return true;
        }
        return false;
    }
    
    /**
     * Elimina una proteína y todas sus interacciones del grafo.
     * 
     * @param nombre Nombre de la proteína a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    public boolean eliminarProteina(String nombre) {
        if (proteinas.eliminar(nombre)) {
            // Eliminar interacciones asociadas
            Lista<Proteinas> aEliminar = new Lista<>();
            for (int i = 0; i < interacciones.getTamaño(); i++) {
                Proteinas a = interacciones.obtener(i);
                if (a.getOrigen().equals(nombre) || a.getDestino().equals(nombre)) {
                    aEliminar.agregar(a);
                }
            }
            for (int i = 0; i < aEliminar.getTamaño(); i++) {
                interacciones.eliminar(aEliminar.obtener(i));
            }
            return true;
        }
        return false;
    }
    
    /**
     * Obtiene la lista de todas las proteínas del grafo.
     * 
     * @return Lista con los nombres de las proteínas
     */
    public Lista<String> getProteinas() { 
        return proteinas; 
    }
    
    /**
     * Verifica si una proteína existe en el grafo.
     * 
     * @param nombre Nombre de la proteína a verificar
     * @return true si existe, false en caso contrario
     */
    public boolean existeProteina(String nombre) { 
        return proteinas.contiene(nombre); 
    }
    
    /**
     * Agrega una nueva interacción entre dos proteínas.
     * 
     * @param origen Proteína de origen
     * @param destino Proteína de destino
     * @param peso Peso de la interacción
     * @return true si se agregó correctamente, false si ya existía o las proteínas no existen
     */
    public boolean agregarInteraccion(String origen, String destino, double peso) {
        if (proteinas.contiene(origen) && proteinas.contiene(destino) && !origen.equals(destino)) {
            Proteinas nueva = new Proteinas(origen, destino, peso);
            
            // Verificar si ya existe
            for (int i = 0; i < interacciones.getTamaño(); i++) {
                if (interacciones.obtener(i).equals(nueva)) {
                    return false;
                }
            }
            
            interacciones.agregar(nueva);
            return true;
        }
        return false;
    }
    
    /**
     * Elimina una interacción entre dos proteínas.
     * 
     * @param origen Proteína de origen
     * @param destino Proteína de destino
     * @return true si se eliminó correctamente, false si no existía
     */
    public boolean eliminarInteraccion(String origen, String destino) {
        for (int i = 0; i < interacciones.getTamaño(); i++) {
            Proteinas a = interacciones.obtener(i);
            if ((a.getOrigen().equals(origen) && a.getDestino().equals(destino)) ||
                (a.getOrigen().equals(destino) && a.getDestino().equals(origen))) {
                return interacciones.eliminar(a);
            }
        }
        return false;
    }
    
    /**
     * Obtiene todas las interacciones del grafo.
     * 
     * @return Lista de interacciones
     */
    public Lista<Proteinas> getInteracciones() { 
        return interacciones; 
    }
    
    /**
     * Obtiene todas las interacciones de una proteína específica.
     * 
     * @param proteina Nombre de la proteína
     * @return Lista de interacciones de esa proteína
     */
    public Lista<Proteinas> getInteraccionesDe(String proteina) {
        Lista<Proteinas> resultado = new Lista<>();
        for (int i = 0; i < interacciones.getTamaño(); i++) {
            Proteinas a = interacciones.obtener(i);
            if (a.getOrigen().equals(proteina) || a.getDestino().equals(proteina)) {
                resultado.agregar(a);
            }
        }
        return resultado;
    }
    
    /**
     * Encuentra los complejos proteicos usando BFS (Búsqueda en Anchura).
     * Los complejos son componentes conexos del grafo (grupos de proteínas que interactúan entre sí).
     * 
     * @return Lista de complejos, donde cada complejo es una lista de nombres de proteínas
     */
    public Lista<Lista<String>> encontrarComplejos() {
        Lista<Lista<String>> complejos = new Lista<>();
        Lista<String> visitadas = new Lista<>();

        int n = proteinas.getTamaño();

        for (int i = 0; i < n; i++) {
            String proteina = proteinas.obtener(i); //Obtener directamente, sin toArray()

            if (!visitadas.contiene(proteina)) {
                Lista<String> complejo = new Lista<>();
                Lista<String> cola = new Lista<>();

                cola.agregar(proteina);
                visitadas.agregar(proteina);

                while (!cola.estaVacia()) {
                    String actual = cola.getPrimero();
                    cola.eliminarPorIndice(0);
                    complejo.agregar(actual);

                    // Buscar vecinos
                    for (int j = 0; j < interacciones.getTamaño(); j++) {
                        Proteinas a = interacciones.obtener(j);
                        if (a.isActiva()) {
                            String vecino = null;
                            if (a.getOrigen().equals(actual)) {
                                vecino = a.getDestino();
                            } else if (a.getDestino().equals(actual)) {
                                vecino = a.getOrigen();
                            }

                            if (vecino != null && !visitadas.contiene(vecino)) {
                                visitadas.agregar(vecino);
                                cola.agregar(vecino);
                            }
                        }
                    }
                }

                if (complejo.getTamaño() > 1) {
                    complejos.agregar(complejo);
                }
            }
        }

        return complejos;
    }
    
    /**
     * Encuentra la ruta más corta entre dos proteínas usando el algoritmo de Dijkstra.
     * 
     * @param origen Proteína de inicio
     * @param destino Proteína de destino
     * @return ResultadoDijkstra con la distancia total y la lista de proteínas en la ruta
     */
    public ResultadoDijkstra rutaMasCorta(String origen, String destino) {
    // Validar que las proteínas existan
        if (!proteinas.contiene(origen) || !proteinas.contiene(destino)) {
            return new ResultadoDijkstra(Double.MAX_VALUE, new Lista<>());
        }

        int n = proteinas.getTamaño();
        String[] nodos = new String[n];
        for (int i = 0; i < n; i++) {
            nodos[i] = proteinas.obtener(i); // Obtener cada elemento como String
        }

        double[] dist = new double[n];
        String[] prev = new String[n];
        boolean[] visitado = new boolean[n];

        // Inicializar distancias
        for (int i = 0; i < n; i++) {
            dist[i] = Double.MAX_VALUE;
            prev[i] = null;
            visitado[i] = false;
            if (nodos[i].equals(origen)) {
                dist[i] = 0;
            }
        }

        // Algoritmo de Dijkstra
        for (int count = 0; count < n - 1; count++) {
            // Encontrar nodo no visitado con distancia mínima
            double min = Double.MAX_VALUE;
            int u = -1;
            for (int i = 0; i < n; i++) {
                if (!visitado[i] && dist[i] < min) {
                    min = dist[i];
                    u = i;
                }
            }

            if (u == -1) break;
            visitado[u] = true;

            // Actualizar vecinos
            for (int i = 0; i < interacciones.getTamaño(); i++) {
                Proteinas a = interacciones.obtener(i);
                if (!a.isActiva()) continue;

                String vecino = null;
                if (a.getOrigen().equals(nodos[u])) {
                    vecino = a.getDestino();
                } else if (a.getDestino().equals(nodos[u])) {
                    vecino = a.getOrigen();
                }

                if (vecino != null) {
                    int v = -1;
                    for (int j = 0; j < n; j++) {
                        if (nodos[j].equals(vecino)) {
                            v = j;
                            break;
                        }
                    }

                    if (v != -1 && !visitado[v] && dist[u] + a.getPeso() < dist[v]) {
                        dist[v] = dist[u] + a.getPeso();
                        prev[v] = nodos[u];
                    }
                }
            }
        }

        // Construir ruta
        int idxDestino = -1;
        for (int i = 0; i < n; i++) {
            if (nodos[i].equals(destino)) {
                idxDestino = i;
                break;
            }
        }

        if (idxDestino == -1 || dist[idxDestino] == Double.MAX_VALUE) {
            return new ResultadoDijkstra(Double.MAX_VALUE, new Lista<>());
        }

        Lista<String> ruta = new Lista<>();
        String actual = destino;
        while (actual != null) {
            ruta.agregarAlInicio(actual);
            int idx = -1;
            for (int i = 0; i < n; i++) {
                if (nodos[i].equals(actual)) {
                    idx = i;
                    break;
                }
            }
            actual = prev[idx];
        }

        return new ResultadoDijkstra(dist[idxDestino], ruta);
    }
    
    /**
     * Identifica las proteínas con más conexiones (hubs).
     * 
     * @param topN Número de hubs a identificar (ej: 5 para top 5)
     * @return Lista de strings con el nombre y grado de cada hub
     */
    public Lista<String> identificarHubs(int topN) {
        int n = proteinas.getTamaño();
        if (n == 0) {
            return new Lista<>(); // Lista vacía si no hay proteínas
        }

        // Crear arreglo de Strings correctamente
        String[] nodos = new String[n];
        for (int i = 0; i < n; i++) {
            nodos[i] = proteinas.obtener(i); // Obtener cada elemento como String
        }

        int[] grados = new int[n];

        // Calcular grados
        for (int i = 0; i < n; i++) {
            grados[i] = 0;
            for (int j = 0; j < interacciones.getTamaño(); j++) {
                Proteinas a = interacciones.obtener(j);
                if (a.getOrigen().equals(nodos[i]) || a.getDestino().equals(nodos[i])) {
                    grados[i]++;
                }
            }
        }

        // Ordenar por grado (burbuja)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (grados[j] < grados[j + 1]) {
                    // Intercambiar grados
                    int tempG = grados[j];
                    grados[j] = grados[j + 1];
                    grados[j + 1] = tempG;

                    // Intercambiar nombres
                    String tempN = nodos[j];
                    nodos[j] = nodos[j + 1];
                    nodos[j + 1] = tempN;
                }
            }
        }

        Lista<String> hubs = new Lista<>();
        int limite = Math.min(topN, n);
        for (int i = 0; i < limite; i++) {
            hubs.agregar(nodos[i] + " (" + grados[i] + " conexiones)");
        }
        return hubs;
    }
    
    /**
     * Elimina todas las proteínas e interacciones del grafo.
     */
    public void vaciar() {
        proteinas.vaciar();
        interacciones.vaciar();
    }
    
    /**
     * Obtiene el número de proteínas en el grafo.
     * 
     * @return Cantidad de proteínas
     */
    public int getNumProteinas() { 
        return proteinas.getTamaño(); 
    }
    
    /**
     * Obtiene el número de interacciones en el grafo.
     * 
     * @return Cantidad de interacciones
     */
    public int getNumInteracciones() { 
        return interacciones.getTamaño(); 
    }
    
    /**
     * Clase para encapsular el resultado del algoritmo de Dijkstra.
     * Contiene la distancia total y la lista de proteínas en la ruta.
     */
    public static class ResultadoDijkstra {
        /** Distancia total de la ruta */
        private double distancia;
        
        /** Lista de proteínas en la ruta (en orden) */
        private Lista<String> ruta;
        
        /**
         * Constructor del resultado de Dijkstra.
         * 
         * @param distancia Distancia total de la ruta
         * @param ruta Lista de proteínas en la ruta
         */
        public ResultadoDijkstra(double distancia, Lista<String> ruta) {
            this.distancia = distancia;
            this.ruta = ruta;
        }
        
        /**
         * Obtiene la distancia total de la ruta.
         * 
         * @return Distancia total
         */
        public double getDistancia() { 
            return distancia; 
        }
        
        /**
         * Obtiene la lista de proteínas en la ruta.
         * 
         * @return Lista de proteínas en orden
         */
        public Lista<String> getRuta() { 
            return ruta; 
        }
        
        /**
         * Verifica si existe una ruta válida.
         * 
         * @return true si hay ruta, false si no
         */
        public boolean hayRuta() { 
            return distancia < Double.MAX_VALUE && ruta.getTamaño() > 0; 
        }
    }
}
