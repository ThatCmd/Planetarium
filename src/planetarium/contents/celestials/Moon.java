/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.celestials;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import planetarium.contents.celestials.enums.CelestialType;
import planetarium.contents.celestials.interfaces.ICelestial;
import planetarium.contents.registry.Registry;
import planetarium.contents.registry.abstracts.RegistrableEntry;
import planetarium.contents.registry.exception.BeSeriousException;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.utils.NamePicker;

/**
 * Definizione del comportamento di una luna.
 *
 * @author TTT
 */
public class Moon extends RegistrableEntry implements ICelestial {

    private ICelestial parent;
    private final double mass;
    private final String name;
    private Position position;
    private Position w_pos;

    /**
     * Inizializza una nuova luna. Di default non viene registrata, la
     * registrazione avviene quando la si inserisce su un orbita di un altro
     * corpo celeste.
     *
     * @param name Il nome della luna
     * @param mass La massa della luna
     * @param position La posizione assoluta al sistema
     */
    public Moon(String name, double mass, Position position) {
        if (mass < 0) {
            throw new BeSeriousException("La massa non può essere negativa.");
        }
        this.mass = mass;
        this.name = name == null || "".equals(name.trim()) ? NamePicker.getIstance().getName(CelestialType.LUNA) : name;
        this.position = position == null ? new Position(0, 0) : position;
        init();
    }

    private void init() {
        w_pos = Position.multiply(position, mass);
        setSubclassObject(this);
        updatePosition(position);
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public List<ICelestial> getOnOrbit() {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    @Override
    public ICelestial getParent() {
        return parent;
    }

    @Override
    public void setParent(ICelestial ic) {
        if (parent == null) {
            parent = ic;
        }
    }

    @Override
    public CelestialType getType() {
        return CelestialType.LUNA;
    }

    @Override
    public void registerCelestial(Object ic) {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " [" + getType().name() + "] ";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void updatePosition(Position position) {
        if (position != null && position != this.position) {
            this.position = position;
            w_pos = Position.multiply(this.position, mass);
        }
    }

    @Override
    public void destroy() {
        Registry.removeEntry(this);
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
        return mass;
    }

    @Override
    public boolean contains(ICelestial ic) {
        return ic == this;
    }

    @Override
    public Optional<ICelestial> getChild(ICelestial ic) {
        return Optional.ofNullable(contains(ic) ? this : null);
    }

    @Override
    public void setWeightedPosition(Position p) {
        w_pos = p;
    }

    @Override
    public Position getWeightedPosition() {
        return w_pos;
    }

}
