//////////////////////////////////////////////////////////////////////////
// Use this class as the Data class:

import java.util.Scanner;
import java.util.Comparator;
import java.io.*;

// DeAnzaContact class for HW#4

public class DeAnzaContact implements DeepCloneable<DeAnzaContact>
{

	private String lastName="";
	private String firstName="";
	private String phone="";
	private String department="";
	
	public DeAnzaContact(){}
	
	public DeAnzaContact(String last,
						String first,
						String ph,
						String dept)
	{
		setLastName(last);
		setFirstName(first);
		setPhone(ph);
		setDepartment(dept);
	}
	
	public String getLastName(){ return lastName; }
	
	public String getFirstName(){ return firstName; }
	
	public String getPhone(){ return phone; }
	
	public String getDepartment(){ return department; }
	
	public boolean setFirstName(String first)
	{
		if( first == null )
			return false;
		firstName = first;
		return true;
	}
	
	public boolean setLastName(String last)
	{
		if( last == null )
			return false;
		lastName = last;
		return true;
	}
	
	public boolean setPhone(String ph)
	{
		if( ph == null )
			return false;
		phone = ph;
		return true;
	}
	
	public boolean setDepartment(String dept)
	{
		if( dept == null )
			return false;
		department = dept;
		return true;
	}
	
	public String toString()
	{
		return lastName + ", " + firstName + ", " + phone + ", " + department;
	}
	
	@Override
	public DeAnzaContact deepClone() 
	{
		return new DeAnzaContact(lastName, firstName, phone, department);
	}

	public static Scanner userScanner = new Scanner(System.in);

	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile()
	{
		String filename;
        Scanner scanner=null;
        
		System.out.print("Enter the input filename: ");
		filename = userScanner.nextLine();
    	File file= new File(filename);

    	try{
    		scanner = new Scanner(file);
    	}// end try
    	catch(FileNotFoundException fe){
    	   System.out.println("Can't open input file\n");
   	    	return null; // array of 0 elements
    	} // end catch
    	return scanner;
	}

	public static void testBST(BST<DeAnzaContact> tree)
	{
		String last, first, dept;
		DeAnzaContact searchItem;
		DeAnzaContact foundItem;
		String prompt=" that should be in the tree: ";

		for (int i = 0; i < 2; ++i)
		{
			System.out.print( "\nEnter a last name" + prompt );
			last = userScanner.nextLine();
			System.out.print( "Enter a first name" + prompt );
			first = userScanner.nextLine();
			System.out.print("Enter department: ");
			dept = userScanner.nextLine();
			searchItem = new DeAnzaContact(last, first, "", dept);
			foundItem = tree.getEntry(searchItem);
			if (foundItem != null)
			{
				System.out.println( "Found : " + foundItem );
			}
			else
				System.out.println( searchItem + " not found" );

			prompt = " that should NOT be in the tree: ";
		} // end for

	} // end testBST

	public static void testClone(BST<DeAnzaContact> tree1, BST<DeAnzaContact> tree2)
	{
		BST<DeAnzaContact> localTree;
		Visitor<DeAnzaContact> visitor, testvisitor;
		testvisitor = new TestVisitor();

		localTree = new BST<DeAnzaContact>(tree1);
		visitor = new NameVisitor();
		System.out.println( "\n\nThe local tree from copy constructor copying name-ordered tree");
		localTree.inorder(visitor);

		localTree.postorder(testvisitor);
		System.out.println("\nlocal tree has been changed, now it has: ");
		localTree.inorder(visitor);
		
		System.out.println( "\nChecking if Original name-ordered Tree was not changed: ");
		tree1.inorder(visitor);

		localTree = new BST<DeAnzaContact>(tree2);
		visitor = new DeptVisitor();
		System.out.println( "\nThe local tree from copy constructor copying dept-ordered tree");
		localTree.inorder(visitor);

		localTree.postorder(testvisitor);
		System.out.println("\nlocal tree has been changed, now it has: ");
		localTree.inorder(visitor);

		System.out.println( "\nChecking if Original dept-ordered Tree was not changed: ");
		tree2.inorder(visitor);
	} // end testClone

