import java.util.*;
import java.util.Map.Entry;

class Location {
	private String name="";
	private String address="";
	private double latitude; // default 0
	private double longitude; // default 0
	
	public Location(String nm, String addr, double lat, double lon)
	{
		setName(nm);
		setAddress(addr);
		setCoordinates(lat, lon);
	}
	
	public Location(String nm)
	{
		setName(nm);
	}
	
	public void setName(String nm)
	{
		if( nm != null )
			name = nm;
	}
	
	public void setAddress(String addr)
	{
		if( addr != null )
			address = addr;
	}
	
	public void setCoordinates(double lat, double lon)
	{
		latitude = lat;
		longitude = lon;
	}

	public String getName(){ return name; }
	public String getAddress(){ return address; }
	public double getLatitude(){ return latitude; }
	public double getLongitude(){ return longitude; }
	public String toString()
	{
		return "Location: "+ name + ", " + address +
		", (" + latitude + ", " + longitude + ")";
	}
	// YOU WRITE equals: public boolean equals(Object obj)
	@Override
	public boolean equals(Object obj)
	{
		String compName;
		if( obj instanceof Location)
			compName = ((Location)obj).name;
		else
			compName = "";
		return name.equalsIgnoreCase(compName);// OR you could do:
		// return name.equalsIgnoreCase(compName);
	}
	// YOU WRITE hashCode: public int hashCode()
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}


public class LocationVisitor implements Visitor<Location> {
	@Override
	public void visit(Location obj) 
	{
		System.out.println(obj.getName() + obj.getAddress());
	}

	public static void main(String[] args)
	{
		Location[] locations = {
			new Location("DeAnza", "21250 Stevens Creek", 37.3192, 122.0453),
			new Location("Foothill", "12345 El Monte Rd", 37.3613, 122.1289),
			new Location("San Jose State", "1 Washington Square", 37.3353, 121.8813),
        	new Location("West Valley College", "14000 Fruitvale Av", 37.2634, 122.0100)
        };

		Graph<Location> myGraph1 = new Graph<Location>();

		myGraph1.addEdge(locations[0], locations[1], 0);	myGraph1.addEdge(locations[0], locations[2], 0);
		myGraph1.addEdge(locations[1], locations[2], 0);
		myGraph1.addEdge(locations[2], locations[3], 0);
		myGraph1.addEdge(locations[3], locations[0], 0);

		breadthFirstTraversal(locations[0], new LocationVisitor());
      
	}

	
}