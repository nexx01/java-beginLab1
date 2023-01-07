package multithreading.account;

import org.junit.jupiter.api.Test;

public class AccountRunner{

    @Test
    void mainTest() throws InterruptedException {
        Account account1 = new Account(20000);
        Account account2 = new Account(20000);

        AccountThread accountThread1 = new AccountThread(account1, account2);
        AccountThread accountThread2 = new AccountThread(account2, account1);
        accountThread1.start();
        accountThread2.start();

        accountThread1.join();
        accountThread2.join();

        System.out.println(account1);
        System.out.println(account2);
    }
}

class AccountThread extends Thread {
    private final Account accountFrom;
    private final Account accountTo;

    public AccountThread(Account accountFrom, Account accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20000; i++) {
            synchronized (accountFrom) {
                synchronized (accountTo) {
            if (accountFrom.takeOff(10)) {
                accountTo.add(10);
            }
        }
            }
        }
    }
}

class Account {

    private static int generator = 1;
    private int id;
    private int money;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }

    public Account( int money) {
        this.id = generator++;
        this.money = money;
    }

    public void add(int money) {
        this.money += money;
    }

    public boolean takeOff(int money) {
        if (this.money >= money) {
            this.money -= money;
            return true;
        }
        return false;
    }
}
