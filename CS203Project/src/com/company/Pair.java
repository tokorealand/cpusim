package com.company;

public class Pair {

        private final String key;
        private final int value;

        public Pair(String aKey, int aValue)
        {
            key   = aKey;
            value = aValue;
        }

        public String key()   { return key; }
        public int value() { return value; }

}
