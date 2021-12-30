package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DatabaseConnection {

    private static final String url = "jdbc:sqlite:database";

    public DatabaseConnection(){
            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void createNewTable() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS results (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	nickname varchar(255) NOT NULL,\n"
                + "	score integer\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<GameResult> selectAllResults(){
        String sql = "SELECT id, nickname, score FROM results order by score desc";
        ArrayList<GameResult> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                resultList.add(new GameResult(rs.getString("nickname"),
                                             rs.getInt("score")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public void insertResult(GameResult result){
        String sql = "INSERT INTO results(nickname, score) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, result.nickname());
            pstmt.setInt(2, result.score());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasNickname(String nickname){
        String sql = "SELECT nickname, score from results where nickname = ?";
        Set<GameResult> resultSet = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, nickname);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}