/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

public class Nodo {
    private String nombre;
    private Lista<Arista> vecinos; // Lista de adyacencia (conexiones con otros nodos)

    /**
     * Constructor para un nodo del grafo.
     * @param nombre Nombre de la proteína (identificador único).
     */
    public Nodo(String nombre) {
        this.nombre = nombre;
        this.vecinos = new Lista<>(); // Se inicializa la lista de vecinos
    }

    // ========== GETTERS ==========

    public String getNombre() {
        return nombre;
    }

    public Lista<Arista> getVecinos() {
        return vecinos;
    }

    /**
     * El grado es el número de conexiones (aristas) que tiene el nodo.
     * @return cantidad de vecinos.
     */
    public int getGrado() {
        return vecinos.getTamaño();
    }

    // ========== OPERACIONES DE VECINDAD ==========

    /**
     * Agrega una conexión hacia otro nodo.
     * @param destino El nodo con el que se conecta.
     * @param peso El valor de la interacción.
     */
    public void agregarVecino(Nodo destino, double peso) {
        // Verificamos si ya existe la arista para evitar duplicados
        if (buscarArista(destino.getNombre()) == null) {
            vecinos.agregar(new Arista(destino, peso));
        }
    }

    /**
     * Elimina la conexión hacia un nodo específico.
     * Requerido por el método eliminarNodo del Grafo.
     */
    public void eliminarVecino(Nodo nodo) {
        for (int i = 0; i < vecinos.getTamaño(); i++) {
            Arista a = vecinos.obtener(i);
            if (a.getDestino().equals(nodo)) {
                vecinos.eliminar(a);
                break; // Terminamos una vez encontrado y eliminado
            }
        }
    }

    /**
     * Busca si existe una arista hacia un nodo por su nombre.
     */
    private Arista buscarArista(String nombreDestino) {
        for (int i = 0; i < vecinos.getTamaño(); i++) {
            Arista a = vecinos.obtener(i);
            if (a.getDestino().getNombre().equals(nombreDestino)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre;
    }
}


