package com.github.wuchao.leetcode.problems;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {

    private int capacity;
    private Map<Integer, Integer> caches;
    private LinkedList<Integer> cacheKeyList = new LinkedList<>();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        caches = new HashMap<>(capacity);
    }

    public int _get(int key) {
        Object value = caches.get(key);
        if (value == null) {
            return -1;
        } else {
            return ((Integer) value).intValue();
        }
    }

    public int get(int key) {
        int value = _get(key);
        if (value != -1) {
            // 更新最新最久未使用队列
            refreshLRUQueue(key);
        }
        return value;
    }

    public void put(int key, int value) {
        if (this.capacity <= 0) {
            return;
        }

        int cacheValue = _get(key);

        // 新增 key，并且缓存容量达限
        if (cacheValue == -1 && caches.size() == capacity) {
            Integer cacheKey = cacheKeyList.getLast();
            // 删除最久未使用的缓存
            caches.remove(cacheKey);
            // 同时更新更新最新最久未使用队列
            cacheKeyList.removeLast();
        }

        // 更新缓存
        caches.put(key, value);
        // 更新最新最久未使用队列
        refreshLRUQueue(key);
    }

    /**
     * 更新最新最久未使用队列
     *
     * @param key
     */
    public void refreshLRUQueue(Integer key) {
        int index = cacheKeyList.indexOf(key);
        if (index == -1) {
            cacheKeyList.addFirst(key);
        } else {
            cacheKeyList.remove(index);
            cacheKeyList.addFirst(key);
        }
    }

    /**
     * https://leetcode-cn.com/problems/lru-cache
     * <p>
     * 缓存用map存储，只是多维护了一个队列
     * 队列和map的关于key的删除和添加操作要保持同步
     * map满的时候将队列中最后一个key从map里删除
     * get方法也要刷新队列，但是key要在缓存中存在
     *
     * @param args
     */
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(0 /* 缓存容量 */);

        cache.put(0, 0);
        System.out.println(cache.get(0));
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
