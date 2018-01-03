package module;

public abstract class User {
    private double budget;
    private double timeBetweenTwoRequest;
    private String userType;
    private int parameter;



    /*
    Accessors
     */
    public void setTimeBetweenTwoRequest(double timeBetweenTwoRequest) {
        this.timeBetweenTwoRequest = timeBetweenTwoRequest;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getTimeBetweenTwoRequest() {
        return timeBetweenTwoRequest;
    }

    public String getUserType() {
        return userType;
    }
}
