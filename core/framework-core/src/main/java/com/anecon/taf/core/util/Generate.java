package com.anecon.taf.core.util;

public class Generate {
    public static StringGenerator string() {
        return new StringGenerator();
    }

    private static int randomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private static char randomLetter() {
        final int start = 'a';
        final int end = 'z';

        return (char) randomNumber(start, end + 1);
    }

    public static class StringGenerator {
        private final StringBuilder sb = new StringBuilder();

        public StringGenerator withNumber(int min, int max) {
            sb.append(randomNumber(min, max));

            return this;
        }

        public StringGenerator withDigits(int digits) {
            for (int i = 0; i < digits; i++) {
                sb.append(randomNumber(0,10));
            }

            return this;
        }

        public StringGenerator withLowercaseChars(int chars) {
            for (int i = 0; i < chars; i++) {
                sb.append(randomLetter());
            }

            return this;
        }

        public StringGenerator withNormalcaseChars(int chars) {
            if (chars != 0) {
                sb.append(String.valueOf(randomLetter()).toUpperCase());
            }

            for (int i = 1; i < chars; i++) {
                sb.append(randomLetter());
            }

            return this;
        }

        public StringGenerator withAllUppercaseChars(int chars) {
            final StringBuilder sb = new StringBuilder(chars);

            for (int i = 0; i < chars; i++) {
                sb.append(randomLetter());
            }
            this.sb.append(sb.toString().toUpperCase());

            return this;
        }

        public String get() {
            return toString();
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }
}
