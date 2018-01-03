package module;

public class ITUser extends User {

    public ITUser(double budgetInput){
        this.setUserType("IT User");
        this.setBudget(budgetInput);
        this.setParameter(1);
        this.setTimeBetweenTwoRequest(Random.exponentialProbability(20,this.getParameter()));
    }
}
