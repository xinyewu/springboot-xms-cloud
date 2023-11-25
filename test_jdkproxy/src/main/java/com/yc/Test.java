package com.yc;

public class Test {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Hello target=new HelloImpl();//目标类
        CustomInvocationhandler handler=new CustomInvocationhandler(target);
        //生成代理类
        Object proxy=handler.createProxy();
        System.out.println(proxy);//Proxy0对象
        Hello hi=(Hello)proxy;//hi=$Proxy0对象
        hi.sayhello();
        hi.showBye();
    }
}
