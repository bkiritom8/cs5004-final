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
   * A constructor that instantiates the Monster class by taking in 10 different arguments.
   *
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
  public Monster(String name, String description, boolean active, int damage, boolean canAttack,
                 String attackDescription, String effects, int value,
                 String solution, String target) {
    this.maxHealth = 100;  // Default value, can be adjusted per monster
    this.health = maxHealth;
  }

  // Methods
  /**
   * This method is used to attack the player if the Monster is active and is able to attack.
   * If the Monster is unable to attack, 0 damage will be displayed.
   *
   * @param player The target player to attack
   * @return inflicted damage
   */
  public int attack(Player player) {
    if (active & canAttack) {
      player.takeDamage(abs(damage));
      return abs(damage);
    }
    return 0;
  }

  /**
   * This method is used to set the Monster's status to inactive and health to 0.
   */
  public void defeat() {
    this.active = false;
    this.health = 0;
  }

  /**
   * This method is used to apply the damage to the Monster and validate if the attack is critical.
   * A message will be displayed based on the health and active status of the Monster.
   *
   * @param amount Damage Amount
   * @param isCritical Critical hit or not (true/false)
   * @return Result of the damage
   */
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

  // Getters and Setters
  public String getName() {
    return name;
  }

  public int getHealthPercentage() {
    return (this.health / this.maxHealth) * 100;
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

  public void setActive(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return active;
  }

  public boolean canAttack() {
    return canAttack;
  }
}
