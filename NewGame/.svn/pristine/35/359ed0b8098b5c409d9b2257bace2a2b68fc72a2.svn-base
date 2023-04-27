package com.smallchill.test;

import java.util.HashMap;
import java.util.Random;

public class TestHash {
    private static class Person {
        int idCard;
        String name;

        public Person(int idCard, String name) {
            this.idCard = idCard;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Person person = (Person) o;
            //两个对象是否等值，通过idCard来确定
            return this.idCard == person.idCard;
        }

        @Override
        public int hashCode() {
            return this.idCard;
        }
    }

    public static void main(String[] args) {

        int[] a = new int[50];
        for(int i:a){
            a[i] = (int) (Math.random()*40);

            System.out.println(a[i]);
        }

    }

}
