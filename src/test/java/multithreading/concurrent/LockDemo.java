package multithreading.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    @Test
    void main() throws InterruptedException {
        Account account1 = new Account(2000);
        Account account2 = new Account(2000);

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

    private Account accountFrom;
    private Account accountTo;

    public AccountThread(Account accountFrom, Account accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    @Override
    public void run() {
            for (int i = 0; i < 2000; i++) {
                lockAccounts();
                try {
                    if (accountFrom.takeOff(10)) {
                        accountTo.add(10);
                    }
                } finally {
                    accountFrom.getLock().unlock();
                    accountTo.getLock().unlock();
                }
            }
    }

    private void lockAccounts() {
        while (true) {
            boolean fromLockResult = accountFrom.getLock().tryLock();
            boolean toLockResult = accountTo.getLock().tryLock();
            if (fromLockResult && toLockResult) {
                break;
            }
            if (fromLockResult) {
                accountFrom.getLock().unlock();
            }
            if (toLockResult) {
                accountTo.getLock().unlock();
            }
        }
    }
}

class Account {
    private static int generator = 1;
    private int id;

    private final Lock lock = new ReentrantLock();
    private int money;

    public Account(int money) {
        this.money = money;
        this.id = generator++;
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

    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }
}