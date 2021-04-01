/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.console.output.helper;

/**
 *
 * @author gabri
 */
@FunctionalInterface
public interface FutureMenuAction {
    public void onSelected();
}
