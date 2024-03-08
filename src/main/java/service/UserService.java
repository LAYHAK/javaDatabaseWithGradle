package service;

import model.User;
import repository.UserRepository;
import utils.TableUtils;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return userRepository.getAllUser();
    }


    public static void searchByID(int finalSearchID, UserService userService) {
        User optionalUser =
                userService.getAllUser()
                        .stream()
                        .filter(user -> user.getId() == finalSearchID)
                        .findFirst()
                        .orElseThrow(() -> new ArithmeticException("Whatever exception!! "));
        TableUtils.renderUserTable(
                Collections.singletonList(optionalUser));
    }

    public int createUser(Scanner input) {
        return userRepository.addNewUser(new User().addUser(input));
    }

    public List<User> generateRandomPerson(int n) {
        return userRepository.generateUserData(n);
    }


    public int deletePersonByID(Scanner input) {
        System.out.println("Enter the Person ID : ");
        int id = input.nextInt();
        try {
            userRepository.getAllUser()
                    .stream().filter(
                            user -> user.getId() == id
                    ).findFirst()
                    .orElseThrow();
            return userRepository.deleteUserByID(id);
        } catch (NoSuchElementException ex) {
            System.out.println("There is no element  with id = " + id);
            return 0;
        }

    }

    public int updateUser(Scanner input) {
        System.out.println("Enter the Person ID : ");
        int id = input.nextInt();
        try {
            // validation , condition
            var originalUser = userRepository.getAllUser()
                    .stream().filter(user -> user.getId() == id)
                    .findFirst().orElseThrow();
            // clear buffer
            input.nextLine();
            originalUser.addUser(input);
            return userRepository.updateUser(originalUser);
        } catch (NoSuchElementException ex) {
            System.out.println("There is no element with id = " + id);
            return 0;
        }
    }

    public void addNewPerson(List<User> users) {
        try {
            for (User user : users) {
                userRepository.addNewUser(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
