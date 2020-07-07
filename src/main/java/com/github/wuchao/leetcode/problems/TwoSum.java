package com.github.wuchao.leetcode.problems;

import java.util.Arrays;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        int v;
        int index;
        int size = nums.length;

        for (int i = 0; i < size; i++) {
            v = target - nums[i];
            index = index(nums, i + 1, v);
            if ( index != -1) {
                return new int[]{i, index};
            }
        }

        return new int[0];
    }

    public static int index(int[] nums, int forIndex, int value) {
        int size = nums.length;
        for (; forIndex < size; forIndex++) {
            if (nums[forIndex] == value) {
                return forIndex;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = twoSum(nums, target);
        System.out.println(Arrays.toString(result));
    }
}
