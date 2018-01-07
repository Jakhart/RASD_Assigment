package module;

public class Student extends User {
    private String curriculumGroup;

    /**
     * Constructor
     * @param budgetInput - Budget of the user
     */
    public Student(double budgetInput){
        this.setUserType("Student");
        this.setBudget(budgetInput);
        this.setParameter(10);
        this.setTimeBetweenTwoRequest(Simulation.exponentialProbability(10000,this.getParameter()));
    }
}
