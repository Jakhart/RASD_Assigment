import module.*;
import sun.awt.geom.AreaOp;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Simulation simulation = Simulation.input();
        Simulation simulation = new Simulation();
        List<Request> requests = simulation.userRequestList(simulation.userListCreation());
        simulation.simulationProcess(requests);
        simulation.printOutput();
        /*for (int i = 0; i < 20; i++) {
            System.out.println(exponentialProbability(1080, 10));
        }*/
    }
}
