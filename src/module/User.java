package module;

public abstract class User {
    private double budget;
    private double timeBetweenTwoRequest;
    private String userType;
    private int parameter;

    /**
     * Check if a user has enough budget to submit the request in parameter
     * @param requestCreated - The request created by the user.
     * @return - a boolean that tell if the user has enough budget or not to compute the demanded request.
     */
    public boolean checkBudget(Request requestCreated){
        if(this.getBudget() < requestCreated.getPriceOfRequest())
            return false;
        else
            this.setBudget(this.getBudget() - requestCreated.getPriceOfRequest());
            return true;
    }

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
