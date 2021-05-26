# Project1_2
Dke Group 17 Mission group17.phase1.group17.phase1.Titan
# MISSION TITAN


## Installation
Required Gradle versions: 7.0.2 .

Use the package manager [gradle](https://gradle.org/install/) to install gradle.
*_If you work in IntelliJ IDEA, you don't need to install Gradle separately, IntelliJ IDEA does it for you_*.

Open a console (or a Windows command prompt) and run gradle -v to run gradle and display the version, e.g.:

```bash

$ gradle -v


------------------------------------------------------------
Gradle 7.0
------------------------------------------------------------

Build time:   2021-04-09 22:27:31 UTC
Revision:     ###################################

Kotlin:       1.4.31
Groovy:       3.0.7
Ant:          Apache Ant(TM) version 1.10.9 compiled on September 27 2020
JVM:          15.0.1 (Oracle Corporation 15.0.1+9-18)
OS:           Windows 10 10.0 amd64

```




## Usage

```java
import group17.phase1.group17.phase1.Titan.Interfaces.SimulationInterface;

public class Main {

    public static volatile SimulationInterface simulation;
    public static UserDialogWindow userDialog = new UserDialogWindow();

    public static void main(String[] args) {
    }
}


```

## create a pre-defined Simulation

 ```java
new AbstractMainMenu() {
@Override
public void startSimulation() {
        if (simulation == null) {
            Main.simulation = new Simulation();
            Main.simulation.setAssist(userAssist);  //optional
            Main.simulation.init();
            Main.simulation.start();
        }
    }
};
```

## UML Diagram

[UML](src/main/java/group17/phase1/group17.phase1.Titan/diagram.png)

## ERRORS EVALUATION

By default, it is possible to allow the program to generate runtime live reports or txt or csv files. If selected during
the simulation process, the generated files will be collected in the resources/ErrorData folder, organised by solver and
step size used If selected during tests (by enabling SolversAccuracyTest.java) these files will be collected in the
test/java/resources/ErrorData folder,as by standard organization.

NOTE : the ErrorData folder MUST be present in both cases, if this options is selected NOTE : to allow the report
generation it is *_required_* to let the simulation Clock to get to the *_first of the month at the exact time "00:00:
00"_*. Check out the implementation of "group17/Simulation/System/Clock.java" or stick to step size exactly dividing a
minute by a natural, integer number.

## Contributing

Group 17, who contributed at this project :

Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan (phase 1 only)

