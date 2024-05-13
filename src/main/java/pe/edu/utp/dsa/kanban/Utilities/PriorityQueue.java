package pe.edu.utp.dsa.kanban.Utilities;

import java.util.*;
import java.util.stream.Stream;

public class PriorityQueue<T extends Comparable<T>> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < numberOfNodes;
            }
            @Override
            public T next() throws RuntimeException{
                try{
                    return getNode(index++).getElement();
                }catch (Exception e){
                    throw new RuntimeException(e);
                }

            }
        };
    }

    static class Node<T>{
        Node<T> next = null;
        T element;
        public Node(T element){
            this.element = element;
        }
        public void setNext(Node<T> next){
            this.next = next;
        }

        public Node<T> getNext() {
            return next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }
    }

    private Node<T> head;

    private Node<T> end;

    private int numberOfNodes = 0;

    public PriorityQueue(){
    }

    public PriorityQueue(T element){
        head = new Node<>(element);
        end = head;
        numberOfNodes++;
    }

    public void add(T element) throws Exception {
        if(element == null) throw new NullPointerException("element entered is null");
        if(isEmpty()){
            head = new Node<>(element);
            end = head;
            numberOfNodes++;
            return;
        }

        for (int i = 0; i < numberOfNodes; i++) {
            Node<T> current = getNode(i);
            if (element.compareTo(current.getElement()) >= 0) {
                add(i, element);
                return;
            } else if (current.getNext() == null) {
                addLast(element);
                return;
            }
        }
        addLast(element);
    }

    private void add(int index, T element) throws Exception{
        if(index < 0 || index >= numberOfNodes)
            throw new IndexOutOfBoundsException("Index out of range");
        if(index == 0) {
            addFirst(element);
            return;
        }
        else if(index == numberOfNodes-1){
            addLast(element);
            return;
        }

        Node<T> newNode = new Node<>(element);
        Node<T> prevNode = getNode(index-1);
        assert prevNode != null;
        newNode.setNext(prevNode.getNext());
        prevNode.setNext(newNode);
        numberOfNodes++;
    }

    private void addLast(T element){
        Node<T> newNode = new Node<>(element);
        end.setNext(newNode);
        end = newNode;
        numberOfNodes++;
    }

    private void addFirst(T element){
        Node<T> newNode = new Node<>(element);
        newNode.setNext(head);
        head = newNode;
        if(isEmpty()) end = head;
        numberOfNodes++;
    }


    private Node<T> getNode(int index) throws Exception{
        if(isEmpty()) return null;
        if(index < 0 || index >= numberOfNodes)
            throw new Exception("Index out of range!");

        Node<T> current = head;
        for (int i = 0; i < index; i++)
            current = current.getNext();

        return current;
    }

    public boolean isEmpty(){
        return numberOfNodes == 0;
    }

    public int size(){
        return numberOfNodes;
    }

    public T element() throws NoSuchElementException {
        try{
            return getNode(0).getElement();
        }catch (Exception e){
            throw new NoSuchElementException("Empty queue");
        }
    }

    public T peek(){
        try{
            return element();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> toList(){

        Object[] arr = new Object[numberOfNodes];
        Node<T> current = head;
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.println(i);
            arr[i] = current.getElement();
            current = current.getNext();
        }
        return Stream.of(arr).map(i -> (T) i).toList();
    }

    public boolean offer(T element){
        try{
            add(element);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public T remove() throws NoSuchElementException{
        if(isEmpty()) throw new NoSuchElementException("Empty queue");
        Node<T> remove = head;
        if(numberOfNodes == 1){
            head = null;
            end = null;
            numberOfNodes--;
            return remove.getElement();
        }
        Node<T> newHead = head.getNext();
        head.setNext(null);
        head = newHead;
        numberOfNodes--;
        return remove.getElement();
    }

    public T poll(){
        try{
            return remove();
        }catch (NoSuchElementException e){
            return null;
        }
    }

}
