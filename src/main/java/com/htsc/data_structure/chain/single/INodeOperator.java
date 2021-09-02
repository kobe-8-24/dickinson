package com.htsc.data_structure.chain.single;

/**
 * 链表操作接口
 */
public interface INodeOperator {
    /**
     * 创建链表
     *
     * @param num num
     * @return node
     */
    Node<Integer> createNode(Integer num);

    /**
     * 添加节点 - 指定数据
     *
     * @param data data
     * @return node
     */
    Node<Integer> addNode(Integer data);

    /**
     * 添加节点 - 指定节点
     *
     * @param node node
     * @return node
     */
    Node<Integer> addNode(Node<Integer> node);

    /**
     * 添加节点 - 指定位置 插入 指定数据
     *
     * @param index index
     * @param data data
     * @return node
     */
    Node<Integer> addPointerNode(Integer index, Integer data);

    /**
     * 替换节点
     *
     * @param index index
     * @param data data
     * @return node
     */
    Node<Integer> replaceNode(Integer index, Integer data);

    /**
     * 删除第index个节点
     *
     * @param index index
     * @return node
     */
    Node<Integer> deleteNode(Integer index);

    /**
     * 打印节点
     *
     * @param node node
     * @param methodName methodName
     */
    void printList(Node<Integer> node, String methodName);

    /**
     * 节点长度
     *
     * @param node node
     * @return length
     */
    Integer length(Node<Integer> node);

    /**
     * 链表反转
     *
     * @param node node
     * @return node
     */
    Node<Integer> reverseIteratively(Node<Integer> node);

    /**
     * 查找单链表的中间节点
     *
     * @param node node
     * @return node
     */
    Node<Integer> searchMid(Node<Integer> node);

    /**
     * 查找倒数 第k个元素
     *
     * @param node node
     * @param k k
     * @return node
     */
    Node<Integer> findElem(Node<Integer> node, Integer k);

    /**
     * 对链表进行排序
     *
     * @param node node
     * @return node
     */
    Node<Integer> orderList(Node<Integer> node);

    /**
     * 删除链表中的重复节点
     *
     * @param node node
     * @return node
     */
    Node<Integer> deleteDuplicate(Node<Integer> node);

    /**
     * 从尾到头输出单链表，采用递归方式实现
     *
     * @param node node
     */
    void printListReversely(Node<Integer> node);

    /**
     * 判断链表是否有环，单向链表有环时，尾节点相同
     *
     * @param node node
     * @return true
     */
    boolean isLoop(Node<Integer> node);

    /**
     * 找出链表环的入口
     *
     * @param node node
     * @return node
     */
    Node<Integer> findLoopPort(Node<Integer> node);
}
