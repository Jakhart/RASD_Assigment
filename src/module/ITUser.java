package module;

public class ITUser extends User {

    public ITUser(double budgetInput){
        this.setUserType("IT User");
        this.setBudget(budgetInput);
        this.setParameter(10);
        this.setTimeBetweenTwoRequest(Simulation.exponentialProbability(10000,this.getParameter()));
    }
}
