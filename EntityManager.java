package Lab4.Managers;

import Lab4.Entities.Entity;
import Lab4.Entities.EntityPlayer;
import Lab4.Utils.ConnectToBD;

import java.sql.*;
import java.util.Date;

public class EntityManager {
    public static long insertNewEntity(Entity entity) throws SQLException {
        long newId = -1;
        try(Connection c = ConnectToBD.getConnection()) {
            String sql = "insert into entities (first_create) values (?);";

            PreparedStatement ps0 = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps0.setTimestamp(1,new Timestamp(new Date().getTime()));

            ps0.executeUpdate();

            ResultSet resultSet = ps0.getGeneratedKeys();

            if(resultSet.next()){
                newId = resultSet.getInt(1);
            }
        }
        return newId;
    }

    public static void insertPlayer(EntityPlayer player) throws SQLException {
        try(Connection c = ConnectToBD.getConnection()) {
            String sql = "insert into players values (?,?,?);";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setLong(1,player.getId());
            ps.setString(2,player.getNickname());
            ps.setDouble(3,player.getExp());

            ps.executeUpdate();
        }
    }

    public static void insertNewBattle(Entity agr, Entity deathE, Date timeDeath) throws SQLException {
        try(Connection c = ConnectToBD.getConnection()) {
            String sql = "insert into battle_logs (entities_identities_agressive, entities_identities_dide, time_deth) values (?,?,?);";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, (int) agr.getId());
            ps.setInt(2, (int) deathE.getId());
            ps.setTimestamp(3,new Timestamp(timeDeath.getTime()));

            ps.executeUpdate();
        }
    }

    public static int getExpFromDB(long id) throws SQLException {
        int exp = 0;

        try(Connection c = ConnectToBD.getConnection()) {
            String sql = "Select experience from players where entities_identities = ?;";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, (int) id);
            ps.executeQuery();

            ResultSet resultSet = ps.getResultSet();

            if(resultSet.next()){
                exp = resultSet.getInt("experience");
            }
        }
        return exp;
    }

    public static void updateDeathTime(Entity e,Date deathTime) throws SQLException {
        try(Connection c = ConnectToBD.getConnection()){
            String sql = """
                    update entities
                    set deth = ?
                    where identities = ?;
                    """;
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setTimestamp(1,new Timestamp(deathTime.getTime()));
            ps.setInt(2, (int) e.getId());

            ps.executeUpdate();
        }
    }
}
