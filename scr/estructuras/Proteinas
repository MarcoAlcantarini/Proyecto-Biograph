package estructuras;

/**
 * Representa una interacción entre dos proteínas en la red PPI.
 * Cada arista tiene un origen, un destino, un peso y un estado activo/inactivo.
 */
public class Proteinas {
    private String origen;
    private String destino;
    private double peso;
    private boolean activa;
    
    /**
     * Constructor que crea una nueva interacción entre dos proteínas.
     * @param origen Nombre de la proteína de origen
     * @param destino Nombre de la proteína de destino
     * @param peso Peso de la interacción
     */
    public Proteinas(String origen, String destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
        this.activa = true;
    }
    
    /**
     * Obtiene la proteína de origen.
     * 
     * @return Nombre de la proteína origen
     */
    public String getOrigen() { 
        return origen; 
    }
    
    /**
     * Obtiene la proteína de destino.
     * 
     * @return Nombre de la proteína destino
     */
    public String getDestino() { 
        return destino;
    }
    
    /**
     * Obtiene el peso de la interacción.
     * 
     * @return Peso de la arista
     */
    public double getPeso() { 
        return peso; 
    }
    
    /**
     * Verifica si la arista está activa.
     * 
     * @return true si está activa, false si está inactiva
     */
    public boolean isActiva() { 
        return activa; 
    }
    
    /**
     * Establece el estado de la arista.
     * 
     * @param activa Nuevo estado de la arista
     */
    public void setActiva(boolean activa) { 
        this.activa = activa; 
    }
    
    /**
     * Compara esta arista con otro objeto para determinar si son iguales.
     * Dos aristas son iguales si conectan las mismas proteínas (sin importar el orden).
     * 
     * @param obj Objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Proteinas a = (Proteinas) obj;
        return (origen.equals(a.origen) && destino.equals(a.destino)) ||
               (origen.equals(a.destino) && destino.equals(a.origen));
    }
    
    /**
     * Retorna una representación en cadena de la arista.
     * 
     * @return String con formato "origen - destino (peso)"
     */
    @Override
    public String toString() {
        return origen + " - " + destino + " (" + peso + ")";
    }
}
