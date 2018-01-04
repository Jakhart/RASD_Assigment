import module.*;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Request request = new Request(new Student(30));
        System.out.println(request.getQueueType());
        request.generateProcessingTime();
        System.out.println(request.getProcessingTime());
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println(rand.nextInt(50));
        }
    }
}
