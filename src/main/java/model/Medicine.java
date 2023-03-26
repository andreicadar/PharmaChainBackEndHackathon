package model;

public class Medicine {
    int medicineID;
    String name;
    int weight;

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
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

    public Medicine(int medicineID, String name, int weight) {
        this.medicineID = medicineID;
        this.name = name;
        this.weight = weight;
    }

}
