package com.tilldawn.model;

import java.io.Serializable;
import java.util.List;

public class Vector implements Serializable {
    public float x;
    public float y;

    public Vector() {
        this(0, 0);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector subtract(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector normalize() {
        float len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    public Vector normalizeTo(float targetLength) {
        normalize();
        scale(targetLength);
        return this;
    }

    public Vector set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector set(Vector v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public float dot(Vector v) {
        return this.x * v.x + this.y * v.y;
    }

    public float distanceTo(Vector v) {
        float dx = this.x - v.x;
        float dy = this.y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public Vector copy() {
        return new Vector(this.x, this.y);
    }

    @Override
    public String toString() {
        return "Vector(" + x + ", " + y + ")";
    }

    public float length(Vector u) {
        return copy().subtract(u).length();
    }

    public Vector findNearest(List<Vector> candidates) {
        Vector best = candidates.get(0);

        for (Vector v : candidates) {
            float d = length(v);
            if (d < length(best))
                best = v;
        }

        return best;
    }

    public Vector rotate(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;
        this.x = newX;
        this.y = newY;
        return this;
    }
}
