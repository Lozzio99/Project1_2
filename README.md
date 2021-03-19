# Project1_2
Dke Group 17 Mission group17.phase1.Titan
# MISSION TITAN


## Installation
Required Gradle versions: 3.3 and later.

Use the package manager [gradle](https://gradle.org/install/) to install gradle.
*_If you work in IntelliJ IDEA, you don't need to install Gradle separately, IntelliJ IDEA does it for you_*.

Open a console (or a Windows command prompt) and run gradle -v to run gradle and display the version, e.g.:

```bash

$ gradle -v

------------------------------------------------------------
Gradle 6.8.3
------------------------------------------------------------
```



## Usage

```java
import group17.phase1.Titan.Interfaces.SimulationInterface;

class Main{

    public static SimulationInterface simulation = new SimulationRepository();

    public static void main(String[] args) {
        simulation.initProbe();
        simulation.calculateTrajectories();
        simulation.initSimulation();
    }
}


```

## Simulation
 ```java
void simulation()
{
    simulation.solarSystem = new SolarSystem();
    simulation.solarSystemState = (StateInterface) solarSystem;
    simulation.rateOfChange = (RateInterface) solarSystem;

    ProbeSimulator probe = new ProbeSimulator();
    probe.init(simulation , simulation , simulation.solarSystemState);
    simulation.solarSystem.getCelestialBodies().add(probe);
}

```
Then follow instruction on the assist frame.    
Press (START SIMULATION) and . . enjoy :=)

##UML Diagram

[UML](src/main/java/group17/phase1/Titan/diagram.png)




## Contributing
Group 17,
who contributed at this project :


Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan

## License
none