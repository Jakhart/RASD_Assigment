package test;

import module.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.ArrayList;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        test.RequestTest.class,
        test.QueueTest.class,
        test.HugeTest.class,
        test.TimeTest.class,
        test.UserTest.class,
        test.SimulationTest.class
})

public class TestSuite {

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

    @AfterClass
    public static void tearDownClass() throws Exception{
    }
}
