package module;

public class Researcher extends User{
    private double additionalRessources;
    private String groupName;

    public double getAdditionalRessources() {
        return additionalRessources;
    }

    public void setAdditionalRessources(double additionalRessources) {
        this.additionalRessources = additionalRessources;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
