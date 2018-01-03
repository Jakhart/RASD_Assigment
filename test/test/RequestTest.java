package test;

import module.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {

    /**
     * Test of the checkSize(int) method of the Request class.
     */
    @Test
    public void testCheckSize() {
        int cores = 32;
        assertEquals(ShortQueue.getInstance(), Request.checkSize(cores));
        cores = 33;
        assertEquals(Medium.getInstance(), Request.checkSize(cores));
        cores = 206;
        assertEquals(Large.getInstance(), Request.checkSize(cores));
        cores = 2048;
        assertEquals(Huge.getInstance(), Request.checkSize(cores));
        cores = -1;
        try {
            Request.checkSize(cores);
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
        Request request1 = new Request(new Student(30));
        request1.setQueueType(ShortQueue.getInstance());
        request1.generateProcessingTime();
        boolean test = (request1.getProcessingTime() > 0
                && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime());
        assertEquals(true, test);

        request1.setQueueType(Medium.getInstance());
        request1.generateProcessingTime();
        test = (request1.getProcessingTime() > 0
                && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime());
        assertEquals(true, test);

        request1.setQueueType(Large.getInstance());
        request1.generateProcessingTime();
        test = (request1.getProcessingTime() > 0
                && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime());
        assertEquals(true, test);

        request1.setQueueType(Huge.getInstance());
        request1.generateProcessingTime();
        test = (request1.getProcessingTime() > 0
                && request1.getProcessingTime() <= request1.getQueueType().getMaximumRequestTime());
        assertEquals(true, test);
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
}