package Lab4;

import Lab4.Configs.GameConfig;
import Lab4.Configs.World;
import Lab4.Entities.Entity;
import Lab4.Entities.EntityPlayer;
import Lab4.Utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer {
    private GameConfig config;
    private World serverWorld;
    private int serverTicks = 0;
    private static GameServer instance;

    public static void main(String[] args){
        GameServer lab4 = new GameServer();
    }

    public GameServer(){
        instance = this;

        try {
            loadConfig();

            loadWorld();

            System.out.println(config);
            System.out.println(serverWorld);

            for(int i = 0; i <= 30;i++)
            {
                Thread.sleep(config.getUpdatePeriod());
                updateServer();
            }
        } catch (IOException | InterruptedException | SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    public void updateServer() throws IOException, SQLException {
        serverWorld.updateWorld();
        serverTicks++;
        if(serverTicks % config.getSavePeriod() == 0){
            FileUtils.saveWorld(new File("world.json"), serverWorld);
        }
    }

    private void loadConfig() throws IOException {
        GameConfig Nconfig = FileUtils.loadConfig(new File("config.json"));

        if(Nconfig == null) {
            config = new GameConfig("176.0.0.1",25655,2,1000,5);
            FileUtils.saveConfig(new File("config.json"), config);
        } else {
            instance.config = Nconfig;
        }
    }

    private void loadWorld() throws IOException, SQLException {
        World world = FileUtils.loadWorld(new File("world.json"));

        if (world == null) {
            world = new World(1,"Lab");
            world.setEntities(generateListEntity(world));
            instance.serverWorld = world;
            FileUtils.saveWorld(new File("world.json"), instance.serverWorld);
            System.out.println("Create new world");
        } else {
            instance.serverWorld = world;
            System.out.println("Load exist world");
        }
    }

    public GameConfig getServerConfig() { return config; }

    public static GameServer getInstance() { return instance; }

    public int getServerTicks() {
        return serverTicks;
    }

    public World getServerWorld() {
        return serverWorld;
    }

    public List<Entity> generateListEntity(World world) throws SQLException {
        List<Entity> res = new ArrayList<>();

        Random random = new Random();

        for(int i = 0; i < 5; i++) {
           res.add(new Entity(world,"Zombie" + i, random.nextInt(80)-40, random.nextInt(80)-40, true, 140, random.nextInt(40)+100, 10));
        }
        for(int i = 0; i < 5; i++) {
            res.add(new Entity(world,"Skeleton" + i, random.nextInt(80)-40, random.nextInt(80)-40, true, 90, random.nextInt(40)+50, 10));
        }
        for(int i = 0; i < 5; i++) {
            res.add(new Entity(world,"Pig" + i, random.nextInt(80)-40, random.nextInt(80)-40, false, 20, random.nextInt(10)+10, 0));
        }
        for(int i = 0; i < 5; i++) {
            res.add(new EntityPlayer(world,"Player" + i, random.nextInt(80)-40, random.nextInt(80)-40, 140, random.nextInt(90)+50, 20, "Nik"+i));
        }
        return res;
    }
}

