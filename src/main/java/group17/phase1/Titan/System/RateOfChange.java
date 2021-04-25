package group17.phase1.Titan.System;

import group17.phase1.Titan.Interfaces.RateInterface;
import group17.phase1.Titan.Interfaces.Vector3dInterface;
import group17.phase1.Titan.Physics.Math.Vector3D;

import java.util.ArrayList;
import java.util.List;

import static group17.phase1.Titan.Main.simulation;

public class RateOfChange implements RateInterface {
    List<Vector3dInterface> vel;

    public RateOfChange() {
        this.vel = new ArrayList<>();
    }

    public RateInterface state0() {
        if (this.vel.size() != 0)
            this.vel = new ArrayList<>();
        for (int i = 0; i < simulation.system().systemState().getPositions().size(); i++) {
            this.vel.add(simulation.system().getCelestialBodies().get(i).getVectorVelocity());
        }
        return this;
    }

    public void setVel(List<Vector3dInterface> vel) {
        this.vel = vel;
    }

    @Override
    public List<Vector3dInterface> getVelocities() {
        return this.vel;
    }

    @Override
    public RateInterface sub(int scalar)
    {
        if (this.vel.size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.vel.size(); i++) {
            this.vel.set(i, this.vel.get(i).sub(new Vector3D(scalar,scalar,scalar)));
        }
        return this;
    }

    @Override
    public RateInterface copy(RateInterface tobeCloned) {
        RateInterface s = new RateOfChange();
        for (Vector3dInterface v : tobeCloned.getVelocities()) {
            s.getVelocities().add(v.clone());
        }
        return s;    }

    @Override
    public RateInterface multiply(double scalar) {
        if (this.vel.size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.vel.size(); i++) {
            this.vel.set(i, this.vel.get(i).mul(scalar));
        }
        return this;
    }

    @Override
    public RateInterface sub(double scalar) {
        if (this.vel.size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.vel.size(); i++) {
            this.vel.set(i, this.vel.get(i).div(scalar));
        }
        return this;    }

    @Override
    public RateInterface add(RateInterface tobeAdded) {
        if (this.vel.size() == 0)
            throw new RuntimeException(" Nothing to add ");

        for (int i = 0; i < this.vel.size(); i++) {
            this.vel.set(i, this.vel.get(i).add(tobeAdded.getVelocities().get(i)));
        }
        return this;
    }

    @Override
    public RateInterface sumOf(RateInterface... states)
    {
        for (int i = 0; i < states[0].getVelocities().size(); i++) {
            Vector3dInterface velsum = this.getVelocities().get(i);

            for (RateInterface r : states) {
                velsum = velsum.add(r.getVelocities().get(i));
            }
            this.getVelocities().set(i, velsum);
        }
        return this;
    }

    @Override
    public RateInterface div(int scalar) {
        if (this.vel.size() == 0)
            throw new RuntimeException(" Nothing to multiply ");

        for (int i = 0; i < this.vel.size(); i++) {
            this.vel.set(i, this.vel.get(i).div(scalar));
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i< vel.size(); i++)
        {
            s.append(simulation.system().getCelestialBodies().get(i).toString());
            s.append(" : " );
            s.append(this.vel.get(i).toString()).append("\n");
        }
        return vel.toString();
    }
}
