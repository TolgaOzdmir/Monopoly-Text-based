
public class Building extends Construction {

	

	
	public Building(String name, int price,int RentPrice) {
		super(name, price,RentPrice);
		super.isUpgradeable=true; 
		super.strait=false;
	}
	
	public String toString() {
		return name;
	}
}
