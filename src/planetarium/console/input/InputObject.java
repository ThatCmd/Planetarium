/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.input;

import planetarium.console.GeneralFormatter;
import java.util.Optional;
import static planetarium.console.GeneralFormatter.printOut;
import static planetarium.console.input.InputQuery.celestialLookup;
import planetarium.contents.celestials.Moon;
import planetarium.contents.celestials.Planet;
import planetarium.contents.celestials.Star;
import planetarium.contents.celestials.customized.MorteNera;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.abstracts.RegistrableEntry;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.utils.OutputPicker;

/**
 * Gestisce la lettura degli oggetti, la loro creazione,registrazione e
 * distruzione.
 *
 * @author TTT
 */
public class InputObject {

    private InputObject() {
    }

    /**
     * Chiede la posizione all'utente.
     *
     * @return La posizione inserita.
     */
    public static Position readPosition() {
        Double d1, d2;
        printOut("Definisci la posizione:", true, false);
        GeneralFormatter.incrementIndents();
        d1 = InputHandler.getIstance().readDouble("X:");
        d2 = InputHandler.getIstance().readDouble("Y:");
        GeneralFormatter.decrementIndents();
        return new Position(d1, d2);
    }

    /**
     * Crea un nuovo corpo celeste chiedendo i dati in input all'utente.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     * @return Il nuovo corpo celeste, potrebbe non essere stato registrato.
     */
    public static ICelestial readCelestial(GestioneSistema gs) {
        printOut("Che corpo celeste vuoi inserire?:\n\t[1] Pianeta" + (gs.atLeastOnePlanet() ? "\n\t[2] Luna" : "") + "\nInserire il numero corrispettivo all'operazione desiderato (oppure * per annullare):", true, false);
        GeneralFormatter.incrementIndents();
        Optional<Integer> op;
        do {
            op = InputHandler.getIstance().readInteger("Operazione :", true, "*");
            if (!op.isPresent()) {
                GeneralFormatter.decrementIndents();
                return null;
            }
        } while ((op.get() != 1 && (gs.atLeastOnePlanet() ? op.get() != 2 : true)));
        String name = InputHandler.getIstance().readString("Inserisci il nome del corpo (premere invio per lasciare vuoto):");
        Double mass = InputHandler.getIstance().readDouble("Inserisci la massa del corpo (10^15 ton.):");
        Position p = readPosition();
        Boolean b = InputHandler.getIstance().readBoolean("La posizione è assoluta? (relativa allo 0 del sistema, altrimenti relativa al corpo celeste padre) ");
        ICelestial parent = null;
        ICelestial c = null;
        RegistrableEntry re = null;
        switch (op.get()) {
            case 1:
                Planet pl = new Planet(name, mass, p);
                re = pl;
                c = pl;
                break;
            case 2:
                Moon mn = new Moon(name, mass, p);
                re = mn;
                c = mn;
                GeneralFormatter.incrementIndents();
                parent = celestialLookup("Su che pianeta orbita?", CelestialType.PIANETA);
                GeneralFormatter.decrementIndents();
                break;
        }
        GeneralFormatter.decrementIndents();
        if (re != null) {
            if (!gs.maximumReached(parent)) {
                gs.addElementTo(parent, c, b);
                String msg = re.isRegistered() ? "Elemento registrato con successo: [ID:" + re.getID() + "] " + c : "Non è stato possibile registrare l'elemento.";
                System.out.println("\n\n");
                printOut(msg, true, !re.isRegistered());
                //GeneralFormatter.incrementIndents();
                printOut(OutputPicker.getIstance().getOnCreate(), true, false);
                //GeneralFormatter.decrementIndents();
            } else {
                printOut("Non è stato possibile registrare l'elemento: il limite massimo è stato raggiunto.", true, true);
            }
        } else {
            printOut("Non è stato possibile registrare l'elemento.", true, true);
        }

        return c;
    }

