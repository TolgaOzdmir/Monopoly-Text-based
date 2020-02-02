
public class Strait extends Construction {

	public Strait(String name, int price, int RentPrice) {
		super(name, price,RentPrice);
		super.isUpgradeable=false;
		super.strait=true;
	}


	public String toString() {
		return name;
	}

	
}
