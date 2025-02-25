import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Predict implements Command{

    public static Map<String, String> predict(String str) {
        str = str.replaceAll("[^A-Za-z0-9]+", " ").toLowerCase(Locale.ROOT);
        List<String> arr = Arrays.asList(str.split(" "));

        List<String> uniq = arr.stream().distinct().collect(Collectors.toList());

        Map<String, String> freqWord = new Hashtable<>();

        for (int index = 0; index < uniq.size(); index++) {

            // List de tous les suivants
            List<String> list = new ArrayList<>();
            for (int w = 0; w < arr.size() - 1; w++) {
                if (arr.get(w).equals(uniq.get(index))) {
                    list.add(arr.get(w + 1));
                }
            }

            List<String> li = list.stream().distinct().collect(Collectors.toList());
            var freqMap = list.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            long max = 0;
            String st = "";
            for (String s: li) {
                if (max < freqMap.get(s)){
                    st = s;
                    max= freqMap.get(s);
                }
            }

            //System.out.printf(freqMap.toString());
            //var max = freqMap.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).map(Map.Entry<String, Long>::getKey).orElse(null);
            freqWord.put(uniq.get(index), st);
        }

        return freqWord;
    }

    public static void interactUser(Scanner console, Map<String, String> freq){
        System.out.printf("Enter a word: ");
        String word = console.nextLine().toLowerCase();
        System.out.printf(word + " ");
        if (freq.containsKey(word)){
            for (int i = 0; i < 19; i++){
                System.out.printf(freq.get(word) + " ");
                word = freq.get(word).toString();
            }
            System.out.printf("\n");
        }
        else {
            System.out.printf("Word does not exist");
        }
    }

    @Override
    public String name() {
        return "predict";
    }

    @Override
    public boolean run(Scanner console) {
        System.out.printf("Enter a path: ");
        String path = console.nextLine();
        Path filepath = Paths.get(path);

        try {
            String content = Files.readString(filepath);
            Map<String, String> freq = predict(content);
            interactUser(console, freq);
        }
        catch (IOException e) {
            System.out.printf("Unreadable file: ");
            e.printStackTrace();
        }
        return true;
    }
}