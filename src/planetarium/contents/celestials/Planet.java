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
import planetarium.contents.registry.events.RegistryEvent;
import planetarium.contents.registry.events.RegistryListener;
import planetarium.contents.registry.exception.BeSeriousException;
import planetarium.contents.system.cartesian.Position;
import planetarium.contents.system.utils.NamePicker;

/**
 * Definizione del comportamento di un pianeta.
 *
 * @author TTT
 */
public class Planet extends RegistrableEntry implements ICelestial {

    private final ArrayList<ICelestial> celestials;
    private ICelestial parent;
    private final double mass;
    private final String name;
    private Position position;
    private Position w_pos;

    private double calculated_mass = 0.0;
    private boolean to_recalculate_mass = false;

    private final RegistryListener my_listener = new RegistryListener() {
        @Override
        public void onElementRegistered(RegistrableEntry re) {
        }

        @Override
        public void onElementRemoved(RegistrableEntry re) {
            Object o = re.getSubclassObject();
            if (o instanceof ICelestial) {
                ICelestial ic = (ICelestial) o;
                if (celestials.contains((ICelestial) ic)) {
                    to_recalculate_mass = true;
                    celestials.remove(ic);
                }
            }
        }
    };

    /**
     * Inizializza un nuovo pianeta. Di default non viene registrato, la
     * registrazione avviene quando lo si inserisce su un orbita di un altro
     * corpo celeste.
     *
     * @param name Il nome del pianeta
     * @param mass La massa del pianeta
     * @param position La posizione relativa al padre
     */
    public Planet(String name, double mass, Position position) {
        celestials = new ArrayList<>();
        if (mass < 0) {
            throw new BeSeriousException("La massa non può essere negativa.");
        }
        this.mass = mass;
        this.name = name == null || "".equals(name.trim()) ? NamePicker.getIstance().getName(CelestialType.PIANETA) : name;
        this.position = position == null ? new Position(0, 0) : position;
        init();
    }

    private void init() {
        calculated_mass = mass;
        w_pos = Position.multiply(position, mass);
        RegistryEvent.getIstance().addListener(my_listener);
        setSubclassObject(this);
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public List<ICelestial> getOnOrbit() {
        return Collections.unmodifiableList(celestials);
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
        return CelestialType.PIANETA;
    }

    @Override
    public void registerCelestial(Object ic) {
        if (ic != null && ic instanceof ICelestial) {
            ICelestial as_celestial = (ICelestial) ic;
            if (!celestials.contains(as_celestial) && (as_celestial.getType() == CelestialType.LUNA || as_celestial.getType() == CelestialType.MORTE_NERA) && ic instanceof RegistrableEntry) {
                RegistrableEntry as_registrable = (RegistrableEntry) ic;
                if (Registry.registerEntry(as_registrable, as_celestial.getName())) {
                    as_celestial.setParent(this);
                    celestials.add(as_celestial);
                    to_recalculate_mass = true;
                }
            }
        }
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
        RegistryEvent.getIstance().removeListener(my_listener);
        celestials.forEach(ICelestial::destroy);
        Registry.removeEntry(this);
    }

    @Override
    public boolean isToBeReloaded() {
        return to_recalculate_mass;
    }

    @Override
    public void setCalculatedMass(double calculated_mass) {
        this.calculated_mass = calculated_mass;
        to_recalculate_mass = false;
    }

    @Override
    public double getCalculatedMass() {
        return calculated_mass;
    }

    @Override
    public boolean contains(ICelestial ic) {
        return ic == this ? true : celestials.stream().filter(c -> c == ic).findFirst().isPresent();
    }

    @Override
    public Optional<ICelestial> getChild(ICelestial ic) {
        return ic == this ? Optional.of(this) : celestials.stream().filter(c -> c == ic).findFirst();
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
