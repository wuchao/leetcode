package com.github.wuchao.leetcode.problems;

public class LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstring(String s) {
        if (s != null && s.length() > 0) {

            char[] subStrs = s.toCharArray();
            int longestStrStartIndex = 0;
            int prevLongestStrCount = 0;
            int longestStrCount = 0;
            int index;

            for (int i = 0; i < subStrs.length; i++) {
                index = contains(subStrs, subStrs[i], longestStrStartIndex, i);
                if (index == -1) {
                    longestStrCount++;
                } else {
                    if (longestStrCount > prevLongestStrCount) {
                        prevLongestStrCount = longestStrCount;
                    }

                    longestStrCount = i - index;
                    longestStrStartIndex = index + 1;
                }
            }

            return longestStrCount > prevLongestStrCount
                    ? longestStrCount
                    : prevLongestStrCount;

        }else {
            return 0;
        }
    }

    public static int contains(char[] strs, char c, int fromIndex, int toIndex) {
        if (strs != null) {
            for (; fromIndex < toIndex; fromIndex++) {
                if (strs[fromIndex] == c) {
                    return fromIndex;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int count;

        String str1 = "abcabcbb";
        count = lengthOfLongestSubstring(str1);
        System.out.println(count);

        String str2 = "bbbbb";
        count = lengthOfLongestSubstring(str2);
        System.out.println(count);

        String str3 = "pwwkew";
        count = lengthOfLongestSubstring(str3);
        System.out.println(count);

        String str4 = "cdd";
        count = lengthOfLongestSubstring(str4);
        System.out.println(count);
    }

}