	public static void testDelete(BST<DeAnzaContact> tree) 
	{
		String toDelete;
		toDelete = tree.getFirst().toString();
		if(tree.delete(tree.getFirst()))
			System.out.println("Deletion successful of " + toDelete);
		toDelete = tree.getLast().toString();
		if(tree.delete(tree.getLast()))
			System.out.println("Deletion successful of " + toDelete);
	}

	public static void main(String [] args) {
		String first, last, phone, dept; 
		BST<DeAnzaContact> name_ordered = new BST<>(new NameComparator());
		BST<DeAnzaContact> dept_ordered = new BST<>(new DeptComparator());

		userScanner = openInputFile();

		while(userScanner.hasNextLine()) { 
			first = userScanner.nextLine();
			last = userScanner.nextLine();
			phone = userScanner.nextLine();
			dept = userScanner.nextLine();
			DeAnzaContact contact = new DeAnzaContact(first, last, phone, dept);
			name_ordered.insert(contact);
			dept_ordered.insert(contact);
		}

		System.out.println("\nName-ordered Tree inorder traversal:");
		name_ordered.inorder(new NameVisitor());

		System.out.println("\nDept-ordered Tree inorder traversal:");
		dept_ordered.inorder(new DeptVisitor());

		System.out.println("\nTesting Name-order Tree");
		userScanner = new Scanner(System.in);
		testBST(name_ordered);

		System.out.println("\nTesting Dept-order Tree");
		userScanner = new Scanner(System.in);
		testBST(dept_ordered);

		System.out.println("\nCalling testDelete method for name-ordered tree");
		testDelete(name_ordered);

		System.out.println("\nCalling testDelete method for dept-ordered tree");
		testDelete(dept_ordered);

		testClone(name_ordered, dept_ordered);

		System.out.println("\nname-order Tree, Postorder traversal:");
		name_ordered.postorder(new NameVisitor());

	}
}

class NameComparator implements Comparator<DeAnzaContact> {
	@Override 
	public int compare(DeAnzaContact left, DeAnzaContact right) {
		int result;
		if((result = left.getLastName().compareToIgnoreCase(right.getLastName())) != 0)
			return result;
		else if ((result = left.getFirstName().compareToIgnoreCase(right.getFirstName())) != 0)
			return result;
		else
			return left.getDepartment().compareToIgnoreCase(right.getDepartment()); 
	}
}

class DeptComparator implements Comparator<DeAnzaContact> {
	@Override 
	public int compare(DeAnzaContact left, DeAnzaContact right) {
		int result;
		if((result = left.getDepartment().compareToIgnoreCase(right.getDepartment())) != 0)
			return result;
		else if((result = left.getLastName().compareToIgnoreCase(right.getLastName())) != 0)
			return result;
		else
			return left.getFirstName().compareToIgnoreCase(right.getFirstName());
	}
}

class TestVisitor implements Visitor<DeAnzaContact>
{
	@Override
	public void visit(DeAnzaContact obj) {
		obj.setDepartment("NONE");
	}
}

class NameVisitor implements Visitor<DeAnzaContact> {
	@Override
	public void visit(DeAnzaContact obj) {
		System.out.println(obj.toString());
	}
}

class DeptVisitor implements Visitor<DeAnzaContact> {
	@Override
	public void visit(DeAnzaContact obj) {
		System.out.println(obj.getDepartment()+ ", " + obj.getLastName() + ", " + obj.getFirstName() + ", " + obj.getPhone());
	}
}

