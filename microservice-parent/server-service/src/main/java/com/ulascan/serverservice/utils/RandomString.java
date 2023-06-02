package com.ulascan.serverservice.utils;

import java.util.Random;

public class RandomString {
    public static final int DEFAULT_LENGTH = 8;
    private static final char[] SYMBOL;
    private static final int KEY_BITS;
    private final Random random;
    private final int length;

    public RandomString() {
        this(8);
    }

    public RandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("A random string's length cannot be zero or negative");
        } else {
            this.length = length;
            this.random = new Random();
        }
    }

    public static String make() {
        return make(8);
    }

    public static String make(int length) {
        return (new RandomString(length)).nextString();
    }

    public static String hashOf(int value) {
        char[] buffer = new char[32 / KEY_BITS + (32 % KEY_BITS == 0 ? 0 : 1)];

        for (int index = 0; index < buffer.length; ++index) {
            buffer[index] = SYMBOL[value >>> index * KEY_BITS & -1 >>> 32 - KEY_BITS];
        }

        return new String(buffer);
    }

    public String nextString() {
        char[] buffer = new char[this.length];

        for (int index = 0; index < this.length; ++index) {
            buffer[index] = SYMBOL[this.random.nextInt(SYMBOL.length)];
        }

        return new String(buffer);
    }

    static {
        StringBuilder symbol = new StringBuilder();

        int character;
        for (character = 48; character <= 57; character = (char) (character + 1)) {
            symbol.append((char) character);
        }

        for (character = 97; character <= 122; character = (char) (character + 1)) {
            symbol.append((char) character);
        }

        for (character = 65; character <= 90; character = (char) (character + 1)) {
            symbol.append((char) character);
        }

        SYMBOL = symbol.toString().toCharArray();
        character = 32 - Integer.numberOfLeadingZeros(SYMBOL.length);
        KEY_BITS = character - (Integer.bitCount(SYMBOL.length) == character ? 0 : 1);
    }
}
