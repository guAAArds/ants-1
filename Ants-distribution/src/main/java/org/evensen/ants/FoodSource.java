package org.evensen.ants;

import java.util.Random;

public class FoodSource {

    final int foodCount = 1000;

    private int width;
    private int height;

    public int foodLeft;
    public Position p;
    public int radius;

    public Random rand;

    public FoodSource(int width, int height){
        this.width = width;
        this.height = height;
        this.radius = 10;

        this.rand = new Random();

        this.foodLeft = this.foodCount;

        this.p = newPosition();
    }

    private Position newPosition(){
        int radius = this.radius;
        return new Position(this.rand.nextFloat(radius, this.width-radius), this.rand.nextFloat(radius, this.height-radius));
    }

    public boolean containsFood(){
        return 0 < this.foodLeft;
    }

    public void takeFood(){
        this.foodLeft--;
    }

    public void resetFoodSource(){
        this.foodLeft = this.foodCount;
        this.p = newPosition();
    }

}