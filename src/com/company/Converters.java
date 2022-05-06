package com.company;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Converters {
    public static String convertFromBase10Int(BigInteger number, int targetRadix) {
        StringBuilder stringBuilder = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) > 0) {
            stringBuilder.append(number.remainder(BigInteger.valueOf(targetRadix)).toString(targetRadix));
            number = number.divide(BigInteger.valueOf(targetRadix));
        }
        return stringBuilder.reverse().toString().toUpperCase();
    }

    public static String convertFromBase10Fraction(BigDecimal fraction, int targetRadix) {
        StringBuilder stringBuilder = new StringBuilder(".");
        BigDecimal fraction2 = new BigDecimal(String.valueOf(fraction)).setScale(5, RoundingMode.HALF_DOWN);
        while (fraction2.compareTo(BigDecimal.ZERO) > 0 && stringBuilder.length() < 6) {
            BigInteger bigInteger = fraction2.multiply(BigDecimal.valueOf(targetRadix)).toBigInteger();
            stringBuilder.append(bigInteger.toString(targetRadix));
            String integer = bigInteger.toString();
            fraction2 = fraction2.multiply(BigDecimal.valueOf(targetRadix)).subtract(new BigDecimal(integer));
        }
        while (stringBuilder.length() < 6) {
            stringBuilder.append("0");
        }
        return stringBuilder.toString().toUpperCase();
    }

    private static BigInteger convertToBase10Int(String bigInteger, int sourceRadix) {
        int lastDigit = bigInteger.length() - 1;
        int firstDigit = 0;
        int exponent = 0;
        BigInteger sum = BigInteger.ZERO;
        for (int i = lastDigit; i >= firstDigit; i--) {
            BigInteger multiplier = BigInteger.valueOf(sourceRadix).pow(exponent);
            BigInteger digit = BigInteger.valueOf(Character.digit(bigInteger.charAt(i), sourceRadix)) ;
            sum = sum.add(digit.multiply(multiplier));
            exponent++;
        }
        return sum;
    }

    private static BigDecimal convertToBase10Fraction(String fraction, int sourceRadix) {
        int lastIndex = fraction.length() -1;
        int firstIndex = 0;
        int exponent = 1;
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = firstIndex; i <= lastIndex; i++) {
            BigDecimal digit = BigDecimal.valueOf(Character.digit(fraction.charAt(i), sourceRadix));
            sum = sum.add(digit.divide(BigDecimal.valueOf(sourceRadix).pow(exponent), 100, RoundingMode.CEILING));
            exponent++;
        }
        //return sum;
        return sum.stripTrailingZeros();
    }

    private static BigDecimal convertToBase10(String bigDecimal, int sourceRadix) {
        String bigInteger;
        String fraction;
        if (!bigDecimal.contains(".")) {
            bigInteger = bigDecimal;
            fraction = "0";
        } else {
            bigInteger = bigDecimal.substring(0, bigDecimal.indexOf(".")).isEmpty() ? "0" :
                    bigDecimal.substring(0, bigDecimal.indexOf("."));
            fraction = bigDecimal.substring(bigDecimal.indexOf(".")).equals(".") ? "0" :
                    bigDecimal.substring(bigDecimal.indexOf(".") + 1);
        }
        BigInteger base10Int = convertToBase10Int(bigInteger, sourceRadix);
        BigDecimal base10fraction = convertToBase10Fraction(fraction, sourceRadix);
        return new BigDecimal(base10Int).add(base10fraction);
    }



    private static String convertFromBase10(BigDecimal bigDecimal, int targetRadix) {
        BigInteger bigInteger = bigDecimal.toBigInteger();
        BigDecimal fraction = bigDecimal.subtract(new BigDecimal(bigInteger));
        String newBaseInt = convertFromBase10Int(bigInteger, targetRadix);
        String newBaseFraction = fraction.equals(BigDecimal.ZERO) ? "" :
                convertFromBase10Fraction(fraction, targetRadix);



        return newBaseInt.isEmpty() ? "0" + newBaseFraction : newBaseInt + newBaseFraction;
    }

    public static String convertNumber(String bigDecimal, int sourceRadix, int targetRadix) {
        BigDecimal base10 = convertToBase10(bigDecimal, sourceRadix);
        String newBase = convertFromBase10(base10, targetRadix);
        if (bigDecimal.contains(".") && !newBase.contains(".")) {
            newBase = newBase + ".00000";
        }
        return newBase;
    }
}
