package Lab4.Configs;

import Lab4.Entities.Entity;
import Lab4.other.SortEntities;

import java.sql.SQLException;
import java.util.*;

public class World
{
    private int id;
    private String worldName;
    List<Entity> entities = new ArrayList<Entity>();

    public World(int id, String worldName) {
        this.id = id;
        this.worldName = worldName;
    }

    public World(int id, String worldName, List<Entity> entities) {
        this.id = id;
        this.worldName = worldName;
        this.entities = entities;
    }

    public void updateWorld() throws SQLException {
        for(Entity e : entities)
        {
            e.update();
        }
        for(int i = entities.size() - 1; i >= 0; i--)
        {
            if(entities.get(i).isDeath())
            {
                entities.remove(i);
            }
        }
    }

    public List<Entity> getEntitiesInRegion(double x, double z, double range)
    {
        List<Entity> sortList = new ArrayList<Entity>();
        double distance = Math.pow(range, 2);

        for(Entity e : entities)
        {
            double distanceToEntity = Math.pow((e.getPosX() - x), 2) + Math.pow((e.getPosZ() - z), 2);
            if (distanceToEntity <= distance)
            {
                sortList.add(e);
            }
        }
        List<SortEntities> sortEntities = new ArrayList<SortEntities>();
        for(Entity e : sortList)
        {
            sortEntities.add(new SortEntities(e,Math.pow((e.getPosX() - x), 2) + Math.pow((e.getPosZ() - z), 2)));
        }

        Collections.sort(sortEntities, new Comparator<SortEntities>() {
            @Override
            public int compare(SortEntities o1, SortEntities o2) {
                return Double.compare(o2.getDistance(), o1.getDistance());
            }
        });
        sortList.clear();

        for(SortEntities e : sortEntities)
        {
            sortList.add(e.getEntity());
        }

        return sortList;
    }


    public List<Entity> getEntitiesNearEntity(Entity entity, double range)
    {
        return getEntitiesInRegion(entity.getPosX(),entity.getPosZ(),range);
    }

    public void addEntity(Entity e)
    {
        entities.add(e);
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public String getWorldName() {
        return worldName;
    }

    @Override
    public String toString() {
        return "World{" +
                "id=" + id +
                ", worldName='" + worldName + '\'' +
                ", entities=" + entities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        World world = (World) o;
        return id == world.id && Objects.equals(worldName, world.worldName) && Objects.equals(entities, world.entities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, worldName, entities);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
