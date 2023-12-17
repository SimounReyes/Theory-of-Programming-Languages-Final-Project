package Project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Enter Source Language: int num = 2;
 * 
 * Returns a 2d array of Strings
 * 
 * 1st row: int num = 2 ;
 * 2nd row: <data_type> <identifier> <assign_op> <value> <delimiter>
 */
public class Lexical {
    String lexi = "\n\n";

    public Lexical(String file) {
        String[][] results = lexical_analyzer(file);

        String[][] arrow_downs = new String[3][results[0].length];
        for (int i = 0; i < results[0].length; i++) {
            int len0 = results[0][i].length();
            int len1 = results[1][i].length();

            int larger = 0;

            if (len0 > len1) {
                results[1][i] = center(results[1][i], len0);
                larger = len0;
            } else {
                results[0][i] = center(results[0][i], len1);
                larger = len1;
            }

            arrow_downs[0][i] = center("|", larger);
            arrow_downs[1][i] = center("|", larger);
            arrow_downs[2][i] = center("V", larger);
        }

        lexi += String.join(" ", results[0]) + "\n\n";
        lexi += String.join(" ", arrow_downs[0]) + "\n";
        lexi += String.join(" ", arrow_downs[1]) + "\n";
        lexi += String.join(" ", arrow_downs[2]) + "\n\n";
        lexi += String.join(" ", results[1]) + "\n";

    }

    public static boolean contains(String[] array, String str) {
        for (String ele : array)
            if (str != null && str.equals(ele))
                return true;

        return false;
    }

    public static String center(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    public static String[][] lexical_analyzer(String input) {
        String[] supported_data_types = { "int", "double", "char", "String", "boolean" };

        Pattern val_pat = Pattern.compile("true|false|'.'|\"[^\"]*\"|\\d+.\\d+|\\d+");
        Pattern ident_pat = Pattern.compile("\\b_*[a-zA-Z][_a-zA-Z0-9]*\\b");
        Pattern operator_pat = Pattern.compile("[+\\-*/]|\\|\\||\\&\\&|[><=]=*");

        // lexeme regex rules
        String[] regex_lexeme = {
                ";", // delimeters
                "[><=]=*|=|[+\\-*/]", // operators
                "\"[^\"]*\"", // quotesDouble
                "'[^']*'", // quotesSingle
                "\\d+\\.\\d+", // decimals
                "[a-zA-Z0-9]+", // alphaNumeric
                "[^a-zA-Z0-9\";'=\\s]+" // others except spaces
        };

        Pattern lex_pat = Pattern.compile(String.join("|", regex_lexeme));
        Matcher lexemes_result = lex_pat.matcher(input);

        int leng = (int) lexemes_result.results().count();
        lexemes_result.reset();
        String[][] tokens = new String[2][leng];

        for (int i = 0; i < leng; i++) {
            String lexeme = "";
            lexemes_result.find();
            lexeme = lexemes_result.group();

            tokens[0][i] = lexeme;
            if (lexeme.equals("="))
                tokens[1][i] = "<assign_op>";
            else if (lexeme.equals(";"))
                tokens[1][i] = "<delimiter>";
            else if (contains(supported_data_types, lexeme))
                tokens[1][i] = "<data_type>";
            else if (val_pat.matcher(lexeme).matches())
                tokens[1][i] = "<value>";
            else if (ident_pat.matcher(lexeme).matches())
                tokens[1][i] = "<identifier>";
            else if (operator_pat.matcher(lexeme).matches())
                tokens[1][i] = "<operator>";
            else
                tokens[1][i] = "<invalid_lexeme>";
        }

        return tokens;
    }
}