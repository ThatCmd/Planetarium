/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable.interfaces;

import java.util.Optional;
import planetarium.contents.celestials.interfaces.ICelestial;

/**
 *
 * @author TTT
 */
public interface QuerySupport {

    /**
     * Controlla se contiene sull'orbita un certo corpo celeste, questo metodo
     * deve chiamare tutti gli oggetti sulla sua orbita.
     *
     * @param ic Il corpo celeste che si cerca
     * @return {@code true} se una delle sue orbite contiene il corpo celeste.
     */
    public boolean contains(ICelestial ic);

    /**
     * Restituisce il corpo celeste che contiene il corpo celeste.
     *
     * @param ic Il corpo celeste da cercare.
     * @return {@link Optional}
     */
    public Optional<ICelestial> getChild(ICelestial ic);

}
