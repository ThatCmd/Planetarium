/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system;

import planetarium.contents.celestials.Moon;
import planetarium.contents.celestials.Planet;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.questionable.Queryable;
import planetarium.contents.celestials.Star;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.abstracts.RegistrableEntry;
import planetarium.contents.registry.events.RegistryEvent;
import planetarium.contents.registry.events.RegistryListener;
import planetarium.contents.system.cartesian.Grid;
import planetarium.contents.system.utils.NamePicker;

/**
 * Gestisce l'aggiunta e l'eliminazione dei corpi celesti semplificandone la
 * registrazione. Aiuta a mantenere in memoria le posizioni e a registrarle.
 *
 * @author TTT
 */
public class GestioneSistema {

    private final String name;
    private final Star main;
    private static final Grid coordinate_sys = new Grid(0, 0);

    private int planet_counter = 0;
    private int moon_counter = 0;

    public GestioneSistema(String name) {
        this.name = name == null || "".equals(name.trim()) ? NamePicker.getIstance().getName(CelestialType.SISTEMA) : name;
        main = Star.genIstance("", (Math.random() + 1) * 1000, new Position(0, 0));
        init();
    }

    private void init() {
        RegistryEvent.getIstance().addListener(new RegistryListener() {
            @Override
            public void onElementRegistered(RegistrableEntry re) {
                if (re.getSubclassObject() instanceof Planet) {
                    planet_counter++;
                } else if (re.getSubclassObject() instanceof Moon) {
                    moon_counter++;
                }
            }

            @Override
            public void onElementRemoved(RegistrableEntry re) {
                if (re.getSubclassObject() instanceof Planet) {
                    planet_counter--;
                } else if (re.getSubclassObject() instanceof Moon) {
                    moon_counter--;
                }
            }
        });
    }

    /**
     * Aggiungi un nuovo elemento al sistema specificando l'ID del corpo celeste
     * su cui il nuovo elemento orbita. Inoltre deve essere specificato se si
     * considerano le coordinate in modo assoluto o relativo.
     *
     * @param ID L'ID del padre
     * @param ic Il nuovo corpo da registrare
     * @param absolute_coords {@code true} Se le coordinate sono riferite al
     * centro del sistema oppure {@code false} se si riferiscono al padre.
     */
    public void addElementTo(long ID, ICelestial ic, boolean absolute_coords) {
        if (ic != null) {
            if (ID >= 0) {
                ICelestial parent = Queryable.getCelestialByID(ID);
                if (parent != null) {
                    if (!absolute_coords) {
                        ic.updatePosition(coordinate_sys.getAbsolutePositionRelativeTo(parent, ic.getPosition()));
                    }
                    parent.registerCelestial(ic);
                }
            }
        }
    }

    /**
     * Aggiungi un nuovo elemento al sistema specificando il corpo celeste su
     * cui il nuovo elemento orbita. Inoltre deve essere specificato se si
     * considerano le coordinate in modo assoluto o relativo.
     *
     * @param parent Il padre.
     * @param ic Il nuovo corpo da registrare.
     * @param absolute_coords {@code true} Se le coordinate sono riferite al
     * centro del sistema oppure {@code false} se si riferiscono al padre.
     */
    public void addElementTo(ICelestial parent, ICelestial ic, boolean absolute_coords) {
        if (ic != null) {
            if (parent != null) {
                if (!absolute_coords) {
                    ic.updatePosition(coordinate_sys.getAbsolutePositionRelativeTo(parent, ic.getPosition()));
                }
                parent.registerCelestial(ic);
            } else {
                main.registerCelestial(ic);
            }
        }
    }

    /**
     * Rimuove un'elemento dal sistema.
     *
     * @param ID L'ID dell'elemento
     */
    public void destroyElementAt(long ID) {
        ICelestial el = Queryable.getCelestialByID(ID);
        if (el != null) {
            el.destroy();
        }
    }

    public void destroyElementAt(ICelestial ic) {
        if (ic != null) {
            ic.destroy();
        }
    }

    /**
     * Restituisce il nome assegnato a questo sistema.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la stella di questo sistema.
     *
     * @return
     */
    public Star getStar() {
        return main;
    }

    /**
     * Restituisce una posizione casuale nel sistema.
     *
     * @return
     */
    public static Position getRandomPosition() {
        return coordinate_sys.getRandomFreePos();
    }

    /**
     * Controlla se esiste almeno un pianeta nel sistema. Tiene traccia se
     * esistono o sono stati distrutti: un sistema a cui viene aggiunto un
     * pianeta e poi lo stesso viene distrutto non ha pianeti, solo una stella.
     *
     * @return {@code true} se esiste almeno un pianet registrato.
     */
    public boolean atLeastOnePlanet() {
        return planet_counter > 0;
    }

    /**
     * Ritorna il sistema di riferimento.
     *
     * @return
     */
    public Grid getGrid() {
        return coordinate_sys;
    }

    public boolean maximumReached(ICelestial i) {
        if (i == null) {
            return Star.getIstance().getOnOrbit().size() >= 26000;
        }
        switch (i.getType()) {
            default:
            case ASTEROIDE:
            case LUNA:
            case SISTEMA:
            case TESLA_ROADSTER:
            case MORTE_NERA:
                return true;
            case STELLA:
                return i.getOnOrbit().size() >= 26000;
            case PIANETA:
                return i.getOnOrbit().size() >= 5000;
        }
    }
}
