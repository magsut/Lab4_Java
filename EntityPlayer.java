package Lab4.Entities;

import Lab4.GameServer;
import Lab4.Configs.World;
import Lab4.Managers.EntityManager;

import java.sql.SQLException;
import java.util.Objects;

public class EntityPlayer extends Entity{

    private String nickname;
    private double exp;


    public EntityPlayer(World world, String title, double posX, double posZ, int maxHealth, int health, int attackDamage, String nickname) throws SQLException {
        super(world, title, posX, posZ, false, maxHealth, health, attackDamage);
        this.nickname = nickname;
        this.exp = 0;
        EntityManager.insertPlayer(this);
    }

    public EntityPlayer(long id, World world, String title, double posX, double posZ, int maxHealth, int health, int attackDamage, boolean death, String nickname, double exp) {
        super(id, world, title, posX, posZ, false, maxHealth, health, attackDamage, death);
        this.nickname = nickname;
        this.exp = exp;
    }

    public EntityPlayer(World world, String title, double posX, double posZ, boolean agressive, int maxHealth, int health, int attackDamage, String nickname, double exp) throws SQLException {
        super(world, title, posX, posZ, agressive, maxHealth, health, attackDamage);
        this.nickname = nickname;
        this.exp = exp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    @Override
    public void update() throws SQLException {
        super.update();
        if(GameServer.getInstance().getServerTicks()%2 == 1) {
            if(this.health < this.maxHealth) {
                this.health++;
            }
        }
        if(GameServer.getInstance().getServerTicks()%5 == 0){
            exp += 10 * GameServer.getInstance().getServerConfig().getDifficulty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EntityPlayer that = (EntityPlayer) o;
        return Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nickname);
    }

    @Override
    public String toString() {
        return "EntityPlayer{" +
                "id=" + id +
                ", world=" + world +
                ", title='" + title + '\'' +
                ", posX=" + posX +
                ", posZ=" + posZ +
                ", aggressive=" + aggressive +
                ", maxHealth=" + maxHealth +
                ", health=" + health +
                ", attackDamage=" + attackDamage +
                ", target=" + target +
                ", death=" + death +
                ", nickname='" + nickname + '\'' +
                ", exp=" + exp +
                '}';
    }
}
