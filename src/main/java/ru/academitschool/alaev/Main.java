package ru.academitschool.alaev;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        Account account1 = new Account(object);
        Account account2 = new Account(object);
        System.out.println("Please Wait");
        Thread t1 = new Thread(new MoneyTransfer(account1, account2));
        Thread t2 = new Thread(new MoneyTransfer(account1, account2));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Finish");
    }
}
