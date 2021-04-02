/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium;

import java.util.Date;
import java.util.Scanner;
import planetarium.console.PlanetariumThread;
import planetarium.contents.celestials.Moon;
import planetarium.contents.celestials.Planet;
import planetarium.contents.celestials.Star;
import planetarium.contents.registry.Registry;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.questionable.Calculator;
import planetarium.contents.system.questionable.Queryable;

/**
 *
 * @author TTT
 */
public class Planetarium {

    public static void testGeneric() {
        GestioneSistema gs = new GestioneSistema(null);

        System.out.println("Creato il sistema:\t" + gs.getName());
        System.out.println("Stella del sistema:\t" + gs.getStar());

        Planet A = new Planet(null, 100, new Position(1, 2));
        Planet B = new Planet(null, 120, new Position(1, 4));
        Planet C = new Planet(null, 120, new Position(3, 6));

        Moon M = new Moon(null, 50, new Position(5, 4));
        Moon N = new Moon(null, 50, new Position(7, 6));

        gs.addElementTo(null, A, false);
        gs.addElementTo(null, B, false);

        gs.addElementTo(null, C, false);
        gs.addElementTo(C, M, false);
        gs.addElementTo(A, N, false);

        System.out.println("Per arrivare a " + A + " da " + M + " seguire il percorso:\t" + Queryable.getStrictPath(M, A));
        Registry.printRegistry();
    }

    public static void testMaximumExpectedLoad() {
        GestioneSistema gs = new GestioneSistema("Sistema di massimo");
        long t1 = new Date().getTime();
        for (int i = 0; i < 26000; i++) {
            Planet tmp = new Planet(null, (i + 1) * 10, new Position(i + 3, i + 3));
            gs.addElementTo(null, tmp, true);
            for (int j = 0; j < 5000; j++) {
                gs.addElementTo(tmp, new Moon(null, j % 2 + 1, new Position(i + j, i + j)), true);
            }
        }
        System.out.println("Add time: " + (new Date().getTime() - t1));

        t1 = new Date().getTime();
        System.out.println(Calculator.recalcNeededValues(Star.getIstance()));
        System.out.println(Star.getIstance().getWeightedPosition());
        System.out.println(new Date().getTime() - t1);
    }

    public static void testArnald() {
        Star s = Star.genIstance("Stella", 30, new Position(0, 0));
        GestioneSistema gs = new GestioneSistema("Sistema di prova");
        Planet pianeta1 = new Planet("Pianeta1", 5, new Position(0, -3));
        Planet pianeta2 = new Planet("Pianeta2", 7, new Position(3, 3));
        Moon luna1 = new Moon("Luna1", 1, new Position(-1, -4));
        Moon luna2 = new Moon("Luna2", 2, new Position(2, 3));
        Moon luna3 = new Moon("Luna3", 1, new Position(4, 4));

        gs.addElementTo(null, pianeta1, true);
        gs.addElementTo(null, pianeta2, true);
        gs.addElementTo(pianeta1, luna1, true);
        gs.addElementTo(pianeta2, luna2, true);
        gs.addElementTo(pianeta2, luna3, true);

        System.out.println("Il registro del sistema:\n");
        Registry.printRegistry();
        System.out.println(Calculator.canCollide(luna2, luna3));
    }

    static final Scanner input = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PlanetariumThread pt = new PlanetariumThread();
        //testArnald();
    }
}
