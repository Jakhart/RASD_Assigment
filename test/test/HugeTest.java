package test;

import module.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HugeTest {
    private void reloadArray(){
        ShortQueue.getInstance().setRunningRequest(new ArrayList<>());
        Medium.getInstance().setRunningRequest(new ArrayList<>());
        Large.getInstance().setRunningRequest(new ArrayList<>());
        Huge.getInstance().setRunningRequest(new ArrayList<>());
    }

    @Test
    public void testProcessHuge(){
        reloadArray();
        Request request1 = new Request(300,0,1000, Large.getInstance(),2048);
        Request request2 = new Request(6500,0,1000,Large.getInstance(),524);
        Request request3 = new Request(700,0,2840,Large.getInstance(),500);
        Request request4 = new Request(6590,0,2840,Large.getInstance(),500);
        Huge queue = Huge.getInstance();
        queue.processRequest(request1);
        queue.processRequest(request3);
        queue.processRequest(request2);
        queue.processRequest(request4);

        queue.processHuge();

        assertEquals(1000, request2.getWaitTime(), 0.0001);
        assertEquals(1, queue.getRunningRequest().size());
        assertEquals(2, Time.getCountWeeks());
    }
    @Test
    public void testProcessRequest(){
    }
}