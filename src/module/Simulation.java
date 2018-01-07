package module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The simulation class is made to create a new simulation.
 */
public class Simulation {
    private List<String> researcherGroup;
    private List<Integer> researcherNumber;
    private List<Double> researcherBudget;
    private List<Double> researcherAdditional;
    private int numberStudent;
    private int numberITUser;
    private int totalNumber = 0;
    private double budgetStudent;
    private double budgetITUser;

    /**
     * Basic Constructor
     * @param researcherGroup - List of group name
     * @param researcherNumber - List of number of people in a group
     * @param researcherBudget - List of budget
     * @param researcherAdditional - List of additional resources
     * @param numberStudent -
     * @param numberITUser -
     * @param budgetStudent -
     * @param budgetITUser -
     */
    public Simulation(List<String> researcherGroup, List<Integer> researcherNumber, List<Double> researcherBudget, List<Double> researcherAdditional, int numberStudent, int numberITUser, double budgetStudent, double budgetITUser) {
        this.researcherGroup = researcherGroup;
        this.researcherNumber = researcherNumber;
        this.researcherBudget = researcherBudget;
        this.researcherAdditional = researcherAdditional;
        this.numberStudent = numberStudent;
        this.numberITUser = numberITUser;
        this.budgetStudent = budgetStudent;
        this.budgetITUser = budgetITUser;
    }

    /**
     * Constructor for quick testing
     */
    public Simulation(){

        researcherGroup = new ArrayList<>();
        researcherGroup.add("group1");
        researcherGroup.add("group2");
        researcherNumber= new ArrayList<>();
        researcherNumber.add(40);
        researcherNumber.add(60);
        researcherBudget = new ArrayList<>();
        researcherBudget.add(10000.0);
        researcherBudget.add(5000.0);
        researcherAdditional = new ArrayList<>();
        researcherAdditional.add(0.0);
        researcherAdditional.add(1000.0);
        numberStudent = 60;
        budgetStudent = 2000;
        numberITUser = 10;
        budgetITUser = 3000;
    }

