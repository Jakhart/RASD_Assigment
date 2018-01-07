package module;

import module.*;

import java.util.*;

/**
 * The class containing the executable method.
 */
public class MainClass {
    public static void main(String[] args) {
        Simulation simulation = Simulation.input();
        List<Request> requests = simulation.userRequestList(simulation.userListCreation());
        simulation.simulationProcess(requests);
        simulation.printOutput();
    }
}
