package model;

import static java.lang.Math.abs;

/**
 * A Monster class.
 */
public class Monster {
  private String name;
  private String attackDescription;
  private String description;
  private String effects;
  private String solution;
  private String target;
  private boolean active;
  private boolean canAttack;
  private int damage;
  private int value;
  private int health;
  private int maxHealth;

  // Constructor
  /**
   * A constructor that instantiates the Monster class by taking 10 different arguments.
   * @param name Monster's name
   * @param active Is Monster active or not (true/false)
   * @param damage Monster's damage per attack
   * @param canAttack If the Monster can attack (true/false)
   * @param attackDescription What Monster's attack does
   * @param description What the Monster is
   * @param effects Monster's effects on the surroundings
   * @param value Points gained by defeating the Monster
   * @param solution Ways to defeat the Monster
   * @param target Monster's target
   */
  public Monster(String name, boolean active, int damage, boolean canAttack, String attackDescription,
                 String description, String effects, int value, String solution, String target) {
    this.maxHealth = 100;  // Default value, can be adjusted per monster
    this.health = maxHealth;
  }

  // Methods
  public int attack(Player player) {
    if (active & canAttack) {
      player.takeDamage(abs(damage));
      return abs(damage);
    }
    return 0;
  }

  public void defeat() {
    this.active = false;
    this.health = 0;
  }

  public String takeDamage(int amount, boolean isCritical) {
    if (!active) {
      return "success: " + false + ",\n"
              + "message: Monster is already defeated" + "\n\n";
    } // if Monster is already defeated

    this.health -= amount; // reduce health by amount dealt by the Player

    if (this.health <= 0) {
      this.health = 0;
      this.defeat();
      return "success: " + true + ",\n"
              + "damage: " + amount + ",\n"
              + "critical: " + isCritical + ",\n"
              + "defeated: " + true + ",\n"
              + "healthRemaining: " + 0 + "\n\n";
    } // if Monster's health drops to 0 or below

    // If Monster is still able to take damage and has HP remaining
    return "success: " + true + ",\n"
            + "damage: " + amount + ",\n"
            + "critical: " + isCritical + ",\n"
            + "defeated: " + false + ",\n"
            + "healthRemaining: " + this.health + "\n\n";
  }

  public int getHealthPercentage() {
    return (this.health / this.maxHealth) * 100;
  }

  // Getters and Setters
  public boolean canAttack() {
    return canAttack;
  }

  public String getName() {
    return name;
  }

  public int getDamage() {
    return damage;
  }

  public String getAttackDescription() {
    return attackDescription;
  }

  public String getDescription() {
    return description;
  }

  public String getEffects() {
    return effects;
  }

  public int getValue() {
    return value;
  }

  public String getSolution() {
    return solution;
  }

  public String getTarget() {
    return target;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
