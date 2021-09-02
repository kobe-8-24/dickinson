package com.htsc.data.structure.chain.single;

/**
 * 链表 结构
 */
class Node<E> {
    //节点数据
    E data;

    // 引用下一个节点对象。
    // 在Java中没有指针的概念，Java中的引用和C语言的指针类似
    Node<E> nextNode;

    Node(E data) {
        this.data = data;
    }
}

