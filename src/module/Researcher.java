package module;

public class Researcher extends User{
    private double additionalResources;
    private String groupName;

    public Researcher(double additionalResources, double budgetInput, String groupName){
        this.setAdditionalResources(additionalResources);
        this.setBudget(budgetInput + additionalResources);
        this.setUserType("Researcher");
        this.setGroupName(groupName);
        this.setParameter(10);
        this.setTimeBetweenTwoRequest(Simulation.exponentialProbability(10000,this.getParameter()));
    }

    public double getAdditionalResources() {
        return additionalResources;
    }

    public void setAdditionalResources(double additionalResources) {
        this.additionalResources = additionalResources;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
