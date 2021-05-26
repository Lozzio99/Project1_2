# Project1_2

Dke Group 17 Mission group17.phase1.group17.phase1.Titan

# MISSION TITAN

### Installation

Required Gradle versions: 7.0.2 . note if some problems arise,it could be that the -all distribution package is
necessary

### Required gradle-wrapper.properties

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-7.0.2-all.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

### Preferred gradle dependencies / testing task properties

```groovy
dependencies {
    implementation 'org.junit.jupiter:junit-jupiter:5.7.0'
    implementation group: 'au.com.bytecode', name: 'opencsv', version: '2.4'
    implementation group: 'org.apache.commons', name: 'commons-csv', version: '1.8'
    compileOnly 'org.jetbrains:annotations:20.1.0'
    testImplementation 'org.junit.jupiter:junit-jupiter:5.6.2'
}

test {
    useJUnitPlatform()
    testLogging {
        events 'passed', 'skipped', 'failed'
    }
    maxParallelForks 1
    minHeapSize "128m"
    maxHeapSize "512m"
    failFast true
    onOutput { descriptor, event -> logger.lifecycle("Test: " + descriptor + " \noutput: " + event.message )}
    finalizedBy jacocoTestReport
}

```

### Compiler

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="CompilerConfiguration">
    <bytecodeTargetLevel target="15" /> 
  </component>
</project>
```

# Usage

### Initialise the assist window and the simulation instance

```java
import group17.phase1.group17.phase1.Titan.Interfaces.SimulationInterface;

public class Main {

    public static volatile SimulationInterface simulation;
    public static UserDialogWindow userDialog = new UserDialogWindow();

    public static void main(String[] args) {
    }
}
```

### create a pre-defined Simulation
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

### set up a simulation

```java
@Override
public void init() {
        this.initReporter();   //first thing, will check all exceptions
        this.initSystem();  // before graphics and userDialog (clock, positions init, ...)
        this.initAssist();
        this.initGraphics();
        this.initUpdater();  //last thing, will start the simulation if it's the only one running
}

@Override
public void start() {
        this.setRunning();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(Executors.privilegedThreadFactory());
        service.scheduleWithFixedDelay(this::loop, 30, 9, MILLISECONDS);
}

@Override
public synchronized void loop() {
        if (this.running) {
            this.updateState();
            this.startGraphics();
            this.startReport();
            this.startAssist();
            if (!waiting()) {
                this.startUpdater();
                this.startSystem();
            }
        }
}
```

## UML Diagram

[UML](src/main/resources/Main.uml)

## ERRORS EVALUATION

By default, it is possible to allow the program to generate runtime live reports or txt or csv files. If selected during
the simulation process, the generated files will be collected in the resources/ErrorData folder, organised by solver and
step size used If selected during tests (by enabling SolversAccuracyTest.java) these files will be collected in the
test/java/resources/ErrorData folder,as by standard organization.

NOTE : the ErrorData folder MUST be present in both cases, if this options is selected NOTE : to allow the report
generation it is **_required_**  to let the simulation Clock to get to the **_first of the month at the exact time "00:
00:00"_**. Check out the implementation of [Clock.java](src/main/java/group17/Simulation/System/Clock.java) or stick to
step size exactly dividing a minute by a natural, integer number.

## Contributing

Group 17, who contributed at this project :

Dan Parii, Lorenzo Pompigna, Nikola Prianikov, Axel Rozental, Konstantin Sandfort, Abhinandan Vasudevan (phase 1 only)

