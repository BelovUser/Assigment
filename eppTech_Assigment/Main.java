import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("Anna");
        words.add("Hannah");
        words.add("Mom");
        words.add("Apple");
        words.add("DefinitelyNotPalindrome");
        words.add("noon");
        words.add("lEVeL");

        System.out.println(words.toString());
        System.out.println("--------------------");
        System.out.println(palindromesOnly(words).toString());

    }

    private static List<String> palindromesOnly(List<String> words) {
        return words.stream()
                    .filter(Main::isPalindrome)
                    .toList();
    }

     private static boolean isPalindrome(String word) {
        String normalizedWord = word.toLowerCase();
        return new StringBuilder(normalizedWord).reverse().toString().equals(normalizedWord);
    }

}
