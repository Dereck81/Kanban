package pe.edu.utp.dsa.DSA;

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
                    return Objects.requireNonNull(getNode(index++)).getElement();
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
        head = null;
        end = null;
    }

    public PriorityQueue(T element){
        head = new Node<>(element);
        end = head;
        numberOfNodes++;
    }

    public boolean isEmpty(){
        return numberOfNodes == 0;
    }

    public int size(){
        return numberOfNodes;
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
            assert current != null;
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

    public T getElement(int index) throws Exception {
        return Objects.requireNonNull(getNode(index)).getElement();
    }

    public boolean offer(T element){
        try{
            add(element);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public T poll(){
        try{
            return remove();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public T peek(){
        try{
            return getItemWithHighestPriority();
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public void setElement(int index, T element) throws Exception {
        Node<T> targetNode = getNode(index);
        assert targetNode != null;
        T targetElement = targetNode.getElement();
        // Here we compare by priority, not by equality
        // in content of the objects.
        if(element.compareTo(targetElement) == 0){
            targetNode.setElement(element);
            return;
        }
        remove(index);
        add(element);
    }

    private void remove(int index) throws Exception{
        if(index == 0){
            removeFirst();
            return;
        } else if (index == numberOfNodes-1) {
            removeLast();
            return;
        }
        Node<T> prevNode = getNode(index-1);
        Node<T> nextNode = getNode(index+1);
        assert prevNode != null;
        prevNode.getNext().setNext(null); // targetNode
        prevNode.setNext(nextNode);
        numberOfNodes--;
    }

    public boolean removeItemAtIndex(int index) {
        try {
            remove(index);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void add(int index, T element) throws Exception{
        if(index < 0 || index >= numberOfNodes)
            throw new IndexOutOfBoundsException("Index out of range");
        if(index == 0) {
            addFirst(element);
            return;
        }/*
        This conditional causes problems when entering an element
        in the last position (replacing that element in said index,
        Example: Position 5 and passing it to 6 instead of passing
        the element that was in position 5 to 6 and the new element
        to the 5).
        else if(index == numberOfNodes-1){
            addLast(element);
            return;
        }
        */
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
        numberOfNodes++;
        if(isEmpty()) end = head;
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

    private T getItemWithHighestPriority() throws NoSuchElementException {
        try{
            return Objects.requireNonNull(getNode(0)).getElement();
        }catch (Exception e){
            throw new NoSuchElementException("Empty queue");
        }
    }

    private void removeFirst() {
        Node<T> newNode = head.getNext();
        head.setNext(null);
        head = newNode;
        numberOfNodes--;
    }

    private void removeLast() throws Exception{
        Node<T> prevNode = getNode(numberOfNodes-2);
        Objects.requireNonNull(prevNode).setNext(null);
        end = prevNode;
        numberOfNodes--;
    }

    private T remove() throws NoSuchElementException{
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

    @SuppressWarnings("unchecked")
    public List<T> toList(){
        Object[] arr = new Object[numberOfNodes];
        Node<T> current = head;
        for (int i = 0; i < numberOfNodes; i++) {
            arr[i] = current.getElement();
            current = current.getNext();
        }
        return Stream.of(arr).map(i -> (T) i).toList();
    }

}
