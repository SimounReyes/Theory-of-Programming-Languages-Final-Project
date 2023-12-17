package TPL2;
/*
 * Enter Expression: int x = 2.0;
 * Syntax is Correct!
 */

import javax.swing.border.Border;

public class Syntax {
    String syntax = " ";
    Boolean marker;
    static String state = "";
    static String forOutput = "";

    public Syntax(String file) {
        forOutput = "";
        String[][] tokens = Lexical.lexical_analyzer(file);

        String results = "Syntax is ";
        boolean what = syntax_analyzer(tokens);
        results += what ? "Correct!" : "Incorrect!";

        if (what == false) {
            forOutput += what_wrong();
        }
        System.out.println("asa"+results+"asa");
        System.out.println("www"+forOutput+"www");
        if(results.equals("Syntax is Incorrect!")){
            marker= false;
        }
        else{
            marker=true;
        }
        syntax = results + "\n" + forOutput;
    }

    public static String what_wrong() {
        String temp = "\nWrong, expected ";

        switch (state) {
            case "q1":
                temp += "<identifier>";
                break;
            case "q2":
                temp += "<assign_op> or <delimiter>";
                break;
            case "q3":
                temp += "<value>";
                break;
            case "q4":
                temp += "<delimiter> or <operator>";
                break;
            case "q5":
                temp += "<value>";
                break;
            default:
                break;
        }

        return temp + " instead.";
    }

    public static String center(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    public static boolean syntax_analyzer(String[][] tokens) {
        String tabs = "";
        String aboveArrow = "│    ";
        String arrow = "└───>";

        state = "q0"; // start state

        int leng = tokens[0].length;
        for (int i = 0; i < leng; i++) {

            int len0 = tokens[0][i].length();
            int len1 = tokens[1][i].length();

            int larger = 0;

            if (len0 > len1) {
                larger = len0;
            } else {
                larger = len1;
            }

            forOutput += aboveArrow + center(tokens[0][i], larger) + "\n" + tabs;
            tabs += "        ";
            forOutput += arrow + center(tokens[1][i], larger) + "\n" + tabs;

            String current = tokens[1][i];
            switch (current) {
                case "<data_type>":
                    switch (state) {
                        case "q0":
                            state = "q1";
                            break;
                        default:
                            return false;
                    }
                    break;
                case "<identifier>":
                    switch (state) {
                        case "q1":
                            state = "q2";
                            break;
                        default:
                            return false;
                    }
                    break;

                case "<assign_op>":
                    switch (state) {
                        case "q2":
                            state = "q3";
                            break;
                        default:
                            return false;
                    }
                    break;
                case "<value>":
                    switch (state) {
                        case "q3":
                            state = "q4";
                            break;
                        case "q5":
                            state = "q4";
                            break;
                        default:
                            return false;
                    }
                    break;
                case "<operator>":
                    switch (state) {
                        case "q4":
                            state = "q5";
                            break;
                        default:
                            return false;
                    }
                    break;
                case "<delimiter>":
                    switch (state) {
                        case "q2":
                            state = "q6";
                            break;
                        case "q4":
                            state = "q6";
                            break;
                        default:
                            return false;
                    }
                    break;
                default:
                    return false;
            }
        }
        // check if state is final state
        if (state.equals("q0")) {
            return true;
        } else if (state.equals("q6")) {
            return true;
        } else {
            return false;
        }
    }

}
