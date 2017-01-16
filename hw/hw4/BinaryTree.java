import java.util.*;

interface DeepCloneable<T>
{
	public T deepClone();
}

public abstract class BinaryTree<E extends DeepCloneable<E>> implements TreeInterface<E> {
	protected BinaryNode<E> root=null; // reference to the root
    protected int size=0; // number of nodes in the tree

    public BinaryTree(){ }

    public BinaryTree(BinaryTree<E> sourceTree)
    {
    	// YOU WRITE (sh2ould do a DEEP COPY by calling deepCopyTree)
        root = deepCopyTree(sourceTree.root);
        size = sourceTree.getSize();
    }

    /** Clears the whole tree */
    public void clear()
    {
    	root = null;
    	size = 0;
    }
    
    protected BinaryNode<E> deepCopyTree(BinaryNode<E> node)
    {
    		BinaryNode<E> newNode;
    	
    	// YOU FINISH: if the node parameter doesn't exist, return null,
	//     otherwise, make a deep copy of the node parameter using a deep clone each Node's data!
	//     then set its left to a deepCopy of the parameter's left node (recursive call here)
    	//     and set its right to a deepCopy of the parameter's right node (recursive call here)
            if(node == null)
                return null;
            else
            {
                newNode = new BinaryNode(node.getData().deepClone());
                newNode.setLeftChild(deepCopyTree(node.getLeftChild()));
                newNode.setRightChild(deepCopyTree(node.getRightChild()));
            }
       		return newNode;
    }

    @Override /** Preorder traversal from the root */
    public void preorder(Visitor<E> visitor) 
    {
        preorder(root, visitor);
    }

    @Override /** Inorder traversal from the root*/
    public void inorder(Visitor<E> visitor) 
    {
        inorder(root, visitor);
    }

    @Override /** Postorder traversal from the root */
    public void postorder(Visitor<E> visitor) 
    {
    	postorder(root, visitor);
    }

    @Override /** Get the number of nodes in the tree */
    public int getSize() 
    {
    	return size;
    }


    @Override /** Return true if the tree is empty */
    public boolean isEmpty() 
    {
    	return getSize() == 0;
    }

    /** Preorder traversal from a subtree */
    protected void preorder(BinaryNode<E> root, Visitor<E> visitor) 
    {
		if (root == null)
			return;
		visitor.visit(root.getData());
		preorder(root.getLeftChild(), visitor);
		preorder(root.getRightChild(), visitor);
    }

     /** Inorder traversal from a subtree */
    protected void inorder(BinaryNode<E> root, Visitor<E> visitor) 
    {
         // you finish (part of HW#4), SEE preorder above this
        if (root == null)
            return;
        inorder(root.getLeftChild(), visitor);
        visitor.visit(root.getData());
        inorder(root.getRightChild(), visitor);

    }

     /** Posorder traversal from a subtree */
    protected void postorder(BinaryNode<E> root, Visitor<E> visitor) 
    {
         // you finish (part of HW#4)
        if (root == null)
                return;
        postorder(root.getLeftChild(), visitor);
        postorder(root.getRightChild(), visitor);
        visitor.visit(root.getData());

    }
} // end abstract BinaryTree class


