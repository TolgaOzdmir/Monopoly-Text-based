
public class Table {
	Area[] area;
	Card[] CardList;

	public Table() {
		area = new Area[36];
		CardList = new Card[10];

		for(int i=0;i<area.length;i++) {
			if(i==5||i==13||i==23||i==32) {
				area[i]=new Card();
			}
			else if(i==0)
				area[i]=new StartingPoint();
			else if(i==18)
				area[i]=new Deepweb();
			else if(i==27)
				area[i]=new Plane();
			else if(i==9) {
				area[i]=new Jail();
			}
		}

		area[1]=new Building("Bali", 100,50);
		area[2]=new Building("Hanoi", 150,75);
		area[3]=new Building("Manila", 200,100);
		area[4]=new Building("Burma", 250,110);
		area[6]=new Building("Trabzon", 300,130);
		area[7]=new Building("Baku", 350,150);
		area[8]=new Building("Tbilisi", 400,170);
		area[10]=new Building("Kiev", 500,190);
		area[11]=new Building("St.Petersburg", 600, 210);
		area[12]=new Building("Moscow", 700,230);
		area[15]=new Building("Izmir", 750,250);
		area[16]=new Building("Ankara", 850,270);
		area[17]=new Building("Istanbul", 1000,300);
		area[19]=new Building("Los Angeles", 1200,320);
		area[21]=new Building("New York", 1300,350);
		area[22]=new Building("Las Vegas", 1400,370);
		area[24]=new Building("Vienna", 1600,400);
		area[25]=new Building("Amsterdam", 1800,420);
		area[26]=new Building("Paris", 2000,440);
		area[29]=new Building("Seul", 2300,470);
		area[30]=new Building("Hong Kong", 2500,500);
		area[31]=new Building("Tokyo", 2700,550);
		area[33]=new Building("Dubai", 3200,600);
		area[35]=new Building("Singapore", 3700,650);

		area[14]=new Strait("Bosphorus Strait", 500,400 );
		area[20]=new Strait("Strait of Juan de Fuca", 500,400);
		area[28]=new Strait("Bering Strait", 500,400);
		area[34]=new Strait("Singapore Strait", 500,400);


		CardList[0]=new GoodCard("Gain 250$", "money", 250);
		CardList[1]=new GoodCard("Gain 500$", "money", 500);
		CardList[2]=new GoodCard("Gain 1000$", "money", 700);
		CardList[3]=new GoodCard("Go to the plane!", "move");
		CardList[4]=new GoodCard("Go to the Starting Point!", "move");
		
		CardList[5]=new BadCard("Go to the Jail!", "move");
		CardList[6]=new BadCard("Lose 250$", "money", 250);
		CardList[7]=new BadCard("Lose 400$", "money", 500);
		CardList[8]=new BadCard("Lose 600$", "money", 700);
		CardList[9]=new BadCard("Pay the tax for each buildings(Straits, apartments etc.) you have!", "tax");

	}

	public void printArea(Player player) {
		System.out.println(area[player.getLocation()]);
	}

	public void printTable(Player[] playerList) {
		//Player[] temp = new Player[playerList.length];
		String name="  ";
		int a=0;
		for(int i=0;i<area.length;i++) {
			name="  ";
			a=0;
			if(area[i].checkArea.equalsIgnoreCase("Construction")) {
				//name="  ";
				for(a=0;a<playerList.length;a++) {
					if(playerList[a].getLocation()==i) {
						name= name+" |0| "+playerList[a].getName();
					}
					if(a==playerList.length-1) {
						System.out.println((i+1)+") "+((Construction)area[i]).name+name);
					}
					//System.out.println(" ***"+playerList[a].getName());
				}
			}else if(area[i].checkArea.equalsIgnoreCase("Card")) {
				//name="  ";
				for(a=0;a<playerList.length;a++) {
					if(playerList[a].getLocation()==i) {
						name= name+" |0| "+playerList[a].getName();
					}
					if(a==playerList.length-1) {
						System.out.println((i+1)+") Chance Card"+name);
					}
					//System.out.println(" ***"+playerList[a].getName());
				}
			}else {
				//name="  ";
				for(a=0;a<playerList.length;a++) {
					if(playerList[a].getLocation()==i) {
						name= name+" |0| "+playerList[a].getName();
					}
					if(a==playerList.length-1) {
						System.out.println((i+1)+") "+area[i].checkArea+name);
					}

				}
			}
		}
	}

}
