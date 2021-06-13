package phase3.Math.Forces;

import phase3.Math.ADT.Vector3D;
import phase3.Math.ADT.Vector3dInterface;
import phase3.Math.Functions.ODEFunctionInterface;
import phase3.Math.Noise.Noise;
import phase3.Math.Noise.Noise2D;
import phase3.Math.Noise.Noise3D;
import phase3.System.State.RateOfChange;

import static java.lang.StrictMath.abs;
import static phase3.Config.NOISE_DIMENSIONS;

public class WindModel implements WindInterface {

    private Noise noise;
    private static double mid;

    public WindModel() {

    }

    @Override
    public void init() {
        switch (NOISE_DIMENSIONS) {
            case BI_DIMENSIONAL -> {
                this.noise = new Noise2D();
                Noise.OCTAVE_COUNT = 8;
                Noise.SCALING_BIAS = 1.4f;
                Noise2D.randomizedAlgorithm();
            }
            case TRI_DIMENSIONAL -> {
                this.noise = new Noise3D();
                ((Noise3D) this.noise).setPerlinFreq(0.7);
                ((Noise3D) this.noise).setLacunarity(.812);
                ((Noise3D) this.noise).setPersistence(0.03);
                ((Noise3D) this.noise).setOctaveCount(30);
                mid = ((Noise3D) this.noise).getMaxValue() / 2.;
            }
        }

    }

    @Override
    public ODEFunctionInterface<Vector3dInterface> getWindFunction() {
        return (t, y) -> {
            Vector3dInterface v = y.get();
            double acc;
            if (NOISE_DIMENSIONS == Noise.NoiseDim.BI_DIMENSIONAL) {
                acc = this.noise.getValue(v.getX(), v.getY(), t);
            } else {
                acc = this.noise.getValue(v.getX(), v.getY(), t);
            }
            double dir = abs(acc - mid);
            acc = dir * (acc > mid ? 1 : -1);
            System.out.println(acc * 10);
            return new RateOfChange<>(new Vector3D(acc * 10, 0, 0));
        };
    }

    @Override
    public Noise getNoise() {
        return this.noise;
    }

}
