package com.htsc.data.structure.chain.single;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 链表 常见操作
 */
@Component
public class NodeOperator implements INodeOperator {
    private static Node<Integer> head = null;

    /**
     * 创建链表
     *
     * @param num num
     * @return node
     */
    @Override
    public Node<Integer> createNode(Integer num) {
        // 首节点如果为空, 则初始化首节点
        head = Objects.isNull(head) ? new Node<>(0) : head;

        // 声明一个变量用来在移动过程中指向当前节点
        // 指向首节点
        Node<Integer> currentNode = head;

        // 创建链表
        for (int i = 1; i < num; i++) {
            // 生成新的节点
            // 把新的节点连起来
            currentNode.nextNode = new Node<>(i);

            // 当前节点往后移动 - 指針引用后移
            currentNode = currentNode.nextNode;
        }

        // 当for循环完成之后 nextNode指向最后一个节点，
        // 重新赋值让它指向首节点
        currentNode = head;

        return currentNode;
    }

    /**
     * 添加节点 - 指定数据
     *
     * @param data data
     * @return node
     */
    @Override
    public Node<Integer> addNode(Integer data) {
        Node<Integer> newNode = new Node<>(data);

        // 判断头节点是否为空, 初始化
        return initialHeadNode(newNode);
    }

    /**
     * 添加节点 - 指定节点
     *
     * @param node node
     * @return node
     */
    @Override
    public Node<Integer> addNode(Node<Integer> node) {
        // 判断头节点是否为空, 初始化
        return initialHeadNode(node);
    }

    /**
     * 添加节点 - 指定位置
     *
     * @param index index
     * @param data  data
     * @return node
     */
    @Override
    public Node<Integer> addPointerNode(Integer index, Integer data) {
        int i = 0;

        Node<Integer> currentNode = head;

        // 插入节点
        while (currentNode != null) {

            if (index == i) {
                // 在該節點後面插入新的節點
                // 生成新的节点
                Node<Integer> toBeInsertedNode = new Node<>(data);

                //先保存下一个节点
                Node<Integer> node = currentNode.nextNode;

                //插入新节点
                currentNode.nextNode = toBeInsertedNode;

                //新节点的下一个节点指向 之前保存的节点
                toBeInsertedNode.nextNode = node;
            }

            currentNode = currentNode.nextNode;

            i++;
        }

        //循环完成之后 nextNode指向最后一个节点

        //重新赋值让它指向首节点
        currentNode = head;

        return currentNode;
    }

    /**
     * 替换节点
     *
     * @param index index
     * @param data  data
     * @return node
     */
    @Override
    public Node<Integer> replaceNode(Integer index, Integer data) {
        int i = 0;

        Node<Integer> currentNode = head;

        // 替换节点
        while (currentNode != null) {

            if (index == i) {
                // 生成新的节点
                Node<Integer> toBeInsertedNode = new Node<>(data);

                // 先保存要替换节点的下一个节点
                Node<Integer> node = currentNode.nextNode.nextNode;

                // 被替换节点 指向为空 ，等待java垃圾回收
                currentNode.nextNode.nextNode = null;

                //插入新节点
                currentNode.nextNode = toBeInsertedNode;

                //新节点的下一个节点指向 之前保存的节点
                toBeInsertedNode.nextNode = node;
            }

            currentNode = currentNode.nextNode;

            i++;
        }

        //循环完成之后 nextNode指向最后一个节点

        //重新赋值让它指向首节点
        currentNode = head;

        return currentNode;
    }

    /**
     * 删除第index个节点
     *
     * @param index index
     * @return boolean
     */
    @Override
    public Node<Integer> deleteNode(Integer index) {
        int i = 0;

        Node<Integer> currentNode = head;

        // 替换节点
        while (currentNode != null) {

            if (index == i) {

                Node<Integer> node = currentNode.nextNode.nextNode;

                currentNode.nextNode.nextNode = null;

                currentNode.nextNode = node;
            }

            currentNode = currentNode.nextNode;

            i++;
        }

        //循环完成之后 nextNode指向最后一个节点

        //重新赋值让它指向首节点
        currentNode = head;

        return currentNode;
    }

    /**
     * 打印节点
     *
     * @param node node
     * @param methodName methodName
     */
    @Override
    public void printList(Node<Integer> node, String methodName) {
        while (node != null) {
            // 打印
            System.out.println("节点:" + node.data);

            node = node.nextNode;
        }

        // 打印間隔
        System.out.println("--------------------------" + methodName + "--------------------------");
    }

