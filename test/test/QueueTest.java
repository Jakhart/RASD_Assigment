package test;

import module.Medium;
import module.Request;
import module.Student;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {

    @Test
    public void testCheckCores() {
        Request request = new Request(new Student(30));
        request.setCoresNeeded(204);
        assertTrue(Medium.getInstance().checkCores(request));

    }
}