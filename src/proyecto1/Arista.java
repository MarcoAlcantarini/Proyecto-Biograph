/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

/**
 *
 * @author USUARIO
 */
public class Arista {
    private Nodo destino;
    private double peso;

    public Arista(Nodo destino, double peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public Nodo getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
}
