import java.awt.*;

public class Network
{
    Neuron[][] hiddenLayers;
    Neuron[] outputLayer;

    public Network(int layers, int layerSize, int inSize, int outSize)
    {
        hiddenLayers = new Neuron[layers][layerSize];
        outputLayer = new Neuron[outSize];

        for(int y = 0; y < layerSize; y++)
        {
            hiddenLayers[0][y] = new Neuron(inSize);
        }

        for(int x = 1; x < layers; x++)
        {
            for(int y = 0; y < layerSize; y++)
            {
                hiddenLayers[x][y] = new Neuron(layerSize);
            }
        }

        for(int y = 0; y < outSize; y++)
        {
            outputLayer[y] = new Neuron(layerSize);
        }
    }

    public Network(Neuron[][] hiddenLayers, Neuron[] outputLayer)
    {
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
    }

    private double getNeuronOutput(Point coord, double[] input)
    {
        double output = 0;

        if(coord.x == 0)
        {
            for(int y = 0; y < input.length; y++)
            {
                output += input[y] * hiddenLayers[coord.x][y].weights[y];
            }
            output += hiddenLayers[coord.x][coord.y].bias;
            return output;
        }

        if(coord.x == hiddenLayers.length)
        {
            for(int y = 0; y < hiddenLayers[coord.x-1].length; y++)
            {
                output += getNeuronOutput(new Point(coord.x-1,y), input) * outputLayer[coord.y].weights[y];
            }
            output += outputLayer[coord.y].bias;
            return output;
        }

        for (int y = 0; y < hiddenLayers[coord.x-1].length; y++)
        {
            output += getNeuronOutput(new Point(coord.x - 1, y), input) * hiddenLayers[coord.x][coord.y].weights[y];
        }

        output += hiddenLayers[coord.x][coord.y].bias;
        return output;
    }

    public double[] getTotalOutput(double[] input)
    {
        double[] output = new double[outputLayer.length];

        for(int y = 0; y < outputLayer.length; y++)
        {
            output[y] = getNeuronOutput(new Point(hiddenLayers.length,y), input);
        }

        return output;
    }

    public String toString()
    {
        String output = "";

        for(int x = 0; x < hiddenLayers.length; x++)
        {
            for(int y = 0; y < hiddenLayers[x].length; y++)
            {
                output += "Point:(" + x + "," + y + ") " + hiddenLayers[x][y].toString() + "\n";
            }
        }

        for(int y = 0; y < outputLayer.length; y++)
        {
            output += "Point:(out" + "," + y + ") " + outputLayer[y].toString();
            if(y != outputLayer.length-1)
            {
                output += "\n";
            }
        }

        return output;
    }

    public void mutate(double rate, double magnitude)
    {
        for(int x = 0; x < hiddenLayers.length; x++)
        {
            for (int y = 0; y < hiddenLayers[x].length; y++)
            {
                if (Math.random() < rate)
                {
                    if (Math.random() <= .5)
                    {
                        hiddenLayers[x][y].weights[(int) (Math.random() * hiddenLayers[x][y].weights.length)] *= ((Math.random() * magnitude) - magnitude);
                    }
                    else
                    {
                        hiddenLayers[x][y].bias *= ((Math.random() * magnitude) - magnitude);
                    }
                }
            }
        }
    }

    public static Network getBest(double[] input, double[] output, Network[] nets)
    {
        double[] differences = new double[nets.length];

        for(int i = 0; i < nets.length; i++)
        {
            double average = 0;

            for(int a = 0; a < output.length; a++)
            {
                average += output[a] - nets[i].getTotalOutput(input)[a];
            }
            differences[i] = average / output.length;
        }

        int pos = 0;
        for(int i = 1; i < differences.length; i++)
        {
            if(differences[1] < differences[pos])
            {
                pos = i;
            }
        }

        return nets[pos];
    }
}
