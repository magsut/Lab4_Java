package Lab4.Utils;

import Lab4.Configs.GameConfig;
import Lab4.Configs.World;
import Lab4.Entities.Entity;
import Lab4.Entities.EntityPlayer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static void saveConfig(File path, GameConfig config) throws IOException
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        Files.write(
                Paths.get(path.getPath()),
                gson.toJson(config).getBytes(),
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
        );
    }

    public static void saveWorld(File path, World world) throws IOException
    {
        final RuntimeTypeAdapterFactory<Entity> typeFactory = RuntimeTypeAdapterFactory
                .of(Entity.class, "Entity")
                .registerSubtype(EntityPlayer.class, "Prayer")
                .registerSubtype(Entity.class, "Entity");
        final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();

        Files.write(
                Paths.get(path.getPath()),
                gson.toJson(world).getBytes(),
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE
        );
    }

    public static GameConfig loadConfig(File path) throws IOException
    {
        Gson gson = new Gson();
        String json = new String(Files.readAllBytes(Paths.get(path.getPath())));
        GameConfig config = gson.fromJson(json, GameConfig.class);
        return config;
    }

    public static World loadWorld(File path) throws IOException
    {
        String json = new String(Files.readAllBytes(Paths.get(path.getPath())));
        final RuntimeTypeAdapterFactory<Entity> typeFactory = RuntimeTypeAdapterFactory
                .of(Entity.class, "Entity")
                .registerSubtype(EntityPlayer.class, "Prayer")
                .registerSubtype(Entity.class, "Entity");
        final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();
        World world = gson.fromJson(json, World.class);
        if(world != null) {
            for(int i = 0; i < world.getEntities().size(); i++)
            {
                world.getEntities().get(i).setWorld(world);
            }
            return world;
        } else return null;


    }
}
