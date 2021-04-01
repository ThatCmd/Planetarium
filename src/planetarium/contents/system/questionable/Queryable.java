/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.Registry;
import planetarium.contents.registry.abstracts.RegistrableEntry;

/**
 * Classe di metodi statici che permettono di trovare gli elementi nel
 * planetario.
 *
 * @author TTT
 */
public class Queryable {

    private Queryable() {
    }

    /**
     * Genera una {@link LinkedList} che parte dal corpo di partenza e arriva
     * fino al corpo di arrivo.
     *
     * @param from Il corpo di partenza
     * @param to Il corpo di arrivo
     * @return La lista ordinata di passaggi.
     */
    public static LinkedList<ICelestial> findPath(ICelestial from, ICelestial to) {
        LinkedList<ICelestial> to_ret = new LinkedList<>();
        if (from != null && to != null) {
            if (from == to) {
                to_ret.add(from);
                return to_ret;
            } else {
                getToCelestial(from, to, to_ret);
            }
        }
        return to_ret;
    }

    /**
     * Dalla lista generata dal metodo {@link Queryable#findPath(planetarium.contents.celestials.interfaces.ICelestial, planetarium.contents.celestials.interfaces.ICelestial)
     * } genera la stringa corrispondente.
     *
     * @param from Il corpo celeste da cui si parte.
     * @param to Il corpo celeste a cui si deve arrivare.
     * @return La stringa che rappresenta il percorso.
     */
    public static String getStrictPath(ICelestial from, ICelestial to) {
        LinkedList<ICelestial> findPath = findPath(from, to);
        StringBuilder sb = new StringBuilder();
        findPath.stream().forEach(ic -> sb.append(ic.getName()).append(" > "));
        sb.delete(sb.length() - 3, sb.length() - 1);
        return sb.toString();
    }

    /**
     * Restituisce una lista non modificabile di lune che ruotano intorno ad un
     * corpo celeste.
     *
     * @param ic Il corpo celeste intorno a cui orbitano le lune.
     * @return Lista di lune.
     */
    public static List<ICelestial> getMoons(ICelestial ic) {
        return searchByCelestialType(ic, CelestialType.LUNA);
    }

    /**
     * Restituisce una lista non modificabile di pianeti che ruotano intorno ad
     * un corpo celeste.
     *
     * @param ic Il corpo celeste intorno a cui orbitano i pianeti.
     * @return Lista di pianeti.
     */
    public static List<ICelestial> getPlanets(ICelestial ic) {
        return searchByCelestialType(ic, CelestialType.PIANETA);
    }

    private static List<ICelestial> searchByCelestialType(ICelestial ic, CelestialType ct) {
        ArrayList<ICelestial> ics = new ArrayList<>();
        if (ic != null && ct != null) {
            ic.getOnOrbit().stream().filter(t -> t.getType() == ct).forEach(t -> ics.add(t));
        }
        return Collections.unmodifiableList(ics);
    }

    /**
     * Dato un ID cerca di restituire un corpo celestiale. Nel caso non esista
     * l'elemento all'ID specificato o non estende l'interfaccia
     * {@link ICelestial} allora viene ritornato il valore {@code null}.
     *
     * @param ID L'ID di riferimento
     * @return L'istanza di un {@link ICelestial}, altrimenti {@code null}
     */
    public static ICelestial getCelestialByID(long ID) {
        RegistrableEntry re = Registry.getEntry(ID);
        if (re != null && re.getSubclassObject() instanceof ICelestial) {
            return (ICelestial) re.getSubclassObject();
        }
        return null;
    }

    /**
     * Dato un nome cerca di restituire un corpo celestiale. Nel caso non esista
     * l'elemento con il nome specificato o non estende l'interfaccia
     * {@link ICelestial} allora viene ritornato il valore {@code null}.
     *
     * @param name Il nome di censimento.
     * @return L'istanza di un {@link ICelestial}, altrimenti {@code null}
     */
    public static ICelestial getCelestialByName(String name) {
        RegistrableEntry re = Registry.getEntry(name);
        if (re != null && re.getSubclassObject() instanceof ICelestial) {
            return (ICelestial) re.getSubclassObject();
        }
        return null;
    }

    /**
     * Calcola il percorso partendo da un elemento e procedendo a ritroso.
     *
     * @param id L'ID dell'elemento
     * @return La stringa che rappresenta il percorso al corpo.
     */
    public static String getPathToCelestial(long id) {
        RegistrableEntry entry = Registry.getEntry(id);
        if (entry != null) {
            Object o = entry.getSubclassObject();
            if (o != null && o instanceof ICelestial) {
                ICelestial as_celestial = (ICelestial) o;
                return getToParentPath(as_celestial);
            }
        }
        return "Nessun dato al record : " + id;
    }

    /**
     * Calcola il percorso partendo da un elemento e procedendo a ritroso.
     *
     * @param ic Il corpo celeste da trovare
     * @return La stringa che rappresenta il percorso al corpo.
     */
    public static String getPathToCelestial(ICelestial ic) {
        if (ic != null) {
            return getToParentPath(ic);
        }
        return "Nessun corpo celeste trovato.";
    }

    private static String getToParentPath(ICelestial ic) {
        if (ic != null) {
            String s = ic.toString();
            if (ic.getParent() != null) {
                s = getToParentPath(ic.getParent()) + " > " + s;
            }
            return s;
        }
        return "";
    }

    private static void getToCelestial(ICelestial from, ICelestial to, LinkedList<ICelestial> path) {
        if (from != null && to != null && path != null) {
            if (to == from) {
                path.add(from);
                return;
            }
            if (from.contains(to)) {
                path.add(from);
                getToCelestial(from.getChild(to).get(), to, path);
            } else {
                path.add(from);
                getToCelestial(from.getParent(), to, path);
            }
        }
    }

}
