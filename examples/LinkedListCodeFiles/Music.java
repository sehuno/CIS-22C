/*	Lab 1.2 Exercise Answers
	
	Name: Sehun Eric Oh
	Class: 22C

*/
public class Music {
	private String title;
	private String composer;
	private int opus;

	public Music() 
	{
		title = "";
		composer = "";
		opus = 0;
	}

	public Music(String ti, String comp, int op) 
	{
		title = ti;
		composer = comp;
		opus = op;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getComposer() 
	{
		return composer;
	}

	public int getOpus()
	{
		return opus;
	}

	public void setTitle(String newTitle) 
	{
		title = newTitle;
	}

	public void setComposer(String newComp) 
	{
		composer = newComp;
	}

	public void setOpus(int newOpus)
	{
		opus = newOpus;
	}

	//overrides Object's equals method
	@Override
	public boolean equals(Object obj) 
	{
        Music other = (Music)obj;
		return title.equals(other.title) && composer.equals(other.composer);
	}

	public String toString() 
	{
		return "title: " + title + "\ncomposer: " + composer + "\nopus: " + opus;
	}

	public static <T> void displayArray(T []myArray)
	{
	    for( int i =0; i < myArray.length; ++i )
	    {
	          System.out.println(myArray[i]);
	    }
	}
	

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
}

/*   OUTPUT

The list as displayed in LList.java:
title: Moonlight Sonata
composer: Beethoven
opus: 27
title: Brandenburg Concerto #3
composer: Bach
opus: 1048
title: Prelude in e minor
composer: Chopin
opus: 28
title: Moonlight Sonata
composer: Beethoven
opus: 27
title: Brandenburg Concerto #3
composer: Bach
opus: 1048
title: Prelude in e minor
composer: Chopin
opus: 28

Found Brandenburg Concerto #3

Found newly added object in list.

title: Liebestraum No. 3 in A Flat
composer: Franz Liszt
opus: 1312

Displaying updated musicList

title: Liebestraum No. 3 in A Flat
composer: Franz Liszt
opus: 1312
title: Moonlight Sonata
composer: Beethoven
opus: 27
title: Brandenburg Concerto #3
composer: Bach
opus: 1048
title: Prelude in e minor
composer: Chopin
opus: 28

Deleting recently added object from list

Deleting updated list after recent remove
title: Moonlight Sonata
composer: Beethoven
opus: 27
title: Brandenburg Concerto #3
composer: Bach
opus: 1048
title: Prelude in e minor
composer: Chopin
opus: 28

*/


