private BinaryNode<E> _findNode( BinaryNode<E> node, E targetData ) 
{
	if(!node)
		return null;
	else if( targetData.compareTo(node.getData()) < 1)
		return _findNode(node.getLeftChild(), targetData);
	else if( targetData.compareTo(node.getData()) > 1)
		return _findNode(node.getRightChild(), targetData);
	else 
		return node;
}
