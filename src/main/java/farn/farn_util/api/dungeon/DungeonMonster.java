package farn.farn_util.api.dungeon;

import farn.farn_util.impl.dungeon.DungeonWeight;

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
        if(index >= 0) {
            DungeonAPI.mobs.remove(index);
            return true;
        } else
            return false;
    }
}
