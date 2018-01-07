package test;

import module.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimulationTest {

    private void reloadArray(){
        ShortQueue.getInstance().setRunningRequest(new ArrayList<>());
        Medium.getInstance().setRunningRequest(new ArrayList<>());
        Large.getInstance().setRunningRequest(new ArrayList<>());
        Huge.getInstance().setRunningRequest(new ArrayList<>());;
        Huge.getInstance().setWaitingRequest(new ArrayList<>());
        Huge.getInstance().setCoreAmountAvailable(Huge.getInstance().getMaxCore());
        Huge.getInstance().setWeekendCount(new ArrayList<>());
    }

    /**
     * @see Simulation #userListCreation
     */
    @Test
    public void testUserListCreation() {
        reloadArray();
        Simulation simulation = new Simulation();
        int researcherNb = 0, resGroup1 = 0, resGroup2 = 0, student = 0, it = 0;
        double budget1 =0, budget2 = 0, budgetStudent =0, budgetIT =0;
        List<User> users = simulation.userListCreation();
        for (User user : users){
            if(user.getUserType().equals("Researcher")){
                researcherNb++;
                Researcher researcher = (Researcher)user;
                if(researcher.getGroupName().equals("group1")) {
                    resGroup1++;
                    budget1 = researcher.getBudget();
                }
                if(researcher.getGroupName().equals("group2")){
                    resGroup2++;
                    budget2 = researcher.getBudget();
                }
            }
            if(user.getUserType().equals("Student")) {
                student++;
                budgetStudent = user.getBudget();
            }
            if(user.getUserType().equals("IT User")) {
                it++;
                budgetIT = user.getBudget();
            }
        }
        assertEquals(100, researcherNb);
        assertEquals(60, student);
        assertEquals(10, it);
        assertEquals(40, resGroup1);
        assertEquals(60, resGroup2);
        assertEquals(10000.0,budget1,0.00001);
        assertEquals(6000.0,budget2,0.00001);
        assertEquals(2000,budgetStudent,0.000001);
        assertEquals(3000,budgetIT,0.00001);
        Time.setEndWeekend(0);
        Time.setweekendPast(0);
    }

    /**
     * @see Simulation #treatOneRequest(Request)
     */
    @Test
    public void testTreatOneRequest(){
        reloadArray();
        Time.setCountWeeks(1);
        Simulation simulation = new Simulation(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0, 0, 0, 0);
        Request request1 = new Request(100, 0, 120, Medium.getInstance(), 50);
        Request request2 = new Request(500, 0, 480, Huge.getInstance(), 50);
        Request request3 = new Request(7000, 0, 240, Large.getInstance(), 50);
        request3.setUser(new Student(0));
        Request request4 = new Request(10100, 0, 480, Huge.getInstance(), 50);
        Request request5 = new Request(20161, 0, 120, Medium.getInstance(), 50);
        Request request6 = new Request(27000, 0, 120, Huge.getInstance(), 50);

        request5.setUser(new Student(0));

        simulation.treatOneRequest(request1);
        assertEquals(1,Medium.getInstance().getRunningRequest().size());
        simulation.treatOneRequest(request2);
        assertEquals(1,Huge.getInstance().getWaitingRequest().size());
        simulation.treatOneRequest(request3);
        assertEquals(1,Huge.getInstance().getRunningRequest().size());
        assertTrue(request3.getUser().getBudget() > 0);
        simulation.treatOneRequest(request4);
        assertEquals(1,Huge.getInstance().getWaitingRequest().size());
        simulation.treatOneRequest(request5);
        assertEquals(1,Medium.getInstance().getRunningRequest().size());
        assertEquals(1,Huge.getInstance().getRunningRequest().size());
        simulation.treatOneRequest(request6);
        assertEquals(1,Huge.getInstance().getRunningRequest().size());

    }

    /**
     * @see Simulation #printOutput
     */
    @Test
    public void testPrintOutput() {
        Simulation simulation = new Simulation();
        Path path = Paths.get("simulationResult.txt");
        File f = new File("simulationResult.txt");
        if(f.isFile()){
            try {
                Files.delete(path);
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file%n", path);
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }
        }
        assertFalse(f.isFile());
        simulation.printOutput();
        assertTrue(f.isFile());
    }

}
