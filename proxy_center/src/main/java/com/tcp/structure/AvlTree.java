package com.tcp.structure;

/**
 * @ClassName AvlTree
 * @Description TODO
 * @Author xuetao
 * @Date 2021/2/7 11:11
 **/
public class AvlTree {
    private class Node {
        Node leftChild;
        Node rightChild;
        int height;
        int data;

        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    private Node root;

    public void add(int data) {
        root = create(root, data);
    }

    public void sortTree() {
        if (root != null) {
            printTree(root);
        }
    }

    private void printTree(Node node) {
        if (node.leftChild != null) {
            printTree(node.leftChild);
        }
        System.out.println(node);
        if (node.rightChild != null) {
            printTree(node.rightChild);
        }

    }

    private boolean disequilibrium(Node node, boolean left) {
        if (left) {
            return getHeight(node.leftChild) - getHeight(node.rightChild) > 1;
        } else {
            return getHeight(node.rightChild) - getHeight(node.leftChild) > 1;
        }

    }

    private Node create(Node node, int data) {
        if (node == null) {
            node = new Node(data);
            return node;
        } else {
            if (node.data > data) {
                //数据小，左排
                node.leftChild = create(node.leftChild, data);
                //向左排后 左右高度差大于1，则开始转
                if (disequilibrium(node, true)) {
                    //比左子树的数据小， 左左旋
                    if (node.leftChild.data > data) {
                        node = LLWhirl(node);
                    } else {
                        //比左子树的数据大，左右旋
                        node = LRWhirl(node);
                    }
                }
            } else if (node.data < data) {
                //数据大，右排
                node.rightChild = create(node.rightChild, data);
                //向右排后 左右高度差大于1 则开始转
                if (disequilibrium(node, false)) {
                    //比右子树的数据小，则右左旋
                    if (node.rightChild.data > data) {
                        node = RLWhirl(node);
                    } else {
                        //比右子树的数据大，则右右旋
                        node = RRWhirl(node);
                    }
                }
            }
        }
        setHeight(node);
        return node;
    }

    private void setHeight(Node node) {
        node.height = Math.max(getHeight(node.rightChild), getHeight(node.leftChild)) + 1;
    }

    private int getHeight(Node node) {
        return node == null ? -1 : node.height;
    }

    private Node LLWhirl(Node node) {
        Node top = node.leftChild;
        node.leftChild = top.rightChild;
        top.rightChild = node;
        setHeight(top);
        setHeight(node);
        return top;
    }

    private Node RRWhirl(Node node) {
        Node top = node.rightChild;
        node.rightChild = top.leftChild;
        top.leftChild = node;
        setHeight(node);
        setHeight(top);
        return top;
    }

    private Node LRWhirl(Node node) {
        node.leftChild = RRWhirl(node.leftChild);
        return LLWhirl(node);

    }

    private Node RLWhirl(Node node) {
        node.rightChild = LLWhirl(node.rightChild);
        return RRWhirl(node);
    }

    private Node find(int data) {
        return get(root, data);
    }

    private Node get(Node node, int data) {
        if (node.data == data) {
            return node;
        }
        if (node.data > data) {
            if (node.leftChild == null) {
                return null;
            }
            return get(node.leftChild, data);
        }
        if (node.rightChild == null) {
            return null;
        }
        return get(node.rightChild, data);
    }

    public boolean delete(int data) {
        Node node = get(root, data);
        if (node != null) {

        }
        return true;
    }


    private void deleteNode(Node root, int data) {
        //数据小，向左找
        if (root.data > data) {
            Node leftChild = root.leftChild;
            if (leftChild != null) {
                if (leftChild.data == data) {
                    Node deleteNode = leftChild;
                    if (deleteNode.leftChild != null && deleteNode.rightChild != null) {

                    } else if (deleteNode.leftChild != null) {
                        root.leftChild = deleteNode.leftChild;
                    } else if (deleteNode.rightChild != null) {
                        root.rightChild = deleteNode.rightChild;
                    }
                }
                deleteNode(leftChild, data);
            }
        }
        //数据大，向右找
        if (root.data < data) {
            if (root.rightChild.data == data) {

            }
            deleteNode(root.rightChild, data);
        }

        if (root.data == data) {
            //根节点
        }
    }

    private Node getLeftChild(Node node) {
        if (node.leftChild == null) {
            return node;
        }
        return getLeftChild(node.leftChild);
    }

    private Node getRightChild(Node node) {
        if (node.rightChild == null) {
            return node;
        }
        return getRightChild(node.rightChild);
    }

    public Node checkLeftChild(Node node) {
        Node leftChild = getLeftChild(node);
        if (leftChild == node) {
            return null;
        }
        return leftChild;
    }


    public Node checkRightChild(Node node) {
        Node rightChild = getRightChild(node);
        if (rightChild == node) {
            return null;
        }
        return rightChild;
    }

    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
        tree.add(88);
        tree.add(24);
        tree.add(17);
        tree.add(14);
        tree.add(32);
        tree.add(87);
        tree.add(23);
        tree.add(16);
        tree.add(13);
        tree.add(31);
        tree.add(12);
        Node node = tree.find(31);
        if (node != null) {
            Node leftChild = tree.checkLeftChild(node);
            System.out.println(leftChild);
        }
        tree.sortTree();
    }
}
