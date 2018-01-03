package module;

public class Random {
    /**
     * This methods enable us to generate random numbers with a
     * exponential probability.
     * @param border
     * @param parameter
     * @return
     */
    public static double exponentialProbability(int border, int parameter){
        int lambda = parameter;
        return (Math.log(1 - Math.random())/(-lambda)) * border + 1;
    }
}
