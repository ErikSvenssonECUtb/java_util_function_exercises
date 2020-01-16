package se.ecutb.data;

import se.ecutb.model.Person;
import se.ecutb.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Skapa implementationer till alla metoder. Jag har redan skrivit en metodimplementation för hjälp
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private List<Person> personList;

    private DataStorageImpl(){
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance(){
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        List<Person> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        for(Person person : personList){
            if(filter.test(person)){
                return person;
            }
        }
        return null;
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString){
        Person person = findOne(filter);
        if(person == null){
            return "Not found";
        }
        return personToString.apply(person);
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString){
       List<String> stringList = new ArrayList<>();
       for(Person person : findMany(filter)){
           stringList.add(personToString.apply(person));
       }
       return stringList;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer){
        for(Person person : findMany(filter)){
            consumer.accept(person);
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator){
        List<Person> copy = new ArrayList<>(personList);
        Collections.sort(copy, comparator);
        return copy;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator){
        List<Person> people = findMany(filter);
        people.sort(comparator);
        return  people;
    }
}
