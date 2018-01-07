package test;

import module.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TimeTest {

    /**
     * Test of the method static checkTime(Request) of the Time class.
     */
    @Test
    public void testCheckTime() {
        Time.setCountWeeks(1);
        Request request = new Request(new Student(30));
        request.setSendTime(Time.FULLWEEK + 1.0);
        assertTrue(Time.checkTime(request));
        request.setSendTime(Time.FULLWEEK);
        assertFalse(Time.checkTime(request));
    }

    @Test
    public void testCheckWeekend(){
        Request request= new Request(Time.FULLWEEK + Time.CUTOFF, 0, 120, Medium.getInstance(), 40);
        Time.addCountWeeks();
        assertFalse(Time.checkWeekend(request));
        request.setSendTime(Time.FULLWEEK + Time.CUTOFF + 1);
        assertTrue(Time.checkWeekend(request));
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