package trees;

class BinarySearchTree {
    TreeNode root;

    void insert(int value) {
        root = insertRec(root, value);
    }

    TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    boolean search(int value) {
        return searchRec(root, value);
    }

    boolean searchRec(TreeNode root, int value) {
        if (root == null) {
            return false;
        }
        if (value == root.value) {
            return true;
        }
        return value < root.value ? searchRec(root.left, value) : searchRec(root.right, value);
    }

    public static void buildBST(int[] values) {
        BinarySearchTree bst = new BinarySearchTree();
        for (int value : values) {
            bst.insert(value);
        }
    }
}