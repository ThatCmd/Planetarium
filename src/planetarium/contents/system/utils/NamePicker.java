/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import planetarium.contents.celestials.enums.CelestialType;

/**
 *
 * @author gabri
 */
public class NamePicker {

    private final Stack<String> stelle = new Stack<>();
    private final Stack<String> pianeti = new Stack<>();
    private final Stack<String> lune = new Stack<>();

    private static NamePicker istance;

    private NamePicker() {
        init();
    }

    private void init() {
        try {
            InputStream stars = getClass().getResourceAsStream("/planetarium/resources/names/stelle");
            InputStream planets = getClass().getResourceAsStream("/planetarium/resources/names/pianeti");
            InputStream moons = getClass().getResourceAsStream("/planetarium/resources/names/lune");
            stelle.addAll(Arrays.asList(new String(stars.readAllBytes()).split("\n")));
            pianeti.addAll(Arrays.asList(new String(planets.readAllBytes()).split("\n")));
            lune.addAll(Arrays.asList(new String(moons.readAllBytes()).split("\n")));
            Collections.shuffle(lune);
            Collections.shuffle(pianeti);
            Collections.shuffle(stelle);
        } catch (IOException ioe) {

        }
    }

    public static NamePicker getIstance() {
        if (istance == null) {
            istance = new NamePicker();
        }
        return istance;
    }

    public String getName(CelestialType ct) {
        switch (ct) {
            default:
            case ASTEROIDE:
                return getRandomName();
            case LUNA:
                return lune.size() > 0 ? lune.pop() : getRandomName();
            case PIANETA:
                return pianeti.size() > 0 ? pianeti.pop() : getRandomName();
            case STELLA:
                return stelle.size() > 0 ? stelle.pop() : getRandomName();
            case SISTEMA:
                return "Sys:" + getRandomName();
            case MORTE_NERA:
                return "Death Star";
            case TESLA_ROADSTER:
                return "Tesla Roadster";
        }
    }

    private String getRandomName() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'A');
        return c + "-" + (int) (Math.random() * (5000 - 100 + 1) + 100);
    }

}
