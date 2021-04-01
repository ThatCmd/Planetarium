/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.output;

import planetarium.console.GeneralFormatter;
import planetarium.contents.celestials.Star;
import planetarium.contents.celestials.interfaces.ICelestial;

/**
 *
 * @author gabri
 */
public class TreeSystem {

    public TreeSystem() {

    }

    public void printTree() {
        GeneralFormatter.printOut("Struttura del sistema:", true, false);
        System.out.println(printTree(Star.getIstance()));
    }

    public String printTree(ICelestial ic) {
        if (ic == null) {
            GeneralFormatter.printOut("Nessun corpo celeste selezionato", true, true);
            return "";
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printCelestialOrbit(ic, indent, sb);
        return sb.toString();
    }

    private void printCelestialOrbit(ICelestial ic, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(ic);
        sb.append("\n");
        ic.getOnOrbit().forEach(c -> {
            if (c.getOnOrbit().size() > 0) {
                printCelestialOrbit(c, indent + 1, sb);
            } else {
                printCelestial(c, indent + 1, sb);
            }
        });
    }

    private void printCelestial(ICelestial ic, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(ic);
        sb.append("\n");
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

}
