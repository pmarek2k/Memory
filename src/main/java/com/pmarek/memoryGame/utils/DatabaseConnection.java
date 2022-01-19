package com.pmarek.memoryGame.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private static final String ApplicationDataFolder = System.getenv("APPDATA");
    private static final String url = "jdbc:sqlite:" + ApplicationDataFolder;
    private static Connection conn = null;
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    public static Connection getConn() {
        return conn;
    }

    public DatabaseConnection() {
        if (conn == null) {
            File directory = new File(ApplicationDataFolder + "\\Memory");
            if(!directory.exists()){
                if(!directory.mkdir()){
                    logger.error("Failed to create game data directory");
                }
            }
            try {
                conn = DriverManager.getConnection(url + "\\Memory\\data.db");
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    logger.info("The driver name is " + meta.getDriverName());
                    logger.info("A new database connection has been created.");
                }

            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void createNewTable() {

        // SQL statement for creating a new table
        String sql = """
                CREATE TABLE IF NOT EXISTS results (
                	id integer PRIMARY KEY AUTOINCREMENT,
                	nickname varchar(255) NOT NULL,
                	score integer
                );""";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public ArrayList<GameResult> selectAllResults() {
        String sql = "SELECT id, nickname, score FROM results order by score desc";
        ArrayList<GameResult> resultList = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                resultList.add(new GameResult(rs.getString("nickname"),
                        rs.getInt("score")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return resultList;
    }

    public void insertResult(GameResult result) {
        String sql = "INSERT INTO results(nickname, score) VALUES(?,?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, result.nickname());
            preparedStatement.setInt(2, result.score());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public boolean hasNickname(String nickname) {
        String sql = "SELECT nickname, score from results where nickname = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, nickname);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}