package view;

import model.Person;
import service.PersonService;
import utils.TableUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainView {
    public final static List<String> MAIN_MENU = new ArrayList<>(List.of(
            "Add New Person ",
            "Update Person ",
            "Delete Person",
            "Show Person Information",
            "Search Person Information",
            "Generate Random Person",
            "list to work with user",
            "Logout",
            "Exit"));

    public final static List<String> USER_MENU = new ArrayList<>(Arrays.asList(
            "Add New User",
            "Update User",
            "Delete User",
            "Show User Information",
            "Generate Random User",
            "Exit"));
    public final static List<String> SHOW_PERSON_INFORMATION = new ArrayList<>(List.of(
            "Show Original Order",
            "Show Descending Order (ID)",
            "Show Descending Order (name) ",
            "Exit"));

    public static int renderMain(List<String> e, Scanner input) {
        TableUtils.renderMenu(e, "Person Management System");
        System.out.print("Enter your option : ");
        return input.nextInt();
    }

    public static List<String> searchMenu = new ArrayList<>(Arrays.asList(
            "Search By ID",
            "Search By Gender",
            "Search By Country",
            "Exit"));
    public static void searchByGender(String searchGender, PersonService personService) {
        List<Person> personList = personService.getAllPerson()
                .stream()
                .filter(person -> person.getGender().equalsIgnoreCase(searchGender))
                .toList();
        TableUtils.renderPersonTable(personList);
    }

    public static void showPersonList(Scanner INPUT, PersonService PERSON_SERVICE) {
        int showOption;
        do {
            TableUtils.renderMenu(SHOW_PERSON_INFORMATION, "Show Person Information");
            System.out.print("Choose your option: ");
            showOption = INPUT.nextInt();
            switch (showOption) {
                case 1 -> TableUtils.renderPersonTable(PERSON_SERVICE.getAllPerson());
                case 2 ->
                    // descending id
                        TableUtils.renderPersonTable(
                                PERSON_SERVICE.getAllPersonDescendingByID()
                        );
                case 3 ->
                    // descending name
                        TableUtils.renderPersonTable(
                                PERSON_SERVICE.getAllPersonDescendingByName()
                        );
                case 4 -> System.out.println("Good Bye");
                default -> System.out.println("Invalid option ...!!!!");
            }
        } while (showOption != SHOW_PERSON_INFORMATION.size());
    }
}
