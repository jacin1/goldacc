package com.test.j2se.singleton;

/**
 * 使用内部类来实现单例模式
 * @author Administrator
 *
 */
public class Singleton {    
    
    private static class SingletonHolder{    
        //单例变量      
        private static Singleton instance = new Singleton();    
    }    
        
    //私有化的构造方法，保证外部的类不能通过构造器来实例化。    
    private Singleton() {    
            
    }    
        
    //获取单例对象实例    
    public static Singleton getInstance() {    
        System.out.println("我是内部类单例！");    
        return SingletonHolder.instance;    
    }    
} 
