/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author gabri
 */
public class OutputPicker {

    private final Stack<String> created = new Stack<>();
    private final Stack<String> deleted = new Stack<>();

    private static OutputPicker istance;

    private OutputPicker() {
        init();
    }

    private void init() {
        try {
            InputStream create = getClass().getResourceAsStream("/planetarium/resources/phrases/on_create");
            InputStream delete = getClass().getResourceAsStream("/planetarium/resources/phrases/on_delete");
            created.addAll(Arrays.asList(new String(create.readAllBytes()).split("\n")));
            deleted.addAll(Arrays.asList(new String(delete.readAllBytes()).split("\n")));
        } catch (IOException ex) {

        }
    }

    public static OutputPicker getIstance() {
        if (istance == null) {
            istance = new OutputPicker();
        }
        return istance;
    }

    public String getOnDelete() {
        if (deleted.size() > 0) {
            String s = deleted.pop();
            deleted.add(0, s);
            return "[SYSTEM]: " + s;
        }
        return "[SYSTEM]: Corpo celeste distrutto!";
    }

    public String getOnCreate() {
        if (created.size() > 0) {
            String s = created.pop();
            created.add(0, s);
            return "[SYSTEM]: " + s;
        }
        return "[SYSTEM]: Corpo celeste creato!";
    }
}
