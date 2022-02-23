package com.rech.rpg;
import java.util.Scanner;

import com.rech.rpg.item.Potion;
import com.rech.rpg.item.Weapon;

import java.util.Arrays;
import java.util.List;

public class Surroundings {

	public static boolean showMenu = true;
	public static boolean isInvFull = false;

	private static String[] localAreas = new String[4]; // allocates 3 total options to configure per town. also used in individual methods to use in dialogue
	private static String[] localInput = new String[4]; // the actual typable input, is here so that player can't enter an input that isn't available in a certain town. last output needs to be BACK
	//"Graydrift", "Greenville", "Kilrock", "Bleakhost", "Duskwood", "Stormvalley", "Summerfelt", "Nevershore", "Southport", "Direwatch"

	public static void setLocation(Player player) {
		if (showMenu == true) {
			if (player.getLocation().equals("Graydrift")) {
				player.setSector(0);
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
				shopWeapons(player, "Blacksmith");
			}else if (in.equals("potions")) {
				showMenu = true;
				shopPotions(player);
			}//TODO add all possible inputs. potion sellers, mines, banks, etc
		}else {
			System.out.println("You don't know what "+in+" means.");
			setLocation(player);
		}
	}


	public static void shopWeapons(Player player, String shopName) { //if statement for locations to sell different items
		Weapon weaponForSale = null;
		if (showMenu == true){
			System.out.println("\n\n\nWelcome to the "+player.getLocation()+" "+shopName+" traveler!");
			System.out.println("\nCoins: "+player.getCoins()+"gp\n");
			
			int x = 0;
			if (player.getLocation().equals("Graydrift")){ // show currently contains very expensive weapons, need to create more static weapons
				for (x = 0; x < 3; ++x) {
					if (x == 0) {
						weaponForSale = Weapon.adminBlade;
					}else if (x == 1) {
						weaponForSale = Weapon.bsGreatSword;
					}else if (x == 2) {
						weaponForSale = Weapon.fist;
					}
				}
			}//TODO add locations

		}
		Scanner input = new Scanner(System.in);
		String in = input.nextLine();

		try{
			System.out.println("The "+weaponForSale.getName()+" costs "+weaponForSale.getCost()+"gp.\nAre you sure you want to purchase it? [yes]/[no]");
			Scanner input2 = new Scanner(System.in);
			String in2 = input2.nextLine().toString();
			if (in2.equals("yes")) {
				if (player.getCoins() >= weaponForSale.getCost()) {
					System.out.println("\nYou pay the merchant "+weaponForSale.getCost()+"gp and recieve the "+weaponForSale.getName()+".");
					player.setCoins(player.getCoins()-weaponForSale.getCost());
					showMenu = true;
					player.getInventory().pickup(weaponForSale); //adds to inv and continues.
					showMenu = true;
					shopWeapons(player, shopName);
				}else {
					int difference = weaponForSale.getCost()-player.getCoins();
					System.out.println("You don't have enough coins!\nYou need "+difference+" more coin(s) for that.\nYou put the "+weaponForSale.getName()+" back on the wall.");
					showMenu = false;
					shopWeapons(player, shopName);
				}
			}else if (in2.equals("no")) {
				showMenu = false;
				shopWeapons(player, shopName);
				System.out.println("You don't know what "+in2+"means. Undecided, you put the "+weaponForSale.getName()+" back on the wall.");
				showMenu = false;
				shopWeapons(player, shopName);
			}
		}catch (Exception e){
			if (in.equals("back")){
				showMenu = true;
				setLocation(player);
			}else{
				System.out.println("You need to enter a number. [#]");
				showMenu = false;
				shopWeapons(player, shopName);
			}
		}

	}
	
	public static void shopPotions(Player player) {
		Potion shopInventory[] = {
				Potion.minorHealth,
				Potion.standardHealth,
				Potion.minorMana
		};
		
		// bool to keep the shopping look going
		boolean stillShopping = true;
		Scanner input = new Scanner(System.in);
		do {
			if (showMenu == true) {
				System.out.println("\n\nHello "+player.getName()+"... A potion for all your needs..."); // quest?
				System.out.println("------------Potions-----------");
				System.out.println("[1] + "+shopInventory[0].getName());
				System.out.println("[2] + "+shopInventory[1].getName());
				System.out.println("[3] + "+shopInventory[2].getName());
	
			}
	
			System.out.println("\nWhich would you like to buy? [1-3] [back]");
			
			int selection = input.nextInt()-1; // subtract 1 to correspond with array index, needs error handling
			
			// the beauty of using objects! every selection contained into one code block
			System.out.println("Ah, a " + shopInventory[selection].getName() + " that'll be " + shopInventory[selection].getCost());
			System.out.println("Pay the shopkeep " + shopInventory[selection].getCost() + "? [y/n]");
			if(input.next().equalsIgnoreCase("y")) {
				player.getInventory().pickup(shopInventory[selection]);
			}
			
			System.out.println("Keep shopping? [y/n]");
			stillShopping = input.next().equalsIgnoreCase("y"); // quick way to set still shopping to true/false
		}while(stillShopping);
		
		input.close();		
	}

}
