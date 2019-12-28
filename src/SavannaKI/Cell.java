package SavannaKI;

import SavannaKI.animals.Animal;
import SavannaKI.plants.Plant;

import java.util.ArrayList;

public class Cell {
    private int X;
    private int Y;
    private ArrayList<Plant> plants;
    private ArrayList<Animal> animals;

    // Sawanna składa się z pól. Każde pole posiada współrzędne oraz listy roślin i zwierząt, które się na nim znajdują.
    // Rośliny są nieruchome, zwierzęta mogą się przemieszczać.

    public Cell(int x, int y, ArrayList<Plant> plants, ArrayList<Animal> animals) {
        this.X = x;
        this.Y = y;
        this.plants = plants;
        this.animals = animals;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

}
