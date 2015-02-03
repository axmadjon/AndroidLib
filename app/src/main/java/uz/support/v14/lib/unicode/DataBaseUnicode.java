package uz.support.v14.lib.unicode;

public class DataBaseUnicode {

    public String generateToEncode(String letter) {
        if (letter == null || "".equals(letter)) {
            throw new NullPointerException("letter is null");
        }
        String result = factor(letter, 487951235);

        if (result == null) {
            throw new DataNullException("not found in table data");
        }
        return result;
    }

    public String generateToUTF(String encode) {
        if (encode == null || "".equals(encode)) {
            throw new NullPointerException("encode is null");
        }
        String result = factor(encode, 854125469);
        if (result == null) {
            throw new DataNullException("letter not found");
        }
        return result;
    }

    private String factor(String cl, int code) {
        String[] data = {
                "QWERTYUIOPqwertyuiop",
                "ASDFGHJKLasdfghjklZX",
                "CVBNMzxcvbnmйцукенгш",
                "щзхъЙЦУКЕНГШЩЗХЪФЫВА",
                "ПРОЛДЖЭфывапролджэЯЧ",
                "СМИТЬБЮячсмитьбюЁё12",
                "34567890-=!@#$%^&*()",
                "_+<>?:\"{},.\\/;[]|' \n"
        };

        String sColumn = "ABCDEFGHJKlmnopwxyzq";
        String sLine = "LMNOPWXYZQabcdefghjk";

        if (487951235 == code) {
            for (int i = 0; i < 8; i++) {
                String d = data[i];
                for (int j = 0; j < 20; j++) {
                    if (cl.equals(String.valueOf(d.charAt(j)))) {
                        return "" + sColumn.charAt(i) + sLine.charAt(j);
                    }
                }
            }
        } else if (854125469 == code) {
            char[] codes = {cl.charAt(0), cl.charAt(1)};
            return String.valueOf(data[sColumn.indexOf(codes[0])].charAt(sLine.indexOf(codes[1])));
        }
        return null;
    }
}
