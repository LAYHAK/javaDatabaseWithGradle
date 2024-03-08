package service;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import model.Person;
import repository.PersonRepository;
import utils.TableUtils;

import java.util.*;

public class PersonService {
    private final PersonRepository personRepository;
public PersonService() {
    personRepository = new PersonRepository();
}
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson() {
        return personRepository.getAllPerson();
    }

    public List<Person> getAllPersonDescendingByID() {
        return personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparingInt(Person::getId).reversed()
                        //  (a,b)-> b.getId() - a.getId()
                )
                .toList();
    }

    public List<Person> getAllPersonDescendingByName() {
        return personRepository.getAllPerson()
                .stream()
                .sorted(
                        Comparator.comparing(Person::getFullName).reversed()
                )
                .toList();
    }

    public static void searchByAddress(String searchCountry, PersonService personService) {
        List<Person> personListByCountry = personService.getAllPerson()
                .stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(searchCountry))
                .toList();
        TableUtils.renderPersonTable(personListByCountry);
    }
    public static void searchByID(int finalSearchID,PersonService personService) {
        Person optionalPerson =
                personService.getAllPerson()
                        .stream()
                        .filter(person -> person.getId() == finalSearchID)
                        .findFirst()
                        .orElseThrow(() -> new ArithmeticException("Whatever exception!! "));
        TableUtils.renderPersonTable(
                Collections.singletonList(optionalPerson));
    }
    public int createPerson(Scanner input) {
        return personRepository.addNewPerson(new Person().addPerson(input));
    }

    public List<Person> generateRandomPerson(int n) {
        return personRepository.generatePersonData(n);
    }



    public int deletePersonByID(Scanner input){
        System.out.println("Enter the Person ID : ");
        int id  = input.nextInt();
        try{
            personRepository.getAllPerson()
                    .stream().filter(
                     person -> person.getId() == id
                    ).findFirst()
                    .orElseThrow();
            return personRepository.deletePersonByID(id);
        }catch (NoSuchElementException ex ){
            System.out.println("There is no element  with id = "+id);
            return 0;
        }

    }

    public int updatePerson(Scanner input) {
        System.out.println("Enter the Person ID : ");
        int id = input.nextInt();
        try {
            // validation , condition
            var originalPerson = personRepository.getAllPerson()
                    .stream().filter(person -> person.getId() == id)
                    .findFirst().orElseThrow(); // throw  no such element if the person doesn't exist

            // ask the user to input the new updated value for the person
            // clear buffer
            input.nextLine();
            originalPerson.addPerson(input);
            return personRepository.updatePerson(originalPerson);
        } catch (NoSuchElementException ex) {
            System.out.println("There is no element with id = "+id);
            return 0;
        }
    }

    public void addNewPerson(List<Person> people) {
        try {
            for (Person person : people) {
                personRepository.addNewPerson(person);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
