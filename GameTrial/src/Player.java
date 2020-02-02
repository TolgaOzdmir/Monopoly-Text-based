import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Player {
	private int cash, turn=0, location, jailTurnCount, jailCount;
	private TwoDice dice=new TwoDice();
	private boolean checkStarting, inJail;
	private String name;
	private ArrayList<Construction> ConstructionList;
	//private ArrayList<Card> CardList;
	public int getJailCount() {
		return jailCount;
	}
	public void setJailCount(int jail) {
		this.jailCount=jail;
	}
	public void printList() {
		String apartment="",villa="",castle="";
		int countA=0, countV=0, countC=0;
		for(int i=0;i<ConstructionList.size();i++) {
			if(ConstructionList.get(i).upgrade.equalsIgnoreCase("apartment")) {
				countA++;
				apartment=apartment+" * "+ConstructionList.get(i).name;
			}else if(ConstructionList.get(i).upgrade.equalsIgnoreCase("villa")) {
				countV++;
				villa=villa+" * "+ConstructionList.get(i).name;
			}else if(ConstructionList.get(i).upgrade.equalsIgnoreCase("castle")) {
				countC++;
				castle=castle+" * "+ConstructionList.get(i).name;
			}
		}
		if(countA==0&&countV==0&&countC==0) {
			System.out.println("You do not have any buildings!");
		}else {
			System.out.println("You have "+countA+" apartment/s..\n("+apartment+")");
			System.out.println("You have "+countV+" villa/s..\n("+villa+")");
			System.out.println("You have "+countC+" castle/s..\n("+castle+")");
		}
	}
	public void bankrupt() {
		for(int i=0;i<ConstructionList.size();i++) {
			ConstructionList.get(i).Owner.remove(0);
		}
	}
	public Player[] delete(Player[] list) {
		int number=0;
		int border=list.length-1;
		Player[] oldList = new Player[list.length];

		for(int i=0; i<list.length;i++) {
			if(list[i].getName().equalsIgnoreCase(this.getName())) {
				number=i;
			}
			oldList[i]=list[i];
		}
		int a=0;
		list =new Player[border];
		for(int i=0;i<oldList.length;i++) {
			if(i!=number) {
				list[a]=oldList[i];
				a++;
			}
		}
		return list;
	}
	public boolean checkLose() {
		if(this.cash<=0)
			return true;
		else 
			return false;
	}
	public boolean checkStrait() {
		int count=0;
		for(int i=0; i<ConstructionList.size();i++) {
			if(ConstructionList.get(i).strait)
				count++;
		}
		if(count==4)
			return true;
		else
			return false;
	}
	public void playCard(Card card) {
		System.out.println(card.text); 
		switch(card.action) {
		case "money":
			if(card.checkCard.equalsIgnoreCase("good")) {
				this.setCash(this.cash+card.getCash());
			}else {
				this.setCash(this.cash-card.getCash());
			}
			break;
		case "move":
			int number = card.text.lastIndexOf(" ");
			String location= card.text.substring(number+1,card.text.length()-1);
			if(location.equalsIgnoreCase("Plane")) {
				this.setLocation(27);
				System.out.println("Do you want to fly?");
			}else if(location.equalsIgnoreCase("Point")) {
				this.setLocation(0);
				this.setCash(this.cash+500);
				System.out.println(this.name+" gain 500$ since you reach or pass the Starting Point");
			}else if(location.equalsIgnoreCase("Jail")) {
				this.setLocation(9);
				this.setJailCount(1);
				//this.increaseJailCount();
				this.inJail=true;
			}
			break;
		case "tax":
			int apartmentNumber=0, villaNumber=0, castleNumber=0;
			for(int a=0;a<ConstructionList.size();a++) {
				if(ConstructionList.get(a).upgrade.equalsIgnoreCase("Apartment")) {
					apartmentNumber++;
				}
				else if(ConstructionList.get(a).upgrade.equalsIgnoreCase("Villa")) {
					villaNumber++;
				}
				else if(ConstructionList.get(a).upgrade.equalsIgnoreCase("Castle")) {
					castleNumber++;
				}
			}

			int tax= apartmentNumber*50+villaNumber*125+castleNumber*200;
			this.setCash(this.cash-tax);
			System.out.println(this.name+" pay "+tax+"$ ");
			System.out.println(this.name+" have "+apartmentNumber+" apartment/s\n"+this.name+" have "+villaNumber+" villa/s\n"+this.name+" have "+castleNumber+" castle/s");
			break;
		}

	}
	public void increaseJailCount() {
		jailCount++;
	}
	public void resetJailCount() {
		this.jailCount=0;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public boolean getInJail() {
		return this.inJail;
	}
	public void setInJail(boolean a) {
		this.inJail=a;
	}
	public Player(String name) {
		this.name=name;
		cash= 2500;
		location=0;
		checkStarting=false;
		ConstructionList=new ArrayList<>();
		//CardList=new ArrayList<>();
		inJail=false;
		jailCount=0;
	}
	public boolean checkJail() {
		if(jailTurnCount==2) {
			inJail=false;
			jailTurnCount=0;
		}else {
			inJail=true;
			jailTurnCount++;
		}
		return inJail;
	}	
	public void payToRival(Player player, Construction cons) {
		//Table table=new Table();
		for(int i=0;i<player.ConstructionList.size();i++) {
			if(cons.name.equals(player.ConstructionList.get(i).name)) {
				if(player.ConstructionList.get(i).checkPay) {
					int payment= player.ConstructionList.get(i).rentPrice;// lose condition
					if(this.getCash()>payment) {
						this.setCash(this.cash-payment);
						player.setCash(payment+player.getCash());
						System.out.println("Successful!");
						player.ConstructionList.get(i).checkPay=false;
						break;
					}else {
						System.out.println("You have no enough money to pay it!");
						player.setCash(player.getCash()+this.getCash());
						this.setCash(this.cash-payment);
						player.ConstructionList.get(i).checkPay=false;
						break;
					}
				}else {
					System.out.println("You have already paid it");
					break;
				}
			}
		}
	}
	public void buyProperty(Construction construction) {
		if(this.checkEnoughMoney(construction)) {
			if(construction.signOwner(this)) {
				cash= cash-construction.getPrice();
				ConstructionList.add(construction);
			}
		}else {
			if(this.getCash()<construction.price)
				System.out.println("You have no enough money to buy "+construction.getName());
			else
				System.out.println("You cannot buy it since if you will buy it, you will bankrupt!");
		}
	}
	public boolean checkEnoughMoney(Construction construction) {
		if(cash>construction.getPrice()) {
			return true;
		}else 
			return false;
	}
	public int rollTurn() {
		dice.rollDice();
		turn=dice.getDice1();
		return turn;
	}
	public void move(int dice) {
		int calculation =location+dice;
		location= (calculation)%36;
		if(location==0||calculation>36) {
			checkStarting=true;
			this.setCash(this.cash+500);
			System.out.println("You gain 500$ since you reach or pass the Starting Point");
		}
	}
	public static void sort(Player[] Player) {

		Player data= new Player("");
		for (int i = 0; i < Player.length; i++) {
			for (int a = 0; a < Player.length - 1; a++) {
				if (Player[a].getTurn() < Player[a + 1].getTurn()) {
					data = Player[a];
					Player[a] = Player[a + 1];
					Player[a + 1] = data;
				}
			}
		}
	}

	public void fly(int i) {
		this.setLocation(i);
		if(i>=0&&i<27) {
			this.setCash(this.cash+500);
			System.out.println("Successful!");
			System.out.println("You gain 500$ since you reach or pass the Starting Point");
		}
	}
	public int getTurn() {
		return turn;
	}
	public int getLocation() {
		return location;
	}	
	public boolean getCheckStarting() {
		return checkStarting;
	}
	public String getName() {
		return name;
	}
	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash=cash;
	}
	public int getJailTurnCount() {
		return jailTurnCount;
	}
	public void setJailTurnCount(int jailTurnCount) {
		this.jailTurnCount = jailTurnCount;
	}
}
