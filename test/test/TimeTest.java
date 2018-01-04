package test;

import module.Request;
import module.Student;
import module.Time;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeTest {

    /**
     * Test of the method static checkTime(Request) of the Time class.
     */
    @Test
    public void testCheckTime() {
        Request request = new Request(new Student(30));
        request.setSendTime(Time.FULLWEEK);
        assertTrue(Time.checkTime(request));
        request.setSendTime(Time.FULLWEEK);
        assertFalse(Time.checkTime(request));
    }
}