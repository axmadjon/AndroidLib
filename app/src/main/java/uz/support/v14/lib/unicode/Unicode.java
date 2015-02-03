package uz.support.v14.lib.unicode;

public class Unicode {

    private static final DataBaseUnicode db = new DataBaseUnicode();

    private Unicode() {
    }

    public static String generateToEncode(String letter) {
        DataBaseUnicode db = new DataBaseUnicode();
        String result = "";
        for (int i = 0; i < letter.length(); i++) {
            result += db.generateToEncode(String.valueOf(letter.charAt(i)));
        }
        return result;
    }

    public static String generateToUTF(String encode) {
        String result = "";
        for (int i = 0; i < encode.length(); i++) {
            result += db.generateToUTF(encode.substring(i, i + 2));
            i++;
        }
        return result;
    }


}
