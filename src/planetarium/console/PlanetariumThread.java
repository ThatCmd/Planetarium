/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console;

import static planetarium.console.GeneralFormatter.printOut;
import planetarium.console.output.Menu;

/**
 *
 * @author TTT
 */
public class PlanetariumThread {

    Menu m;

    public PlanetariumThread() {
        init();
    }

    private void init() {
        printOut("\tBENVENUTO IN : PLANETARIUM", true, true);
        consoleSpaces(2);
        printMenu();
    }

    private void printMenu() {
        printOut("Scegli un'operazione:", true, false);
        GeneralFormatter.incrementIndents();
        m = new Menu();
    }

    public Menu getMenu() {
        return m;
    }
    
    public void start(){
        m.paintMenu();
    }

    private void consoleSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
        }
    }

}
