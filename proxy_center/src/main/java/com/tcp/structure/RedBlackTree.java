package com.tcp.structure;


/**
 * @ClassName RBTree
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/7 15:34
 **/
public class RedBlackTree {
    static final int RED = 1;
    static final int BLACK = 0;

    private class Node {

        int value;
        Node leftChild;
        Node rightChild;
        Node parent;
        int colour;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public void create(int value) {
        if (root == null) {
            root = new Node(value);
            root.colour = BLACK;
        }
    }

    public void add(int value) {
        if (root == null) {
            create(value);
        } else {
            create(root, root, value);
        }
    }

    public Node brother(Node node) {
        Node parent = node.parent;
        if (parent.leftChild == node) {
            return parent.rightChild;
        } else {
            return parent.leftChild;
        }
    }

    public boolean isLeft(Node node) {
        Node parent = node.parent;
        if (parent.leftChild == node) {
            return true;
        }
        return false;
    }

    public Node changeColour(Node node, Node parent) {
        if (parent.colour == RED) {
            Node brother = brother(parent);
            if (brother != null && brother.colour == RED) {
                //叔叔节点为红色
                parent.colour = BLACK;
                brother.colour = BLACK;
                parent.parent.colour = RED;

            }
            if(brother==null){
                
            }

        }
        return node;
    }

    public Node create(Node node, Node parent, int value) {
        if (node == null) {
            node = new Node(value);
            node.parent = parent;
            node.colour = RED;
            //插入修复操作
            changeColour(node, parent);


            return node;
        }
        if (value > node.value) {
            node.rightChild = create(node.rightChild, node, value);
        }
        if (value < node.value) {
            node.leftChild = create(node.leftChild, node, value);
        }
        return node;
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.add(33);
        tree.add(11);
        tree.add(21);
        tree.add(10);
        tree.add(12);
        tree.add(32);
        tree.add(45);
        tree.add(44);
        tree.add(46);
        System.out.println(1);
    }
}
