package group17.phase1.Titan.Physics.Bodies;


import group17.phase1.Titan.Physics.Math.Vector3D;

import java.awt.*;

public class Planet extends CelestialBody
{
    PlanetsEnum planet;

    public Planet(PlanetsEnum planet){
        this.planet = planet;
    }

    @Override
    public String toString() {
        return planet.name();
    }

    @Override
    public void initProperties() {
        switch (planet) {
            case MERCURY -> {
                this.setMASS(3.302e+23);
                this.setRADIUS(2.4397e6);
                this.setColour(new Color(92, 87, 87, 233));
                this.setVectorLocation(new Vector3D(6.047855986424127e+06, -6.801800047868888e+10, -5.702742359714534e+09));
                this.setVectorVelocity(new Vector3D(3.892585189044652e+04, 2.978342247012996e+03, 3.327964151414740e+03));
            }
            case VENUS -> {
                this.setMASS(4.8685e24);
                this.setRADIUS(6.0518e6);
                this.setColour(new Color(172, 75, 1, 232));
                this.setVectorLocation(new Vector3D(-9.435345478592035e+10, 5.350359551033670e+10, 6.131453014410347e+09));
                this.setVectorVelocity(new Vector3D(-1.726404287724406e+04, -3.073432518238123e+04, 5.741783385280979e-04));
            }
            case EARTH -> {
                this.setMASS(5.97219e24);
                this.setRADIUS(6.371e6);
                this.setColour(new Color(16, 54, 167, 232));
                this.setVectorLocation(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06));
                this.setVectorVelocity(new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01));
            }
            case MARS -> {
                this.setMASS(6.4171e23);
                this.setRADIUS(3.3895e6);
                this.setColour(new Color(173, 77, 39, 237));
                this.setVectorLocation(new Vector3D(-3.615638921529161e+10, -2.167633037046744e+11, -3.687670305939779e+09));
                this.setVectorVelocity(new Vector3D(2.481551975121696e+04, -1.816368005464070e+03, -6.467321619018108e+02));
            }
            case JUPITER -> {
                this.setMASS(1.89813e27);
                this.setRADIUS(6.9911e7);
                this.setColour(new Color(234, 161, 71, 228));
                this.setVectorLocation(new Vector3D(1.781303138592153e+11, -7.551118436250277e+11, -8.532838524802327e+08));
                this.setVectorVelocity(new Vector3D(1.255852555185220e+04, 3.622680192790968e+03, -2.958620380112444e+02));
            }
            case SATURN -> {
                this.setMASS(5.6834e26);
                this.setRADIUS(5.8232e7);
                this.setColour(new Color(165, 134, 107, 208));
                this.setVectorLocation(new Vector3D(6.328646641500651e+11, -1.358172804527507e+12, -1.578520137930810e+09));
                this.setVectorVelocity(new Vector3D(8.220842186554890e+03, 4.052137378979608e+03, -3.976224719266916e+02));
            }
            case URANUS -> {
                this.setMASS(8.6813e25);
                this.setRADIUS(2.5362e7);
                this.setColour(new Color(201, 238, 238, 226));
                this.setVectorLocation(new Vector3D(2.395195786685187e+12, 1.744450959214586e+12, -2.455116324031639e+10));
                this.setVectorVelocity(new Vector3D(-4.059468635313243e+03, 5.187467354884825e+03, 7.182516236837899e+01));
            }
            case NEPTUNE -> {
                this.setMASS(1.02413e26);
                this.setRADIUS(2.4622e7);
                this.setColour(new Color(0, 71, 179, 240));
                this.setVectorLocation(new Vector3D(4.382692942729203e+12, -9.093501655486243e+11, -8.227728929479486e+10));
                this.setVectorVelocity(new Vector3D(1.068410720964204e+03, 5.354959501569486e+03, -1.343918199987533e+02));
            }
        }
    }


    public enum PlanetsEnum {
        MERCURY,
        VENUS,
        EARTH,
        MARS,
        JUPITER,
        SATURN,
        URANUS,
        NEPTUNE
    }

}
