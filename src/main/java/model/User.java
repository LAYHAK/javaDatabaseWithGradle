package model;

import lombok.*;
import lombok.experimental.Accessors;
import repository.UserRepository;

import java.util.Scanner;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    protected int id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void checkUser(Scanner input){
    while (true) { // Loop until valid credentials are provided
        System.out.println("Enter username: ");
        String username = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.nextLine();
        UserRepository userRepository = new UserRepository();
        if (userRepository.loginUser(username, password)) {
            System.out.println("Login successful!");
            new User( username, password);
            return; // Create a User object
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }


    }
    public User addUser(Scanner input){
        input.nextLine();
        System.out.println("Enter username: ");
        String username = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.nextLine();
        return new User(username, password);
    }
}
