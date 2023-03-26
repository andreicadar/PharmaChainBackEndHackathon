package model;

public class ActiveSubstance {

    private int activeSubstance_ID;
    private String name;

    private int weight;

    public int getActiveSubstance_ID() {
        return activeSubstance_ID;
    }

    public void setActiveSubstance_ID(int activeSubstance_ID) {
        this.activeSubstance_ID = activeSubstance_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public ActiveSubstance(int activeSubstance_ID, String name, int weight) {
        this.activeSubstance_ID = activeSubstance_ID;
        this.name = name;
        this.weight = weight;
    }

}
