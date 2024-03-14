import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NicknameGenerator {

    private static AtomicInteger beautifulWordsLength3 = new AtomicInteger(0);
    private static AtomicInteger beautifulWordsLength4 = new AtomicInteger(0);
    private static AtomicInteger beautifulWordsLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        String[] texts = generateTexts(100_000);

        Thread palindromeThread = new Thread(() -> countBeautifulPalindromes(texts));
        Thread sameLetterThread = new Thread(() -> countBeautifulSameLetters(texts));
        Thread increasingLettersThread = new Thread(() -> countBeautifulIncreasingLetters(texts));

        palindromeThread.start();
        sameLetterThread.start();
        increasingLettersThread.start();

        try {
            palindromeThread.join();
            sameLetterThread.join();
            increasingLettersThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Красивых слов с длиной 3: " + beautifulWordsLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautifulWordsLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautifulWordsLength5.get() + " шт");
    }

    public static String[] generateTexts(int numTexts) {
        Random random = new Random();
        String[] texts = new String[numTexts];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void countBeautifulPalindromes(String[] texts) {
        for (String text : texts) {
            if (isPalindrome(text)) {
                switch (text.length()) {
                    case 3:
                        beautifulWordsLength3.incrementAndGet();
                        break;
                    case 4:
                        beautifulWordsLength4.incrementAndGet();
                        break;
                    case 5:
                        beautifulWordsLength5.incrementAndGet();
                        break;
                }
            }
        }
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left++) != text.charAt(right--)) {
                return false;
            }
        }
        return true;
    }

    public static void countBeautifulSameLetters(String[] texts) {
        for (String text : texts) {
            if (isSameLetters(text)) {
                switch (text.length()) {
                    case 3:
                        beautifulWordsLength3.incrementAndGet();
                        break;
                    case 4:
                        beautifulWordsLength4.incrementAndGet();
                        break;
                    case 5:
                        beautifulWordsLength5.incrementAndGet();
                        break;
                }
            }
        }
    }

    public static boolean isSameLetters(String text) {
        char firstLetter = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstLetter) {
                return false;
            }
        }
        return true;
    }

    public static void countBeautifulIncreasingLetters(String[] texts) {
        for (String text : texts) {
            if (isIncreasingLetters(text)) {
                switch (text.length()) {
                    case 3:
                        beautifulWordsLength3.incrementAndGet();
                        break;
                    case 4:
                        beautifulWordsLength4.incrementAndGet();
                        break;
                    case 5:
                        beautifulWordsLength5.incrementAndGet();
                        break;
                }
            }
        }
    }

    public static boolean isIncreasingLetters(String text) {
        char currentLetter = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            char nextLetter = text.charAt(i);
            if (nextLetter < currentLetter) {
                return false;
            }
            currentLetter = nextLetter;
        }
        return true;
    }
}
