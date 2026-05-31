package farn.farn_util.impl.dungeon;

public abstract class DungeonWeight {
    public int weight;
    public int index = -1;

    public DungeonWeight(int weight) {
        this.weight = weight;
    }

    public abstract boolean remove();
}
