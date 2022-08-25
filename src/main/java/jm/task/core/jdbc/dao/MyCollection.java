package jm.task.core.jdbc.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MyCollection implements Collection {

    // static Collection<?> collection = new Collection<Object>();
    // Object object = new Object();

    public void checkObj() {
        Collection<Object> collection = new Collection<Object>();
        Object object = new Object();
        collection.toArray();
        collection.size();
        collection.remove(object);
        collection.contains(object);
        collection.addAll(Arrays.asList(object));
        collection.clear();
        collection.add(object);
        collection.iterator();
    }

    public void task_6212() {
        Object o = new Object();
        System.out.println("1" + add(o));
        System.out.println("2" + contains(o));
        System.out.println("3" + addAll(Arrays.asList(o)));
        clear();
        System.out.println("4" + remove(o));
        System.out.println("5" + toArray());
        System.out.println("6" + iterator());
        System.out.println("7" + size());
    }

    public static void main() {
        MyCollection mc = new MyCollection();
        mc.task_6212();
    }
}