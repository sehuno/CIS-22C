
// Adapted from Code By Y. Daniel Liang
// Modified by C. Lee-Klawender


public class BST<E extends Comparable<E>>
	extends BinaryTree<E>
{

    private boolean foundNode; // helper variable

    /** Create a default binary tree */
    public BST() {
    }

    public BST(BST<E> sourceTree)
    {
        super(sourceTree);
    }

    /** Create a binary tree from an array of objects */
    public BST(E[] objects)
    {
    	for (int i = 0; i < objects.length; i++)
    		insert(objects[i]);
    }

    @Override /** Returns true if the element is in the tree */
    public boolean contains(E e)
    {
    	BinaryNode<E> current = root; // Start from the root

    	while (current != null)
    	{
    		if (e.compareTo(current.getData()) < 0)
    		{
    			current = current.getLeftChild();
    		}
    		else if (e.compareTo(current.getData()) > 0)
    		{
    				current = current.getRightChild();
    		}
    		else // element matches current.getData()
    			return true; // Element is found
    	} // end while

    	return false;
    }


	@Override
    /**
	 * Returns the data of the Node that equals the parameter, null if not found.
	 * */
	 public E getEntry(E e)
	 {
		BinaryNode<E> foundNode = _findNode(root, e); // YOU WRITE FOR LAB EX. 7.3
		if( foundNode != null)
			return foundNode.getData();

		return null;
	}


	@Override
	/** Insert element o into the binary tree
	 * Return true if the element is inserted successfully */
	public boolean insert(E e)
	{
		if (root == null)
			root = new BinaryNode<E>(e); // Create a new root
		else
		{

			// FILL IN HERE FOR HW#4***********************

			size++;
		}
		return true; // Element inserted successfully

	}


	@Override
	/** Delete an element from the binary tree.
	 * Return true if the element is deleted successfully
	 * Return false if the element is not in the tree */
	public boolean delete(E e)
	{
		foundNode = false;		// initialize boolean instance variable
		root = _delete(root, e); //call private method to do actual deletion

		if( foundNode )
		{
			size--;// Element deleted successfully
		}
		return foundNode;
	}

	// Private recursive method that returns an updated "root" node from where current node is
    private BinaryNode<E> _delete( BinaryNode<E> node, E e )
    {
        if( node==null )
        {
            return null;
        }
        if ( e.compareTo(node.getData()) < 0 )
             node.setLeftChild( _delete(node.getLeftChild(), e) );//recursive call
        else
            if( e.compareTo(node.getData()) > 0 )
                node.setRightChild( _delete(node.getRightChild(), e) );//recursive call
            else
            {
                foundNode = true;
                node = _deleteNode( node );
            }
        return node;
    } // end _delete

    // Private method that either "moves up" the left or right child, OR
    //    replaces the data in the nodeToDelete with the data in the rightmost child of
    //    the nodeToDelete's left child, then "removes" that node
    private BinaryNode<E> _deleteNode( BinaryNode<E> nodeToDelete )
    {
        if( nodeToDelete.isLeaf() ) // node to delete has no children
        {
           return null;
        }
        if( !nodeToDelete.hasLeftChild() ) // node to delete has no LEFT child
        {
            return nodeToDelete.getRightChild();
        }
        if( !nodeToDelete.hasRightChild() ) // node to delete has no RIGHT child
        {
            return nodeToDelete.getLeftChild();
        }
        // must have left and right children
        // Locate the rightmost node in the left subtree of
        // the node to delete and also its parent
        BinaryNode<E> parentOfRightMost = nodeToDelete;
        BinaryNode<E> rightMost = nodeToDelete.getLeftChild();

        while (rightMost.getRightChild() != null) {
            parentOfRightMost = rightMost;
            rightMost = rightMost.getRightChild(); // Keep going to the right
        }

        // Replace the element in nodeToDelete by the element in rightMost
        nodeToDelete.setData( rightMost.getData() ); // don't really delete the node, just change the data

        // Eliminate rightmost node
        if (parentOfRightMost.getRightChild() == rightMost)
            parentOfRightMost.setRightChild( rightMost.getLeftChild() );
        else
            // Special case: nodeToDelete's leftChild has no rightChild
            parentOfRightMost.setLeftChild( rightMost.getLeftChild() );

        return nodeToDelete;
    } // end private _deleteNode


} // end class BST
