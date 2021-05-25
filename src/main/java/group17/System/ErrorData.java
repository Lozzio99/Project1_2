package group17.System;

import group17.Interfaces.StateInterface;
import group17.Interfaces.Vector3dInterface;
import org.jetbrains.annotations.Contract;

import java.util.LinkedList;
import java.util.List;


import static group17.Config.*;


public class ErrorData {
    private List<Vector3dInterface> positions, velocities;

    @Contract(pure = true)
    public ErrorData() {
    }

    public ErrorData(StateInterface state) {
        StateInterface copy = state.copy();
        this.positions = copy.getPositions();
        if (this.positions.size() >= 12) this.positions.remove(11);
        this.velocities = copy.getRateOfChange().getVelocities();
        if (this.velocities.size() >= 12) this.velocities.remove(11);
    }

    public ErrorData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = List.copyOf(positions);
        this.velocities = List.copyOf(velocities);
    }

    public ErrorData setData(List<Vector3dInterface> positions, List<Vector3dInterface> velocities) {
        this.positions = positions;
        this.velocities = velocities;
        if (this.positions.size() >= 12) {
            this.positions.remove(11);
            this.velocities.remove(11);//removing rocket , no data to compare for it
        }
        return this;
    }

    static int k =1;
    //TODO Check if better with exact value from NASA coords
    public static void checkOrder(ErrorData ph, ErrorData ph2){
        boolean value = false;
        List<Double> orderList = new LinkedList<>();
        // ph2 - exactph
        // ph - exactph
        for(int i = 0; i < ph.positions.size();i++){
            double numerator = (ph2.positions.get(i).dist(ORIGINAL_DATA[k].getPositions().get(i)));

            double denominator= (ph.positions.get(i).dist(ORIGINAL_DATA[k].getPositions().get(i)));

            orderList.add((Math.log10(numerator/denominator)/Math.log10(2))*-1);
        }



            System.out.println("********************************************");
            System.out.println(" AT STEP  : " + k);
            System.out.println(orderList);
            System.out.println("********************************************");



        k++;
    }

    public List<Vector3dInterface> getPositions() {
        return positions;
    }

    public List<Vector3dInterface> getVelocities() {
        return velocities;
    }

}
