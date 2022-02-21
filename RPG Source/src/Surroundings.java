import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Surroundings {

	public static boolean showMenu = true;
	public static boolean isInvFull = false;

	private static String[] localAreas = new String[4]; // allocates 3 total options to configure per town. also used in individual methods to use in dialogue
	private static String[] localInput = new String[4]; // the actual typable input, is here so that player can't enter an input that isn't available in a certain town. last output needs to be BACK
	//"Graydrift", "Greenville", "Kilrock", "Bleakhost", "Duskwood", "Stormvalley", "Summerfelt", "Nevershore", "Southport", "Direwatch"

	public static void setLocation() {
		if (showMenu == true) {
			if (main.location.equals("Graydrift")) {
				main.sector = 0;
				localAreas[0] = "Blacksmith"; localInput[0] = "blacksmith";
				localAreas[1] = "Gavin's Home"; localInput[1] = "gavin";
				localAreas[2] = "Potion Seller"; localInput[2] = "potions";
				localInput[3] = "back";
				System.out.println("You are currently in the town of Graydrift.\nTo the West, there is the [BLACKSMITH]. Just North of me, is [GAVIN]'s Home. To the East is the [POTIONS] Seller.\n[BACK]");
			}//TODO add other location options
			showMenu = false;
		}

		List<String> validInput = Arrays.asList(localInput);
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();

		if (validInput.contains(in) == true) {//will have all valid inputs for any town, but is narrowed down by 'localInputs'
			if (in.equals("back")) {
				return;
			}else if (in.equals("blacksmith")) {
				showMenu = true;
				shopWeapons("Blacksmith");
			}else if (in.equals("potions")) {
				showMenu = true;
				shopPotions();
			}//TODO add all possible inputs. potion sellers, mines, banks, etc
		}else {
			System.out.println("You don't know what "+in+" means.");
			setLocation();
		}
	}


	public static void shopWeapons(String shopName) { //if statement for locations to sell different items
		int[] itemNumPrice = new int[5];
		String[] product = new String[5];
		int[] itemDmg = new int[5];
		if (showMenu == true){
			System.out.println("\n\n\nWelcome to the "+main.location+" "+shopName+" traveler!");
			System.out.println("\nCoins: "+main.coins+"gp\n");
			String forSale = null;
			int x = 0;
			if (main.location.equals("Graydrift")){
				for (x = 0; x < 3; ++x) {
					if (x == 0) {
						forSale = "Bronze Shortsword";
					}else if (x == 1) {
						forSale = "Pig-Iron Dagger";
					}else if (x == 2) {
						forSale = "Bronze Scimitar";
					}
					itemNumPrice[x] = showItem(forSale, x);
					product[x] = Weapon.name;
					itemDmg[x] = Weapon.atk;
				}
			}//TODO add locations

		}
		Scanner input = new Scanner(System.in);
		String in = input.nextLine();

		try{
			int k = Integer.parseInt(in)-1;
			System.out.println("The "+product[k]+" costs "+itemNumPrice[k]+"gp.\nAre you sure you want to purchase it? [yes]/[no]");
			Scanner input2 = new Scanner(System.in);
			String in2 = input2.nextLine().toString();
			if (in2.equals("yes")) {
				if (main.coins >= itemNumPrice[k]) {
					System.out.println("\nYou pay the merchant "+itemNumPrice[k]+"gp and recieve the "+product[k]+".");
					main.coins = main.coins-itemNumPrice[k];
					showMenu = true;
					addToInv(product[k], itemDmg[k]); //adds to inv and continues.
					showMenu = true;
					shopWeapons(shopName);
				}else {
					int difference = itemNumPrice[k]-main.coins;
					System.out.println("You don't have enough coins!\nYou need "+difference+" more coin(s) for that.\nYou put the "+product[k]+" back on the wall.");
					showMenu = false;
					shopWeapons(shopName);
				}
			}else if (in2.equals("no")) {
				showMenu = false;
				shopWeapons(shopName);
			}else{
				System.out.println("You don't know what "+in2+"means. Undecided, you put the "+product[k]+" back on the wall.");
				showMenu = false;
				shopWeapons(shopName);
			}
		}catch (Exception e){
			if (in.equals("back")){
				showMenu = true;
				setLocation();
			}else{
				System.out.println("You need to enter a number. [#]");
				showMenu = false;
				shopWeapons(shopName);
			}
		}

	}
	
	public static int showItem(String item, int listNum) {
		Weapon.defineItem(item);
		System.out.println("["+listNum+"] "+Weapon.name+"\t"+Weapon.atk+" ATK \t ("+Weapon.getCost(Weapon.material, Weapon.type)+"gp)");
		return Weapon.getCost(Weapon.material, Weapon.type);		
	}

	public static void addToInv(String item, int atk) {
		int testSlot = 0;
		String inSlot = main.inventory[0];
		int index = -1;
		do{	 
			if (testSlot < 10) {
				++index;
				inSlot = main.inventory[index];
				isInvFull = false;
			}else if (testSlot >= 10) {
				inSlot = "full";
				isInvFull = true;
			}
			++testSlot;
		}while (!inSlot.equals("empty") && !inSlot.equals("full")); //testing a variable rather than the array directly.  
		if (isInvFull == false) {
			main.inventory[index] = item;
			main.atkInventory[index] = atk;
			System.out.println("You put it in your inventory.\n");
		}else if (isInvFull == true){
			System.out.println("\nYour backpack is full of weapons! You'll need to discard the "+item+" or replace an item you already own.");
			main.equipMenu(true, "drop", false);//sends to equip menu to drop an item.
			if (isInvFull == true) { // drop item just acquired, since inv is still full?
				System.out.println("Do you want to drop the "+item+" that you just got?\nYou cannot undo this![yes]/[no]");
				Scanner input = new Scanner(System.in);
				String in = input.nextLine().toString();
				if (in.equals("yes")) { //TODO this will need to be modified so that it doesnt send you back to the main menu during travel.
					showMenu = true;
					setLocation();
				}else {
					System.out.println("Undecided, you take another look.");
					addToInv(item, atk);
				}
			}
			addToInv(item,atk); // this should be where the item gets re-added after dropping one
		}

	}

	public static void shopPotions() {
		if (showMenu == true) {
			System.out.println("\n\nHello "+main.name+"... A potion for all your needs..."); // quest?
			System.out.println("[1] Major Health Potion...... 48gp ("+main.potInventory[2]+")\n[2] Standard Health Potion... 24gp ("+main.potInventory[1]+")\n[3] Minor Health Potion...... 12gp ("+main.potInventory[0]+")\n\n"
					+ 		   "[4] Major Mana Potion........ 32gp ("+main.potInventory[5]+")\n[5] Standard Mana Potion..... 16gp ("+main.potInventory[4]+")\n[6] Minor Mana Potion........  8gp ("+main.potInventory[3]+")\n[back]\n");
		}
		System.out.println("\nWhich would you like to buy? [1-6] [back]");
		String[] potName = new String[] {"Major Health Potion", "Standard Health Potion", "Minor Health Potion", "Major Mana Potion", "Standard Mana Potion", "Minor Mana Potion"};
		int[] potCost = new int[] {48, 24, 12, 32, 16, 8};
		showMenu = false;
		Scanner input = new Scanner(System.in);
		String j = input.nextLine().toString();
		String in2 = null;
		try {
			int in = Integer.parseInt(j);
			if (in > 0 && in < 7) {
				System.out.println("How many "+potName[in-1]+"s would you like to buy?");
				Scanner input2 = new Scanner(System.in);
				in2 = input2.nextLine();
				int amount = Integer.parseInt(in2);//will go to exception if this isn't right.
				int cost = potCost[in-1]*amount;
				showMenu = true;
				buyPot(in-1, potName[in-1], amount, cost);
				showMenu = true; //intentional
				shopPotions();
			}else {
				System.out.println("choose a potion to buy [1-6] or [back]");
				shopPotions();
			}
		}catch (Exception e){
			if (j.equals("back")) {
				showMenu = true;
				setLocation();
			}else if (in2.equals("back")){
				showMenu = false;
				shopPotions();
			}else {
				System.out.println("choose a potion to buy [1-6] or [back]");
				shopPotions();
			}
		}

	}

	public static void buyPot(int choice, String potName, int amount, int cost) {
		if (showMenu == true) {
			System.out.println("Buy "+amount+" "+potName+"(s) for "+String.format("%,d", cost)+"gp? [yes]/[no]");
		}
		showMenu = false;
		Scanner in = new Scanner(System.in);
		String yesno = in.nextLine();
		if (yesno.equals("yes")) {
			if (main.coins >= cost) {
				main.potInventory[choice] = main.potInventory[choice] + amount;
				main.coins = main.coins - cost;
				System.out.println("You pay the merchant "+cost+"gp and put the potion(s) in your backpack.");
			}else{
				int difference = cost-main.coins;
				System.out.println("You don't have enough coins!\nYou need "+difference+" more coin(s) for that.");
				showMenu = false;
			}
		}else if (yesno.equals("no")) {
			showMenu = false;
			System.out.println("You change your mind, and keep looking at your options.");
			return;
		}else {
			System.out.println("answer, [yes] or [no]");
			buyPot(choice, potName, amount, cost);
		}
	}

}
