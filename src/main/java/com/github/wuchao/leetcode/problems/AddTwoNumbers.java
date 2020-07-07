package com.github.wuchao.leetcode.problems;

public class AddTwoNumbers {

    /**
     * Definition for singly-linked list.
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) { val = x; }
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            if (l1 == null) {
                return l2;
            }
            if (l2 == null) {
                return l1;
            }
        }

        // 进位
        int carry = 0;
        int sum;
        ListNode result = null;
        ListNode next = null;
        ListNode node = next;
        ListNode tNode;
        ListNode p1 = l1;
        ListNode p2 = l2;

        /**
         * 加法要执行完，只要有一个加数不为空，或者进位不为空，都要继续执行
         */
        while (p1 != null || p2 != null || carry > 0) {
            sum = 0;

            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }

            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }

            sum += carry;

            if (sum >= 10) {
                tNode = new ListNode(sum % 10);
                carry = sum / 10;
            } else {
                tNode = new ListNode(sum);
                carry = 0;
            }

            if (node == null) {
                node = tNode;
                result = node;
            } else {
                node.next = tNode;
                node = tNode;
            }
        }

        return result;
    }


}
