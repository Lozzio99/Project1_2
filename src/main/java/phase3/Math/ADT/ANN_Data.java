package phase3.Math.ADT;

public class ANN_Data {
    private final double[] input, output;

    public ANN_Data(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }
}
