package estructuras;

/**
 * Implementación de una lista doblemente enlazada.
 * Permite agregar, eliminar, buscar y recorrer elementos en ambas direcciones.
 */
public class Lista<T> {
    private Nodo<T> cabeza; // Primer nodo de la lista
    private Nodo<T> cola;   // Último nodo de la lista
    private int tamaño;     // Cantidad de elementos
    
    /**
     * Constructor que crea una lista vacía.
     */
    public Lista() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    
    // ========== OPERACIONES BÁSICAS ==========
    
    /**
     * Agrega un elemento al final de la lista.
     * @param dato El dato a agregar.
     */
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        
        if (estaVacia()) {
            // Lista vacía: el nuevo nodo es cabeza y cola
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            // Agregar al final
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
        tamaño++;
    }
    
    /**
     * Elimina la primera ocurrencia de un dato en la lista.
     * @param dato El dato a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        
        // Buscar el nodo que contiene el dato
        Nodo<T> actual = cabeza;
        while (actual != null && !actual.getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }
        
        // Si no se encontró
        if (actual == null) {
            return false;
        }
        
        // Caso 1: Eliminar el único nodo
        if (cabeza == cola) {
            cabeza = null;
            cola = null;
        }
        // Caso 2: Eliminar la cabeza
        else if (actual == cabeza) {
            cabeza = cabeza.getSiguiente();
            cabeza.setAnterior(null);
        }
        // Caso 3: Eliminar la cola
        else if (actual == cola) {
            cola = cola.getAnterior();
            cola.setSiguiente(null);
        }
        // Caso 4: Eliminar un nodo intermedio
        else {
            actual.getAnterior().setSiguiente(actual.getSiguiente());
            actual.getSiguiente().setAnterior(actual.getAnterior());
        }
        
        tamaño--;
        return true;
    }
    
    /**
     * Busca un dato en la lista.
     * @param dato El dato a buscar.
     * @return El dato si se encuentra, null si no.
     */
    public T buscar(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    
    /**
     * Obtiene un elemento por su posición.
     * @param indice La posición (0 = primero).
     * @return El dato en esa posición, o null si el índice es inválido.
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            return null;
        }
        
        Nodo<T> actual;
        
        // Optimización: comenzar desde el inicio o final según sea más cercano
        if (indice < tamaño / 2) {
            // Recorrer desde el inicio
            actual = cabeza;
            for (int i = 0; i < indice; i++) {
                actual = actual.getSiguiente();
            }
        } else {
            // Recorrer desde el final
            actual = cola;
            for (int i = tamaño - 1; i > indice; i--) {
                actual = actual.getAnterior();
            }
        }
        
        return actual.getDato();
    }
    
    // ========== MÉTODOS DE CONSULTA ==========
    
    /**
     * Obtiene el tamaño de la lista.
     * @return Número de elementos.
     */
    public int getTamaño() {
        return tamaño;
    }
    
    /**
     * Verifica si la lista está vacía.
     * @return true si está vacía, false si no.
     */
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    /**
     * Verifica si la lista contiene un dato.
     * @param dato El dato a verificar.
     * @return true si contiene el dato, false si no.
     */
    public boolean contiene(T dato) {
        return buscar(dato) != null;
    }
    
    // ========== MÉTODOS DE RECORRIDO ==========
    
    /**
     * Recorre la lista de inicio a fin y muestra los elementos.
     */
    public void recorrerHaciaAdelante() {
        System.out.print("Recorrido hacia adelante: ");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.getDato() + " <-> ");
            actual = actual.getSiguiente();
        }
        System.out.println("null");
    }
    
    /**
     * Recorre la lista de fin a inicio y muestra los elementos.
     */
    public void recorrerHaciaAtras() {
        System.out.print("Recorrido hacia atrás: ");
        Nodo<T> actual = cola;
        while (actual != null) {
            System.out.print(actual.getDato() + " <-> ");
            actual = actual.getAnterior();
        }
        System.out.println("null");
    }
    
    /**
     * Vacía la lista completamente.
     */
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamaño = 0;
    }
    
    /**
     * Representación en texto de la lista.
     * @return String con todos los elementos.
     */
    @Override
    public String toString() {
        if (estaVacia()) {
            return "Lista vacía";
        }
        
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = cabeza;
        
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(" <-> ");
            }
            actual = actual.getSiguiente();
        }
        
        return sb.toString();
    }
}