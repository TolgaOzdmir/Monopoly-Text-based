import java.util.*;


public class Monopoly {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int turn = 0;
		String sentence = "";
		//	String[] command= new String[10];
		int Pnumber = 0;

		/* Eksikler:
		 * 	GraphiXD
		 */

		Scanner sc = new Scanner(System.in);
		Table table = new Table();
		TwoDice dice = new TwoDice();
		Player[] PlayerTurnList = new Player[Pnumber];

		System.out.println("How many players will play this game(2-4): ");

		while (Pnumber < 2 || Pnumber > 4) {
			Pnumber = sc.nextInt();
			String name = "";
			PlayerTurnList = new Player[Pnumber];		
			boolean check=true;
			if (Pnumber >= 2 && Pnumber <= 4) {
				for (int i = 0; i < Pnumber;) {
					System.out.println("Player-" + (i + 1) + " choose a name: ");
					name = sc.next().toUpperCase();
					while(!name.matches("^([A-Za-z]+)\\s?")) {
						System.out.println("Name cannot contain symbols and digits");
						System.out.println("Player-" + (i + 1) + " choose a name: ");
						name= sc.next();
					}
					if(i!=0) {
						for(int a=0;a<i;a++) {
							if(PlayerTurnList[a].getName().equalsIgnoreCase(name)) {
								check=false;
								break;
							}else
								check=true;
						}
						if(check) {
							PlayerTurnList[i] = new Player(name);
							i++;
						}else
							System.out.println("This name already exists!");
					}
					if(i==0) {
						PlayerTurnList[i] = new Player(name);
						i++;
					}
				}
			} else
				System.out.println("This game can be played with 2-4\nHow many players will play this game: ");
		}

		String sent1="";
		for (int i = 0; i < Pnumber;) {
			System.out.println(PlayerTurnList[i].getName() + " roll the dice for turn order?");
			sent1 = sc.next().toLowerCase();
			sent1.trim();
			switch (sent1) {
			case "roll":
				// PlayerTurnList[i]=Empty.get(i);
				PlayerTurnList[i].rollTurn();
				System.out.println("Dice: " + PlayerTurnList[i].getTurn());
				i++;
				break;
			default:
				System.out.println("Unkown Command!\nPlease enter the command again: ");
			}
		}

		Player.sort(PlayerTurnList);
		System.out.println("\nTurn list: \n");

		for (int i = 0; i < Pnumber; i++) { // for check
			System.out.println(
					"Player" + (i + 1) + "....." + PlayerTurnList[i].getName() + "   Dice: " + PlayerTurnList[i].getTurn());
		}
		System.out.println();

		boolean canEnd=true, checkTurn=true;
		int count=0;//for solving the first round bug
		int TurnCount=0,i=0;
		int q=0;

		System.out.println("Command List:\n\t1) roll\n\t2) buy\n\t3) pay\n\t4) fly\n\t5) draw\n\t6) list(for property list) \n\t7) location(too see board)\n\t8) command(for command list)\n\t9) end");
		System.out.println();

