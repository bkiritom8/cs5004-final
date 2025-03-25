package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private int health;
    private List<Item> inventory;
    private Room currentRoom;
    private int score;
    private static final int MAX_WEIGHT = 13;
    private int attackPower;
    private int criticalChance;

    public static class AttackResult {
        private final boolean success;
        private final int damage;
        private final boolean critical;
        private final boolean defeated;
        private final int healthRemaining;

        public AttackResult(boolean success, int damage, boolean critical, boolean defeated, int healthRemaining) {
            this.success = success;
            this.damage = damage;
            this.critical = critical;
            this.defeated = defeated;
            this.healthRemaining = healthRemaining;
        }

        public boolean isSuccess() {
            return success;
        }

        public int getDamage() {
            return damage;
        }

        public boolean isCritical() {
            return critical;
        }

        public boolean isDefeated() {
            return defeated;
        }

        public int getHealthRemaining() {
            return healthRemaining;
        }
    }

    public Player(Room startRoom) {
        this.name = "Player";
        this.health = 100;
        this.inventory = new ArrayList<>();
        this.currentRoom = startRoom;
        this.score = 0;
        this.attackPower = 10;
        this.criticalChance = 15;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public String getHealthStatus() {
        if (health <= 0) {
            return "ASLEEP";
        }
        if (health < 40) {
            return "WOOZY";
        }
        if (health < 70) {
            return "FATIGUED";
        }
        return "AWAKE";
    }

    public boolean addToInventory(Item item) {
        if (getInventoryWeight() + item.getWeight() <= MAX_WEIGHT) {
            inventory.add(item);
            return true;
        }
        return false;
    }

    public boolean removeFromInventory(Item item) {
        return inventory.remove(item);
    }

    public Item getItemFromInventory(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public int getInventoryWeight() {
        int totalWeight = 0;
        for (Item item : inventory) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = new ArrayList<>(inventory);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public String getRank() {
        if (score >= 1000) {
            return "Adventure Master";
        }
        if (score >= 750) {
            return "Expert Explorer";
        }
        if (score >= 500) {
            return "Seasoned Adventurer";
        }
        if (score >= 250) {
            return "Novice Explorer";
        }
        return "Beginner";
    }

    public AttackResult attack(Monster monster) {
        Random random = new Random();
        boolean isCritical = random.nextInt(100) < criticalChance;

        int damage = attackPower;
        if (isCritical) {
            damage = attackPower * 2;
        }

        int monsterHealth = monster.getHealth();
        monster.takeDamage(damage);
        boolean defeated = monster.getHealth() <= 0;

        return new AttackResult(true, damage, isCritical, defeated, monster.getHealth());
    }
}
