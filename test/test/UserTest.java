package test;

import org.junit.Test;
import module.*;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * Test the method checkBudget(Request) of the User class.
     */
    @Test
    public void testCheckBudget() {
        User user = new Student(4000);
        Request request = new Request(user);
        request.setPriceOfRequest(4000);
        assertEquals(true, user.checkBudget(request));
        assertEquals(0, user.getBudget(), 0.000001);
        user = new Student(4000);
        request = new Request(user);
        request.setPriceOfRequest(4001);
        assertEquals(false, user.checkBudget(request));

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
}