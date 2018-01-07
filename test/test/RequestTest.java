package test;

import module.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Timer;

import static org.junit.Assert.*;

public class RequestTest {

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
    /**
     * Test of the checkSize(int) method of the Request class.
     */
    @Test
    public void testCheckSize() {
        Request request = new Request(100, 0, 600, ShortQueue.getInstance(), 10);
        request.setCoresNeeded(32);
        assertEquals(ShortQueue.getInstance(), request.checkSize());
        request.setCoresNeeded(33);
        assertEquals(Medium.getInstance(), request.checkSize());
        request.setCoresNeeded(206);
        assertEquals(Large.getInstance(), request.checkSize());
        request.setCoresNeeded(2048);
        assertEquals(Huge.getInstance(), request.checkSize());
        request.setCoresNeeded(-1);
        try {
            request.checkSize();
            fail("Should throw exception  when calculating the size with an incorrect number of cores");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test of the compare(object, object) method of the Request class, implemented by Comparable.
     */
    @Test
    public void testCompare() {
        Request request1 = new Request(new Student(30));
        request1.setSendTime(200);
        Request request2 = new Request(new Student(90));
        request2.setSendTime(199);
        assertEquals(1, request1.compare(request1, request2));
        assertEquals(-1, request1.compare(request2, request1));
        request2.setSendTime(200);
        assertEquals(0, request1.compare(request1, request2));
    }

    /**
     * Test of the method generatingProcessingTime() of the Request class.
     */
    @Test
    public void testGenerateProcessingTime(){
        //Test for a request with coresAmount linked to the Short queue
        Request request1 = new Request(new Student(30));
        request1.setQueueType(ShortQueue.getInstance());
        int t = 0;
        boolean test;
        while(t < 100) {
            request1.generateProcessingTime();
            test = (request1.getProcessingTime() > 0
                    && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime() + 1);
            assertEquals(true, test);
            t++;
        }
        t = 0;
        while(t < 100) {
            //Test for a request with coresAmount linked to the Medium queue
            request1.setQueueType(Medium.getInstance());
            request1.generateProcessingTime();
            test = (request1.getProcessingTime() > 0
                    && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime() + 1);
            assertEquals(true, test);
            t++;
        }
        t=0;
        while(t < 100) {
            //Test for a request with coresAmount linked to the Large queue
            request1.setQueueType(Large.getInstance());
            request1.generateProcessingTime();
            test = (request1.getProcessingTime() > 0
                    && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime() + 1);
            assertEquals(true, test);
            t++;
        }
        t=0;
        while (t<100) {
            //Test for a request with coresAmount linked to the Huge queue
            request1.setQueueType(Huge.getInstance());
            request1.generateProcessingTime();
            test = (request1.getProcessingTime() > 0
                    && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime() + 1);
            assertEquals(true, test);
            t++;
        }
    }

    /**
     * Test of the method refundPayment() of the Request class.
     */
    @Test
    public void testRefundPayment(){
        User user = new Student(4000);
        Request request = new Request(user);
        request.setPriceOfRequest(4000);
        request.refundPayment();
        assertEquals(8000.0, user.getBudget(), 0.01);
    }

    /**
     * Test of the method calcTurnaround() of Request class.
     */
    @Test
    public void testCalcTurnaround(){
        Request request = new Request(220, 50, 100, Medium.getInstance(), 50);
        assertEquals((100+50)/100.0, request.calcTurnaround(), 0.1);
        request.setProcessingTime(0);
        try {
            request.calcTurnaround();
            fail("Should send a ValueException");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test of the method finishBeforeWE() of the Request class.
     */
    @Test
    public void testFinishBeforeWE(){
        Request request = new Request(new Student(30));
        //Request send on the second friday of the simulation just before cutoff time
        request.setSendTime(Time.FULLWEEK + Time.CUTOFF - 60);
        //Verify that the program is working if a request finish before the cutoff time
        request.setProcessingTime(60);
        Time.addCountWeeks();
        assertTrue(request.finishBeforeWE());

        //Verify that the program is working if a request finish after the cutoff time
        request.setWaitTime(60);
        assertFalse(request.finishBeforeWE());

        //Verify the exception handling
        request.setSendTime(- 60);
        request.setProcessingTime(0);
        try {
            assertTrue(request.finishBeforeWE());
            fail("Should throw an exception if the finishing time isn't a possible value of a week time.");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * Test of the method finishDuringWE() of the Request class.
     */
    @Test
    public void testFinishDuringWE(){
        Request request = new Request(300,0,3840,Medium.getInstance(),50);
        assertTrue(request.finishDuringWE());
        request.setProcessingTime(3841);
        assertFalse(request.finishDuringWE());
    }
}