package farn.farn_util.api.dungeon;

import farn.farn_util.impl.dungeon.DungeonWeight;
import java.util.Objects;

public class DungeonMonster extends DungeonWeight {
    public String entityId;

    public DungeonMonster(String entityId) {
        super(100);
        this.entityId = entityId;
    }

    public DungeonMonster(String entityId, int weight) {
        this(entityId);
        this.weight = weight;
    }

    @Override
    public boolean remove() {
        return DungeonAPI.mobs.remove(this);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        else if(other instanceof DungeonMonster dg)
            return Objects.equals(this.entityId, dg.entityId) &&
                   this.weight == dg.weight;
        return false;
    }
}
