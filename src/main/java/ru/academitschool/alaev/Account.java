package ru.academitschool.alaev;

import java.util.Random;

class Account {
    private String id;
    private int money;
    private final Object monitor;


    Account(Object object) {
        this.id = idGenerator();
        this.money = 10000;
        this.monitor = object;
    }

    int takeMoney(int x) throws InterruptedException {
        synchronized (monitor) {
            money -= x;
            return x;
        }
    }

    void addMoney(int money) throws InterruptedException {
        synchronized (monitor) {
            this.money += money;
        }
    }

    int getMoney() {
        return this.money;
    }

    String getId() {
        return id;
    }

    private String idGenerator() {
        int nameLength = 0;
        Random randomGenerator = new Random();
        while (nameLength == 0) {
            nameLength = randomGenerator.nextInt(15);
        }
        final String symbols = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder randString = new StringBuilder();
        for (int i = 0; i < nameLength; i++) {
            char symbol = symbols.charAt(randomGenerator.nextInt(symbols.length()));
            randString.append(symbol);
        }
        return randString.toString();
    }
}
