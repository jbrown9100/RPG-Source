import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

/*
TODO:
- Make a save file to return to previous position - DONE
- integrate inventory - DONE
- integrate shops - DONE
- integrate story
- make an method that makes random names for enemies
- make separate methods for different locations
- make loot tables with crafting materials
- side quests and main quest
- fast traveling
- make a max level cap for skills

 */

//new comment

public class main {
	//geography
	public static String[] worldMap = new String[]{"Graydrift", "Greenville", "Kilrock", "Bleakhost", "Duskwood", "Stormvalley", "Summerfelt", "Nevershore", "Southport", "Direwatch"};
	public static String name = "no name";
	public static int sector = 0;
	public static String location = worldMap[sector];
	//stats
	public static int level = 1, exp = 0, levelup= level*81, points = 0, coins = 0, health = 50 , maxHealth = 50, strength = 1, defense = 1, dodge = 1, luck = 1, magic = 1, mana = 50, maxMana = 50, resistance = 1;

	//usables
	public static boolean justTPd = false;
	public static boolean showMenu = true;

	//inventory
	public static String[] inventory = new String[] {"Blue-Steel Greatsword", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty"}; //10 slots. 0 is equipped.
	public static int[] atkInventory = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 

	public static int[] potInventory = new int[] {3,0,0,0,0,0}; //25% hp, 50% hp, 100% hp, 25% mana, 50% mana, 100% mana 

	//magic
	public static String[] spellBooks = new String[] {"empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty", "empty",}; //10 slots. 0 is equipped.
	public static int[] mgcInventory = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	public static String[] mgcInvElement = new String[] {"none", "none", "none", "none", "none", "none", "none", "none", "none", "none"}; //element aligned to each slot


	public static void main(String[] args) {
		Weapon.defineItem(inventory[0]);
		atkInventory[0] = Weapon.atk;
		showMenu = true;
		optionsMenu(true);
		//intro
		System.out.println("\n\nHello stranger...\nYou seem like you're not from around here.\nDo you have a name?\n");
		Scanner input = new Scanner(System.in);
		input.toString();

		name = input.next();
		if (name.equals(null)) {
			name = "no name";
		}else if (name.equals("admin")) {
			coins = 999999;
			inventory[0] = "Admin Blade";
			atkInventory[0] = 999;
			health = 10000;
			maxHealth = 10000;
		}
		System.out.println("\n"+name+"? ... Can't say that's the name I would've given you... \nWell,  my name is Gavin. Welcome to "+location+".\n I'll let you rest in my home just down the way. It's not much, but I bet it'll work until you can sort yourself out.\n");
		justTPd = true;
		showMenu = true;
		teleport("Graydrift");
	}

	public static void teleport(String destination) {
		location = destination;
		if (showMenu == true) {
			if (justTPd == true) {
				System.out.println("\n\nYou arrive at "+location+". What would you like to do?");
			}else{
				System.out.println("\n\nYou are at "+location+". What would you like to do?");
			}
			justTPd = false;
			System.out.println("[stats] - check your statistics\n[backpack] - look at, potions, coins, and equip weapons that you own\n[spells] - look at and equip spellbooks owned\n[look] - examine your surroundings\n[travel] - move to another location\n[OPTIONS] - load or save your game\n");
		}
		showMenu = true;
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();

		if (in.equals("stats")) {showStats();}
		else if (in.equals("backpack") || in.equals("inv")) {showInventory(true);}
		else if (in.equals("spellbooks") || in.equals("spells")) {showInventory(false);}
		else if (in.equals("look")) {
			showMenu = true; 
			showSurroundings(location);}
		else if (in.equals("travel")) {showTravelMenu(location);}
		else if (in.equals("options")) {
			showMenu = true;
			optionsMenu(false);
			teleport(location);
		}else{ 
			System.out.println("\nYou don't know what '"+in+"' means.\n");
			showMenu = false;
			teleport(location);
		}
	}

	public static void showStats() {
		if (showMenu == true) {
			System.out.println("\n\n\n     STATISTICS");
			if (points > 0) { System.out.println("You have "+points+" skill points to spend!  [LEVELUP] - to spend points.\n"); }else{ System.out.println("\n"); }
			//add the sword and shield bonuses in parentheses after those are worked in
			System.out.println("Name: "+name+"\nLevel "+level+"\n"+"EXP: "+exp+"/"+levelup+"\n[Health]: "+health+"/"+maxHealth+"\n[Mana]: "+mana+"/"+maxMana+"\n\n[Strength]: "+strength+"\n[Defense]: "+defense+"\n[Dodge]: "+dodge+"\n[Luck]: "+luck+"\n[Magic]: "+magic+"\n[Resistance]: "+resistance+"\n\n");
			System.out.println("[HELP] - show descriptions for each stat.\n[BACK] - go back to the previous prompt.\n");
		}
		Scanner input = new Scanner(System.in);

		String in = input.next();
		if (in.equals("help")) {
			System.out.println("\nSTRENGTH = melee damage modifier\r\nDEFENSE = how much incoming damage is reduced\r\nDODGE = chance to negate damage all together\r\nLUCK = modifies how many coins and materials you can gain.\r\nMAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\nRESISTANCE = like defense, but against magic/status effects");
			showMenu = false;
			showStats();
		} else if (in.equals("levelup")) {
			if (points <= 0) {
				showMenu = false;
				System.out.println("You don't have any points to spend on skills.");
				showStats();
			}else{
				showMenu = true;
				skillMenu();
			}
		} else if (in.equals("back")) {
			showMenu = true;
			System.out.println("\n\n\n");
			teleport(location);
		}else {
			System.out.println("\nYou don't know what '"+in+"' means.\n");
			showMenu = false;
			showStats();
		}
	}

	public static void skillMenu() {
		if (showMenu == true) {
			System.out.println("\n\nYou have "+points+" skill point(s) to spend. Enter the name of the skill you want to add points to.\n\rOnce added, they cannot be reset, without a fee.\n\r[STR][DEF][DGE][LCK][MGC][RST]\n\r[HELP] - show descriptions for each stat.\n[BACK] - go back to the previous prompt.\n");
			showMenu = false;
		}
		//making an array and converting it into a list, so that it can check the list against the input and see if it is a valid stat to modify
		final String validate[] = new String[] {"str", "Strength", "def", "Defense", "lck", "luck", "dge", "dodge", "mgc", "magic", "rst", "resistance"};
		List<String> validStat = Arrays.asList(validate); 
		Scanner input = new Scanner(System.in);		
		String in = input.next().toString();;

		if (in.equals("help")) {//checking input for menu related navigation
			System.out.println("\nSTRENGTH = melee damage modifier\r\nDEFENSE = how much incoming damage is reduced\r\nDODGE = chance to negate damage all together\r\nLUCK = modifies how many coins and materials you can gain.\r\nMAGIC = how powerful spells will be, will use spell books that work like swords with elemental bonuses and healing\r\nRESISTANCE = like defense, but against magic/status effects");
			skillMenu();
		}else if (in.equals("back")) {
			showMenu = true;
			teleport(location);
		}else if (validStat.contains(in) == true) { //seeing which stat to modify
			showMenu = true;
			addSkillPoint(in);
		}else {
			System.out.println("\nYou don't know what '"+in+"' means.\n");
			showMenu = false;
			skillMenu();
		}
	}

	public static void addSkillPoint(String stat) { //stat has been validated by the "validStat.contains" condition
		if (showMenu == true) {
			System.out.println("How many skill points do you want to add?\nSkill Points: "+points+"\n[#] or [BACK]" );
			showMenu = false;
		}
		Scanner input = new Scanner(System.in);

		String in = input.next();

		try {
			int amount = Integer.parseInt(in); //converts string to int
			if (amount > points) {
				System.out.println("You don't have enough points for that!");
				addSkillPoint(stat);
				return;
			}
			int total;
			switch (stat) {
			case "str":
			case "strength":
				stat = "Strength"; //converts to proper full word
				strength = strength+amount;
				total = strength;
				break;
			case "def":
			case "defense":
				stat = "Defense";
				defense = defense+amount;
				total = defense;
				break;
			case "lck":
			case "luck":
				stat = "Luck";
				defense = luck+amount;
				total = luck;
				break;
			case "dge":
			case "dodge":
				stat = "Dodge";
				dodge = dodge+amount;
				total = dodge;
				break;
			case "mgc":
			case "magic":
				stat = "Magic";
				magic = magic+amount;
				total = magic;
				break;
			case "rst":
			case "resistance":
				stat = "Resistance";
				resistance = resistance+amount;
				total = resistance;
				break;
			default:
				total = 0;
				System.err.println("ERROR: line 220 in main");
			}
			points = points-amount;
			System.out.println("You've advanced your "+stat+ " by " +amount+".\nYour "+stat+" is now level "+total+".\nYou have "+points+" left.");
			showMenu = true;
			showStats();
		}catch (Exception e) {
			if (in.equals("back")) {
				showMenu = true;
				skillMenu();
			}else{
				System.out.println("Enter a number.");
				addSkillPoint(stat);
			}
		}

	}

	public static void showInventory(boolean isWeapon) {
		if (showMenu == true) {
			showMenu = false;
			System.out.println("\n\n      INVENTORY\n");
			sortInventory();
			int quantity = 1;
			if (isWeapon == true) {
				if (inventory[0].equals("empty")) {
					inventory[0] = "fist";
				}
				System.out.println("Equipped: "+ inventory[0] + "  "+ atkInventory[0] +" ATK\n");
				for (int i = 1; i < inventory.length; i++) { //shows the rest of the inventory until it's empty
					if (!inventory[i].equals("empty")) {
						System.out.println("SLOT"+i+": "+ inventory[i] + "  "+ atkInventory[i] + " ATK");
						++quantity;
					}
				}
			}else if (isWeapon == false){
				quantity = 0;
				if (!spellBooks[0].equals("empty")) {
					System.out.println("Equipped: "+ spellBooks[0] + "  ELEMENT: "+mgcInvElement[0]+ "  "+ mgcInventory[0] +" ATK\n");
					quantity = 1;
				}
				for (int i = quantity; i < spellBooks.length; i++) { //shows the rest of the spells until it's empty
					if (!spellBooks[i].equals("empty")) {
						System.out.println("SLOT"+i+": "+ spellBooks[i] + "  ELEMENT: "+mgcInvElement[i]+ "   " + mgcInventory[i] + " ATK");
						++quantity;
					}
				}
			}
			System.out.println(quantity+"/10 slots used.\n");
			if (isWeapon == true) {
				System.out.println("Minor Health Potion: "+potInventory[0]+", Standard Health Potion: "+potInventory[1]+", Major Health Potion: "+potInventory[2]+",\nMinor Mana Potion: "+potInventory[3]+", Standard Mana Potion: "+potInventory[4]+", Major Mana Potion: "+potInventory[5]+"\n");
				System.out.println("Coins: "+String.format("%,d",coins)+"gp\n"); // formats coins with a comma
			}
			System.out.println("[EQUIP] [DROP] [BACK]");
			Scanner input = new Scanner(System.in);

			String in = input.nextLine().toString();
			if (in.equals("back")) {
				showMenu = true;
				System.out.println("\n\n\n");
				teleport(location);
			}else if (in.equals("equip")) {
				showMenu = true;
				equipMenu(isWeapon, "equip", true);
			}else if (in.equals("drop")) {
				showMenu = true;
				equipMenu(isWeapon, "drop", true);
			}else {
				System.out.println("You don't know  what "+in+" means.");
				showInventory(isWeapon);
			}
		}
	}

	public static void equipMenu(boolean isWeapon, String action, boolean fromMain){ //fromMain checks to see if you came from the backpack menu
		if (showMenu == true) {
			String menuVersion = null;
			if (action.equals("equip") || action.equals("drop") && fromMain == true) { menuVersion = "back"; } else
				if (action.equals("drop")) { menuVersion = "discard"; } 
			System.out.println("\n\nWhich slot do you want to "+action+"? [1-9] ["+menuVersion+"]");
			sortInventory();
			if (action.equals("drop")) {
				System.out.println("Equipped: "+ inventory[0] + "  "+ atkInventory[0] +" ATK\n");
				for (int i = 1; i < inventory.length; i++) { //shows inv if dropping
					if (!inventory[i].equals("empty")) {
						System.out.println("SLOT"+i+": "+ inventory[i] + "  "+ atkInventory[i] + " ATK");
					}
				}
			}
		}
		showMenu = false;
		Scanner input = new Scanner(System.in);

		String in = input.next();
		String item = null;
		try {
			int slot = Integer.parseInt(in);
			if (slot > 0 && slot < 10){
				if (isWeapon == true) {
					if (inventory[slot].equals("empty")) {
						System.out.println("There isn't anything to "+action+" in this slot.\n");
						equipMenu(isWeapon, action, fromMain);
					}else { //equipping or dropping
						if (action.equals("equip")) {
							String tIA = inventory[0], tIB = inventory[slot];
							int tIA2 = atkInventory[0], tIB2 = atkInventory[slot];
							item = tIB;
							inventory[0] = tIB; inventory[slot] = tIA;
							atkInventory[0] = tIB2; atkInventory[slot] = tIA2;
						}else if (action.equals("drop")){
							item = inventory[slot];
							System.out.println("Are you sure you want to drop the "+item+"? [yes]/[no]");
							Scanner input2 = new Scanner(System.in);
							String in2 = input2.nextLine().toString();

							if (in2.equals("yes")) {
								inventory[slot] = "empty"; atkInventory[slot] = 0;
								if (Surroundings.isInvFull == true) {
									Surroundings.isInvFull = false;
								}
							}else if (in2.equals("no")){
								Surroundings.isInvFull = true;
								equipMenu(isWeapon, action, fromMain);
							}else {
								System.out.println("\nYou seem unsure. Best leave it alone.");
								equipMenu(isWeapon, action, fromMain);
							}
						}
						System.out.println("\n\nYou've "+action+"ped the "+item+".");
						showMenu = true;
						if (fromMain == true) {
							showInventory(true);
						}
					}
				}else  if (isWeapon == false){
					if (spellBooks[slot].equals("empty")) {
						System.out.println("There isn't anything to equip in this slot.\n");
						equipMenu(isWeapon, action, fromMain);
					}else { //equipping
						String tIA = spellBooks[0], tIB = spellBooks[slot];
						int tIA2 = mgcInventory[0], tIB2 = mgcInventory[slot];
						spellBooks[0] = tIB; spellBooks[slot] = tIA;
						mgcInventory[0] = tIB2; mgcInventory[slot] = tIA2;
						System.out.println("\n\nYou've equipped the "+spellBooks[0]+".");
						showMenu = true;
						showInventory(isWeapon);
					}
				}
			}else {
				System.out.println("You only have an equip slot and 9 inventory slots.");
				equipMenu(isWeapon, action, fromMain);
			}
		}catch (Exception e){
			if (in.equals("back") || in.equals("discard")) {
				showMenu = true;
				if (action.equals("equip") || action.equals("drop") && fromMain == true) {
					teleport(location);
				}else{
					return; //goes back to invFull menu in Surroundings
				}
			}else{
				System.out.println("Enter a slot number, 1 through 9.\n");
				equipMenu(isWeapon, action, fromMain);
			}
		}
	}

	public static void sortInventory() {
		for (int i = 1; i < 9; ++i) {
			for (int index = 1; index < 9; ++index) {
				if (!inventory[index+1].equals("empty") && inventory[index].equals("empty")) {
					System.out.println("index: "+index+" current slot = "+inventory[index]+" next slot = "+inventory[index+1]);
					inventory[index] = inventory[index+1];
					atkInventory[index] = atkInventory[index+1];
					inventory[index+1] = "empty";
					atkInventory[index+1] = 0;
				}
			}
		}
	}

	public static void showSurroundings(String where) {
		System.out.println("\n\nYou take a look at your surroundings.");
		Surroundings.showMenu = true;
		Surroundings.setLocation();
		teleport(location);
	}

	public static void optionsMenu(boolean fromStart) {
		if (showMenu == true) {
			System.out.println("\n\nOPTIONS\n");
			if (fromStart == true) {
				System.out.println("[NEW] Game");
			}else {
				System.out.println("[SAVE] Game");
			}
			System.out.println("[LOAD] Game");
			if (fromStart == false) {
				System.out.println("[BACK]");
			}
		}
		showMenu = false;		
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();

		if (in.equals("new") && fromStart == true) {
			return;
		}else if (in.equals("back") && fromStart == false) {
			showMenu = true;
			teleport(location);
		}else if (in.equals("save")) {
			FileManager.saveGame();
		}else if (in.equals("load")) {
			FileManager.loadGame();
		}else {
			System.out.println("Choose an option listed above\n");
			optionsMenu(fromStart);
		}
	}

	public static void showTravelMenu(String where) {
		//TODO
		System.out.println("WIP");
		teleport(location);
	}

}
