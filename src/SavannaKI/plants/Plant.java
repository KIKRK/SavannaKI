package SavannaKI.plants;

public abstract class Plant{
    int size;

    Plant(int size) {
        this.size = size;
    }

    abstract public void grow();

    public void beEaten(int n){
        size -= n;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + size;
    }

    public int getSize() {
        return size;
    }

}
