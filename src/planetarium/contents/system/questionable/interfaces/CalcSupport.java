/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable.interfaces;

import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.questionable.Calculator;

/**
 *
 * @author TTT
 */
public interface CalcSupport {

    /**
     * Serve per diminuire il carico di lavoro del programma: la classe
     * {@link Calculator} userà i dati già presenti se questo metodo resituisce
     * {@code false} altrimenti ricalcola la massa di tutti i corpi celesti che
     * sono sulla sua orbita.
     *
     * @return {@code true} se bisogna ricalcolare i valori, altrimenti
     * {@code false}.
     */
    public boolean isToBeReloaded();

    /**
     * Imposta il nuovo valore della massa ricalcolata.
     *
     * @param calculated_mass La nuova massa.
     */
    public void setCalculatedMass(double calculated_mass);

    /**
     * Ritorna il valore della massa calcolata.
     *
     * @return La massa.
     */
    public double getCalculatedMass();

    /**
     * Imposta la massa pesata delle posizioni.
     *
     * @param p La posizione pesata del corpo.
     */
    public void setWeightedPosition(Position p);

    /**
     * Ritorna la massa pesata delle posizioni.
     *
     * @return La posizione in base alla massa.
     */
    public Position getWeightedPosition();
}
