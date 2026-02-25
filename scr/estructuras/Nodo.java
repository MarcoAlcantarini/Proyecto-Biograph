package estructuras;

/**
 * Nodo para lista doblemente enlazada.
 * Cada nodo tiene un dato y referencias al nodo anterior y siguiente.
 */
public class Nodo<T> {
    private T dato;           // El dato almacenado en el nodo
    private Nodo<T> anterior; // Referencia al nodo anterior
    private Nodo<T> siguiente; // Referencia al nodo siguiente
    
    /**
     * Constructor que crea un nodo con un dato específico.
     * @param dato El dato a almacenar en el nodo.
     */
    public Nodo(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }
    
    // ========== GETTERS ==========
    
    /**
     * Obtiene el dato almacenado en el nodo.
     * @return El dato del nodo.
     */
    public T getDato() {
        return dato;
    }
    
    /**
     * Obtiene el nodo anterior.
     * @return El nodo anterior, o null si es el primero.
     */
    public Nodo<T> getAnterior() {
        return anterior;
    }
    
    /**
     * Obtiene el nodo siguiente.
     * @return El nodo siguiente, o null si es el último.
     */
    public Nodo<T> getSiguiente() {
        return siguiente;
    }
    
    // ========== SETTERS ==========
    
    /**
     * Cambia el dato almacenado en el nodo.
     * @param dato El nuevo dato.
     */
    public void setDato(T dato) {
        this.dato = dato;
    }
    
    /**
     * Establece el nodo anterior.
     * @param anterior El nuevo nodo anterior.
     */
    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }
    
    /**
     * Establece el nodo siguiente.
     * @param siguiente El nuevo nodo siguiente.
     */
    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
    
    /**
     * Representación en texto del nodo.
     * @return El dato convertido a String.
     */
    @Override
    public String toString() {
        return dato.toString();
    }
}