package com.github.wuchao.leetcode.problems;

public class MedianOfTwoSortedArrays {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size1 = nums1 != null ?  nums1.length : 0;
        int size2 = nums2 != null ? nums2.length : 0;

        int size = size1 + size2;
        boolean even = size % 2 == 0 ? true : false;
        int medianIndex = size / 2;

        int index1 = 0;
        int index2 = 0;

        int value1 = 0;
        int value2 = 0;

        if (size1 == 0) {
            if (even) {
                return (nums2[medianIndex - 1] + nums2[medianIndex]) * 1.0 / 2;
            } else {
                return (nums2[medianIndex]);
            }

        } else if (size2 == 0) {
            if (even) {
                return (nums1[medianIndex - 1] + nums1[medianIndex]) * 1.0 / 2;
            } else {
                return (nums1[medianIndex]);
            }

        } else {

            for (int i = 0; i <= medianIndex; i++) {
                value1 = value2;

                if (index1 >= size1) {
                    value2 = nums2[index2++];
                } else if (index2 >= size2) {
                    value2 = nums1[index1++];
                } else {
                    if (nums1[index1] <= nums2[index2]) {
                        value2 = nums1[index1++];
                    } else {
                        value2 = nums2[index2++];
                    }
                }
            }

            if (even) {
                return (value1 + value2) * 1.0 / 2;
            } else {
                return value2;
            }
        }
    }


    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{2};
        double median = findMedianSortedArrays(nums1, nums2);
        System.out.println(median);

        nums1 = new int[]{1, 2};
        nums2 = new int[]{3, 4};
        median = findMedianSortedArrays(nums1, nums2);
        System.out.println(median);
    }

}
