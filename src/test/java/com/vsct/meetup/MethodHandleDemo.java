package com.vsct.meetup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleDemo {

    public static void main(String[] args) throws Throwable {
        MethodType mt = MethodType.methodType(boolean.class, int.class, String.class);
        MethodHandle mh = MethodHandles.lookup().findStatic(MethodHandleDemo.class, "lambda$1", mt);
        System.out.println(mh.invoke(3, "test"));
        System.out.println(mh.invoke(5, "test"));

        MethodHandle cstmh = MethodHandles.constant(String.class, "Hello world !");
        System.out.println(cstmh.invoke());
    }

    public static boolean lambda$1(int captured, String s){
        return s.length() >= captured;
    }

}
