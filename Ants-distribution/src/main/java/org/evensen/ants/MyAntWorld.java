package org.evensen.ants;

import org.evensen.ants.Ant;
import org.evensen.ants.AntWorld;
import org.evensen.ants.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAntWorld implements AntWorld {

    private int width;
    private int height;

    private float[][] foragingPheromones;
    private float[][] foodPheromones;

    private boolean[][] foodMatrix;

    private List<FoodSource> foodSources;


    public MyAntWorld(int width, int height, int food){
        this.width = width;
        this.height = height;

        this.foodSources = new ArrayList<>();
        for(int i = 0; i < food; i++){
            this.foodSources.add(new FoodSource(width, height));
            //addFoodSource(this.foodSources.get(i));
        }

        this.foodMatrix = new boolean[width][height];

        this.foragingPheromones = new float[width][height];
        this.foodPheromones = new float[width][height];
    }

    private void addFoodSource(FoodSource foodSource) {
        Position center = foodSource.p;
        int radius = foodSource.radius;
        for (int x = (int)center.getX() - radius; x < center.getX() + radius; x++) {
            for (int y = (int)center.getY() - radius; y < center.getY() + radius; y++) {
                if (center.isWithinRadius(new Position(x, y), radius)) {
                    this.foodMatrix[x][y] = true;
                }
            }
        }
    }

    private void removeFoodSource(FoodSource foodSource) {
        Position center = foodSource.p;
        int radius = foodSource.radius;
        for (int x = (int)center.getX() - radius; x < center.getX() + radius; x++) {
            for (int y = (int)center.getY() - radius; y < center.getY() + radius; y++) {
                if (center.isWithinRadius(new Position(x, y), radius)) {
                    this.foodMatrix[x][y] = false;
                }
            }
        }
    }



    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean isObstacle(final Position p) {
        final float x = p.getX();
        final float y = p.getY();

        return x < 0 || y < 0 || y >= this.height || x >= this.width;
    }

    @Override
    public void dropForagingPheromone(final Position p, final float amount) {
        this.foragingPheromones[(int)p.getX()][(int)p.getY()] += 1.0;
    }

    @Override
    public void dropFoodPheromone(final Position p, final float amount) {
        this.foodPheromones[(int)p.getX()][(int)p.getY()] += 1.0;
    }

    @Override
    public void dropFood(final Position p) {

    }

    @Override
    public void pickUpFood(final Position p) {
        for(FoodSource fs : this.foodSources){
            if(p.isWithinRadius(fs.p, fs.radius)){
                if(fs.containsFood()){
                    fs.takeFood();
                }
                else{
                    removeFoodSource(fs);
                    fs.resetFoodSource();
                    addFoodSource(fs);
                }

            }
        }

        /*
            this.foodLeft = this.foodCount;
            this.p = new Position(rand.nextFloat(0, this.width), rand.nextFloat(0, this.height));

        for(FoodSource fs : this.foodSources){
            if(p.isWithinRadius(fs.p, fs.radius)){
                fs.takeFood();
            }
        }
         */
    }

    @Override
    public float getDeadAntCount(final Position p) {
        return 0;
    }

    @Override
    public float getForagingStrength(final Position p) {
        return this.foragingPheromones[(int)p.getX()][(int)p.getY()];
    }

    @Override
    public float getFoodStrength(final Position p) {
        return this.foodPheromones[(int)p.getX()][(int)p.getY()];
    }

    @Override
    public boolean containsFood(final Position p) {
        //return this.foodMatrix[(int)p.getX()][(int)p.getY()];

        for(FoodSource fs : this.foodSources){
            if(p.isWithinRadius(fs.p, fs.radius)){
                return true;
            }
        }
        //Position food = new Position(this.width/2, this.height/2);
        //return p.isWithinRadius(food, 20);
        return false;


    }

    @Override
    public long getFoodCount() {
        return 0;
    }

    @Override
    public boolean isHome(final Position p) {
        Position home = new Position(this.width, this.height/2);


        return p.isWithinRadius(home, 20);
    }

    @Override
    public void dispersePheromones() {
        float evaporationRate = 0.80f; // Kan justeras
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                this.foragingPheromones[x][y] *= evaporationRate;
                this.foodPheromones[x][y] *= evaporationRate;
            }
        }
    }

    @Override
    public void setObstacle(final Position p, final boolean add) {

    }

    @Override
    public void hitObstacle(final Position p, final float strength) {

    }
}