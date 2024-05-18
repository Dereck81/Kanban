package pe.edu.utp.dsa.DSA;

import java.util.*;

public class PriorityQueue<T extends Comparable<T>> {

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

        private int maxChild(int n) {
            return indexedMax(leftChild(n), rightChild(n));
        }

        private int indexedMin(int i, int j) {
            return heap.at(i).compareTo(heap.at(j)) >= 0 ? j : i;
        }

        private int indexedMax3(int i, int j, int k) {
            return indexedMax(i, indexedMax(j, k));
        }

        private void heapify(int node) {
            if (isLeaf(node))
                return;
            int left = leftChild(node);
            int right = rightChild(node);
            int res = indexedMax3(node, left, right);
            if (res != node) {
                heap.swap(res, node);
                heapify(res);
            }
        }

        public void insert(T elem) {
            heap.pushBack(elem);
            int current = heap.size() - 1;
            while (heap.at(current).compareTo(heap.at(parent(current))) > 0) {
                heap.swap(current, parent(current));
                current = parent(current);
            }
        }

        public T popMax() {
            T popped = heap.front();
            heap.setAt(0,  heap.pop());
            heapify(0);
            return popped;
        }

        /**
         * Finds the element with the N priority (the element that would be retrieved after N+1 calls to popMax)
         * @param n priority
         * @return Index to the element with the first N priority
         */
        public int traverse(int n) {
            int i = 0;
            int max1 = 0;
            int max2 = indexedMin(leftChild(i), rightChild(0));

            while (i < n) {
                max1 = maxChild(max1);

                if (heap.at(max2).compareTo(heap.at(max1)) >= 0) {
                    int tmp = max1;
                    max1 = max2;
                    max2 = tmp;
                }

                i++;
            }

            return max1;
        }

        public void arbitraryRemoval(int n) {
            int target = traverse(n);

            while (!isLeaf(target)) {
                int maxChild = maxChild(target);
                heap.swap(target, maxChild);
                target = maxChild;
            }

            heap.swap(target, heap.size() - 1);
            heap.pop();
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

    public T getElement(int idx) {
        return maxHeap.heap.at((maxHeap.traverse(idx)));
    }

    public void removeAt(int i) {
        maxHeap.arbitraryRemoval(i);
    }

    public ArrayList<T> toList() {
        return maxHeap.toList();
    }
}
