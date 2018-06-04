import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        Network net = new Network(2,2,2,2);
        System.out.println(net);
        System.out.println();

        Network net2 = net;

        net.mutate(.5,100);

        System.out.println(net);
        System.out.println();

        System.out.println(net2);

    }
}
