package se.ecutb;


import se.ecutb.data.DataStorage;
import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class App
{

    private static DataStorage dataStorage;
    private static Scanner scanner;

    static {
        dataStorage = DataStorage.INSTANCE;
        scanner = new Scanner(System.in);
    }

    public static void main( String[] args )
    {
        boolean done = false;
        String message = "Running exercise...";
        do{
            System.out.println(menu());
            System.out.print("Choose from 1-13: ");
            int number = getInt();
            switch (number){
                case 0:
                    done = true;
                    break;
                case 1:
                    exercise1(message + number);
                    break;
                case 2:
                    exercise2(message + number);
                    break;
                case 3:
                    exercise3(message + number);
                    break;
                case 4:
                    exercise4(message + number);
                    break;
                case 5:
                    exercise5(message + number);
                    break;
                case 6:
                    exercise6(message + number);
                    break;
                case 7:
                    exercise7(message + number);
                    break;
                case 8:
                    exercise8(message + number);
                    break;
                case 9:
                    exercise9(message + number);
                    break;
                case 10:
                    exercise10(message + number);
                    break;
                case 11:
                    exercise11(message + number);
                    break;
                case 12:
                    exercise12(message + number);
                    break;
                case 13:
                    exercise13(message + number);
                    break;
            }


        }while(!done);
        scanner.close();
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
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message){
        System.out.println(message);
        Function<Person, String> personStringFunction = person -> "Name: " + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate();
        System.out.println(dataStorage.findOneAndMapToString(person -> person.getId() == 456, personStringFunction));
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
        Comparator<Person> comparator = Comparator.comparing(Person::getBirthDate).reversed();
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

    public static String menu(){
        StringBuilder stringBuilder = new StringBuilder("Please make a choice: \n");
        stringBuilder.append("0.\tQuit\n");
        stringBuilder.append("1.\tFind everyone that has firstName: “Erik” using findMany().\n");
        stringBuilder.append("2.\tFind all females in the collection using findMany().\n");
        stringBuilder.append("3.\tFind all who are born after (and including) 2000-01-01 using findMany().\n");
        stringBuilder.append("4.\tFind the Person that has an id of 123 using findOne().\n");
        stringBuilder.append("5.\tFind the Person that has an id of 456 and convert to String with following content:\n" +
                "            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().\n");
        stringBuilder.append("6.\tFind all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().\n");
        stringBuilder.append("7.\tFind all people who are below age of 10 and convert them to a String like this:\n" +
                "            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.\n");
        stringBuilder.append("8.\tUsing findAndDo() print out all people with firstName “Ulf”.\n");
        stringBuilder.append("9.\tUsing findAndDo() print out everyone who have their lastName contain their firstName.\n");
        stringBuilder.append("10.\tUsing findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.\n");
        stringBuilder.append("11.\tUsing findAndSort() find everyone whose firstName starts with A sorted by birthDate.\n");
        stringBuilder.append("12.\tUsing findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.\n");
        stringBuilder.append("13.\tUsing findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.\n");
        return stringBuilder.toString();
    }

    public static int getInt(){
        int number = 0;
        boolean validNumber = false;
        while(!validNumber){
            try{
                number = Integer.parseInt(scanner.nextLine().trim());
                validNumber = number >= 0 && number < 14 ? true : false;
            }catch (NumberFormatException ex){
                System.out.println("Not a integer");
            }
            if(!validNumber){
                System.out.println("Try again");
            }
        }

        return number;
    }



}
