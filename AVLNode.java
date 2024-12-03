package trees;

class AVLNode {
    int value;
    AVLNode left, right;
    int height;

    AVLNode(int value) {
        this.value = value;
        this.height = 1; // New node is initially added at leaf
    }
}

