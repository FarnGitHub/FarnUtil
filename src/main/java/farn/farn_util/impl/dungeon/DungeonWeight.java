package farn.farn_util.impl.dungeon;

public abstract class DungeonWeight {
    public int weight;

    public DungeonWeight(int weight) {
        this.weight = weight;
    }

    @SuppressWarnings("unused")
    public abstract boolean remove();

    public boolean equals(Object other) {
        return this == other || (other instanceof DungeonWeight dg && this.weight == dg.weight);
    }
}
