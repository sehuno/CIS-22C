/**
   A class that implements the ADT list by using a chain of
   linked nodes that has a head reference.

   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
/* 
  Name: Eric Sehun OH
  22C
  Exercise 4.1

*/
public void display()
{
    Node currNode;
    currNode = firstNode;
    if(currNode == null)
      return;
    else
    {
      do 
      {
        System.out.println(currNode.getData());
        currNode = currNode.getNextNode();
      } while(currNode != firstNode && currNode != null);
    }
} // end display

 



