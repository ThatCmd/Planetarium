/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console;

import static planetarium.console.GeneralFormatter.printOut;
import planetarium.console.output.Menu;
import planetarium.contents.system.GestioneSistema;

/**
 *
 * @author gabri
 */
public class PlanetariumThread {

    GestioneSistema gs;
    Menu m;

    public PlanetariumThread() {
        init();
    }

    private void init() {
        printOut("\tBENVENUTO IN : PLANETARIUM", true, true);
        consoleSpaces(2);
        printMenu();
    }
    
    private void printMenu(){
        printOut("Scegli un'operazione:", true, false);
        GeneralFormatter.incrementIndents();
        m = new Menu();
        m.paintMenu();
    }

    private void consoleSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
        }
    }

}
