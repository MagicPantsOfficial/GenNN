import java.util.Arrays;

public class Neuron
{
    double[] weights;
    double bias;

    public Neuron(int inputSize)
    {
        weights = new double[inputSize];
        bias = (Math.random()*2) - 1;

        for(int x = 0; x < inputSize; x++)
        {
            weights[x] = (Math.random()*2) - 1;
        }
    }

    public String toString()
    {
        return "Weights:" + Arrays.toString(weights) + " Bias:" + bias;
    }
}
