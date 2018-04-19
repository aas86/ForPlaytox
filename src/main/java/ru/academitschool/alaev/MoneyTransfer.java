package ru.academitschool.alaev;

import org.apache.log4j.Logger;

import java.util.Random;

public class MoneyTransfer implements Runnable {
    private static final Logger log = Logger.getLogger(MoneyTransfer.class.getName());
    private Account account1;
    private Account account2;
    private static int count = 1;

    public MoneyTransfer(Account account1, Account account2) {
        this.account1 = account1;
        this.account2 = account2;
    }

    @Override
    public void run() {
        while (account1.getMoney() > 0 && account2.getMoney() > 0 && count <= 30) {
            try {
                Random randomGenerator = new Random();
                int sum = randomGenerator.nextInt(15000);

                //System.out.printf("%d",count);
               /* System.out.println(count);
                System.out.println("Поток " + Thread.currentThread().getName() + " взял задачу");
                System.out.println("Сумма к списанию/зачислению = " + sum);
                System.out.println("Денег на счету " + account1.getId() + " " + account1.getMoney() + " до операции");
                System.out.println("Денег на счету " + account2.getId() + " " + account2.getMoney() + " до операции");*/
                // 1 поток переводит деньги со счёта account2 на account1, 2ой поток наоборот
                if (Thread.currentThread().getName().equals("Thread-1")) {
                    if (sum > account2.getMoney()) {
                        log.info("Transfer" + sum +  " $");
                        Thread.currentThread().interrupt();
                    } else {
                        account1.addMoney(account2.takeMoney(sum));
                        log.info("Transfer " + sum + " $ from " + account2.getId() + " to " + account1.getId());
                        log.info(account1.getId() + " " + account1.getMoney() + " " + account2.getId() + " " + account2.getMoney());
                    }
                } else if (Thread.currentThread().getName().equals("Thread-0")) {
                    if (sum > account1.getMoney()) {
                        log.info("Transfer " + sum +  " $");
                        Thread.currentThread().interrupt();
                    } else {
                        account2.addMoney(account1.takeMoney(sum));
                        log.info("Transfer " + sum + " $ from " + account1.getId() + " to " + account2.getId());
                        log.info(account1.getId() + " " + account1.getMoney() + " " + account2.getId() + " " + account2.getMoney());
                    }
                }
                count++;
                /*System.out.println("Денег на счету " + account1.getId() + " " + account1.getMoney() + " после операции");
                System.out.println("Денег на счету " + account2.getId() + " " + account2.getMoney() + " после операции");*/

            } catch (InterruptedException e) {
                log.warn("Not enough money on the account " + account2.getId());
                e.printStackTrace();
            }
            try {
                Random randomGenerator = new Random();
                long time = (long) (randomGenerator.nextFloat() * 1000) + 1000;
               /* System.out.println("Поток 1 уснул на " + time);
                System.out.println("---------------------------------------------------------------");*/
                Thread.sleep(time);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.warn("Not enough money on the account " + account1.getId());
                log.info("Account " + account1.getId() + " = " + account1.getMoney());
                log.info("Account " + account2.getId() + " = " + account2.getMoney());
            }
        }
    }
}
