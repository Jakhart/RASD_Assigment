import module.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<Request> requests = new ArrayList<>();
        requests.add(new Request(new Student(200)));
        requests.add(new Request(new Student(199)));
        requests.sort(requests.get(0));
        System.out.println(requests.get(0).compare(requests.get(0), requests.get(1)));
    }
}
