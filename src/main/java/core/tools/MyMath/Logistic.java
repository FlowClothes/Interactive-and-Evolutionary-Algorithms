package core.tools.MyMath;

public class Logistic
{
    //Logistic映射的初始值，设定为一个0到1之间的随机double
    private static double initNumber = 0;

    public static void setInitNumber()
    {
        do
        {
            initNumber = RandomGenerator.nextDouble();
        }
        while (initNumber == 0);
    }

    public static double getInitNumber()
    {
        return initNumber;
    }

    public static double getLogistic(int generation)
    {
        if (initNumber == 0)
        {
            return -1;
        }

        if (generation == 1)
        {
            return initNumber;
        }

        return 4 * getLogistic(generation - 1) * (1 - getLogistic(generation - 1));
    }
}
