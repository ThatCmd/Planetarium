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
 * Definizione del comportamento di una stella. Inoltre è permessa una singola
 * instanza che viene generata tramite il metodo {@link Star#genInstance(java.lang.String, double, planetarium.contents.system.Position)
 * }.
 *
 * @author TTT
 */
public class Star extends RegistrableEntry implements ICelestial {

    private static Star istance;

    private final ArrayList<ICelestial> celestials;
    private final double mass;
    private final String name;
    private Position position;
    private Position w_pos;

    private double calculated_mass = 0.0;
    private boolean to_recalculate_mass = false;

    private final RegistryListener my_listener = new RegistryListener() {
        @Override
        public void onElementRegistered(RegistrableEntry re) {
            Object o = re.getSubclassObject();
            if (o instanceof ICelestial) {
                to_recalculate_mass = true;
            }
        }

        @Override
        public void onElementRemoved(RegistrableEntry re) {
            Object o = re.getSubclassObject();
            if (o instanceof ICelestial) {
                ICelestial ic = (ICelestial) o;
                if (celestials.contains((ICelestial) ic)) {
                    celestials.remove(ic);
                    to_recalculate_mass = true;
                }
            }
        }
    };

    private Star(String name, double mass, Position position) {
        celestials = new ArrayList<>();
        if (mass < 0) {
            throw new BeSeriousException("La massa non può essere negativa.");
        }
        this.mass = mass;
        this.name = name == null || "".equals(name.trim()) ? NamePicker.getIstance().getName(CelestialType.STELLA) : name;
        this.position = position == null ? new Position(0, 0) : position;
        init();
    }

    private void init() {
        calculated_mass = mass;
        w_pos = Position.multiply(position, mass);
        setSubclassObject(this);
        Registry.registerEntry(this, name);
        RegistryEvent.getIstance().addListener(my_listener);
    }

    /**
     * Genera una singola istanza statica di una stella. Se un'istanza è già
     * stata creata ritorna quella precedente.
     *
     * @param name Il nome della stella
     * @param mass La massa del corpo
     * @param p La sua posizione nel sistema
     * @return La nuova (o vecchia) istanza della stella.
     */
    public static Star genIstance(String name, double mass, Position p) {
        if (istance == null) {
            istance = new Star(name, mass, p);
        }
        return istance;
    }

    /**
     * Ritorna l'istanza della stella
     *
     * @return L'unica istanza della stella.
     */
    public static Star getIstance() {
        if (istance == null) {
            throw new NullPointerException("La stella non è stata ancora definita.");
        }
        return istance;
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
        return null;
    }

    @Override
    public void setParent(ICelestial ic) {
    }

    @Override
    public CelestialType getType() {
        return CelestialType.STELLA;
    }

    @Override
    public void registerCelestial(Object ic) {
        if (ic != null && ic instanceof ICelestial) {
            ICelestial as_celestial = (ICelestial) ic;
            if (!celestials.contains(as_celestial) && as_celestial.getType() == CelestialType.PIANETA && ic instanceof RegistrableEntry) {
                RegistrableEntry as_registrable = (RegistrableEntry) ic;
                as_celestial.setParent(this);
                if (Registry.registerEntry(as_registrable, as_celestial.getName())) {
                    celestials.add(as_celestial);
                    to_recalculate_mass = true;
                }
            }
        }
    }

    @Override
    public void destroy() {
        RegistryEvent.getIstance().removeListener(my_listener);
        celestials.forEach(ICelestial::destroy);
        Registry.removeEntry(this);
    }

    @Override
    public String toString() {
        return name + " [" + getType().name() + "] ";
    }

    @Override
    public String getName() {
        return name;
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
    public boolean isToBeReloaded() {
        return to_recalculate_mass;
    }

    @Override
    public void setCalculatedMass(double calculated_mass) {
        if (calculated_mass > 0) {
            this.calculated_mass = calculated_mass;
            to_recalculate_mass = false;
        }
    }

    @Override
    public double getCalculatedMass() {
        return calculated_mass;
    }

    @Override
    public boolean contains(ICelestial ic) {
        return ic != null ? ic == this ? true : celestials.stream().filter(c -> c.contains(ic)).findFirst().isPresent() : false;
    }

    @Override
    public Optional<ICelestial> getChild(ICelestial ic) {
        return ic != null ? ic == this ? Optional.of(this) : celestials.stream().filter(c -> c.contains(ic)).findFirst() : Optional.empty();
    }

    @Override
    public void setWeightedPosition(Position p) {
        if (p != null && w_pos == p) {
            w_pos = p;
        }
    }

    @Override
    public Position getWeightedPosition() {
        return w_pos;
    }

}
