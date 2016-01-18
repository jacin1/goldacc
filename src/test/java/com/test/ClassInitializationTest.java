package com.test;

public class ClassInitializationTest {

    public static void main(String[] args) {
//        NotUsed notUsed=null;
//        Child dChild=new Child();
//        System.out.println(Parent.fname);
        Parent p=new Parent();
        Parent child=new Child();
        System.out.println(p.someVariable);
        System.out.println(child.someVariable);
    }

    
}

class Parent{
    public String someVariable="Super variable";
    static final String fname="123";
    static { System.out.println("static block of Super class is initialized");
         
    }
    {
        System.out.println("non static parent init");
        System.out.println();
    }
}

class NotUsed{
    static{
        System.out.println("notuser class is initialized");
    }
}

class Child extends Parent{
    public String someVariable="child variable";
    static{
        System.out.println("static child class is initialized");
    }
    {
        System.out.println("non static blocks child is initialized");
    }
}
