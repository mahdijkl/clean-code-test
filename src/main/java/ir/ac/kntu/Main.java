package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class Main {

    public static void whileLoopChecker2() {

        boolean whileLoopIsValid = true;
        int numNestedLoops = 0;
        int numOpenBraces = 0;

        // Check each line of the program
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            // Check if the line contains a while loop
            if (line.contains("while")) {
                int openingParenIndex = line.indexOf('(');
                int closingParenIndex = line.indexOf(')', openingParenIndex);
                int openingBraceIndex = line.indexOf('{', closingParenIndex);

                // Check that the opening brace is on the same line as the while loop
                if (openingBraceIndex == -1 || openingBraceIndex != line.lastIndexOf('{')) {
                    System.out.println("Opening brace for while loop must be on same line as conditional parentheses");
                    whileLoopIsValid = false;
                }

                // Check that the closing brace is on a separate line and not indented
                int closingBraceIndex = -1;
                for (int j = i + 1; j < lines.length; j++) {
                    String nextLine = lines[j];
                    if (!nextLine.trim().isEmpty()) {
                        closingBraceIndex = nextLine.indexOf('}');
                        break;
                    }
                }
                if (closingBraceIndex == -1 || closingBraceIndex != 0) {
                    System.out.println("Closing brace for while loop must be on a separate line and not indented");
                    whileLoopIsValid = false;
                }

                // Check for nested loops
                numNestedLoops++;
                if (openingBraceIndex != -1) {
                    numOpenBraces++;
                }
                if (closingBraceIndex == 0) {
                    numOpenBraces--;
                }
                if (numOpenBraces < 0) {
                    System.out.println("Nested while loops not allowed");
                    whileLoopIsValid = false;
                }
            }
        }

        // Check that all loops are properly closed
        if (numOpenBraces != 0 || numNestedLoops % 2 != 0) {
            System.out.println("All while loops must be closed with a matching brace");
            whileLoopIsValid = false;
        }

        // Output whether or not the input program's while loops are valid
        if (whileLoopIsValid) {
            System.out.println("All while loops are valid");
        } else {
            System.out.println("Not all while loops are valid");
        }
    }

    public static void whileLoopChecker(String lines) {

        // Regular expression to match while loops
        Pattern pattern = Pattern.compile("while\\s*\\(.*?\\)\\s*\\{.*?\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(lines);

        while (matcher.find()) {
            String whileLoop = matcher.group();
            // Check if while loop follows the rules
            if (!whileLoop.matches("while\\s*\\(.*?\\)\\{\\s*.*?\\n\\}")) {
                System.out.println("The rule is not observed. while loop isn't followed the rule.\n");
                break;
            }
        }

    }

    public static void forLoopChecker(String lines) {

        Pattern pattern = Pattern.compile("for\\s*\\(.*?\\)\\s*\\{.*?\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(lines);

        while (matcher.find()) {
            String forLoop = matcher.group();

            // Check if for loop follows the rules
            if (!forLoop.matches("for\\s*\\(.*?\\)\\{\\s*.*?\\n\\}")) {
                System.out.println("The rule is not observed. For loop isn't followed the rule.\n");
                break;
            }
        }
    }

    public static void ifElseChecker(String lines) {

        String regex = "if\\s*\\(\\S*\\)\\s*\\{\\s*\\S*\\s*\\}\\s*(else\\s+if\\s*\\(\\S*\\)\\s*\\{\\s*\\S*\\s*\\}\\s*)*(else\\s*\\{\\s*\\S*\\s*\\})*";
        String correctRegex = "if\\s*\\(\\S*\\)\\{\\s*\\S*\\s*\\n\\}(else\\s+if\\s*\\(\\S*\\)\\{\\s*\\S*\\s*\\n\\})*(else\\{\\s*\\S*\\s*\\n\\})*";

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(lines);
        while (matcher.find()) {
            String ifStatment = matcher.group();
            if (!ifStatment.matches(correctRegex)) {
                System.out.println(errorMessage + "if else statment isn't followed the rule.\n");
                break;
            }
        }

    }

    public static void switchCaseChecker(String lines) {

        String regex = "\\s*switch\\s*\\(.*\\)\\s*\\{\\s*(case\\s*.*\\s*:\\s*.*\\s*)+\\s*(default\\s*:\\s*.*\\s*)?\\s*\\}";
        String correctRegex = "\\s*switch\\s*\\(.*\\)\\s*\\{\\s*(case\\s*.*\\s*:\\s*.*\\s*)+\\s*default\\s*:\\s*.*\\s*\\n\\s*\\}";

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(lines);
        while (matcher.find()) {
            String switchStatment = matcher.group();
            if (!switchStatment.matches(correctRegex)) {
                System.out.println(errorMessage
                        + "The input program does not follow the rules for switch-case statements.\n");
                break;
            }
        }
    }

    public static String errorMessage = "Rule not observed:";

    public static void checkPackageAndImport(String lines) {
        if (!lines.startsWith("package ") && lines.contains("package ")) {
            System.out.println(errorMessage + "Package must appear in the first line of the program.\n");
        }
        boolean hasImport = lines.contains("import ");
        boolean impoertAtfterPackage = !(lines.indexOf("import") < lines.indexOf("package"));
        if (hasImport && !impoertAtfterPackage) {
            System.out.println(errorMessage + "Import must appear after the package.\n");
        }

    }

    public static void ProgramAnalyzer(ArrayList<String> linesList, String input) {

        String[] lines = input.split("\n");
        String lengthError = " The length of each program line should not exceed 80 characters.";
        int bracketCount = 0;

        for (String line : linesList) {
            int packageLine = 0;
            int importLine = 0;
            if (line.contains("package")) {
                packageLine = linesList.indexOf(line);
            }
            if (line.contains("import")) {
                importLine = linesList.indexOf(line);
            }
            if (line.contains("}")) {
                bracketCount--;
            }
            if (!line.isEmpty() && !line.startsWith("    ".repeat(bracketCount))) {
                System.out.format("The lines %d between {} must start with 4 spaces.\n\n", linesList.indexOf(line) + 1);
            }
            if (line.contains("{")) {
                bracketCount++;

            }

            if (line.length() > 80) {
                System.out.print(errorMessage + lengthError);
                System.out.printf("line %dth is more than 80 charaters.\n\n",
                        linesList.indexOf(line) + 1);

            }

            if (line.contains(";") && line.split(";").length > 1) {

                System.out.printf(errorMessage + "There should be at most one \";\" in %dth line.\n\n",
                        linesList.indexOf(line) + 1);

            }
            if (line.matches(".*public\\s+class\\s+.+")) {
                if (!line.matches(".*public\\s+class\\s+[A-Z]([a-z]|[0-9])+.+")) {
                    System.out.println(errorMessage + "The name of the class should be UpperCamelCase.\n");

                }
            }
            if (line.contains("public") && line.contains("static") && !line.contains(";")) {
                if (!line.matches(
                        ".*public\\s+static\\s+(int|float|double|String|boolean|char|long|byte|void)(\\[\\])*\\s+[a-z]([a-z]|[A-Z]|[0-9])+\\s*\\(.+")) {
                    System.out.println(
                            errorMessage
                                    + "The name of the program methods must be lowerCamelCase and have at least two characters.\n");

                }
            }
            if (line.matches(
                    "\\s*(private|public|protected)?\\s*(static)?\\s*(int|float|double|String|boolean|char|long|byte)(\\[\\])*\\s+(\\w+)(\\s*=.*)?;")) {
                if (!line.matches(
                        "\\s*(private|public|protected)?\\s*(static)?\\s*(int|float|double|String|boolean|char|long|byte)(\\[\\])*\\s+[a-z]([a-z]|[A-Z]|[0-9])+\\s*(\\s*=.*)?;")) {
                    System.out.println(
                            errorMessage
                                    + "The name of the program Variable must be lowerCamelCase and have at least two characters.\n");
                }
            }

        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enten name of your file with .java");
        String fileName = scanner.nextLine();
        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<String>();
        String input = "";

        try {
            reader = new BufferedReader(new FileReader(
                    "C:/Users/moham/Documents/Java_floder/project/project1/src/main/java/ir/ac/kntu/" + fileName));
            String line = reader.readLine();

            while (line != null) {
                // System.out.println(line);
                lines.add(line);

                input += line + "\n";
                // read next line
                line = reader.readLine();
            }
            reader.close();
            scanner.close();

            checkPackageAndImport(input);
            ProgramAnalyzer(lines, input);
            /*
             * whileLoopChecker(input);
             * forLoopChecker(input);
             * ifElseChecker(input);
             * switchCaseChecker(input);
             */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
