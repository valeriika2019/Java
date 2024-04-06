package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService{
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "odmen";
    public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ? AND player = ?";
    public static final String SELECT_ALL = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ?";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?) ON CONFLICT (game, player) DO UPDATE SET rating = EXCLUDED.rating, ratedOn = EXCLUDED.ratedOn";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem inserting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                List<Rating> rating = new ArrayList<>();
                while (rs.next()) {
                    rating.add(new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                int avgRating = 0;
                for(Rating r : rating) {
                    avgRating += r.getRating();
                }
                avgRating = avgRating / rating.size();
                return avgRating;
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                List<Rating> rating = new ArrayList<>();
                while (rs.next()) {
                    rating.add(new Rating(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));
                }
                return rating.get(0).getRating();
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting rating", e);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting rating", e);
        }
    }
}
