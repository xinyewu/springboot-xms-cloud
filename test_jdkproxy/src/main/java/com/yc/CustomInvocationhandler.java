package com.yc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class CustomInvocationhandler implements InvocationHandler {
    private Object target;//目标类
    public CustomInvocationhandler(Object target){this.target=target;}
    //生成代理 对象的方法
    public Object createProxy(){
        //jdk中提供了Proxy类，有一个方法专门用于根据接口生成代理类对象的方法
        Object proxy= Proxy.newProxyInstance(CustomInvocationhandler.class.getClassLoader(),target.getClass().getInterfaces(),this);
        return proxy;//$Proxy01: sayHello() showBye()
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().indexOf("say")>=0){//解释@Poincut("execution(* com.yc.projects.biz.add*(..))")
            showTime();
        }
        //反射机制调用目标类的目标方法
        Object returnValue=method.invoke(target,args);//HelloImpl.sayHello()目标类的目标方法
        
        return returnValue;
    }
    //增强方法
    private void showTime() {
        System.out.println("时间为："+new Date());
    }
}
