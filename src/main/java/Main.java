import model.User;
import repository.PersonRepository;
import repository.UserRepository;
import service.PersonService;
import service.UserService;
import utils.TableUtils;
import view.MainView;

import java.util.*;

import static service.PersonService.searchByAddress;
import static service.PersonService.searchByID;
import static view.MainView.*;

public class Main {
    private static final PersonService PERSON_SERVICE =
            new PersonService(new PersonRepository());
    private static final Scanner INPUT = new Scanner(System.in);
    private static final UserService USER_SERVICE = new UserService(new UserRepository());

    public static void main(String[] args) {
        int option;
        User user = new User();
        user.checkUser(INPUT);
        do {
            option = MainView.renderMain(MainView.MAIN_MENU, INPUT);
            switch (option) {
                case 1: {
                    INPUT.nextLine(); // clear buffer
                    System.out.println(
                            PERSON_SERVICE.createPerson(INPUT) > 0 ?
                                    "Successfully Created a New Person"
                                    : ""
                    );
                }
                break;
                case 2: {
                    System.out.println(
                            PERSON_SERVICE
                                    .updatePerson(INPUT) > 0 ?
                                    "Successfully Update Person Info"
                                    : ""
                    );
                }
                break;
                case 3: {
                    System.out.println(
                            PERSON_SERVICE
                                    .deletePersonByID(INPUT) > 0 ?
                                    "Successfully Remove the Person"
                                    : "");
                    ;
                }
                break;
                case 4: {
                    showPersonList(INPUT, PERSON_SERVICE);
                }
                break;
                case 5: {
                    searchPersonList();
                }
                break;
                case 6:
                    System.out.println("Generating Random Person Data");
                    System.out.println("Enter the number of person to generate: ");
                    int n = INPUT.nextInt();
                    PERSON_SERVICE.generateRandomPerson(n);
                    PERSON_SERVICE.addNewPerson(PERSON_SERVICE.generateRandomPerson(n));
                    break;
                case 7:
                    int userOption;
                    do{

                        TableUtils.renderMenu(USER_MENU, "User Management System");
                        System.out.print("Enter your option : ");
                        userOption = INPUT.nextInt();
                        switch (userOption) {
                            case 1:
                                USER_SERVICE.createUser(INPUT);
                                break;
                            case 2:
                                USER_SERVICE.updateUser(INPUT);
                                break;
                            case 3:
                                System.out.println("Delete User");
                                break;
                            case 4:
                                TableUtils.renderUserTable(USER_SERVICE.getAllUser());
                                break;
                            case 5:
                                System.out.println("Generating Random User Data");
                                System.out.println("Enter the number of user to generate: ");
                                int num = INPUT.nextInt();
                            USER_SERVICE.generateRandomPerson(num);
                                break;
                            case 6:
                                System.out.println("Good Bye");
                                break;
                            default:
                                System.out.println("Invalid Option!!!!!! ");
                                break;
                        }
                    }while (userOption != 3);
                case 8:
                    user.checkUser(INPUT);
                    break;
                case 9:
                    System.out.println("Good Bye");
                   break;
                default:
                    System.out.println("Invalid Option!!!!!! ");
                    break;
            }
        } while (option != 9);


    }

    private static void searchPersonList() {
        int searchOption;
        do {
            TableUtils.renderMenu(searchMenu, "Search for Person");
            System.out.print("Choose your option:");
            searchOption = INPUT.nextInt();
            switch (searchOption) {
                case 1 -> {
                    System.out.println("Enter Person ID to search:");
                    int searchID = INPUT.nextInt();
                    try {
                        searchByID(searchID, PERSON_SERVICE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("There is no element with ID=" + searchID);
                    }
                }
                case 2 -> {
                    System.out.println("Enter Person Gender to search:");
                    String searchGender = INPUT.nextLine();
                    searchByGender(searchGender, PERSON_SERVICE);
                }
                case 3 -> {
                    System.out.println("Enter Person Country to search:");
                    String searchCountry = INPUT.nextLine();
                    searchByAddress(searchCountry, PERSON_SERVICE);
                }
            }
        } while (searchOption != searchMenu.size());
    }


}
