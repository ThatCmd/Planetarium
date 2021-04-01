/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import planetarium.console.GeneralFormatter;

/**
 * Stampa l'Help, magari si può fare altro, però boh.
 *
 * @author TTT
 */
public class Help {

    private static final Help h = new Help();

    private Help() {

    }

    public static Help getIstance() {
        return h;
    }

    public void printHelp() {
        String create = getClass().getClassLoader().getResource("planetarium/resources/phrases/help").getFile();
        try (FileInputStream fr_h = new FileInputStream(new File(create))) {
            System.out.println(new String(fr_h.readAllBytes()));
        } catch (IOException ioe) {
            GeneralFormatter.printOut("E niente: non c'è nessuna pagina di help: " + ioe.getMessage(), true, true);
        }
    }

}
