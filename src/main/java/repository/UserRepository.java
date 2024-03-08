package repository;

import com.github.javafaker.Faker;
import model.User;
import utils.PropertyUtils;
import utils.SQLUtils;
import utils.SQLUtils.PersonSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static utils.SQLUtils.PersonSQL.*;

public class UserRepository {

    private final Properties properties;


    public UserRepository() {
        properties = PropertyUtils.loadProperty();
    }

    public boolean loginUser(String username, String password) {
        try (Connection connection = startDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_USER)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error checking user credentials.");
            ex.printStackTrace();
        }

        return false; // Default to invalid
    }

    private Connection startDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("DB_URL"),
                properties.getProperty("USERNAME"),
                properties.getProperty("PASSWORD")
        );
    }

    public List<User> getAllUser() {
        try (
                Connection connection = startDatabaseConnection();
                Statement statement = connection.createStatement();
        ) {
            List<User> userList = new ArrayList<>();
            ResultSet rs = statement.executeQuery(GET_ALL_USER);
            while (rs.next()) {
                userList.add(
                        new User()
                                .setId(rs.getInt("id"))
                                .setUsername(rs.getString("username"))
                                .setPassword(rs.getString("password"))
                );
            }
            return userList;

        } catch (SQLException ex) {
            System.out.println("Failed to retreive all the person data ! ");
            ex.printStackTrace();
        }

        return null;
    }

    public int addNewUser(User user) {
        try (
                Connection connection = startDatabaseConnection();
                PreparedStatement ps = connection.prepareStatement(INSERT_USER);
        ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error when adding a new person");
            ex.printStackTrace();
        }
        return 0;
    }

    public List<User> generateUserData(int n) {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            users.add(new User()
                    .setUsername(faker.name().username())
                    .setPassword(faker.internet().password())
            );
        }
        return users;
    }

    public int updateUser(User updatedUser) {
        try
                (
                        Connection connection = startDatabaseConnection();
                        PreparedStatement ps = connection.prepareStatement(UPDATE_USER)
                ) {

            ps.setString(1, updatedUser.getUsername());
            ps.setString(2, updatedUser.getPassword());

            return ps.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public int deleteUserByID(int userID) {
        try (
                Connection connection = startDatabaseConnection();
                PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID)

        ) {
            ps.setInt(1, userID);
            return ps.executeUpdate(); // return int -> number of records that we deleted !

        } catch (SQLException ex) {
            System.out.println("Failed to delete the person record with ID = " + userID);
            ex.printStackTrace();
            return 0;
        }

    }


}
