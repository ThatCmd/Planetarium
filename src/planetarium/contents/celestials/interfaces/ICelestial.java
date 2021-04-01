/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.celestials.interfaces;

import java.util.List;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.registry.abstracts.RegistrableEntry;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.questionable.interfaces.CalcSupport;
import planetarium.contents.system.questionable.interfaces.QuerySupport;

/**
 * Interfaccia che deve essere implementata da ogni corpo celeste che può essere
 * rilevato in un sistema.
 * <p>
 * Raccoglie i metodi essenziali e comuni in modo da poter favorire il passaggio
 * tra diversi metodi.
 *
 * @author TTT
 */
public interface ICelestial extends CalcSupport, QuerySupport {

    /**
     * Ritorna la massa del corpo celeste. La massa corrispondente al valore 1.0
     * equivale a 10^15 tonnellate.
     *
     * @return Massa del corpo
     */
    public double getMass();

    /**
     * Restituisce una lista non modificabile di corpi celesti in orbita
     * rispetto questo corpo celeste.
     *
     * @return Lista non modificabile di corpi nell'orbita.
     */
    public List<ICelestial> getOnOrbit();

    /**
     * Ritorna il corpo celeste padre su cui questo corpo orbita.
     *
     * @return <code>null</code> se non ha nessun padre, altrimenti il corpo
     * celeste padre.
     */
    public ICelestial getParent();

    /**
     * Imposta il corpo celeste padre su cui questo corpo orbita.
     *
     * @param ic Il corpo intorno a cui orbita.
     */
    public void setParent(ICelestial ic);

    /**
     * Ritorna il tipo di corpo celeste definito nell'enumerazione
     * {@link CelestialType}.
     *
     * @return Il tipo di corpo celeste
     */
    public CelestialType getType();

    /**
     * Si occupa di registrare un nuovo corpo celeste a questo celeste.
     *
     * @param ic L'elemento da registrare (deve implementare {@link ICelestial}
     * e estendere {@link RegistrableEntry}).
     */
    public void registerCelestial(Object ic);

    /**
     * Ritorna il nome del corpo celeste.
     *
     * @return Il nome
     */
    public String getName();

    /**
     * Ritorna la posizione assoluta del corpo celeste.
     *
     * @return Posizione assoluta nel sistema.
     */
    public Position getPosition();

    /**
     * Aggiorna la posizione assoluta del corpo celeste.
     *
     * @param position La posizione assoluta (con riferimento allo zero assoluto
     * di {@link Grid}).
     */
    public void updatePosition(Position position);

    /**
     * Elimina il corpo dal planetario.
     */
    public void destroy();
}
