package com.ernestas.entities;

public abstract class AliveEntity extends Entity {

    int maxHealth;
    int health;

    public AliveEntity() {
        this(0);
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void damage(int amount) {
        if (amount > 0) {
            this.health -= amount;
            this.health = Math.max(this.health, 0);
        }
    }

    public void heal(int amount) {
        if (amount > 0) {
            this.health += amount;
            this.health = Math.min(this.health, this.maxHealth);
        }
    }

    public AliveEntity(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
