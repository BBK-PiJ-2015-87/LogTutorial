import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vladimirsivanovs on 16/10/2015.
 */
public class Test {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("name=Vlad");
        list.add("surname=Ivanovs");
        list.add("address=7 Somerville Point");

        parseList(list);
    }

    public static void parseList(List<String> list) {
        Map<String, String> mapString = new HashMap<>();
        list.stream()
                .map(x -> x.split("="))
                .forEach(x -> System.out.println(x[1]));


//        mapString.put("Vlad", "1");
//        mapString.put("Vlad2", "2");
//        mapString.put("Vlad3", "3");

//        for (String key : mapString.keySet()) {
//            System.out.print(key + " : " + mapString.get(key));
//            System.out.println();
//        }
    }



}
