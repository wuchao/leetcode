package com.github.wuchao.leetcode.problems;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    private int capacity;
    private Map<Integer, CacheNode> caches;
    private Map<Integer, CustomLinkedList> frequentQueueMap;
    private Integer frequentQueueMapSize = 10000;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        caches = new HashMap<>(capacity);
        frequentQueueMap = new HashMap<>(frequentQueueMapSize);
    }


    public int _get(int key) {
        CacheNode cacheNode = this.caches.get(key);
        if (cacheNode == null) {
            return -1;
        } else {
            return cacheNode.getValue();
        }
    }

    public int get(int key) {
        int value = _get(key);
        if (value != -1) {
            refreshLFUQueue(key);
        }
        return value;
    }

    public void put(int key, int value) {
        if (this.capacity <= 0) {
            return;
        }

        CacheNode cacheNode = this.caches.get(key);

        // 新插入的缓存
        if (cacheNode == null) {
            LFUQueueNode queueNode = new LFUQueueNode(key, null, null);
            cacheNode = new CacheNode(value, 0, queueNode);

            // 缓存已满，删除访问频次最小的一个缓存
            if (caches.size() == this.capacity) {

                CustomLinkedList queue = null;

                for (int count = 1; count <= this.frequentQueueMapSize; count++) {
                    queue = frequentQueueMap.get(count);
                    if (queue != null && !queue.isEmpty()) {
                        break;
                    }
                }

                if (queue != null) {
                    LFUQueueNode first = queue.getFirst();
                    if (first != null) {
                        this.caches.remove(first.getKey());
                        queue.removeFirst();
                    }
                }
            }

        } else {
            cacheNode.setValue(value);
        }

        caches.put(key, cacheNode);
        refreshLFUQueue(key);
    }


    /**
     * 刷新缓存在频次列表中的位置
     * @param key
     */
    void refreshLFUQueue(int key) {
        CacheNode cacheNode = this.caches.get(key);

        if (cacheNode != null) {

            CustomLinkedList queue;

            if (cacheNode.getCount() > 0) {
                queue = frequentQueueMap.get(cacheNode.getCount());
                if (queue != null && !queue.isEmpty()) {
                    queue.removeNode(cacheNode.getQueueNode());
                }
            }

            cacheNode.setCount(cacheNode.getCount() + 1);
            queue = frequentQueueMap.get(cacheNode.getCount());
            if (queue == null) {
                queue = new CustomLinkedList();
                frequentQueueMap.put(cacheNode.getCount(), queue);
            }
            queue.addLast(cacheNode.getQueueNode());
        }
    }


    /**
     * 缓存 value 数据结构
     */
    public class CacheNode {
        private int value;
        private int count;
        private LFUQueueNode queueNode;

        public CacheNode(int value, int count,LFUQueueNode queueNode) {
            this.value = value;
            this.count = count;
            this.queueNode = queueNode;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public LFUQueueNode getQueueNode() {
            return queueNode;
        }

        public void setQueueNode(LFUQueueNode queueNode) {
            this.queueNode = queueNode;
        }
    }

    public class CustomLinkedList {
        private LFUQueueNode head;
        private LFUQueueNode tail;

        /**
         * 添加节点
         * @param node
         */
        void addLast(LFUQueueNode node) {
            if (node != null) {
                // 队列为空
                if (tail == null) {
                    head = node;
                } else {
                    tail.setNext(node);
                    node.setPrev(tail);
                }
                tail = node;
            }
        }

        /**
         * 删除节点
         * @param node
         */
        void removeNode(LFUQueueNode node) {
            if (node != null) {
                if (node.getPrev() != null) {
                    node.getPrev().setNext(node.getNext());
                } else if (this.head == node){
                    this.head = node.getNext();
                }

                if (node.getNext() != null) {
                    node.getNext().setPrev(node.getPrev());
                } else if (this.tail == node){
                    this.tail = node.getPrev();
                }

                node.setPrev(null);
                node.setNext(null);
            }
        }


        LFUQueueNode getFirst() {
            return this.head;
        }


        /**
         * 删除最后一个节点
         */
        void removeFirst() {
            if (this.head != null) {
                if (this.head == this.tail) {
                    this.head = null;
                    this.tail = null;
                } else {
                    if (this.head.getNext() != null) {
                        this.head.getNext().setPrev(null);
                    }
                    this.head = this.head.getNext();
                }
            }
        }


        /**
         * 队列是否为空
         * @return
         */
        boolean isEmpty() {
            return this.getHead() == null && this.getTail() == null ? true : false;
        }


        public CustomLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public LFUQueueNode getHead() {
            return head;
        }

        public void setHead(LFUQueueNode head) {
            this.head = head;
        }

        public LFUQueueNode getTail() {
            return tail;
        }

        public void setTail(LFUQueueNode tail) {
            this.tail = tail;
        }
    }

    public class LFUQueueNode {
        private int key;
        private LFUQueueNode prev;
        private LFUQueueNode next;

        public LFUQueueNode(int key, LFUQueueNode prev, LFUQueueNode next) {
            this.key = key;
            this.prev = prev;
            this.next = next;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public LFUQueueNode getPrev() {
            return prev;
        }

        public void setPrev(LFUQueueNode prev) {
            this.prev = prev;
        }

        public LFUQueueNode getNext() {
            return next;
        }

        public void setNext(LFUQueueNode next) {
            this.next = next;
        }
    }


    /**
     * https://leetcode-cn.com/problems/lfu-cache/
     *
     * @param args
     */
    public static void main(String[] args) {
        LFUCache cache = new LFUCache(10 /* 缓存容量 */);

        cache.put(10, 13);
        cache.put(3, 17);
        cache.put(6, 11);
        cache.put(10, 5);
        cache.put(9, 10);
        System.out.println(cache.get(13));
        cache.put(2, 19);
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        cache.put(5, 25);
        System.out.println(cache.get(8));
        cache.put(9, 22);
        cache.put(5, 5);
        cache.put(1, 30);
        System.out.println(cache.get(11));
        cache.put(9, 12);
        System.out.println(cache.get(7));
        System.out.println(cache.get(5));
        System.out.println(cache.get(8));
        System.out.println(cache.get(9));
        cache.put(4, 30);
        cache.put(9, 3);
        System.out.println(cache.get(9));
        System.out.println(cache.get(10));
        System.out.println(cache.get(10));
        cache.put(6, 14);
        cache.put(3, 1);
        System.out.println(cache.get(3));
        cache.put(10, 11);
        System.out.println(cache.get(8));
        cache.put(2, 14);
        System.out.println(cache.get(1));
        System.out.println(cache.get(5));
        System.out.println(cache.get(4));
        cache.put(11, 4);
        cache.put(12, 24);
        cache.put(5, 18);
        System.out.println(cache.get(13));
        cache.put(7, 23);
        System.out.println(cache.get(8));
        System.out.println(cache.get(12));
        cache.put(3, 27);
        cache.put(2, 12);
        System.out.println(cache.get(5));
        cache.put(2, 9);
        cache.put(13, 4);
        cache.put(8, 18);
        cache.put(1, 7);
        System.out.println(cache.get(6));
        cache.put(9, 29);
        cache.put(8, 21);
        System.out.println(cache.get(5));
        cache.put(6, 30);
        cache.put(1, 12);
        System.out.println(cache.get(10));
        cache.put(4, 15);
        cache.put(7, 22);
        cache.put(11, 26);
        cache.put(8, 17);
        cache.put(9, 29);
        System.out.println(cache.get(5));
        cache.put(3, 4);
        cache.put(11, 30);
        System.out.println(cache.get(12));
        cache.put(4, 29);
        System.out.println(cache.get(3));
        System.out.println(cache.get(9));
        System.out.println(cache.get(6));
        cache.put(3, 4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(10));
        cache.put(3, 29);
        cache.put(10, 28);
        cache.put(1, 20);
        cache.put(11, 13);
        System.out.println(cache.get(3));
        cache.put(3, 12);
        cache.put(3, 8);
        cache.put(10, 9);
        cache.put(3, 26);
        System.out.println(cache.get(8));
        System.out.println(cache.get(7));
        System.out.println(cache.get(5));
        cache.put(13, 17);
        cache.put(2, 27);
        cache.put(11, 15);
        System.out.println(cache.get(12));
        cache.put(9, 19);
        cache.put(2, 15);
        cache.put(3, 16);
        System.out.println(cache.get(1));
        cache.put(12, 17);
        cache.put(9, 1);
        cache.put(6, 19);
        System.out.println(cache.get(4)); // 29
        System.out.println(cache.get(5)); // 18
        System.out.println(cache.get(5)); // 18
        cache.put(8, 1);
        cache.put(11, 7);
        cache.put(5, 2);
        cache.put(9, 28);
        System.out.println(cache.get(1)); // 20
        cache.put(2, 2);
        cache.put(7, 4);
        cache.put(4, 22);
        cache.put(7, 24);
        cache.put(9, 26);
        cache.put(13, 28);
        cache.put(11, 26);
    }

}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
