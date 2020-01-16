package se.ecutb;


import se.ecutb.data.DataStorage;
import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class App
{

    private static DataStorage dataStorage;

    static {
        dataStorage = DataStorage.INSTANCE;
    }

    public static void main( String[] args )
    {
        String message = "Running exercise...";
        exercise1(message + "1");
        exercise2(message + "2");
        exercise3(message + "3");
        exercise4(message + "4");
        exercise5(message + "5");
        exercise6(message + "6");
        exercise7(message + "7");
        exercise8(message + "8");
        exercise9(message + "9");
        exercise10(message + "10");
        exercise11(message + "11");
        exercise12(message + "12");
        exercise13(message + "13");
    }

    /*
        1.	Find everyone that has firstName: “Erik” using findMany().
     */
    public static void exercise1(String message){
        System.out.println(message);
        dataStorage.findMany(person -> person.getFirstName().equalsIgnoreCase("Erik"))
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message){
        System.out.println(message);
        dataStorage.findMany(person -> person.getGender() == Gender.FEMALE)
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message){
        System.out.println(message);
        dataStorage.findMany(person -> person.getBirthDate().isAfter(LocalDate.parse("1999-12-31")))
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message){
        System.out.println(message);
        System.out.println(dataStorage.findOne(person -> person.getId() == 123));
        System.out.println("----------------------");

    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
     */
    public static void exercise5(String message){
        System.out.println(message);
        System.out.println(dataStorage.findOneAndMapToString(person -> person.getId() == 456, Person::toString));
        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message){
        System.out.println(message);
        Predicate<Person> filter = person -> person.getFirstName().startsWith("E") && person.getGender() == Gender.MALE;
        dataStorage.findManyAndMapEachToString(filter, Person::toString)
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message){
        System.out.println(message);
        Function<Person, String> mapper =
                        person -> person.getFirstName() + " " +
                        person.getLastName() + " " +
                        Period.between(person.getBirthDate(), LocalDate.now()).getYears() + " years";

        Predicate<Person> personPredicate = person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10;
        dataStorage.findManyAndMapEachToString(personPredicate, mapper)
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message){
        System.out.println(message);
        dataStorage.findAndDo(person -> person.getFirstName().equalsIgnoreCase("Ulf"), System.out::println);
        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message){
        System.out.println(message);
        Predicate<Person> lastNameContainsFirstName = person ->  person.getLastName().toLowerCase().contains(person.getFirstName().toLowerCase());
        dataStorage.findAndDo(lastNameContainsFirstName, System.out::println);
        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message){
        System.out.println(message);
        Predicate<Person> firstNameIsPalindrome =
                person -> new StringBuilder(person.getFirstName())
                .reverse()
                .toString()
                .equalsIgnoreCase(person.getFirstName());

        Consumer<Person> printer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        dataStorage.findAndDo(firstNameIsPalindrome, printer);
        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message){
        System.out.println(message);
        Predicate<Person> firstNameStartsWithA = person -> person.getFirstName().charAt(0) == 'A';
        Comparator<Person> personComparatorBirthDate = (person1, person2) -> person1.getBirthDate().compareTo(person2.getBirthDate());
        //...comparator can be replaced with Comparator.comparing(Person::getBirthDate)
        dataStorage.findAndSort(firstNameStartsWithA, personComparatorBirthDate)
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message){
        System.out.println(message);
        Predicate<Person> bornBefore1950 = person -> person.getBirthDate().getYear() < 1950;
        Comparator comparator = Comparator.comparing(Person::getBirthDate).reversed();
        dataStorage.findAndSort(bornBefore1950, comparator)
                .forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message){
        System.out.println(message);
        Comparator<Person> chain = Comparator.comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate);

        dataStorage.findAndSort(chain)
            .forEach(System.out::println);
        System.out.println("----------------------");
    }



}
