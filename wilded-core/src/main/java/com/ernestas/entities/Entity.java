package com.ernestas.entities;

public abstract class Entity {

    float x, y, z;

    public Entity() {
        this(0, 0);
    }

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float x, float z) {
        this.setX(this.x + x);
        this.setZ(this.z + z);
    }

    public void translate(float x, float y, float z) {
        this.setX(this.x + x);
        this.setY(this.y + y);
        this.setZ(this.z + z);
    }

    public void setPosition(float x, float z) {
        setPosition(x, this.y, z);
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