/* OUTPUT:

Enter the input filename: HW4 Input.txt

Name-ordered Tree inorder traversal:
APPIO, MIKE, 408-864-8283, MCNC
BOTSFORD, LYDIA, 408-864-5760, ACCT
BRANDT, MICHAEL, 408-864-8527, AUTO
BREEN, MIA, 408-864-5736, ACCT
BRYANT, RANDY, 408-864-8840, AUTO
CAPITOLO, DAVE, 408-864-8312, AUTO
CAPPELLO, MANNY, 408-864-5501, GBUS
FAYEK, MOATY, 408-864-8896, BUSCS
FRITZ, MICHELE, 408-864-8615, GBUS
GARBACEA, DELIA, 408-864-8308, CIS
GARBE, EMILY, 408-864-8488, GBUS
GEORGIOU, SPERA, 408-864-8803, CIS
GHAMRAAVWI, ABDUL, 408-864-8849, AUTO
GILLELAND, MAX, 408-864-5578, CDI
GOEL, MANISH, 408-864-8996, CS
GOUGH, MIKE-2, 408-864-8622, ACCT
HOM, NELSON, 408-864-8994, AUTO
KHA, BACHMAI, 408-864-8276, CIS
KLINGMAN, PAUL, 408-864-8696, CDI
KWAK, CHRISTOPHER, 408-864-5727, ACCT
LAM, PHONG, 408-864-8797, BUSCS
LARSON, MARK, 408-864-8445, MCNC
LEE-KLAWENDER, CYNTHIA, 408-864-8609, CIS
LILLY, BYRON, 408-864-8431, GBUS
MAYNARD, LORNA, 408-864-8849, AUTO
MAYNARD-2, RICK, 408-864-8704, AUTO
MCCART, MICHAEL-2, 408-864-8376, AUTO
MELLO, KEVIN, 408-864-8902, ACCT
MELLO-2, KEITH, 408-864-tba, ACCT
NGUYEN, BACHLAN, 408-864-8608, CIS
NGUYEN-2, CLARE, 408-864-8461, CIS
OLDHAM, IRA, 408-864-8562, CIS
OSBORNE, SCOTT, 408-864-8714, ACCT
PAPE, MARY, 408-864-8877, CIS
SALAH, DAN, 408-864-5563, GBUS
SHERBY, MARK-2, 408-864-5471, CIS
SINGH, SUKHJIT, 408-864-5566, CIS
SPENCER, SANDRA, 408-864-8932, GBUS
STODDARD, ANDREW, 408-864-8697, MCNC
VERNAZZA, PETE, 408-864-8216, AUTO
WALTON, JOHN, 408-864-8508, AUTO

Dept-ordered Tree inorder traversal:
ACCT, BOTSFORD, LYDIA, 408-864-5760
ACCT, BREEN, MIA, 408-864-5736
ACCT, GOUGH, MIKE-2, 408-864-8622
ACCT, KWAK, CHRISTOPHER, 408-864-5727
ACCT, MELLO, KEVIN, 408-864-8902
ACCT, MELLO-2, KEITH, 408-864-tba
ACCT, OSBORNE, SCOTT, 408-864-8714
AUTO, BRANDT, MICHAEL, 408-864-8527
AUTO, BRYANT, RANDY, 408-864-8840
AUTO, CAPITOLO, DAVE, 408-864-8312
AUTO, GHAMRAAVWI, ABDUL, 408-864-8849
AUTO, HOM, NELSON, 408-864-8994
AUTO, MAYNARD, LORNA, 408-864-8849
AUTO, MAYNARD-2, RICK, 408-864-8704
AUTO, MCCART, MICHAEL-2, 408-864-8376
AUTO, VERNAZZA, PETE, 408-864-8216
AUTO, WALTON, JOHN, 408-864-8508
BUSCS, FAYEK, MOATY, 408-864-8896
BUSCS, LAM, PHONG, 408-864-8797
CDI, GILLELAND, MAX, 408-864-5578
CDI, KLINGMAN, PAUL, 408-864-8696
CIS, GARBACEA, DELIA, 408-864-8308
CIS, GEORGIOU, SPERA, 408-864-8803
CIS, KHA, BACHMAI, 408-864-8276
CIS, LEE-KLAWENDER, CYNTHIA, 408-864-8609
CIS, NGUYEN, BACHLAN, 408-864-8608
CIS, NGUYEN-2, CLARE, 408-864-8461
CIS, OLDHAM, IRA, 408-864-8562
CIS, PAPE, MARY, 408-864-8877
CIS, SHERBY, MARK-2, 408-864-5471
CIS, SINGH, SUKHJIT, 408-864-5566
CS, GOEL, MANISH, 408-864-8996
GBUS, CAPPELLO, MANNY, 408-864-5501
GBUS, FRITZ, MICHELE, 408-864-8615
GBUS, GARBE, EMILY, 408-864-8488
GBUS, LILLY, BYRON, 408-864-8431
GBUS, SALAH, DAN, 408-864-5563
GBUS, SPENCER, SANDRA, 408-864-8932
MCNC, APPIO, MIKE, 408-864-8283
MCNC, LARSON, MARK, 408-864-8445
MCNC, STODDARD, ANDREW, 408-864-8697

Testing Name-order Tree

Enter a last name that should be in the tree: Appio
Enter a first name that should be in the tree: Mike
Enter department: MCNC
Found : APPIO, MIKE, 408-864-8283, MCNC

Enter a last name that should NOT be in the tree: Last
Enter a first name that should NOT be in the tree: First
Enter department: DEPT
Last, First, , DEPT not found

Testing Dept-order Tree

Enter a last name that should be in the tree: Osborne
Enter a first name that should be in the tree: Scott
Enter department: ACCT
Found : OSBORNE, SCOTT, 408-864-8714, ACCT

Enter a last name that should NOT be in the tree: Last
Enter a first name that should NOT be in the tree: First
Enter department: Dept
Last, First, , Dept not found

Calling testDelete method for name-ordered tree
Deletion successful of APPIO, MIKE, 408-864-8283, MCNC
Deletion successful of WALTON, JOHN, 408-864-8508, AUTO

Calling testDelete method for dept-ordered tree
Deletion successful of BOTSFORD, LYDIA, 408-864-5760, ACCT
Deletion successful of STODDARD, ANDREW, 408-864-8697, MCNC


The local tree from copy constructor copying name-ordered tree
BOTSFORD, LYDIA, 408-864-5760, ACCT
BRANDT, MICHAEL, 408-864-8527, AUTO
BREEN, MIA, 408-864-5736, ACCT
BRYANT, RANDY, 408-864-8840, AUTO
CAPITOLO, DAVE, 408-864-8312, AUTO
CAPPELLO, MANNY, 408-864-5501, GBUS
FAYEK, MOATY, 408-864-8896, BUSCS
FRITZ, MICHELE, 408-864-8615, GBUS
GARBACEA, DELIA, 408-864-8308, CIS
GARBE, EMILY, 408-864-8488, GBUS
GEORGIOU, SPERA, 408-864-8803, CIS
GHAMRAAVWI, ABDUL, 408-864-8849, AUTO
GILLELAND, MAX, 408-864-5578, CDI
GOEL, MANISH, 408-864-8996, CS
GOUGH, MIKE-2, 408-864-8622, ACCT
HOM, NELSON, 408-864-8994, AUTO
KHA, BACHMAI, 408-864-8276, CIS
KLINGMAN, PAUL, 408-864-8696, CDI
KWAK, CHRISTOPHER, 408-864-5727, ACCT
LAM, PHONG, 408-864-8797, BUSCS
LARSON, MARK, 408-864-8445, MCNC
LEE-KLAWENDER, CYNTHIA, 408-864-8609, CIS
LILLY, BYRON, 408-864-8431, GBUS
MAYNARD, LORNA, 408-864-8849, AUTO
MAYNARD-2, RICK, 408-864-8704, AUTO
MCCART, MICHAEL-2, 408-864-8376, AUTO
MELLO, KEVIN, 408-864-8902, ACCT
MELLO-2, KEITH, 408-864-tba, ACCT
NGUYEN, BACHLAN, 408-864-8608, CIS
NGUYEN-2, CLARE, 408-864-8461, CIS
OLDHAM, IRA, 408-864-8562, CIS
OSBORNE, SCOTT, 408-864-8714, ACCT
PAPE, MARY, 408-864-8877, CIS
SALAH, DAN, 408-864-5563, GBUS
SHERBY, MARK-2, 408-864-5471, CIS
SINGH, SUKHJIT, 408-864-5566, CIS
SPENCER, SANDRA, 408-864-8932, GBUS
STODDARD, ANDREW, 408-864-8697, MCNC
VERNAZZA, PETE, 408-864-8216, AUTO

local tree has been changed, now it has:
BOTSFORD, LYDIA, 408-864-5760, NONE
BRANDT, MICHAEL, 408-864-8527, NONE
BREEN, MIA, 408-864-5736, NONE
BRYANT, RANDY, 408-864-8840, NONE
CAPITOLO, DAVE, 408-864-8312, NONE
CAPPELLO, MANNY, 408-864-5501, NONE
FAYEK, MOATY, 408-864-8896, NONE
FRITZ, MICHELE, 408-864-8615, NONE
GARBACEA, DELIA, 408-864-8308, NONE
GARBE, EMILY, 408-864-8488, NONE
GEORGIOU, SPERA, 408-864-8803, NONE
GHAMRAAVWI, ABDUL, 408-864-8849, NONE
GILLELAND, MAX, 408-864-5578, NONE
GOEL, MANISH, 408-864-8996, NONE
GOUGH, MIKE-2, 408-864-8622, NONE
HOM, NELSON, 408-864-8994, NONE
KHA, BACHMAI, 408-864-8276, NONE
KLINGMAN, PAUL, 408-864-8696, NONE
KWAK, CHRISTOPHER, 408-864-5727, NONE
LAM, PHONG, 408-864-8797, NONE
LARSON, MARK, 408-864-8445, NONE
LEE-KLAWENDER, CYNTHIA, 408-864-8609, NONE
LILLY, BYRON, 408-864-8431, NONE
MAYNARD, LORNA, 408-864-8849, NONE
MAYNARD-2, RICK, 408-864-8704, NONE
MCCART, MICHAEL-2, 408-864-8376, NONE
MELLO, KEVIN, 408-864-8902, NONE
MELLO-2, KEITH, 408-864-tba, NONE
NGUYEN, BACHLAN, 408-864-8608, NONE
NGUYEN-2, CLARE, 408-864-8461, NONE
OLDHAM, IRA, 408-864-8562, NONE
OSBORNE, SCOTT, 408-864-8714, NONE
PAPE, MARY, 408-864-8877, NONE
SALAH, DAN, 408-864-5563, NONE
SHERBY, MARK-2, 408-864-5471, NONE
SINGH, SUKHJIT, 408-864-5566, NONE
SPENCER, SANDRA, 408-864-8932, NONE
STODDARD, ANDREW, 408-864-8697, NONE
VERNAZZA, PETE, 408-864-8216, NONE

Checking if Original name-ordered Tree was not changed:
BOTSFORD, LYDIA, 408-864-5760, ACCT
BRANDT, MICHAEL, 408-864-8527, AUTO
BREEN, MIA, 408-864-5736, ACCT
BRYANT, RANDY, 408-864-8840, AUTO
CAPITOLO, DAVE, 408-864-8312, AUTO
CAPPELLO, MANNY, 408-864-5501, GBUS
FAYEK, MOATY, 408-864-8896, BUSCS
FRITZ, MICHELE, 408-864-8615, GBUS
GARBACEA, DELIA, 408-864-8308, CIS
GARBE, EMILY, 408-864-8488, GBUS
GEORGIOU, SPERA, 408-864-8803, CIS
GHAMRAAVWI, ABDUL, 408-864-8849, AUTO
GILLELAND, MAX, 408-864-5578, CDI
GOEL, MANISH, 408-864-8996, CS
GOUGH, MIKE-2, 408-864-8622, ACCT
HOM, NELSON, 408-864-8994, AUTO
KHA, BACHMAI, 408-864-8276, CIS
KLINGMAN, PAUL, 408-864-8696, CDI
KWAK, CHRISTOPHER, 408-864-5727, ACCT
LAM, PHONG, 408-864-8797, BUSCS
LARSON, MARK, 408-864-8445, MCNC
LEE-KLAWENDER, CYNTHIA, 408-864-8609, CIS
LILLY, BYRON, 408-864-8431, GBUS
MAYNARD, LORNA, 408-864-8849, AUTO
MAYNARD-2, RICK, 408-864-8704, AUTO
MCCART, MICHAEL-2, 408-864-8376, AUTO
MELLO, KEVIN, 408-864-8902, ACCT
MELLO-2, KEITH, 408-864-tba, ACCT
NGUYEN, BACHLAN, 408-864-8608, CIS
NGUYEN-2, CLARE, 408-864-8461, CIS
OLDHAM, IRA, 408-864-8562, CIS
OSBORNE, SCOTT, 408-864-8714, ACCT
PAPE, MARY, 408-864-8877, CIS
SALAH, DAN, 408-864-5563, GBUS
SHERBY, MARK-2, 408-864-5471, CIS
SINGH, SUKHJIT, 408-864-5566, CIS
SPENCER, SANDRA, 408-864-8932, GBUS
STODDARD, ANDREW, 408-864-8697, MCNC
VERNAZZA, PETE, 408-864-8216, AUTO

The local tree from copy constructor copying dept-ordered tree
ACCT, BREEN, MIA, 408-864-5736
ACCT, GOUGH, MIKE-2, 408-864-8622
ACCT, KWAK, CHRISTOPHER, 408-864-5727
ACCT, MELLO, KEVIN, 408-864-8902
ACCT, MELLO-2, KEITH, 408-864-tba
ACCT, OSBORNE, SCOTT, 408-864-8714
AUTO, BRANDT, MICHAEL, 408-864-8527
AUTO, BRYANT, RANDY, 408-864-8840
AUTO, CAPITOLO, DAVE, 408-864-8312
AUTO, GHAMRAAVWI, ABDUL, 408-864-8849
AUTO, HOM, NELSON, 408-864-8994
AUTO, MAYNARD, LORNA, 408-864-8849
AUTO, MAYNARD-2, RICK, 408-864-8704
AUTO, MCCART, MICHAEL-2, 408-864-8376
AUTO, VERNAZZA, PETE, 408-864-8216
AUTO, WALTON, JOHN, 408-864-8508
BUSCS, FAYEK, MOATY, 408-864-8896
BUSCS, LAM, PHONG, 408-864-8797
CDI, GILLELAND, MAX, 408-864-5578
CDI, KLINGMAN, PAUL, 408-864-8696
CIS, GARBACEA, DELIA, 408-864-8308
CIS, GEORGIOU, SPERA, 408-864-8803
CIS, KHA, BACHMAI, 408-864-8276
CIS, LEE-KLAWENDER, CYNTHIA, 408-864-8609
CIS, NGUYEN, BACHLAN, 408-864-8608
CIS, NGUYEN-2, CLARE, 408-864-8461
CIS, OLDHAM, IRA, 408-864-8562
CIS, PAPE, MARY, 408-864-8877
CIS, SHERBY, MARK-2, 408-864-5471
CIS, SINGH, SUKHJIT, 408-864-5566
CS, GOEL, MANISH, 408-864-8996
GBUS, CAPPELLO, MANNY, 408-864-5501
GBUS, FRITZ, MICHELE, 408-864-8615
GBUS, GARBE, EMILY, 408-864-8488
GBUS, LILLY, BYRON, 408-864-8431
GBUS, SALAH, DAN, 408-864-5563
GBUS, SPENCER, SANDRA, 408-864-8932
MCNC, APPIO, MIKE, 408-864-8283
MCNC, LARSON, MARK, 408-864-8445

local tree has been changed, now it has:
NONE, BREEN, MIA, 408-864-5736
NONE, GOUGH, MIKE-2, 408-864-8622
NONE, KWAK, CHRISTOPHER, 408-864-5727
NONE, MELLO, KEVIN, 408-864-8902
NONE, MELLO-2, KEITH, 408-864-tba
NONE, OSBORNE, SCOTT, 408-864-8714
NONE, BRANDT, MICHAEL, 408-864-8527
NONE, BRYANT, RANDY, 408-864-8840
NONE, CAPITOLO, DAVE, 408-864-8312
NONE, GHAMRAAVWI, ABDUL, 408-864-8849
NONE, HOM, NELSON, 408-864-8994
NONE, MAYNARD, LORNA, 408-864-8849
NONE, MAYNARD-2, RICK, 408-864-8704
NONE, MCCART, MICHAEL-2, 408-864-8376
NONE, VERNAZZA, PETE, 408-864-8216
NONE, WALTON, JOHN, 408-864-8508
NONE, FAYEK, MOATY, 408-864-8896
NONE, LAM, PHONG, 408-864-8797
NONE, GILLELAND, MAX, 408-864-5578
NONE, KLINGMAN, PAUL, 408-864-8696
NONE, GARBACEA, DELIA, 408-864-8308
NONE, GEORGIOU, SPERA, 408-864-8803
NONE, KHA, BACHMAI, 408-864-8276
NONE, LEE-KLAWENDER, CYNTHIA, 408-864-8609
NONE, NGUYEN, BACHLAN, 408-864-8608
NONE, NGUYEN-2, CLARE, 408-864-8461
NONE, OLDHAM, IRA, 408-864-8562
NONE, PAPE, MARY, 408-864-8877
NONE, SHERBY, MARK-2, 408-864-5471
NONE, SINGH, SUKHJIT, 408-864-5566
NONE, GOEL, MANISH, 408-864-8996
NONE, CAPPELLO, MANNY, 408-864-5501
NONE, FRITZ, MICHELE, 408-864-8615
NONE, GARBE, EMILY, 408-864-8488
NONE, LILLY, BYRON, 408-864-8431
NONE, SALAH, DAN, 408-864-5563
NONE, SPENCER, SANDRA, 408-864-8932
NONE, APPIO, MIKE, 408-864-8283
NONE, LARSON, MARK, 408-864-8445

Checking if Original dept-ordered Tree was not changed:
ACCT, BREEN, MIA, 408-864-5736
ACCT, GOUGH, MIKE-2, 408-864-8622
ACCT, KWAK, CHRISTOPHER, 408-864-5727
ACCT, MELLO, KEVIN, 408-864-8902
ACCT, MELLO-2, KEITH, 408-864-tba
ACCT, OSBORNE, SCOTT, 408-864-8714
AUTO, BRANDT, MICHAEL, 408-864-8527
AUTO, BRYANT, RANDY, 408-864-8840
AUTO, CAPITOLO, DAVE, 408-864-8312
AUTO, GHAMRAAVWI, ABDUL, 408-864-8849
AUTO, HOM, NELSON, 408-864-8994
AUTO, MAYNARD, LORNA, 408-864-8849
AUTO, MAYNARD-2, RICK, 408-864-8704
AUTO, MCCART, MICHAEL-2, 408-864-8376
AUTO, VERNAZZA, PETE, 408-864-8216
AUTO, WALTON, JOHN, 408-864-8508
BUSCS, FAYEK, MOATY, 408-864-8896
BUSCS, LAM, PHONG, 408-864-8797
CDI, GILLELAND, MAX, 408-864-5578
CDI, KLINGMAN, PAUL, 408-864-8696
CIS, GARBACEA, DELIA, 408-864-8308
CIS, GEORGIOU, SPERA, 408-864-8803
CIS, KHA, BACHMAI, 408-864-8276
CIS, LEE-KLAWENDER, CYNTHIA, 408-864-8609
CIS, NGUYEN, BACHLAN, 408-864-8608
CIS, NGUYEN-2, CLARE, 408-864-8461
CIS, OLDHAM, IRA, 408-864-8562
CIS, PAPE, MARY, 408-864-8877
CIS, SHERBY, MARK-2, 408-864-5471
CIS, SINGH, SUKHJIT, 408-864-5566
CS, GOEL, MANISH, 408-864-8996
GBUS, CAPPELLO, MANNY, 408-864-5501
GBUS, FRITZ, MICHELE, 408-864-8615
GBUS, GARBE, EMILY, 408-864-8488
GBUS, LILLY, BYRON, 408-864-8431
GBUS, SALAH, DAN, 408-864-5563
GBUS, SPENCER, SANDRA, 408-864-8932
MCNC, APPIO, MIKE, 408-864-8283
MCNC, LARSON, MARK, 408-864-8445

name-order Tree, Postorder traversal:
BRANDT, MICHAEL, 408-864-8527, AUTO
BOTSFORD, LYDIA, 408-864-5760, ACCT
BRYANT, RANDY, 408-864-8840, AUTO
CAPITOLO, DAVE, 408-864-8312, AUTO
BREEN, MIA, 408-864-5736, ACCT
FAYEK, MOATY, 408-864-8896, BUSCS
FRITZ, MICHELE, 408-864-8615, GBUS
GHAMRAAVWI, ABDUL, 408-864-8849, AUTO
GEORGIOU, SPERA, 408-864-8803, CIS
GARBE, EMILY, 408-864-8488, GBUS
GARBACEA, DELIA, 408-864-8308, CIS
GOEL, MANISH, 408-864-8996, CS
HOM, NELSON, 408-864-8994, AUTO
GOUGH, MIKE-2, 408-864-8622, ACCT
KLINGMAN, PAUL, 408-864-8696, CDI
KHA, BACHMAI, 408-864-8276, CIS
LAM, PHONG, 408-864-8797, BUSCS
KWAK, CHRISTOPHER, 408-864-5727, ACCT
GILLELAND, MAX, 408-864-5578, CDI
CAPPELLO, MANNY, 408-864-5501, GBUS
LEE-KLAWENDER, CYNTHIA, 408-864-8609, CIS
MAYNARD, LORNA, 408-864-8849, AUTO
MAYNARD-2, RICK, 408-864-8704, AUTO
LILLY, BYRON, 408-864-8431, GBUS
MELLO-2, KEITH, 408-864-tba, ACCT
MELLO, KEVIN, 408-864-8902, ACCT
NGUYEN, BACHLAN, 408-864-8608, CIS
PAPE, MARY, 408-864-8877, CIS
OSBORNE, SCOTT, 408-864-8714, ACCT
OLDHAM, IRA, 408-864-8562, CIS
NGUYEN-2, CLARE, 408-864-8461, CIS
MCCART, MICHAEL-2, 408-864-8376, AUTO
SALAH, DAN, 408-864-5563, GBUS
SPENCER, SANDRA, 408-864-8932, GBUS
STODDARD, ANDREW, 408-864-8697, MCNC
VERNAZZA, PETE, 408-864-8216, AUTO
SINGH, SUKHJIT, 408-864-5566, CIS
SHERBY, MARK-2, 408-864-5471, CIS
LARSON, MARK, 408-864-8445, MCNC

