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
        List<String> palindromes = new ArrayList<>();
        for (String word : words) {
            if (isPalindrome(word)) {
                palindromes.add(word);
            }
        }
        return palindromes;
    }

    private static boolean isPalindrome(String word){
        String lowerCaseWord = word.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i = lowerCaseWord.length() - 1; i >= 0; i--) {
            sb.append(lowerCaseWord.charAt(i));
        }
        return sb.toString().equals(lowerCaseWord);
    }

}
