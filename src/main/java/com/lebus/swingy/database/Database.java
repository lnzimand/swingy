package com.lebus.swingy.database;

import com.lebus.swingy.model.PlayerCharacter;
import com.lebus.swingy.model.PlayerClasses;
import com.lebus.swingy.model.armoury.armor.*;
import com.lebus.swingy.model.armoury.helm.*;
import com.lebus.swingy.model.armoury.weapons.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database {

    private static final String			DB_USER 		=	"";
    private static final String			DB_PASS			=	"";
    private static final String 		DB_TABLE 		=	"players";
    private static final String 		P_NAME			= 	"name";
    private static final String 		P_CLASS			=	"class";
    private static final String 		P_LEVEL		 	=	"level";
    private static final String 		P_HEALTH	 	=	"health";
    private static final String 		P_XP 			=	"xp";
    private static final String 		P_ATTACK 		=	"attack";
    private static final String			P_DEFENSE		=	"defense";
    private static final String 		P_HITPOINTS 	=	"hitpoints";
    private static final String 		P_WEAPON 		=	"weapon";
    private static final String 		P_ARMOR 		=	"armor";
    private static final String			P_HELMET 		=	"helmet";
    private static final String			CREATE_TABLE 	=	"CREATE TABLE IF NOT EXISTS " + DB_TABLE + " (" + P_NAME
            + " VARCHAR(10) PRIMARY KEY, " + P_CLASS + " VARCHAR(20), " + P_LEVEL + " INTEGER, "
            + P_HITPOINTS + " INTEGER, " + P_HEALTH + " INTEGER, " + P_XP + " INTEGER, "
            + P_ATTACK + " INTEGER, " + P_DEFENSE + " INTEGER, " + P_WEAPON + " TEXT, "
            + P_ARMOR + " TEXT, " + P_HELMET + " TEXT)";
    private static final String			INSERT_PLAYER	=	"INSERT INTO " + DB_TABLE + " (" + P_NAME + ", " + P_CLASS + ", " + P_LEVEL
            + ", " + P_HITPOINTS + ", " + P_HEALTH + ", " + P_XP + ", " + P_ATTACK + ", "
            + P_DEFENSE + ", " + P_WEAPON + ", " + P_ARMOR + ", " + P_HELMET
            + ") VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String			GET_DB_TABLE	=	"SELECT * FROM " + DB_TABLE;
    private static final String			GET_PLAYER		=	"SELECT * FROM " + DB_TABLE + " WHERE " + P_NAME + " = ?";
    private static final String			UPDATE_PLAYER	=	"UPDATE " + DB_TABLE + " SET " + P_LEVEL + " = ?, " + P_HITPOINTS + " = ?, " + P_HEALTH
            + " = ?, " + P_XP +" = ?, " + P_ATTACK + " = ?, " + P_WEAPON + " = ?, " + P_ARMOR
            + " = ?, " + P_HELMET + " = ?, " + P_DEFENSE + " = ?" +" WHERE " + P_NAME + " = ?";
    private static final String 		DELETE_PLAYER	=	"DELETE FROM " + DB_TABLE + " WHERE " + P_NAME + " = ?";
    private static final String			DB_NAME 		=	"";
    private static final String			SQL_URL			= 	"jdbc:mysql://localhost:3306/" + DB_NAME;

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement updatePlayer;
    private static PreparedStatement	insertPlayer;
    private static PreparedStatement	preparedStatement;
    private static ResultSet result;
    private static final Properties connectionProps	= new Properties();

    private Connection getConnection() throws SQLException {
        try {
            connectionProps.put("user", DB_USER);
            connectionProps.put("password", DB_PASS);
            connection = DriverManager.getConnection(SQL_URL, connectionProps);
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            closeDB();
        } finally {
            connection.setAutoCommit(true);
        }
        return connection;
    }

    private boolean isPlayerExisting(String name) throws SQLException {
        preparedStatement = connection.prepareStatement(GET_PLAYER);

        preparedStatement.setString(1, name);
        result = preparedStatement.executeQuery();
        if (result.next())
            return name.equals(result.getString(P_NAME));
        return false;
    }

    public void updatePInfo(PlayerCharacter player) throws SQLException {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            updatePlayer = connection.prepareStatement(UPDATE_PLAYER);

            updatePlayer.setInt(1, player.getLevel());
            updatePlayer.setInt(2, player.getHitPoints());
            updatePlayer.setInt(3, player.getHealth());
            updatePlayer.setInt(4, player.getExperience());
            updatePlayer.setInt(5, player.getAttack());
            if (player.getWeapon() != null) updatePlayer.setString(6, player.getWeapon().getName());
            else updatePlayer.setString(6, "-");
            if (player.getArmor() != null) updatePlayer.setString(7, player.getArmor().getName());
            else updatePlayer.setString(7, "-");
            if (player.getHelmet() != null) updatePlayer.setString(8, player.getHelmet().getName());
            else updatePlayer.setString(8, "-");
            updatePlayer.setInt(9, player.getDefense());
            updatePlayer.setString(10, player.getName());
            updatePlayer.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            closeDB();
            throw new SQLException(e.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void insertHero(PlayerCharacter player) throws SQLException {
        try {
            connection = getConnection();
            if (!isPlayerExisting(player.getName())) {
                connection.setAutoCommit(false);
                insertPlayer = connection.prepareStatement(INSERT_PLAYER);

                insertPlayer.setString(1, player.getName());
                insertPlayer.setString(2, player.getPlayerClass().name());
                insertPlayer.setInt(3, player.getLevel());
                insertPlayer.setInt(4, player.getHitPoints());
                insertPlayer.setInt(5, player.getHealth());
                insertPlayer.setInt(6, player.getExperience());
                insertPlayer.setInt(7, player.getAttack());
                insertPlayer.setInt(8, player.getDefense());
                if (player.getWeapon() != null) insertPlayer.setString(9, player.getWeapon().getName());
                else insertPlayer.setString(9, "-");
                if (player.getArmor() != null) insertPlayer.setString(10, player.getArmor().getName());
                else insertPlayer.setString(10, "-");
                if (player.getHelmet() != null) insertPlayer.setString(11, player.getHelmet().getName());
                else insertPlayer.setString(11, "-");
                insertPlayer.executeUpdate();
                connection.commit();
            } else {
                throw new SQLException("Hero already exist");
            }
        } catch (SQLException e) {
            closeDB();
            throw new SQLException("Hero already exist");
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeHero(PlayerCharacter player) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            updatePlayer = connection.prepareStatement(DELETE_PLAYER);

            updatePlayer.setString(1, player.getName());
            updatePlayer.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public ArrayList<PlayerCharacter> getHeroes() throws SQLException {

        ArrayList<PlayerCharacter> heros = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(GET_DB_TABLE);

            while(result.next()) {
                PlayerCharacter hero;
                hero = new PlayerCharacter(result.getString(P_NAME), getClassName(result.getString(P_CLASS)), result.getInt(P_HEALTH), result.getInt(P_ATTACK), result.getInt(P_HITPOINTS), result.getInt(P_DEFENSE), result.getInt(P_LEVEL), result.getInt(P_XP));
                hero.setWeapon(getWeapon(result.getString(P_WEAPON)));
                if(!result.getString(P_ARMOR).equals("-")) hero.setArmor(getArmor(result.getString(P_ARMOR)));
                if (!result.getString(P_HELMET).equals("-")) hero.setHelmet(getHelmet(result.getString(P_HELMET)));
                heros.add(hero);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            closeDB();
        }
        return heros;
    }

    public PlayerCharacter getHero(String name) throws SQLException {

        PlayerCharacter hero = null;
        PlayerClasses playerClasses;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_PLAYER);
            preparedStatement.setString(1, name);
            result = preparedStatement.executeQuery();

            if (result.next()) {
                playerClasses = getClassName(result.getString(P_CLASS));
                hero = new PlayerCharacter(result.getString(P_NAME), playerClasses, result.getInt(P_LEVEL) * 100, result.getInt(P_ATTACK), result.getInt(P_HITPOINTS), result.getInt(P_DEFENSE), result.getInt(P_LEVEL), result.getInt(P_XP));
                if (!result.getString(P_WEAPON).equals("-")) hero.setWeapon(getWeapon(result.getString(P_WEAPON)));
                if(!result.getString(P_ARMOR).equals("-")) hero.setArmor(getArmor(result.getString(P_ARMOR)));
                if (!result.getString(P_HELMET).equals("-")) hero.setHelmet(getHelmet(result.getString(P_HELMET)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            closeDB();
        }
        return hero;
    }

    private void closeDB() {
        try {
            if (statement != null && !statement.isClosed()) statement.close();
            if (result != null && !result.isClosed()) result.close();
            if (preparedStatement != null && !preparedStatement.isClosed()) preparedStatement.close();
            if (updatePlayer != null && !updatePlayer.isClosed()) updatePlayer.close();
            if (insertPlayer != null && !insertPlayer.isClosed()) insertPlayer.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private Helmet getHelmet(String string) {
        Helmet helmet;

        switch(string.toLowerCase()) {
            case "bronze helmet":
                helmet = new BronzeHelmet();
                break;
            case "gold helmet":
                helmet = new GoldHelmet();
                break;
            case "platinum helmet":
                helmet = new PlatinumHelmet();
                break;
            default:
                helmet = new SilverHelmet();
                break;
        }
        return helmet;
    }

    private Armor getArmor(String string) {
        Armor armor;
        switch(string.toLowerCase()) {
            case "bronze shield":
                armor = new BronzeShield();
                break;
            case "gold shield":
                armor = new GoldShield();
                break;
            case "platinum shield":
                armor = new PlatinumShield();
                break;
            case "silver shield":
                armor = new SilverShield();
                break;
            default:
                armor = new WoodShield();
        }
        return armor;
    }

    private Weapons getWeapon(String string) {
        Weapons weapon;

        switch(string.toLowerCase()) {
            case "fists":
                weapon = new Fists();
                break;
            case "bronze bow":
                weapon = new BronzeBow();
                break;
            case "bronze dagger":
                weapon = new BronzeDagger();
                break;
            case "bronze sword":
                weapon = new BronzeSword();
                break;
            case "cross bow":
                weapon = new CrossBow();
                break;
            case "gold bow":
                weapon = new GoldBow();
                break;
            case "gold dagger":
                weapon = new GoldDagger();
                break;
            case "gold sword":
                weapon = new GoldSword();
                break;
            case "halberd":
                weapon = new Halberd();
                break;
            case "knightly sword":
                weapon = new KnightlySword();
                break;
            case "panabas":
                weapon = new Panabas();
                break;
            case "platinum bow":
                weapon = new PlatinumBow();
                break;
            case "platinum dagger":
                weapon = new PlatinumDagger();
                break;
            case "platinum sword":
                weapon = new PlatinumSword();
                break;
            case "silver bow":
                weapon = new SilverBow();
                break;
            case "silver dagger":
                weapon = new SilverDagger();
                break;
            case "silver sword":
                weapon = new SilverSword();
                break;
            case "spiked ball flails":
                weapon = new SpikedBallFlails();
                break;
            case "wood bow":
                weapon = new WoodBow();
                break;
            case "wood dagger":
                weapon = new WoodDagger();
                break;
            default:
                weapon = new WoodSword();
        }
        return weapon;
    }

    private PlayerClasses getClassName(String string) {
        PlayerClasses className;

        switch (string.toLowerCase()) {
            case "dimachaeri":
                className = PlayerClasses.DIMACHAERI;
                break;
            case "gaul":
                className = PlayerClasses.GAUL;
                break;
            case "hoplomachus":
                className = PlayerClasses.HOPLOMACHUS;
                break;
            case "murmillo":
                className = PlayerClasses.MURMILLO;
                break;
            case "retiarius":
                className = PlayerClasses.RETIARIUS;
                break;
            default:
                className = PlayerClasses.THRAEX;
                break;
        }
        return className;
    }

}
