package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String numbers = "0123456789";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String input;

        input = scanner.nextLine();

        for (int i = 0; i < input.length(); i++) {
            if (numbers.indexOf(input.charAt(i)) == -1) {
                System.out.println("error you should enter number");

                return;
            }
        }

        int sourceRadix = Integer.parseInt(input);
        String number = scanner.nextLine();

        for (int i = 0; i < number.length(); i++) {
            if (alphabet.indexOf(number.charAt(i)) != -1) {
                if (number.charAt(i) - 'a' + 11 > sourceRadix) {
                    System.out.println("error wrong number to convert");
                    return;
                }
            }
        }

        input = scanner.nextLine();

        for (int i = 0; i < input.length(); i++) {
            if (numbers.indexOf(input.charAt(i)) == -1) {
                System.out.println("error you should enter number");

                return;
            }
        }

        int targetRadix = Integer.parseInt(input);

        if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
            System.out.println("error base should be in 1..36");

            return;
        }

        String[] parts = number.split("\\.");

        if (parts[0].equals("0")) {
            System.out.printf("0.%s", convertFractionPart(sourceRadix, parts[1], targetRadix));
        } else if (parts.length == 2) {
            if (parts[1].equals("0")) {
                System.out.printf("%s.00000", convertDecimalPart(sourceRadix, parts[0], targetRadix));
            } else {
                System.out.printf("%s.%s",
                        convertDecimalPart(sourceRadix, parts[0], targetRadix),
                        convertFractionPart(sourceRadix, parts[1], targetRadix)
                );
            }
        } else {
            System.out.printf("%s.00000", convertDecimalPart(sourceRadix, parts[0], targetRadix));
        }
    }

    static String convertDecimalPart(int sourceRadix, String number, int targetRadix) {
        if (sourceRadix == 1) {
            return Integer.toString(number.length(), targetRadix);
        } else if (targetRadix == 1) {
            return "1".repeat(Integer.parseInt(number, sourceRadix));
        } else {
            return Integer.toString(Integer.parseInt(number, sourceRadix), targetRadix);
        }
    }

    static String convertFractionPart(int sourceRadix, String number, int targetRadix) {
        double sum = 0.0;
        char[] tmp = new char[5];
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int iPart;

        for (int i = 0; i < number.length(); i++) {
            if (alphabet.indexOf(number.charAt(i)) != -1) {
                sum += (number.charAt(i) - 'a' + 10) / Math.pow(sourceRadix, i + 1);
            } else {
                sum += Integer.parseInt(String.valueOf(number.charAt(i))) / Math.pow(sourceRadix, i + 1);
            }
        }


        for (int i = 0; i < 5; i++) {
            sum *= targetRadix;
            iPart = (int) sum;
            sum -= iPart;

            if (iPart < 10) {
                tmp[i] = (char)('0' + iPart);
            } else {
                tmp[i] = (char)('a' + iPart - 10);
            }
        }

        return String.valueOf(tmp);
    }
}