    /**
     * 节点长度
     *
     * @param node node
     * @return length
     */
    @Override
    public Integer length(Node<Integer> node) {
        int length = 0;
        Node<Integer> tmp = head;
        while (tmp != null) {
            length++;
            tmp = tmp.nextNode;
        }
        return length;
    }

    /**
     * 链表反转
     *
     * @param node node
     * @return node
     */
    @Override
    public Node<Integer> reverseIteratively(Node<Integer> node) {
        Node<Integer> pReversedHead = head;
        Node<Integer> pNode = head;
        Node<Integer> pPrev = null;
        while (pNode != null) {
            Node<Integer> pNext = pNode.nextNode;
            if (pNext == null) {
                pReversedHead = pNode;
            }
            pNode.nextNode = pPrev;
            pPrev = pNode;
            pNode = pNext;
        }
        head = pReversedHead;
        return head;
    }

    /**
     * 查找单链表的中间节点
     *
     * @param node node
     * @return node
     */
    @Override
    public Node<Integer> searchMid(Node<Integer> node) {
        Node<Integer> p = head, q = head;
        while (p != null && p.nextNode != null && p.nextNode.nextNode != null) {
            p = p.nextNode.nextNode;
            q = q.nextNode;
        }
        return q;
    }

    /**
     * 查找倒数 第k个元素
     *
     * @param node node
     * @param k    k
     * @return node
     */
    @Override
    public Node<Integer> findElem(Node<Integer> node, Integer k) {
        if (k < 1 || k > this.length(node)) {
            return null;
        }
        Node<Integer> p1 = head;
        Node<Integer> p2 = head;
        for (int i = 0; i < k; i++)// 前移k步
            p1 = p1.nextNode;
        while (p1 != null) {
            p1 = p1.nextNode;
            p2 = p2.nextNode;
        }
        return p2;
    }

    /**
     * 对链表进行排序
     *
     * @param node node
     * @return node
     */
    @Override
    public Node<Integer> orderList(Node<Integer> node) {
        Node<Integer> nextNode;
        int tmp;
        Node<Integer> curNode = head;
        while (curNode.nextNode != null) {
            nextNode = curNode.nextNode;
            while (nextNode != null) {
                if (curNode.data > nextNode.data) {
                    tmp = curNode.data;
                    curNode.data = nextNode.data;
                    nextNode.data = tmp;
                }
                nextNode = nextNode.nextNode;
            }
            curNode = curNode.nextNode;
        }
        return head;
    }

    /**
     * 删除链表中的重复节点
     *
     * @param node node
     * @return node
     */
    @Override
    public Node<Integer> deleteDuplicate(Node<Integer> node) {
        Node<Integer> p = head;
        while (p != null) {
            Node<Integer> q = p;
            while (q.nextNode != null) {
                if (Objects.equals(p.data, q.nextNode.data)) {
                    q.nextNode = q.nextNode.nextNode;
                } else {
                    q = q.nextNode;
                }
            }
            p = p.nextNode;
        }

        return head;
    }

    /**
     * 从尾到头输出单链表，采用递归方式实现
     *
     * @param node node
     */
    @Override
    public void printListReversely(Node<Integer> node) {
        if (node != null) {
            printListReversely(node.nextNode);
            System.out.println("printListReversely:" + node.data);
        }
    }

    /**
     * 判断链表是否有环，单向链表有环时，尾节点相同
     *
     * @param node node
     * @return true
     */
    @Override
    public boolean isLoop(Node<Integer> node) {
        Node<Integer> fast = head, slow = head;
        if (fast == null) {
            return false;
        }
        while (fast != null && fast.nextNode != null) {
            fast = fast.nextNode.nextNode;
            slow = slow.nextNode;
            if (fast == slow) {
                System.out.println("该链表有环");
                return true;
            }
        }
        return false;
    }

    /**
     * 找出链表环的入口
     *
     * @param node node
     * @return node
     */
    @Override
    public Node<Integer> findLoopPort(Node<Integer> node) {
        Node<Integer> fast = head, slow = head;
        while (fast != null && fast.nextNode != null) {
            slow = slow.nextNode;
            fast = fast.nextNode.nextNode;
            if (slow == fast)
                break;
        }
        if (fast == null || fast.nextNode == null)
            return null;
        slow = head;
        while (slow != fast) {
            slow = slow.nextNode;
            fast = fast.nextNode;
        }
        return slow;
    }

    private Node<Integer> initialHeadNode(Node<Integer> newNode) {
        head = Objects.isNull(head) ? new Node<>(0) : head;

        Node<Integer> tmp = head;

        while (tmp.nextNode != null) {
            tmp = tmp.nextNode;
        }

        tmp.nextNode = newNode;

        tmp = head;

        return tmp;
    }
}
