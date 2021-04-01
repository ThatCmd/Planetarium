/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable;

import java.util.LinkedList;
import planetarium.contents.celestials.Star;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.questionable.interfaces.CalcSupport;

/**
 * Classe di metodi statici che permettono il calcolo tramite i corpi celesti.
 *
 * @author TTT
 */
public class Calculator {

    /**
     * Valore massimo di errore ammesso.
     */
    public static final double THRESHOLD = 0.000001;

    private Calculator(){
        
    }
    
    /**
     * Calcola le masse pesate.
     *
     * @param ic Il corpo da controllare (e la sua orbita).
     * @return un array di 3 componenti: La massa totale, la massa lungo l'asse
     * X e una lungo Y.
     * @deprecated Da sostituire con un metodo che faccia utilizzo
     * dell'interfaccia {@link CalcSupport}
     * @see
     * Calculator#recalcNeededValues(planetarium.contents.celestials.interfaces.ICelestial)
     */
    @Deprecated
    public static double[] calcMassaPesata(ICelestial ic) {
        double sommaMasse = ic.getMass();
        double sommaX = ic.getMass() * ic.getPosition().getX();
        double sommaY = ic.getMass() * ic.getPosition().getY();
        if (ic.getOnOrbit().size() > 0) {
            for (ICelestial sic : ic.getOnOrbit()) {
                double[] triplet = calcMassaPesata(sic);
                sommaMasse += triplet[0];
                sommaX += triplet[1];
                sommaY += triplet[2];
            }
        }
        return new double[]{sommaMasse, sommaX, sommaY};
    }

    /**
     * Calcola la somma delle masse solo se ci sono state delle modifiche al
     * sistema.
     *
     * @param ic Il corpo celeste di cui bisogna ricalcolare i valori.
     * @return 0 se il corpo celeste passato è nullo, altrimenti il valore
     * ricalcolato.
     */
    public static double recalcNeededValues(ICelestial ic) {
        if (ic != null) {
            double final_mass = ic.getMass();
            if (ic.isToBeReloaded()) {
                Position final_wpos = Position.multiply(ic.getPosition(), final_mass);
                for (ICelestial sic : ic.getOnOrbit()) {
                    final_mass += recalcNeededValues(sic);
                    final_wpos = Position.sum(final_wpos, sic.getWeightedPosition());
                }
                ic.setCalculatedMass(final_mass);
                ic.setWeightedPosition(final_wpos);
            } else {
                /*for (ICelestial sic : ic.getOnOrbit()) {
                if (sic.isToBeReloaded()) {
                    final_mass += recalcNeededValues(sic);
                }
            }*/
                final_mass = ic.getCalculatedMass();
            }
            return final_mass;
        }
        return 0.0;
    }

    /**
     * Calcola la distanza tra le posizioni dei centri di due corpi celestiali.
     *
     * @param ic1 Il celestiale 1
     * @param ic2 Il celestiale 2
     * @return La distanza da percorrere.
     */
    public static double calcDistance(ICelestial ic1, ICelestial ic2) {
        return ic1 == null || ic2 == null ? 0.0 : Math.sqrt(Math.pow(ic1.getPosition().getX() - ic2.getPosition().getX(), 2.0) + Math.pow(ic1.getPosition().getY() - ic2.getPosition().getY(), 2.0));
    }

    /**
     * Calcola la lunghezza del percorso da fare dato un array ordinato (secondo
     * il percorso scelto) dei pianeti.
     *
     * @param ordered_list Lista ordinata di pianeti
     * @return La somma delle distanze fatte a coppie di celesti.
     */
    public static double calcDistance(LinkedList<ICelestial> ordered_list) {
        double somma = 0.0;
        if (ordered_list != null) {
            for (int i = 0; i < ordered_list.size() - 1; i++) {
                somma += Calculator.calcDistance(ordered_list.get(i), ordered_list.get(i + 1));
            }
        }
        return somma;
    }

    /**
     * Calcola il raggio dell'orbita di un celeste intorno al suo corpo "padre".
     *
     * @param ic Il corpo celeste.
     * @return La distanza del corpo celeste dal padre, altrimenti 0.
     */
    public static double calcOrbitRadius(ICelestial ic) {
        if (ic != null && ic.getParent() != null) {
            ICelestial parent = ic.getParent();
            return Calculator.calcDistance(ic, parent);
        }
        return 0.0;
    }

    /**
     * Controlla se due corpi possono collidere tra loro. Due corpi collidono se
     * la loro distanza dalla stella è uguale.
     *
     * @param ic1 Il primo corpo celeste
     * @param ic2 Il secondo corpo celeste
     * @return {@code true} nel caso i due corpi potrebbero collidere.
     */
    public static boolean canCollide(ICelestial ic1, ICelestial ic2) {
        if (ic1 != null && ic2 != null) {
            if (ic1 == ic2 || ic2.getParent() == ic1 || ic1.getParent() == ic2) {
                return false;
            } else if (ic1.getParent() == ic2.getParent()) {
                return Math.abs(Calculator.calcDistance(ic1.getParent(), ic1) - Calculator.calcDistance(ic2.getParent(), ic2)) < THRESHOLD;
            } else {
                Star main = Star.getIstance();
                double d1_min;
                double d2_min;
                double d1_max;
                double d2_max;
                if (ic1.getType() == CelestialType.LUNA || ic1.getType() == CelestialType.MORTE_NERA) {
                    double p_t_s = Calculator.calcDistance(main, ic1.getParent());
                    double m_t_p = Calculator.calcDistance(ic1, ic1.getParent());
                    d1_min = Math.abs(p_t_s - m_t_p);
                    d1_max = Math.abs(p_t_s + m_t_p);
                } else {
                    d1_max = d1_min = Math.abs(Calculator.calcDistance(main, ic1));
                }
                if (ic2.getType() == CelestialType.LUNA || ic2.getType() == CelestialType.MORTE_NERA) {
                    double p_t_s = Calculator.calcDistance(main, ic2.getParent());
                    double m_t_p = Calculator.calcDistance(ic2, ic2.getParent());
                    d2_min = Math.abs(p_t_s - m_t_p);
                    d2_max = Math.abs(p_t_s + m_t_p);
                } else {
                    d2_max = d2_min = Math.abs(Calculator.calcDistance(main, ic2));
                }

                if (d2_min < d1_max && (d2_max < d1_min || d1_min < d2_max)) {
                    return (d1_min - d2_max) < 0;
                } else if (d1_min < d2_max && (d1_max < d2_min || d2_min < d1_max)) {
                    return (d2_min - d1_max) < 0;
                }

                return Math.abs(d1_min - d2_min) < THRESHOLD;
            }
        }
        return false;
    }

}
