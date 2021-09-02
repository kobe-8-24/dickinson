package com.htsc.data.structure.chain.single;

/**
 * 测试 链表 基本操作
 */
public class NodeMain {
    public static void main(String[] args) {
        INodeOperator nodeOperator = new NodeOperator();

        // 创建链表 - 固定大小
        final Node<Integer> createNode = nodeOperator.createNode(5);
        nodeOperator.printList(createNode, "nodeOperator.createNode");

        // 增加节点 - 指定数据
        final Node<Integer> addNode = nodeOperator.addNode(999);
        nodeOperator.printList(addNode, "nodeOperator.addNode(data)");

        // 增加节点 - 指定节点
        final Node<Integer> addNode2 = nodeOperator.addNode(new Node<>(1024));
        nodeOperator.printList(addNode2, "nodeOperator.addNode(node)");

        // 增加节点 - 指定节点插入指定位置
        final Node<Integer> addPointerNode = nodeOperator.addPointerNode(3, 2048);
        nodeOperator.printList(addPointerNode, "nodeOperator.addPointerNode");
        
        // 替换节点 - 指定位置
        final Node<Integer> replaceNode = nodeOperator.replaceNode(3, 888);
        nodeOperator.printList(replaceNode, "nodeOperator.replaceNode");

        // 删除节点 - 指定位置
        final Node<Integer> deleteNode = nodeOperator.deleteNode(3);
        nodeOperator.printList(deleteNode, "nodeOperator.deleteNode");

        // 打印节点长度
        final Integer length = nodeOperator.length(deleteNode);
        System.out.println("节点长度：" + length);

        // TODO 链表反转
        final Node<Integer> reverseIteratively = nodeOperator.reverseIteratively(deleteNode);
        nodeOperator.printList(reverseIteratively, "nodeOperator.reverseIteratively");

        // TODO 查找单链表的中间节点
        final Node<Integer> searchMid = nodeOperator.searchMid(reverseIteratively);
        nodeOperator.printList(searchMid, "nodeOperator.searchMid");

        // TODO 查找倒数 第k个元素
        final Node<Integer> findElem = nodeOperator.findElem(searchMid, 2);
        nodeOperator.printList(findElem, "nodeOperator.findElem");

        // TODO 删除重复元素
        final Node<Integer> deleteDuplicate = nodeOperator.deleteDuplicate(findElem);
        nodeOperator.printList(deleteDuplicate, "nodeOperator.deleteDuplicate");

        // TODO 对链表进行排序
        final Node<Integer> orderList = nodeOperator.orderList(findElem);
        nodeOperator.printList(orderList, "nodeOperator.orderList");

        // TODO 判断链表是否有环，单向链表有环时，尾节点相同
        final boolean loop = nodeOperator.isLoop(orderList);
        System.out.println("nodeOperator.isLoop:" + loop);

        // TODO 找出链表环的入口
        final Node<Integer> loopPort = nodeOperator.findLoopPort(orderList);
        nodeOperator.printList(loopPort, "nodeOperator.findLoopPort");
    }
}
