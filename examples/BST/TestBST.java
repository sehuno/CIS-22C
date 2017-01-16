// Example of using the BST class

public class TestBST {
	static class DisplayString implements Visitor<String>
	{
		@Override
		public void visit(String obj) {
			System.out.println(obj);
		}
	}

	static class DisplayInteger implements Visitor<Integer>
	{
		@Override
		public void visit(Integer obj) {
			System.out.println(obj);
		}
	}

  public static void main(String[] args) {
    // Create a BST
    BST<String> tree = new BST<>();
    tree.insert("George");
    tree.insert("Michael");
    tree.insert("Tom");
    tree.insert("Adam");
    tree.insert("Jones");
    tree.insert("Peter");
    tree.insert("Daniel");

    // Traverse tree
    DisplayString displayStr = new DisplayString();
    System.out.print("Inorder (sorted): ");
    tree.inorder(displayStr);
    System.out.print("\nPostorder: ");
    tree.postorder(displayStr);
    System.out.print("\nPreorder: ");
    tree.preorder(displayStr);
    System.out.print("\nThe number of nodes is " + tree.getSize());

    // Search for an element
    System.out.print("\nIs Peter in the tree? " +
      tree.contains("Peter"));

    Integer[] numbers = {2, 4, 3, 1, 8, 5, 6, 7};
    BST<Integer> intTree = new BST<>(numbers);
    DisplayInteger displayInt = new DisplayInteger();
    System.out.print("\nInorder (sorted): ");
    intTree.inorder(displayInt);
  }
}
