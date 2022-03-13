package com.vgrazi.javaconcurrentanimated.study;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockStudy{
    @Test
    public void testInterruptOnPlainLock() {
        // create the lock
        ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread(()->
        {
            lock.lock();
        });
        // see what happens if a non-interruptible thread is interrupted. Test this when it is waiting for the lock, and when it has the lock
    }
    public static void main(String[] args){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

/// do work
        Condition condition = lock.newCondition();
        try{
            condition.await();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        lock.unlock();

        lock.lock();

/// do work
        condition.signal();
        condition.signalAll();
        lock.unlock();


        try{
            lock.lockInterruptibly();
            // do work
            lock.unlock();
        }catch(InterruptedException e){
            // interrupted
        }finally{
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }

        Thread.currentThread().interrupt();

        boolean success = lock.tryLock();
        if(success){
            // do work
            lock.unlock();
        }
    }
}