    /**
     * Distrugge un corpo celestiale.Se il corpo ha altri corpi celestiali sulle
     * sue orbite vengono distrutti.
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     * @return Il corpo distrutto.
     */
    public static ICelestial destroyCelestial(GestioneSistema gs) {
        ICelestial celestialLookup = InputQuery.celestialLookup("Che corpo celeste vuoi eliminare?");
        if (celestialLookup != null) {
            Boolean confirm = InputHandler.getIstance().readBoolean("Sei certo di voler eliminare " + celestialLookup + "? Tutti i corpi celesti orbitanti attorno a lui verranno distrutti.");
            if (confirm) {
                celestialLookup.destroy();
                //GeneralFormatter.incrementIndents();
                printOut(OutputPicker.getIstance().getOnDelete(), true, false);
                //GeneralFormatter.decrementIndents();
            }
        }
        return celestialLookup;
    }

    /**
     * Genera il sistema chiedendo i dati base e se si vuolde creare la stella
     * oppure farla generare al programma.
     *
     * @return
     */
    public static GestioneSistema readSistem() {
        printOut("Creazione guidata di un nuovo sistema: ", true, false);
        GeneralFormatter.incrementIndents();
        printOut("-Scelta del nome del sistema: ", true, false);
        GeneralFormatter.incrementIndents();
        String sys_name = InputHandler.getIstance().readString("Nome (premere invio per lasciare vuoto): ");
        GeneralFormatter.decrementIndents();
        Boolean to_create = InputHandler.getIstance().readBoolean("-Vuoi creare la stella? Altrimenti viene automaticamente generata. ");
        if (to_create) {
            printOut("Creazione della stella del sistema (unica): ", true, false);
            GeneralFormatter.incrementIndents();
            String star_name = InputHandler.getIstance().readString("Nome (premere invio per lasciare vuoto):");
            Double mass = InputHandler.getIstance().readDouble("Massa : ");
            Position pos = readPosition();
            Star.genIstance(star_name, mass, pos);
        }
        GeneralFormatter.decrementIndents();
        return new GestioneSistema(sys_name);
    }

    private static boolean isDS_Enabled = false;

    /**
     * Gestisce la Morte Nera
     *
     * @param gs Richiede l'accesso alla gestione del sistema.
     */
    public static void handleDeathStar(GestioneSistema gs) {
        if (!isDS_Enabled) {
            printOut("Abilita l'utilizzo della Morte Nera.", true, false);
            GeneralFormatter.incrementIndents();
            ICelestial ic = celestialLookup("Scegli il pianeta intorno a cui orbitare e la posizione:", CelestialType.PIANETA);
            if (ic != null) {
                Position p = readPosition();
                Boolean b = InputHandler.getIstance().readBoolean("La posizione è assoluta? (relativa allo 0 del sistema, altrimenti relativa al corpo celeste padre) ");
                MorteNera.getIstance().updatePosition(p);
                gs.addElementTo(ic, MorteNera.getIstance(), b);
                isDS_Enabled = true;
                GeneralFormatter.decrementIndents();
                printOut("La Morte Nera è ora nel sistema.", true, false);
            } else {
                GeneralFormatter.decrementIndents();
            }
        } else {
            printOut("Scegli come comandare la stazione spaziale.", true, false);
            GeneralFormatter.incrementIndents();
            printOut("[1] Cambia posizione.", true, false);
            printOut("[2] Distruggi pianeta", true, false);
            printOut("Inserire il numero corrispettivo all'operazione desiderato (oppure * per annullare):", true, false);
            GeneralFormatter.incrementIndents();
            Optional<Integer> op;
            do {
                op = InputHandler.getIstance().readInteger("Operazione :", true, "*");
                if (!op.isPresent()) {
                    GeneralFormatter.decrementIndents();
                    GeneralFormatter.decrementIndents();
                    return;
                }
            } while (op.get() != 1 && op.get() != 2);
            switch (op.get()) {
                case 1:
                    printOut("Aggiorna la posizione:", true, false);
                    Position p = readPosition();
                    MorteNera.getIstance().updatePosition(p);
                    GeneralFormatter.decrementIndents();
                    GeneralFormatter.decrementIndents();
                    break;
                case 2:
                    ICelestial ic = celestialLookup("Scegli il corpo celeste da distruggere:");
                    if (ic != null && ic != MorteNera.getIstance()) {
                        MorteNera.getIstance().toDeath(ic);
                        GeneralFormatter.decrementIndents();
                        GeneralFormatter.decrementIndents();
                        System.out.println("\n\n");
                        printOut(OutputPicker.getIstance().getOnDelete(), true, false);
                    }
                    break;
            }
        }
    }
}