    /**
     * Create a list of random users.
     * @return - The list of random user generated
     */
    public List<User> userListCreation(){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < researcherGroup.size(); i++) {
            totalNumber += researcherNumber.get(i);
            for (int j = 0; j < researcherNumber.get(i); j++) {
                User user = new Researcher(researcherBudget.get(i), researcherAdditional.get(i),researcherGroup.get(i));
                users.add(user);
            }
        }
        for (int i = totalNumber; i < numberStudent + totalNumber; i++) {
            users.add(new Student(budgetStudent));
        }
        totalNumber += numberStudent;
        for (int i = totalNumber; i < numberITUser +totalNumber; i++) {
            users.add(new ITUser(budgetITUser));
        }
        totalNumber += numberITUser;
        return users;
    }

    /**
     * Create a list of random request made by the user.
     * @param users - A list of user
     * @return - The list of random request generated
     */
    public List<Request> userRequestList(List<User> users){
        List<Request> requests = new ArrayList<>();
        for (User user : users){
            Request request = new Request(user);
            while(user.checkBudget(request)){
                requests.add(request);
                request = new Request(user);
            }
        }
        return requests;
    }

    /**
     * Program of the simulation.
     * @param requests - A list of request
     * @see Queue #processRequest(Request)
     * @see Queue #processWeekend(Request)
     * @see Request #refundPayment
     * @see Time #checkWeekend(Request)
     * @see Time #checkTime(Request)
     */
    public void simulationProcess(List<Request> requests){
        requests.sort(requests.get(0));
        System.out.println("Request processing");
        for (Request request : requests){
            treatOneRequest(request);
        }
    }

    /**
     * The full treatment of one request
     * @param request The request process
     */
    public void treatOneRequest(Request request){
        if(request.getSendTime() < Time.getEndWeekend()){
            request.refundPayment();
        }else {
            //If a week pass without any request and that some request we need to proceed the weekend queue.
            if (Time.checkTime(request)) {
                Huge.getInstance().processHuge(request);
            }
            if (Time.checkWeekend(request)) {
                request.getQueueType().processWeekend(request);

            } else {
                request.getQueueType().processRequest(request);
            }
        }
    }

    /**
     * Create the Output file.
     */
    public void printOutput(){
        DecimalFormat df = new DecimalFormat("0.##");
        try(
                FileWriter writer = new FileWriter("simulationResult.txt");
                ){
            writer.write("*******SIMULATION RESULT********\n");
            writer.write("Input information: \n");
            writer.write("Number of users: " + totalNumber + "\n");
            for (int i = 0; i < researcherNumber.size() ; i++) {
                writer.write("Group name: " + researcherGroup.get(i)+ "\n");
                writer.write("Number of Researcher: "+ researcherNumber.get(i) + "\t" + "Budget per Researcher: "
                        + researcherBudget.get(i) + " $\t" + "Additional Budget: " + researcherBudget.get(i) + " $\n");
            }
            writer.write("Number of Student: " + numberStudent + "\t" + "Budget per Student: "+ budgetStudent + " $\n");
            writer.write("Number of IT USer: " + numberITUser + "\t" + "Budget per ITUser: " + budgetITUser + " $\n");
            writer.write("\n");
            writer.write("Result: \n");
            Queue[] queues = {ShortQueue.getInstance(), Medium.getInstance(), Large.getInstance(), Huge.getInstance()};
            double totalHoursProcess = 0;
            double totalMoney = 0;
            for (Queue queue : queues) {
                writer.write("\n");
                writer.write("********************\n");
                writer.write("For the "+queue.getQueueType()+" queue: \n");
                writer.write("Number of job process per week: "
                        + df.format((double)queue.getNumberRequestTreat() / Time.getCountWeeks()) + "\n");
                writer.write("Actual machine hour consume by user jobs: "
                        + Math.round(queue.getTotalProcessingTime()) + " min "
                        + df.format(Math.round(queue.getTotalProcessingTime())/60.0) +" h\n");
                totalHoursProcess += queue.getTotalProcessingTime();
                writer.write("The resulting price paid by the user: "
                        + df.format(queue.getTotalMoneyCollected()) + " $\n");
                totalMoney += queue.getTotalMoneyCollected();
                writer.write("The average wait time in the queue: "
                        + Math.round(queue.getTotalWaitTime() / queue.getNumberRequestTreat()) + " min\n");
                writer.write("The average turnaround time ratio: "
                        + df.format(queue.getTurnaroundSum()/queue.getNumberRequestTreat()) + "\n");
            }
            writer.write(" \n");
            writer.write("Total: \n");
            writer.write(" \n");
            writer.write("Number of machine hour consume by user job: "+ Math.round(totalHoursProcess) + " min, "
                    + df.format(Math.round(totalHoursProcess)/60.0) +" h\n");
            writer.write("The resulting price paid by the user: "+ df.format(totalMoney) + " $\n");
            writer.write("The simulation has been made on "+ Time.getCountWeeks() + " week(s)");
            writer.write(" \n");
            writer.write("The economic balance of the center: \n" );
            System.out.println("Simulation file has been generated");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Ask the user the Input of the simulation.
     * @return - A simulation object created with the inputof the user.
     */
    public static Simulation input(){
        List<String> groupNames = new ArrayList<>();
        List<Integer> numberResearcher = new ArrayList<>();
        List<Double> budgets = new ArrayList<>();
        List<Double> addMoneys = new ArrayList<>();
        int studentNumber = 0;
        double budgetStudent = 0;
        int itUser = 0;
        double budgetIT = 0;
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to the simulation");
        System.out.println("Assumption: ");
        System.out.println("- The budget group for Researcher is dispatch equally in the group.");
        System.out.println("- Student and IT user all have the same budget");
        System.out.println("- The size of the request depend on the amount of cores it demand.");
        System.out.println("- The system have 128 node of 16 processor cores per nodes.");
        System.out.println("- There is nothing happening during the weekend if no Huge task has been generated.");
        System.out.println("- The simulation begin on a Monday at 9:00 am");
        System.out.println("Press enter when you are ready.");
        String input = in.nextLine();
        System.out.println("Do you want to create a group of Researcher ? (y/n)");
        input = in.nextLine();
        while(input.equals("y")){
            System.out.println("Enter the name of the group: ");
            groupNames.add(in.nextLine());
            System.out.println("Enter the number of Researcher in that group: ");
            numberResearcher.add(Integer.parseInt(in.nextLine()));
            System.out.println("How budget budget do you want to give to this each user of this group ?");
            budgets.add(Double.parseDouble(in.nextLine()));
            System.out.println("How much additional Ressource do you want to this group (it will go to all person of the group)");
            addMoneys.add(Double.parseDouble(in.nextLine()));
            System.out.println("Do you want to create another group of Researcher ? (y/n)");
            input = in.nextLine();
        }
        System.out.println("How many Student do you want ?");
        studentNumber = Integer.parseInt(in.nextLine());
        System.out.println("How much budget do you want to give each student ?");
        budgetStudent = Double.parseDouble(in.nextLine());
        System.out.println("How many IT support do you want ?");
        itUser = Integer.parseInt(in.nextLine());
        System.out.println("How much budget do you want to give each It support ?");
        budgetIT = Double.parseDouble(in.nextLine());

        return new Simulation(groupNames, numberResearcher, budgets, addMoneys, studentNumber, itUser, budgetStudent, budgetIT);
    }

    /**
     * This methods enable us to generate random numbers with a
     * exponential probability.
     * @param border - The maximum value that can reach the random number.
     * @param parameter - The parameter of the exponential probability.
     * @return - a random number between 0 and the border.
     */
    public static double exponentialProbability(int border, int parameter){
        int lambda = parameter;
        double num = Math.round((lambda*Math.exp(-lambda*Math.random()))* 10000 +Math.random()*10);
        while(num > border){
            num = Math.round((lambda*Math.exp(-lambda*Math.random()))*10000 + Math.random()*10);
        }
        return num;
    }


}
