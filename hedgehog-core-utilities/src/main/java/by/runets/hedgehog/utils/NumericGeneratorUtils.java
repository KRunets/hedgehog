package by.runets.hedgehog.utils;

public class NumericGeneratorUtils {
    private NumericGeneratorUtils(){
    }

    public static int generateNumeric(Long length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(9);
        }
        return Integer.parseInt(stringBuilder.toString());
    }
}
