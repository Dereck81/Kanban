package pe.edu.utp.dsa.DSA;

import java.util.*;

public class PriorityQueue<T extends Comparable<T>> {

    @FunctionalInterface
    public interface ElementEditor<T> {
        void edit(T elem);
    }

    private class MaxHeap<T extends Comparable<T>> {

        private DynamicArray<T> heap;

        public MaxHeap() {
            heap = new DynamicArray<>(0);
        }

        private int parent(int nd) {
            return (nd - 1) / 2;
        }

        private int leftChild(int root) {
            return root * 2 + 1;
        }

        private boolean isLeaf(int node) {
            return node >= (heap.size() / 2) && node <= heap.size();
        }

        private int rightChild(int root) {
            return (root + 1)* 2;
        }

        private int indexedMax(int i, int j) {
            return heap.at(i).compareTo(heap.at(j)) >= 0 ? i : j;
        }

        private void downHeapify(int node) {
            if (isLeaf(node))
                return;
            int left = leftChild(node);
            int right = rightChild(node);
            int res = 0;

            if (left < heap.size())
                res = indexedMax(node, left);
            if (right < heap.size())
                res = indexedMax(res, right);

            if (res != node) {
                heap.swap(res, node);
                downHeapify(res);
            }
        }

        private void upHeapify(int i) {
            int current = i;
            while (heap.at(current).compareTo(heap.at(parent(current))) > 0) {
                heap.swap(current, parent(current));
                current = parent(current);
            }
        }

        public void insert(T elem) {
            heap.pushBack(elem);
            upHeapify(heap.size() - 1);
        }

        public T popMax() {
            T popped = heap.front();
            heap.setAt(0,  heap.pop());
            downHeapify(0);
            return popped;
        }

        /**
         * Creates an ArrayList with the elements of the heap without altering the heap
         * @return An ArrayList with all the elements sorted as if remove() was called sequentially
         */
        public ArrayList<T> toList() {
            DynamicArray<T> clone = heap.clone();
            ArrayList<T> list = new ArrayList<>(heap.size());
            while (!isEmpty())
                list.add(popMax());
            heap = clone;
            return list;
        }

        public void editElement(ElementEditor<T> ee, int i) {
            T elem = heap.at(i);
            ee.edit(elem);
            downHeapify(i);
            upHeapify(i);
        }

        public void clear() {
            heap.clear();
        }

        public int find(T x) {
            return heap.find(x);
        }

        public void deleteAt(int pos) {
            heap.swap(pos, heap.size() - 1);
            heap.pop();
            downHeapify(pos);
        }
    }

    private final MaxHeap<T> maxHeap;

    public PriorityQueue() {
        maxHeap = new MaxHeap<>();
    }

    public boolean isEmpty() {
        return maxHeap.heap.size() == 0;
    }

    @SuppressWarnings("unused")
    public int size() {
        return maxHeap.heap.size();
    }

    public void enqueue(T elem) {
        maxHeap.insert(elem);
    }

    public T dequeue() {
        if (maxHeap.heap.size() <= 0)
            throw new IndexOutOfBoundsException("Heap is already empty");
        return maxHeap.popMax();
    }

    public T getElement(int i) {
        return maxHeap.heap.at(i);
    }

    public ArrayList<T> toList() {
        return maxHeap.toList();
    }

    public void clear() {
        maxHeap.clear();
    }

    public void deleteWithRealPos(int realPos) {
        maxHeap.deleteAt(realPos);
    }

    public void editElement(ElementEditor<T> ee, int i) {
        maxHeap.editElement(ee, i);
    }

    public int find(T x) {
        return maxHeap.find(x);
    }
}
