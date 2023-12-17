package Project;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * Enter Expression: int x = 2.0;
 * Semantically Incorrect!
 */

public class Semantics {
    String sem = " ";
    static String global_type_result = "";

    public Semantics(String file) {
        String[][] tokens = Lexical.lexical_analyzer(file);

        String results = "Semantically ";
        results += semantics_analyzer(tokens) ? "Correct!" : "Incorrect!";

        sem = results + "\n\n";

        List<String> row1 = new ArrayList<String>();
        List<String> row2 = new ArrayList<String>();

        for (int i = 0; i < tokens[0].length; i++) {
            String current = tokens[1][i];

            switch (current) {
                case "<data_type>":
                    row2.add(tokens[0][i]);
                    break;
                case "<identifier>":
                    row1.add(tokens[0][i]);
                    break;
                case "<assign_op>":
                    row1.add("=");
                    row2.add(" compare to ");
                    break;
                case "<value>":
                    row1.add(tokens[0][i]);
                    break;
                case "<operator>":
                    row1.add(tokens[0][i]);
                    break;
                case "<delimiter>":
                    if (i > 2) {
                        if (global_type_result.equals("")) {
                            global_type_result = "<Could not determine type>";
                        }
                        row2.add(global_type_result);
                    }

                default:
            }
        }

        String[] arrayRow2 = row2.toArray(new String[0]);
        int leng = arrayRow2.length;
        String[] arrayRow1 = new String[leng];
        arrayRow1[leng - 1] = "";

        for (int i = 0; i < row1.size(); i++) {
            String current = row1.get(i);

            if (i < leng - 1) {
                arrayRow1[i] = current;
            } else {
                arrayRow1[leng - 1] += current + " ";
            }
        }

        arrayRow1[leng - 1] = arrayRow1[leng - 1].trim();

        String[][] arrow_downs = new String[3][arrayRow1.length];
        for (int i = 0; i < arrayRow1.length; i++) {
            int len1 = arrayRow1[i].length();
            int len2 = arrayRow2[i].length();

            int larger = 0;

            if (len1 > len2) {
                arrayRow2[i] = center(arrayRow2[i], len1);
                larger = len1;
            } else {
                arrayRow1[i] = center(arrayRow1[i], len2);
                larger = len2;
            }

            arrow_downs[0][i] = center("|", larger);
            arrow_downs[1][i] = center("|", larger);
            arrow_downs[2][i] = center("V", larger);
        }

        sem += String.join(" ", arrayRow1) + "\n\n";
        sem += String.join(" ", arrow_downs[0]) + "\n";
        sem += String.join(" ", arrow_downs[1]) + "\n";
        sem += String.join(" ", arrow_downs[2]) + "\n\n";
        sem += String.join(" ", arrayRow2) + "\n";
    }

    public static String data_type_analyzer(String target) {
        String[] supported_data_types = { "int", "double", "char", "String", "boolean" };
        String[] data_type_regex = {
                "\\d+", // int
                "\\d+.\\d+", // double
                "'.'", // char
                "\"[^\"]*\"", // String
                "true|false" // boolean
        };

        int leng = supported_data_types.length;
        for (int i = 0; i < leng; i++) {
            Pattern pattern = Pattern.compile(data_type_regex[i]);
            if (pattern.matcher(target).matches()) {
                return supported_data_types[i];
            }
        }

        return "";
    }

    public static String center(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    public static boolean semantics_analyzer(String[][] tokens) {
        Pattern bool_pat = Pattern.compile("\\|\\||\\&\\&|[><=]=*");
        Pattern algebra_pat = Pattern.compile("[+\\-*/]");

        int leng = tokens[0].length;

        if (leng == 3 &&
                tokens[1][0].equals("<data_type>") &&
                tokens[1][1].equals("<identifier>") &&
                tokens[1][2].equals("<delimiter>")) {
            return true;
        }

        String variable_data_type = "";
        String prev_operator = "";
        String type_result = "";

        for (int i = 0; i < leng; i++) {
            String current = tokens[1][i];
            switch (current) {
                case "<data_type>" -> variable_data_type = tokens[0][i];
                case "<value>" -> {
                    if (type_result.equals("")) {
                        type_result = data_type_analyzer(tokens[0][i]);
                    } else {
                        if (bool_pat.matcher(prev_operator).matches()) {
                            type_result = "boolean";
                        } else if (algebra_pat.matcher(prev_operator).matches()) {
                            String temp = data_type_analyzer(tokens[0][i]);
                            if (type_result.length() < temp.length()) {
                                type_result = temp;
                            }
                        }
                    }
                }
                case "<operator>" -> prev_operator = tokens[0][i];
            }
        }

        global_type_result = type_result;
        return variable_data_type.equals(type_result);
    }
}
