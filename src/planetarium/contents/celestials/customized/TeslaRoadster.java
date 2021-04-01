/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.celestials.customized;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.abstracts.RegistrableEntry;
import planetarium.contents.system.cartesian.Position;

/**
 * Boh,così.
 *
 * @author TTT
 */
public class TeslaRoadster extends RegistrableEntry implements ICelestial {

    Position position;

    public TeslaRoadster(Position position) {
        this.position = position;
    }

    @Override
    public double getMass() {
        return 0.00000001;
    }

    @Override
    public List<ICelestial> getOnOrbit() {
        return new ArrayList<>();
    }

    @Override
    public ICelestial getParent() {
        return null;
    }

    @Override
    public void setParent(ICelestial ic) {

    }

    @Override
    public CelestialType getType() {
        return CelestialType.TESLA_ROADSTER;
    }

    @Override
    public void registerCelestial(Object ic) {

    }

    @Override
    public String getName() {
        return "Tesla Roadster";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isToBeReloaded() {
        return false;
    }

    @Override
    public void setCalculatedMass(double calculated_mass) {

    }

    @Override
    public double getCalculatedMass() {
        return getMass();
    }

    @Override
    public boolean contains(ICelestial ic) {
        return false;
    }

    @Override
    public Optional<ICelestial> getChild(ICelestial ic) {
        return Optional.empty();
    }

    @Override
    public void updatePosition(Position position) {
        this.position = position;
    }

    @Override
    public void setWeightedPosition(Position p) {
    }

    @Override
    public Position getWeightedPosition() {
        return position;
    }

}
