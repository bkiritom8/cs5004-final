package model;

public class Item {
    private String name;
    private int weight;
    private int maxUses;
    private int usesRemaining;
    private int value;
    private String whenUsed;
    private String description;

    public Item(String name, int weight, int maxUses, int usesRemaining, int value, String whenUsed, String description) {
        this.name = name;
        this.weight = weight;
        this.maxUses = maxUses;
        this.usesRemaining = usesRemaining;
        this.value = value;
        this.whenUsed = whenUsed;
        this.description = description;
    }

    public boolean use() {
        if (usesRemaining > 0) {
            usesRemaining--;
            return true;
        }
        return false;
    }

    public String getWhenUsed() {
        return whenUsed;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public int getUsesRemaining() {
        return usesRemaining;
    }

    public void setUsesRemaining(int uses) {
        this.usesRemaining = uses;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
