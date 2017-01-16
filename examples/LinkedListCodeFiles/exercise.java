public static void main( String [] args )
{
  Music [] musicArray = {
    new Music("Moonlight Sonata",  "Beethoven", 27),
    new Music("Brandenburg Concerto #3", "Bach", 1048),
    new Music("Prelude in e minor", "Chopin", 28) };

  ArrayList<Music> arrList = new ArrayList<Music>(3);
  for( int i=0; i  < 3; ++i )
    arrList.add(musicArray[i]);

  LList<Music>  musicList = new LList<Music>(); // creates LinkedList!
  ListIterator<Music> litr = arrList.listIterator();
  while (litr.hasNext()) 
  {
    musicList.add(litr.next());
  }

  Music tempMusic;
  System.out.println("\nThe list as displayed in LList.java: ");
  displayArray(musicArray);// template is Music here

  musicList.display();
  tempMusic = musicList.getEntry(2);
  if( tempMusic != null  )
    System.out.println("\nFound " + tempMusic.getTitle() );
  else
    System.out.println( "\nUnable to find " + musicArray[1].getTitle() );
  // you'll need  to test more in an exercise!

} // end main 