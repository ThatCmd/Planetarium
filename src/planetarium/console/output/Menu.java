/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.output;

import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import planetarium.console.GeneralFormatter;
import planetarium.console.input.InputHandler;
import planetarium.console.input.InputObject;
import planetarium.console.input.InputQuery;
import planetarium.console.output.helper.BasicPair;
import planetarium.console.output.helper.FutureMenuAction;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.Registry;
import planetarium.contents.system.GestioneSistema;

/**
 * Gestisce il menù principale.
 *
 * @author TTT
 */
public class Menu {

    private static final ResourceBundle menu_bundle = ResourceBundle.getBundle("planetarium/resources/i18n/menu_bundle");

    ArrayList<BasicPair<String, FutureMenuAction>> menu = new ArrayList<>();
    ArrayList<FutureMenuAction> to_execute_later = new ArrayList<>();

    GestioneSistema gs;
    private boolean death_star_unlocked = false;
    private int death_star_pos = -1;

    public Menu() {
        init();
    }

    private void init() {
        addOption(menu_bundle.getString("exit"), () -> {
            System.exit(0);
        });
        addOption(menu_bundle.getString("help"), () -> {
            Help.getIstance().printHelp();
        });
        addOption(menu_bundle.getString("sys_gen"), () -> {
            gs = InputObject.readSistem();
            if (gs != null) {
                to_execute_later.add(() -> {
                    menu.remove(2);
                    addAllBasicOptions();
                });
            }
        });
    }

    /**
     * Aggiunge tutte le opzioni iniziali al menù,le altre vengono sbolccate e/o
     * bloccate seguendo dei criteri. Ad esempio:
     * <p>
     * La possibilità di usare la Morte Nera si sblocca solo dopo aver creato
     * per la prima volta una luna.<p>
     * La possibilità di creare una luna si sblocca solo dopo aver aggiunto un
     * pianeta.
     */
    private void addAllBasicOptions() {
        addOption(menu_bundle.getString("add_celestial"), () -> {
            ICelestial to_check = InputObject.readCelestial(gs);
            if (!death_star_unlocked && to_check != null && to_check.getType() == CelestialType.LUNA) {
                System.err.println(menu_bundle.getString("ds_unlocked"));
                death_star_unlocked = true;
                death_star_pos = menu.size();
                addOption(menu_bundle.getString("death_star"), () -> {
                    InputObject.handleDeathStar(gs);
                });
            }
        });
        addOption(menu_bundle.getString("rem_celestial"), () -> {
            ICelestial destroyedCelestial = InputObject.destroyCelestial(gs);
            if (destroyedCelestial != null && destroyedCelestial.getType() == CelestialType.MORTE_NERA) {
                to_execute_later.add(() -> {
                    menu.remove(death_star_pos);
                });
            }
        });
        addOption(menu_bundle.getString("show_moons"), () -> {
            InputQuery.getMoons(gs);
        });
        addOption(menu_bundle.getString("show_cel_hier"), () -> {
            InputQuery.hierarchy(gs);
        });
        addOption(menu_bundle.getString("show_cel_path"), () -> {
            InputQuery.bestPath(gs);
        });
        addOption(menu_bundle.getString("show_reg"), () -> {
            Registry.printRegistry();
        });
        addOption(menu_bundle.getString("show_sys"), () -> {
            TreeSystem ts = new TreeSystem();
            ts.printTree();
        });
        addOption(menu_bundle.getString("find_celestial"), () -> {
            ICelestial c = InputQuery.celestialLookup(menu_bundle.getString("find_cel_desc"));
            if (c != null) {
                System.out.println();
                GeneralFormatter.printOut(java.text.MessageFormat.format(menu_bundle.getString("found"), new Object[] {c.getName()}), true, false);
                GeneralFormatter.incrementIndents();
                GeneralFormatter.printOut(java.text.MessageFormat.format(menu_bundle.getString("mass_cel"), new Object[] {c.getMass()}), true, false);
                GeneralFormatter.printOut(java.text.MessageFormat.format(menu_bundle.getString("type_cel"), new Object[] {c.getType()}), true, false);
                GeneralFormatter.printOut(java.text.MessageFormat.format(menu_bundle.getString("pos_abs_cel"), new Object[] {c.getPosition()}), true, false);
                if (c.getType() != CelestialType.STELLA) {
                    GeneralFormatter.printOut(java.text.MessageFormat.format(menu_bundle.getString("app_orb_cel"), new Object[] {c.getParent()}), true, false);
                    GeneralFormatter.printOut(java.text.MessageFormat.format(menu_bundle.getString("pos_rel_cel"), new Object[] {gs.getGrid().getPositionRelativeToParent(c)}), true, false);
                }
                GeneralFormatter.decrementIndents();
            }
        });
        addOption(menu_bundle.getString("calc_sum_mass_pos"), () -> {
            InputQuery.calcSystemMass(gs);
        });
        addOption(menu_bundle.getString("collision_cel"), () -> {
            InputQuery.calcCollision(gs);
        });
    }

    /**
     * Aggiunge una nuova opzione al menu.
     *
     * @param option_message
     * @param action
     */
    public void addOption(String option_message, FutureMenuAction action) {
        if (option_message != null && action != null) {
            menu.add(new BasicPair<>(option_message, action));
        }
    }

    /**
     * Rimuove l'opzione dal menù all'indice specificato. L'indice parte da 1 e
     * non da 0.
     *
     * @param indice
     */
    public void removeOption(int indice) {
        if (indice > 0 && indice <= menu.size()) {
            menu.remove(indice - 1);
        }
    }

    /**
     * Stampa il menù e attende una risposta.
     */
    public void paintMenu() {
        GeneralFormatter.printOut(menu_bundle.getString("menu_title"), true, false);
        for (int i = 0; i < menu.size(); i++) {
            GeneralFormatter.printOut("[" + (i + 1) + "] " + menu.get(i).getKey(), true, false);
        }
        System.out.println();
        waitAnswer();
    }

    /**
     * Stampa N nuove linee per separare i testi.
     *
     * @param n Il numero di linee da stampare
     */
    private void consoleSpaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
        }
    }

    /**
     * Attende una risposta valida. Per valida si intende una risposta che
     * faccia parte dell'array {@link Menu#menu} per poi farla eseguire.
     */
    private void waitAnswer() {
        GeneralFormatter.incrementIndents();
        Optional<Integer> op;
        do {
            op = InputHandler.getIstance().readInteger(menu_bundle.getString("oper"), false, null);
        } while (op.get() < 1 || op.get() > menu.size());
        GeneralFormatter.decrementIndents();
        consoleSpaces(3);
        executeAt(op.get() - 1);
        consoleSpaces(5);
        paintMenu();
    }

    /**
     * Fa eseguire una {@link FutureMenuAction} associata ad una voce del menù.
     *
     * @param index Indice selezionato.
     */
    private void executeAt(int index) {
        BasicPair<String, FutureMenuAction> p = menu.get(index);
        p.getValue().onSelected();
        to_execute_later.forEach((f) -> f.onSelected());
        to_execute_later.clear();
    }
}
