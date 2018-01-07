package test;

import org.junit.BeforeClass;
import org.junit.Test;
import module.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * Test the method checkBudget(Request) of the User class.
     */
    @Test
    public void testCheckBudget() {
        //Verify the implementation for a Student and IT User
        User user = new Student(4000);
        Request request = new Request(user);
        request.setPriceOfRequest(4000);
        assertEquals(true, user.checkBudget(request));
        assertEquals(0, user.getBudget(), 0.000001);
        user = new Student(4000);
        request = new Request(user);
        request.setPriceOfRequest(4001);
        assertEquals(false, user.checkBudget(request));
        //Verify the implementation for the Researcher class
        user = new Researcher(2000, 4000, "");
        request = new Request(user);
        request.setPriceOfRequest(6000);
        assertEquals(true, user.checkBudget(request));
        assertEquals(0, user.getBudget(), 0.000001);
        user = new Researcher(2000, 4000, "");
        request = new Request(user);
        request.setPriceOfRequest(6001);
        assertEquals(false, user.checkBudget(request));
    }

    @BeforeClass
    public static void setUpClass() throws Exception{
        Queue[] queues = {ShortQueue.getInstance(),Medium.getInstance(),Large.getInstance(),Huge.getInstance()};
        for (Queue queue : queues) {
            queue.setRunningRequest(new ArrayList<>());
            queue.setCoreAmountAvailable(queue.getMaxCore());
        }
        Huge.getInstance().setWaitingRequest(new ArrayList<>());
        Huge.getInstance().setWeekendCount(new ArrayList<>());
        Time.setCountWeeks(1);
    }
}