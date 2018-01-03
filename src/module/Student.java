package module;

public class Student extends User {
    private String curriculumGroup;

    /**
     * Constructor
     * @param budgetInput
     */
    public Student(double budgetInput){
        this.setUserType("Student");
        this.setBudget(budgetInput);
        this.setParameter(1);
        this.setTimeBetweenTwoRequest(Random.exponentialProbability(20,this.getParameter()));
    }
}
