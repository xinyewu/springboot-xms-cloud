package com.yc;

public class HelloImpl implements Hello{
    @Override
    public void sayhello() {
        System.out.println("HelloImpl中的sayHello（）");
    }

    @Override
    public void showBye() {
        System.out.println("HelloImpl中的showBye（）");
    }
}
