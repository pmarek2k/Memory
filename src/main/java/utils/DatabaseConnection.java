package utils;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private static final String url = "jdbc:sqlite:database";
    private static Connection conn = null;

    public DatabaseConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
        return false;
    }
}