		while (true) {
			for(i=0;i<PlayerTurnList.length;) {						

				Player player=PlayerTurnList[i];
				Area area = table.area[player.getLocation()];
				
				

				if(PlayerTurnList.length==1||player.checkStrait()) {
					System.out.println(player.getName()+" W I N  T H I S  G A M E!!!");
					System.exit(0);
				}else {
					if(player.checkLose()) {
						sentence="end";
					}else {

						for(q=0;q<15&&checkTurn;q++) {
							if(q<7)
								System.out.print("* _ ");
							else if(q==7)
								System.out.print(PlayerTurnList[i].getName()+"'s Turn ");
							else
								System.out.print("_ * ");
						}
						checkTurn=false;

						
						if(TurnCount==0){
							System.out.println();
							if(!player.getInJail()) {
								System.out.print("Please roll the dice to move: ");
							}else {
								System.out.println("\nPlease roll the dice to escape the Jail: ");
								canEnd=false;
							}
						}
						else if(area.checkArea.equalsIgnoreCase("Construction")&&(TurnCount==1)) {
							if( ((Construction)area).checkOwner(player)) {
								System.out.println("Do you want to buy this area,\""+area.toString()+", "+((Construction)area).getPrice()+"$\" ? (for yes, buy)");
							}else {
								TurnCount++;
								if(!((Construction)area).checkEnd)
									canEnd=false;
							}
						}
						else if(area.checkArea.equalsIgnoreCase("Plane")&&(TurnCount==1)) {
							System.out.println("Do you want to fly? (for yes, fly)");
						}
						else if(area.checkArea.equalsIgnoreCase("Jail")) {
							player.setInJail(true);
						}
						else if(area.checkArea.equalsIgnoreCase("Card")&&TurnCount==1) {
							System.out.println("Draw a card: ");
							canEnd=false;
						}
						else if(area.checkArea.equalsIgnoreCase("Deepweb")&&TurnCount==1) {
							System.out.println("Do you want buying (400$) a bad card to damage the rival that you choose?\n(for yes, draw)");
						}

						if(i==0&&count==0) {
							sentence=sc.next().toLowerCase();
							count++;
						}else {
							sentence=sc.next();
							sentence.toLowerCase();
						}

					}
					switch(sentence) {
					case "roll":
						if(!player.getInJail()) {
							if(TurnCount==0 ){
								dice.rollDice();
								player.move(dice.getTotalDice());//dice.getTotalDice()
								System.out.println();
								table.printTable(PlayerTurnList);
								System.out.println();
								System.out.println("Dice: "+dice.getTotalDice());
								System.out.println();
								System.out.print("Your current location: ");
								table.printArea(player);
								System.out.println("Your current money: "+player.getCash()+"$\n");
								TurnCount++;
								
							}else
								System.out.println("You cannot roll multiple dice in a round!");
						}else {
							if(player.getJailCount()>0&&TurnCount==0) {
								dice.rollDice();
								System.out.println("Dice1: " + dice.getDice1());
								System.out.println("Dice2: " + dice.getDice2());
								TurnCount++;
								if(dice.getTotalDice()==dice.getDice1()*2) {
									System.out.println("You escape the Jail because of dice!");
									player.setInJail(false);
									canEnd=true;
									player.move(dice.getTotalDice());
									System.out.print("Your current location: ");
									table.printArea(player);
									System.out.println("Your current money: "+player.getCash()+"$\n");
									player.resetJailCount();
								}else {
									if(!player.checkJail()) {
										System.out.println("You are out of the Jail now because of turn count!");
										canEnd=true;
										player.move(dice.getTotalDice());
										System.out.print("Your current location: ");
										table.printArea(player);
										System.out.println("Your current money: "+player.getCash()+"$\n");
										player.resetJailCount();
									}
									else
										canEnd=true;
								}
							}else if(player.getJailCount()==0) {
								canEnd=true;
								System.out.println("You cannot roll more than once!");
							}else {
								System.out.println("You cannot roll more than once!");
							}
						}
						break;
					case "buy":
						if(!player.getInJail()) {
							if(TurnCount!=0) {
								if(area.checkArea.equalsIgnoreCase("Construction")) {
									if(!((Construction)area).checkPay) {
										player.buyProperty(((Construction)area));
										TurnCount++;
										canEnd=true;
									}else
										System.out.println("You should pay the rent!");
								}else
									System.out.println("It is not a construction");
							}else 
								System.out.println("You cannot do anything before rolling the dice!");
						}else 
							System.out.println("You are in Jail!");
						break;
					case "draw":
						if(!player.getInJail()) {
							if(TurnCount!=0) {
								if(area.checkArea.equalsIgnoreCase("Card")) {
									if(TurnCount==1) {
										int random = (int)(Math.random()*10);
										Card card=table.CardList[random];
										player.playCard(card);
										canEnd=true;
										TurnCount++;
									}else
										System.out.println("You cannot draw the card more than once!");
								}else if(area.checkArea.equalsIgnoreCase("Deepweb")) {
									if(TurnCount==1) {
										if(player.getCash()>400) {
											player.setCash(player.getCash()-400);
											String rivalName= "";
											Player rival=new Player("");
											int zh=0;
											boolean checkRivalName=false;
											while(!checkRivalName) {
												System.out.println("Choose the player(name): ");
												rivalName= sc.next();
												int a=0,b=0;
												zh=0;
												for(a=0; a<PlayerTurnList.length;a++) {
													if(PlayerTurnList[a].getName().equalsIgnoreCase(rivalName)) {
														if(!(PlayerTurnList[a].getName().equalsIgnoreCase(player.getName()))) {
															rival= PlayerTurnList[a];
															checkRivalName=true;
															zh=0;
															break;
														}else {
															checkRivalName=false;
															zh=1;
															b=a;
															break;
														}
													}else {
														checkRivalName=false;
														//b=a;
														zh=0;
													}
												}
												if((PlayerTurnList[b].getName().equalsIgnoreCase(player.getName()))&&zh!=0) {
													System.out.println("You cannot punish yourself!");
													//zh=0;
												}
												else if(!checkRivalName) {
													System.out.println("There is no one whose name is "+rivalName+"!");
												}
											}
											int random= (int)(Math.random()*5)+5;
											Card card= table.CardList[random];
											rival.playCard(card);
											TurnCount++;
											canEnd=true;
										}else {
											System.out.println("You do not have enough money!");
											TurnCount++;
										}
									}else
										System.out.println("You cannot draw the card more than once!");
								}else
									System.out.println("It is not a chance card!");
							}else
								System.out.println("You cannot do anything before rolling the dice!");
						}else
							System.out.println("You are in Jail!");
						break;
					case "pay":
						if(!player.getInJail()) {
							if(TurnCount!=0) {
								if(area.checkArea.equals("Construction")) {
									if(((Construction)area).checkPay) {
										player.payToRival(((Construction)area).Owner.get(0), ((Construction)area));
										TurnCount++;
										canEnd=true;
									}
									else{
										System.out.println("You cannot pay!");
									}
								}else
									System.out.println("It is not a construction");
							}else
								System.out.println("You cannot do anything before rolling the dice!");
						}else
							System.out.println("You are in Jail!");
						break;
					case "fly":
						if(!player.getInJail()) {
							if(TurnCount!=0) {
								if(area.checkArea.equals("Plane")) {
									table.printTable(PlayerTurnList);
									System.out.println("Which area do you want to go?");
									int a=1000;
									while(a>36) {
										a= sc.nextInt();
										if(a>36)
											System.out.println("You must enter an integer between 0 and 35!\nEnter a new integer: ");
									}
									TurnCount=1;
									player.fly(a-1);
								}else
									System.out.println("You cannot fly here!");
							}else 
								System.out.println("You cannot do anything before rolling the dice!");
						}else
							System.out.println("You are in Jail!");
						break;
					case "end":
						if(player.checkLose()) {
							player.bankrupt();
							System.out.println(player.getName()+" lose this game! Bye bye...");
							PlayerTurnList=player.delete(PlayerTurnList);
							TurnCount=0;
							q=0;
							checkTurn=true;
						}
						else if(area.checkArea.equalsIgnoreCase("Jail")) {
							if(canEnd) {
								i++;
								TurnCount=0;
								checkTurn=true;
								player.increaseJailCount();
							}else 
								System.out.println("You cannot end right now!");
						}else if(TurnCount>0&&canEnd) {
							i++;
							TurnCount=0;
							checkTurn=true;
						}else {
							System.out.println("You cannot end right now!");
						}
						break;
					case "list":
						player.printList(); 
						break;
					case "location":
						table.printTable(PlayerTurnList);
						break;
					case "command":
						System.out.println("Command List:\n\t1) roll\n\t2) buy\n\t3) pay\n\t4) fly\n\t5) draw\n\t6) list(for property list) \n\t7) location(too see board)\n\t8) command(for command list)\n\t9) end");
						break;
					case "exit":
						System.exit(0);
						break;
					default:
						System.out.println("Unkown command");
						break;
					}


				}
			}
		}
	}
}
