package com.druid.learning.sentineldemo;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class SentinelGuavaDemo {

    RateLimiter rateLimiter = RateLimiter.create(10); //qps 10

    public void doTest() {

        if (rateLimiter.tryAcquire()) {
            System.out.println("允许通过访问");
        }else {
            System.out.println("被限流");
        }
    }

    public static void main(String[] args) throws IOException {

        SentinelGuavaDemo sentinelGuavaDemo = new SentinelGuavaDemo();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Random random = new Random();

        for (int i=0; i<20; i++) {

            new Thread(()->{

                try {
                    countDownLatch.await();
                    Thread.sleep(random.nextInt(1000));
                    sentinelGuavaDemo.doTest();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        countDownLatch.countDown();;
        System.in.read();

    }
}
