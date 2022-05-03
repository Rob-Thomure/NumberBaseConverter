package com.company;

import java.math.BigInteger;

public class Converters {

    public static String convertFromDecimal(BigInteger number, int radix) {
        StringBuilder stringBuilder = new StringBuilder();
        while (number.compareTo(BigInteger.ZERO) == 1) {
            stringBuilder.append(number.remainder(BigInteger.valueOf(radix)).toString(radix));
            number = number.divide(BigInteger.valueOf(radix));
        }
        return stringBuilder.reverse().toString().toUpperCase();
    }

    public static BigInteger convertToDecimal(String number, int radix) {
        int lastDigit = number.length() - 1;
        int firstDigit = 0;
        int exponent = 0;
        BigInteger sum = BigInteger.ZERO;
        for (int i = lastDigit; i >= firstDigit; i--) {
            BigInteger multiplier = BigInteger.valueOf(radix).pow(exponent);
            BigInteger digit = BigInteger.valueOf(Character.digit(number.charAt(i), radix)) ;
            sum = sum.add(digit.multiply(multiplier));
            exponent++;
        }
        return sum;
    }
}
