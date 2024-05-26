package pe.edu.utp.dsa.DSA;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Stack<T> {
    private T[] arr;
    private int capacity;
    private int top;

    @SuppressWarnings("unchecked")
    public Stack(int capacity){
        arr = (T[]) new Object[capacity];
        this.capacity = capacity;
        top = -1;
    }

    public void push(T element){
        if(size() == capacity) return;
        top++;
        arr[top] = element;
    }

    public T peek(){
        if(isEmpty()) return null;
        return arr[top];
    }

    public T pop(){
        if (isEmpty()) return null;
        T element = peek();
        arr[top--] = null;
        return element;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public int size(){
        return top+1;
    }

    @SuppressWarnings("unchecked")
    public void clear(){
        arr = (T[]) Stream.of(arr).map(i -> null).toArray(Object[]::new);
        top = -1;
    }

    public T[] toArray(){
        return arr;
    }

    public List<T> toList(){
        return Arrays.asList(arr);
    }

}
