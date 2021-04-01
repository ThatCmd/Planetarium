/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.input;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import static planetarium.console.GeneralFormatter.printOut;

/**
 * Gestisce la lettura dei dati in input.
 *
 * @author TTT
 */
public class InputHandler {

    private static InputHandler istance;
    private final Scanner input;

    private InputHandler() {
        input = new Scanner(System.in);
        input.useLocale(Locale.ENGLISH); // Si usa il punto e non la virgola.
    }

    public static InputHandler getIstance() {
        if (istance == null) {
            istance = new InputHandler();
        }
        return istance;
    }

    /**
     * Legge un double in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public double readDouble(String question) {
        return readDouble(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore double. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal giro (se e solo se {@code skippable = true}).
     * @return Può resistire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Double> readDouble(String question, boolean skippable, String skippable_keyword) {
        Double d = null;
        printOut(question + " ", false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    d = Double.parseDouble(in);
                } else {
                    d = input.nextDouble();
                }
                break;
            } catch (NoSuchElementException | NumberFormatException ex) {
                input.nextLine();
                printOut("Input non valido. Inserire di nuovo"
                        + (skippable ? "(Scrivere \"" + skippable_keyword + "\" per annullare)" : "")
                        + " :", false, true);
            }
        }
        input.nextLine();
        return Optional.ofNullable(d);
    }

    /**
     * Legge un intero in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public int readInteger(String question) {
        return readInteger(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore intero. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal giro (se e solo se {@code skippable = true}).
     * @return Può resistire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Integer> readInteger(String question, boolean skippable, String skippable_keyword) {
        Integer i = null;
        printOut(question + " ", false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    i = Integer.parseInt(in);
                } else {
                    i = input.nextInt();
                }
                break;
            } catch (NoSuchElementException | NumberFormatException ex) {
                input.nextLine();
                printOut("Input non valido. Inserire di nuovo"
                        + (skippable ? "(Scrivere \"" + skippable_keyword + "\" per annullare)" : "")
                        + " :", false, true);
            }
        }
        input.nextLine();
        return Optional.ofNullable(i);
    }

    /**
     * Legge un long in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public long readLong(String question) {
        return readLong(question, false, null).get();
    }

    /**
     * Chiede di inserire un valore long. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal giro (se e solo se {@code skippable = true}).
     * @return Può resistire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<Long> readLong(String question, boolean skippable, String skippable_keyword) {
        Long l = null;
        printOut(question + " ", false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    l = Long.parseLong(in);
                } else {
                    l = input.nextLong();
                }
                break;
            } catch (NoSuchElementException | NumberFormatException ex) {
                input.nextLine();
                printOut("Input non valido. Inserire di nuovo"
                        + (skippable ? "(Scrivere \"" + skippable_keyword + "\" per annullare)" : "")
                        + " :", false, true);
            }
        }
        input.nextLine();
        return Optional.ofNullable(l);
    }

    /**
     * Legge una stringa in input. L'operazione non è cancellabile.
     *
     * @param question La domanda che viene stampata in output.
     * @return il valore immesso.
     */
    public String readString(String question) {
        return readString(question, false, null).get();
    }

    /**
     * Chiede di inserire una stringa. Si può uscire dal ciclo tramite una
     * parola specifica.
     *
     * @param question Domanda iniziale di richiesta del dato.
     * @param skippable Deve essre {@code true} se la richiesta può essre
     * annullata, altrimenti {@code false}.
     * @param skippable_keyword La parola o lettera (o qualsiasi altra cosa) per
     * uscire dal giro (se e solo se {@code skippable = true}).
     * @return Può resistire il valore immesso oppure {@code null} nel caso
     * l'utente sia uscito dal l'immisione.
     */
    public Optional<String> readString(String question, boolean skippable, String skippable_keyword) {
        String s = null;
        printOut(question + " ", false, false);
        while (true) {
            try {
                if (skippable) {
                    String in = input.next();
                    if (in.startsWith(skippable_keyword)) {
                        break;
                    }
                    s = in;
                } else {
                    s = input.nextLine();
                }
                break;
            } catch (NoSuchElementException | NumberFormatException ex) {
                input.nextLine();
                printOut("Input non valido. Inserire di nuovo"
                        + (skippable ? "(Scrivere \"" + skippable_keyword + "\" per annullare)" : "")
                        + " :", false, true);
            }
        }
        //input.nextLine();
        return Optional.ofNullable(s);
    }

    /**
     * Legge un boolean. Se la stringa inizia con "y" o "s" allora ritorna
     * {@code true}, se inizia con "n" ritorna {@code false}.
     *
     * @param question La domanda.
     * @return Vero o Falso.
     */
    public boolean readBoolean(String question) {
        Boolean b = null;
        printOut(question + "[s/n]", false, false);
        while (true) {
            try {
                String in = input.nextLine().toLowerCase();
                if (in.startsWith("s")) {
                    b = true;
                    break;
                } else if (in.startsWith("n")) {
                    b = false;
                    break;
                }
            } catch (NoSuchElementException | NumberFormatException ex) {
                input.nextLine();
                printOut("Input non valido. Inserire di nuovo [s/n] :", false, true);
            }
        }
        return b;
    }

}
