/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.cartesian;

import java.util.LinkedList;
import java.util.Random;
import planetarium.contents.celestials.Star;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.abstracts.RegistrableEntry;
import planetarium.contents.registry.events.RegistryEvent;
import planetarium.contents.registry.events.RegistryListener;

/**
 *
 * @author TTT
 */
public class Grid {

    /**
     * Centro del sistema di riferimento.
     */
    private final Position absolute_zero;
    private double maximum_x = 0;
    private double maximum_y = 0;

    private final LinkedList<Position> positions = new LinkedList<>();

    public Grid(Position absolute_zero) {
        this.absolute_zero = absolute_zero;
        init();
    }

    public Grid(double x, double y) {
        this(new Position(x, y));
    }

    private void init() {
        RegistryEvent.getIstance().addListener(new RegistryListener() {
            @Override
            public void onElementRegistered(RegistrableEntry re) {
                if (re.getSubclassObject() instanceof ICelestial) {
                    ICelestial c = (ICelestial) re.getSubclassObject();
                    registerPosition(c.getPosition());
                    if (c.getPosition().getX() > maximum_x) {
                        maximum_x = c.getPosition().getX();
                    }
                    if (c.getPosition().getY() > maximum_y) {
                        maximum_y = c.getPosition().getY();
                    }
                }
            }

            @Override
            public void onElementRemoved(RegistrableEntry re) {
                if (re.getSubclassObject() instanceof ICelestial) {
                    ICelestial c = (ICelestial) re.getSubclassObject();
                    deletePosition(c.getPosition());
                }
            }
        });
    }

    /**
     * Ritorna la posizione assoluta (nel sistema) ripsetto ad un corpo celeste.
     *
     * @param relative Il corpo su cui orbita.
     * @param x La posizione x relativamente al corpo intorno a cui orbita.
     * @param y La posizione y relativamente al corpo intorno a cui orbita.
     * @return La posizione assoluta nel sistema.
     */
    public Position getAbsolutePositionRelativeTo(ICelestial relative, double x, double y) {
        return new Position(absolute_zero.getX() + x + relative.getPosition().getX(), absolute_zero.getY() + y + relative.getPosition().getY());
    }

    /**
     * Ritorna la posizione assoluta (nel sistema) ripsetto ad un corpo celeste.
     *
     * @param relative Il corpo su cui orbita.
     * @param p La posizione relativa al corpo intorno a cui orbita.
     * @return La posizione assoluta nel sistema.
     */
    public Position getAbsolutePositionRelativeTo(ICelestial relative, Position p) {
        return new Position(absolute_zero.getX() + p.getX() + relative.getPosition().getX(), absolute_zero.getY() + p.getY() + relative.getPosition().getY());
    }

    /**
     * Restituisce la posizione relativamente al proprio padre. Se il padre è
     * nulla allora vengono considereate le coordinate definite come zero
     * assoluto del sistema.
     *
     * @param ic Il corpo celeste
     * @return La posizione relativa al padre.
     */
    public Position getPositionRelativeToParent(ICelestial ic) {
        if (ic.getParent() != null) {
            return new Position(ic.getPosition().getX() - ic.getParent().getPosition().getX(), ic.getPosition().getY() - ic.getParent().getPosition().getY());
        } else {
            return new Position(absolute_zero.getX() + ic.getPosition().getX(), absolute_zero.getY() + ic.getPosition().getY());
        }
    }

    /**
     * Posizione di un corpo celeste relativamente alla stella.
     *
     * @param ic Il corpo celeste.
     * @return La posizione relativa alla stella del sistema.
     */
    public Position getPositionRelativeToStar(ICelestial ic) {
        Star in = Star.getIstance();
        return new Position(ic.getPosition().getX() - in.getPosition().getX(), ic.getPosition().getY() - in.getPosition().getY());
    }

    /**
     * Registra una nuova posizione.
     *
     * @param p La posizione da registrare.
     */
    public void registerPosition(Position p) {
        positions.add(p);
    }

    /**
     * Rimuove una posizione.
     *
     * @param p La posizione da rimuovere
     */
    public void deletePosition(Position p) {
        positions.removeFirstOccurrence(p);
    }

    /**
     * Controlla se è già presente una certa posizione.
     *
     * @param p La posizione da controllare.
     * @return {@code true} nel caso esista già una posizione, altrimenti
     * {@code false}.
     */
    public boolean existPosition(Position p) {
        return positions.contains(p);
    }

    /**
     * Ritorna una posizione libera nel sistema.
     *
     * @return Nuova {@link Position}.
     */
    public Position getRandomFreePos() {
        Position new_pos;
        Random r = new Random();
        do {
            new_pos = new Position(r.nextInt((int) maximum_x + 10), r.nextInt((int) maximum_y + 10));
        } while (!existPosition(new_pos));
        return new_pos;
    }

}
