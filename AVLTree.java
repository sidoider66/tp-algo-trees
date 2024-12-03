package trees;

class AVLTree {
    AVLNode root;

    // Get height of the node
    int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // Update the height of a node
    void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    // Right rotation
    AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        // Return new root
        return x;
    }

    // Left rotation
    AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        // Return new root
        return y;
    }

    // Get balance factor of node
    int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Insert a node
    AVLNode insert(AVLNode node, int value) {
        // 1. Perform normal BST insert
        if (node == null) {
            return new AVLNode(value);
        }
        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node; // Duplicates are not allowed
        }

        // 2. Update height of this ancestor node
        updateHeight(node);

        // 3. Get the balance factor of this ancestor node to check whether
        // this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && value < node.left.value) {
            return rotateRight(node);
        }

        // Right Right Case
        if (balance < -1 && value > node.right.value) {
            return rotateLeft(node);
        }

        // Left Right Case
        if (balance > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right Left Case
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // return the (unchanged) node pointer
        return node;
    }

 void buildAVL(int[] values) {
        for (int value : values) {
            root = insert(root, value);
        }
    }

    boolean search(int value) {
        return searchRec(root, value);
    }

    boolean searchRec(AVLNode node, int value) {
        if (node == null) {
            return false;
        }
        if (value == node.value) {
            return true;
        }
        return value < node.value ? searchRec(node.left, value) : searchRec(node.right, value);
    }
}