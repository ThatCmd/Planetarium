/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.input;

import java.util.Optional;
import javafx.beans.property.SimpleIntegerProperty;
import planetarium.console.GeneralFormatter;
import static planetarium.console.GeneralFormatter.printOut;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.questionable.Calculator;
import planetarium.contents.system.questionable.Queryable;

/**
 * Gestisce la lettura in input di query e l'esecuzione.
 *
 * @author TTT
 */
public class InputQuery {

    private InputQuery() {
    }

    /**
     * Trova il miglior percorso percorribile e ne calcola la distanza.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     */
    public static void bestPath(GestioneSistema gs) {
        if (gs != null) {
            if (gs.atLeastOnePlanet()) {
                printOut("Calcola il percorso migliore dal corpo celeste:", true, false);
                GeneralFormatter.incrementIndents();
                ICelestial from = celestialLookup("Corpo celeste di partenza:");
                ICelestial to = celestialLookup("Corpo celeste di arrivo:");
                GeneralFormatter.decrementIndents();
                printOut("Il percorso migliore è : " + Queryable.getStrictPath(from, to), true, false);
                printOut("La distanza totale è   : " + Calculator.calcDistance(Queryable.findPath(from, to)), true, false);
            } else {
                printOut("Non ci sono abbastanza corpi celesti per poter eseguire dei calcoli. Aggiungine altri.", true, true);
            }
        }
    }

    /**
     * Mostra il percorso a cui un corpo celeste appartiene.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     */
    public static void hierarchy(GestioneSistema gs) {
        if (gs != null) {
            if (gs.atLeastOnePlanet()) {
                printOut("Ordine di appartenenza di un corpo celeste:", true, false);
                GeneralFormatter.incrementIndents();
                ICelestial to = celestialLookup("Corpo celeste:");
                GeneralFormatter.decrementIndents();
                printOut("La gerarchia è : " + Queryable.getPathToCelestial(to), true, false);
            } else {
                printOut("Non ci sono abbastanza corpi celesti per poter eseguire dei calcoli. Aggiungine altri.", true, true);
            }
        }
    }

    /**
     * Ricerca un corpo celeste tramite ID o nome.
     *
     * @param message Il messaggio da mostrare
     * @param expected Il tipo di corpo celeste che si sta cercando.
     * @return Il corpo celeste.
     */
    public static ICelestial celestialLookup(String message, CelestialType expected) {
        printOut(message, true, false);
        ICelestial toret = null;
        while (toret == null) {
            GeneralFormatter.incrementIndents();
            printOut("Cerca per [1] ID oppure [2] Nome (oppure * per annullare):", true, false);
            GeneralFormatter.incrementIndents();
            Optional<Integer> op;
            do {
                op = InputHandler.getIstance().readInteger("Operazione :", true, "*");
                if (!op.isPresent()) {
                    GeneralFormatter.decrementIndents();
                    GeneralFormatter.decrementIndents();
                    return null;
                }
            } while ((op.get() != 1 && op.get() != 2));
            GeneralFormatter.decrementIndents();
            switch (op.get()) {
                case 1:
                    toret = Queryable.getCelestialByID(InputHandler.getIstance().readLong("ID:"));
                    break;
                case 2:
                    toret = Queryable.getCelestialByName(InputHandler.getIstance().readString("Nome:"));
                    break;
            }
            if (toret == null || toret.getType() != expected) {
                printOut("Nessun corpo celeste di tipo " + expected + " è stato trovato. Riprovare:", true, true);
                toret = null;
            }
            GeneralFormatter.decrementIndents();
        }
        return toret;
    }

    /**
     * Ricerca un corpo celeste tramite ID o nome.
     *
     * @param message Il messaggio da mostrare
     * @return Il corpo celeste.
     */
    public static ICelestial celestialLookup(String message) {
        printOut(message, true, false);
        ICelestial toret = null;
        while (toret == null) {
            GeneralFormatter.incrementIndents();
            printOut("Cerca per [1] ID oppure [2] Nome (oppure * per annullare):", true, false);
            GeneralFormatter.incrementIndents();
            Optional<Integer> op;
            do {
                op = InputHandler.getIstance().readInteger("Operazione :", true, "*");
                if (!op.isPresent()) {
                    GeneralFormatter.decrementIndents();
                    GeneralFormatter.decrementIndents();
                    return null;
                }
            } while ((op.get() != 1 && op.get() != 2));
            GeneralFormatter.decrementIndents();
            switch (op.get()) {
                case 1:
                    toret = Queryable.getCelestialByID(InputHandler.getIstance().readLong("ID:"));
                    break;
                case 2:
                    toret = Queryable.getCelestialByName(InputHandler.getIstance().readString("Nome:"));
                    break;
            }
            if (toret == null) {
                printOut("Nessun corpo celeste è stato trovato. Riprovare:", true, true);
            }
            GeneralFormatter.decrementIndents();
        }
        return toret;
    }

    /**
     * Stampa le lune in orbit intorno ad un pianeta.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     */
    public static void getMoons(GestioneSistema gs) {
        if (gs != null) {
            if (gs.atLeastOnePlanet()) {
                printOut("Trova le lune in orbita intorno ad un pianeta:", true, false);
                GeneralFormatter.incrementIndents();
                ICelestial planet = celestialLookup("Pianeta:", CelestialType.PIANETA);
                GeneralFormatter.decrementIndents();
                if (planet != null) {
                    SimpleIntegerProperty i = new SimpleIntegerProperty(1);
                    printOut("Lune appartenenti a questo pianeta:", true, false);
                    Queryable.getMoons(planet).stream().forEach(moon -> {
                        printOut(moon.getName(), i.intValue() % 3 == 0, false);
                        if (i.intValue() % 3 != 0) {
                            i.set(i.intValue() + 1);
                            System.out.print(" , ");
                        } else {
                            i.set(0);
                        }
                    });
                }
            } else {
                printOut("Non ci sono abbastanza corpi celesti per poter eseguire dei calcoli. Aggiungine altri.", true, true);
            }
        }
    }

    /**
     * Calcola la massa e la posizione finale.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     */
    public static void calcSystemMass(GestioneSistema gs) {
        if (gs != null) {
            double mass_val = Calculator.recalcNeededValues(gs.getStar());
            Position p = gs.getStar().getWeightedPosition();
            GeneralFormatter.incrementIndents();
            printOut("La somma delle masse vale                        : " + mass_val, true, false);
            printOut("La somma delle posizioni in base alle masse vale : " + p, true, false);
            printOut("Il centro di massa è                             : " + Position.divide(p, mass_val), true, false);
            GeneralFormatter.decrementIndents();
        }
    }

    /**
     * Controlla la collisione tra due corpi.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     */
    public static void calcCollision(GestioneSistema gs) {
        if (gs != null) {
            if (gs.atLeastOnePlanet()) {
                printOut("Controlla la collisione tra due pianeti:", true, false);
                GeneralFormatter.incrementIndents();
                ICelestial c1 = celestialLookup("Seleziona corpo 1:");
                ICelestial c2 = celestialLookup("Seleziona corpo 2:");
                GeneralFormatter.decrementIndents();
                boolean b = Calculator.canCollide(c1, c2);
                if (b) {
                    printOut("Le orbite dei due corpi hanno almeno un punto di collisione.", true, false);
                } else {
                    printOut("I due corpi non potranno mai collidere.", true, false);
                }
            } else {
                printOut("Non ci sono abbastanza corpi celesti per poter eseguire dei calcoli. Aggiungine altri.", true, true);
            }
        }
    }

}
