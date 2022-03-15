package frc.robot;

public final class Util {
    private static String capitalizeWord(String word) {
        String firstLetter = word.substring(0, 1).toUpperCase();
        return firstLetter + word.substring(1).toLowerCase();
    }

    public static String capitalize(String string, boolean onlyFirstWord) {
        if (onlyFirstWord) {
            return capitalizeWord(string);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            String[] words = string.trim().split("\\s+"); // Regex for whitespace

            for (String word : words) {
                stringBuilder.append(capitalizeWord(word));
            }

            return stringBuilder.toString();
        }
    }
}
