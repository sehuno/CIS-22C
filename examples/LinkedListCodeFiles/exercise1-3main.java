/*  
  
  Lab 1.3 Exercise Answers
  
  Name: Sehun Eric Oh
  Class: 22C
  
*/


public static void main(String [] args) 
{
    Music [] musicArray = {
          new Music("Moonlight Sonata",  "Beethoven", 27),
          new Music("Brandenburg Concerto #3", "Bach", 1048),
          new Music("Prelude in e minor", "Chopin", 28) };
    LList<Music>  musicList = new LList<Music>(); // creates LinkedList!
    Music tempMusic;
    System.out.println("\nThe list as displayed in LList.java: ");
    displayArray(musicArray);// template is Music here

    for( int i=0; i  < 3; ++i )
          musicList.add(musicArray[i]);
    musicList.display();
	tempMusic = musicList.getEntry(2);
    if( tempMusic != null  )
          System.out.println("\nFound " + tempMusic.getTitle() + "\n");
    else
          System.out.println( "\nUnable to find " + musicArray[1].getTitle() );

//       // For exercise 1.3
//       // - call the musicList's add method to insert a new Music object (make up 2 Strings and an int) to the BEGINNING of the musicList
    musicList.add(1, new Music("Liebestraum No. 3 in A Flat", "Franz Liszt", 1312));
    // musicList.display();
	// // - assign to a local Music variable a new Music object with the same 2 Strings you just used in #1 of this exercise
    Music new_obj = new Music("Liebestraum No. 3 in A Flat", "Franz Liszt", 1312);

	// - call contains passing the local Music variable you assigned in #2 of this exercise
			// // - display the entry if found, or "not found" if not found
	if(musicList.contains(new_obj))
	{
		System.out.println("Found newly added object in list.\n\n" + new_obj.toString() + "\n");
	}
	else
	{
		System.out.println("not found\n");
	}

	// // - call display for the musicList
	System.out.println("Displaying updated musicList\n");
	musicList.display();

	// // - call remove to remove the local Music object you created in #2 of this exercise
	System.out.println("\nDeleting recently added object from list");
	musicList.remove(new_obj);

	// // - call display for the musicList again.
	System.out.println("\nDeleting updated list after recent remove");
	musicList.display();
}