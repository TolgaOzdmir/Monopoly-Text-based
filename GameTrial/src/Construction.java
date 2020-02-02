import java.util.ArrayList;
import java.util.Scanner;

public abstract class Construction extends Area{

	protected ArrayList<Player> Owner;
	protected String name, upgrade;
	protected int price;
	protected int rentPrice;
	protected boolean checkPay,isUpgradeable,checkEnd,strait;

	public Construction(String name, int price, int rentPrice) {
		this.name=name;
		this.price=price;
		Owner= new ArrayList<>();
		this.checkArea="Construction";
		this.rentPrice=rentPrice;
		checkPay=false;
		checkEnd=true;
		this.upgrade="Apartment";
	}
	public String toString() {
		return "Constructor";
	}

	public boolean checkOwner(Player player) {
		if(Owner.size()>=1)
			if(Owner.get(0).getName().equals(player.getName())) {
				System.out.println("You have this area");
				this.upgradeBuilding();
				checkEnd=true;
				return false;
			}else {
				System.out.println(Owner.get(0).getName()+" has this area\nYou must pay the rent, "+this.rentPrice+"$ : ");
				this.checkPay=true;
				checkEnd=false;
				return false;
			}

		else 
			return true;
	}
	public boolean signOwner(Player player) {
		if(Owner.size()>=1) {
			if(Owner.get(0).getName().equals(player.getName())) {
				System.out.println("You have already bought it");
				//checkPay=false;
			}else {
				System.out.println("This area is already bought by "+Owner.get(0).getName());
				//checkPay=true;
			}
			return false;
		}else {
			Owner.add(player);
			System.out.println("Successful!");
			//this.checkPay=false;
			return true;
		}

	}
	public void upgradeBuilding() {
		if(!isUpgradeable) {
			System.out.println("You cannot upgrade Straits!");
		}else {
			Scanner sc= new Scanner(System.in);
			System.out.println("Do you want to upgrade this building?");
			String a="";
			if(this.upgrade.equalsIgnoreCase("villa")) {
				System.out.println("(2 for Castle, "+(this.price*50/100) +"$)\n(end for exit)");
				a=sc.next();
			}else if(this.upgrade.equalsIgnoreCase("Castle")) {
				a="end";
				System.out.println("in \""+this.name+"\", you already have castle");
			}else {
				System.out.println("(1 for Villa, "+ (this.price*25/100) +"$)\n(2 for Castle, "+(this.price*75/100) +"$)\n(end for exit)");
				a=sc.next();
			}
			Player newbie = Owner.get(0);
			switch(a) {
			case "1":
				if(newbie.getCash()>(this.price*25/100)) {
					if(!this.upgrade.equalsIgnoreCase("villa") && !this.upgrade.equalsIgnoreCase("Castle")) {
						this.rentPrice= rentPrice*135/100;
						this.upgrade="Villa";
						newbie.setCash(newbie.getCash()-(this.price*25/100));
						System.out.println("Successful!");
					}else
						System.out.println("This is \""+this.upgrade+"\" so you cannot upgrade it!");
				}else {
					System.out.println("You do not have enough money!");
					this.upgradeBuilding();
				}
				break;
			case "2":
				if(!this.upgrade.equalsIgnoreCase("Castle")) {
					if(this.upgrade.equalsIgnoreCase("Villa")) {
						if(newbie.getCash()>(this.price*50/100)) {
							this.rentPrice= rentPrice*135/100;
							this.upgrade="Castle";
							newbie.setCash(newbie.getCash()-(this.price*50/100));
							System.out.println("Successful!");
						}else {
							System.out.println("You do not have enough money!");
							this.upgradeBuilding();
						}
					}else {
						if(newbie.getCash()>(this.price*75/100)) {
							this.rentPrice= rentPrice*135/100*135/100;
							this.upgrade="Castle";
							newbie.setCash(newbie.getCash()-(this.price*75/100));
							System.out.println("Successful!");
						}else {
							System.out.println("You do not have enough money!");
							this.upgradeBuilding();
						}
					}
				}else 
					System.out.println("This is \""+this.upgrade+"\" so you cannot upgrade it!");
				break;
			case "end":
				System.out.println("You can end now..");
				break;
			default:
				System.out.println("Unknown command");
				this.upgradeBuilding();
				break;
			}
		}
	}
	public int getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
	public String getUpgrade() {
		return upgrade;
	}


}
