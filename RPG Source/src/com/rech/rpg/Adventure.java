package com.rech.rpg;
import java.util.Random;
import java.util.Scanner;

public class Adventure{


	public static int strike = 0;
	
	public static void travel(boolean nextTown) {
		if (nextTown == true) {
			System.out.println("You head out toward "+Main.worldMap[Main.sector+1]+".");
		}else {
			System.out.println("You head out toward "+Main.worldMap[Main.sector-1]+".");
		}
		Random chance = new Random();
		int events = chance.nextInt(3); //3 events that will happen.
		do {
			randomEvent();
			--events;
		}while(events > 0);
		if (nextTown == true) { //whether or not to go to the next town or previous town.
			++Main.sector;
		}else {
			--Main.sector;
		}
		Main.justTPd = true;
		Main.teleport(Main.worldMap[Main.sector]);

	}

	private static void randomEvent() {
		Random chance = new Random();
		int destiny = (int) (chance.nextInt(101)+Math.ceil(Main.luck*1.25)); //destiny decides event
		int dialogue = chance.nextInt(2);//start with 3 dialogues/circumstances

		if (destiny >= 0 && destiny < 10) { //ambush
			generateDialogue(dialogue, 1);
		}else if (destiny >= 10 && destiny < 20) { //hurt self/status effect
			generateDialogue(dialogue, 2);
		}else if (destiny >= 20 && destiny < 30) { //lose item
			generateDialogue(dialogue, 3);
		}else if (destiny >= 30 && destiny < 70) { //fight
			dialogue = chance.nextInt(7);
			generateDialogue(dialogue, 4);
		}else if (destiny >= 70 && destiny < 80) { //find item
			generateDialogue(dialogue, 5);
		}else if (destiny >= 80 && destiny < 90) { //heal self
			generateDialogue(dialogue, 6);
		}else if (destiny >= 90 && destiny <= 100) { //find gp/weapon
			generateDialogue(dialogue, 7);			
		}
		return;
	}


	private static void generateDialogue(int dialogue, int event) { //dialogue (out of 3) to say, event is which group of dialogue to choose based on the event.
		String enemy = null;
		String weapon = null;
		switch (event) { //attack = fight. flee & main.luck are evasions
		case 1: //ambush
			if (dialogue == 0) {
				enemy = getEnemy("human"); 
				weapon = WeaponDeprecated.randomItem(1, 1);
				System.out.println("You walk outside of the gates of "+Main.location+", when you hear a desparate cry for help.\n"
						+ "You dash over to see who is in trouble.\nYou see a "+enemy+" slouched against the wall of a building.\nAs you reach out to see if he has a pulse, "
						+ "he punches you in the face (-1 HP)!\nYou Stagger back as the "+enemy+" stands up revealing a "+weapon+". \n\n'Ha ha ha, the look on your face,'\nhe laughs,\n\n"
						+ "'Why don't you hand over the gold in that backpack of yours?'\n\nWhat do you do? [attack] [flee] [main.luck]");
				--Main.health;
				testHealth();
			}else if (dialogue == 1) {
				enemy = getEnemy("human"); 
				weapon = WeaponDeprecated.randomItem(6, 6);  
				System.out.println("As you walk across the bridge just outside of town, you come across a small building made of brick.\nThere aren't many people living just outside of "+Main.location+", so you decide to investigate.\n"
						+ "Just before you get to the entrance you hear a loud scraping noise. You look over to see a "+enemy+", grinding his "+weapon+" against the brick as he approaches.\n\n'Not from around here are ya?'\nHe says, grinning.\n\n"
						+ "He's getting closer,\n\n What do you do? [attack] [flee] [main.luck]");
			}else if (dialogue == 2) {
				enemy = getEnemy("orc");
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("You find a small opening in the mountain face just ahead. You decide to investigate.\nInside you find distiguished torches.\n'Odd.' you say to yourself aloud.\nSomething falls over just outside of sight, deeper in"
						+ " the cave.\nYou walk further into the dark cave...\n~woosh~ You duck as a "+weapon+" swings over your head.\n\n'Human! You don't belong here!'\nAn "+enemy+" emerges from the depths of the cave darkness. He lifts his"
						+ "weapon again.\n\n What do you do? [attack] [flee] [main.luck]");
			}
			response(event, dialogue, enemy, weapon);
			break;
		case 2: //hurt self/status effect
			if (dialogue == 0) {
				System.out.println("You find a shortcut down a valley, this'll definitely cut the distance alot shorter.\nHalfway through the valley, there is a pond of bright green water in the way. You've gotten this far, so you decide to walk through it."
						+ "\nWhen you finally get across the pond, your legs begin to burn. You're not sure that was water anymore... (-6hp)");
				Main.health = Main.health - 6;
				testHealth();
			}else if (dialogue == 1) {
				System.out.println("You come across a small steep cliff where the path connects down alot closer.\nYou attempt to climb down the cliff, but lose your footing and land on your back. Ouch... (-10hp)");
				Main.health = Main.health - 10;
				testHealth();
			}else if (dialogue == 2) {
				System.out.println("You find a berry bush that you haven't noticed before. You seem to recognise the berries.\nYou decide to eat a few to recover some of the energy that you've lost traveling."
						+ "\nYou fall to your knees and vomit onto the ground... Tasted good going down, not the other way though... (-5hp)");
				Main.health = Main.health - 5;
				testHealth();
			}
			break;
		case 3: //lose item
			if (dialogue == 0) {
				System.out.print("You come across a small steep cliff where the path connect down alot closer.\nAs you attempt to climb down the cliff, ");
				if (Main.potInventory[0] > 0) {
					System.out.print("one of your Minor health Potions falls from your pack and shatters on the ground... great.\nAt least you made it down safely.\n");
					--Main.potInventory[0];
				}else if (Main.potInventory[1] > 0) {
					System.out.print("one of your Standard health Potions falls from your pack and shatters on the ground... great.\nAt least you made it down safely.\n");
					--Main.potInventory[1];
				}else if (Main.potInventory[2] > 0) {
					System.out.print("one of your Major health Potions falls from your pack and shatters on the ground... great.\nAt least you made it down safely.\n");
					--Main.potInventory[2];
				}else {
					System.out.print("you lose your footing and land on your back. Ouch... (-10hp)\n");
					Main.health = Main.health - 10;
					testHealth();
				}
			}else if (dialogue == 1) {
				System.out.print("You walk into a forest. This forest is so dense, you're finding yourself squeezing through the trees and bushes. ");
				if (Main.potInventory[0] > 0) {
					System.out.print("You stumble over and one of your Minor main.health Potions falls from your pack and smashes on the ground... great.\nYou make it through safely.\n");
					--Main.potInventory[0];
				}else if (Main.potInventory[1] > 0) {
					System.out.print("You stumble over and one of your Standard main.health Potions falls from your pack and smashes on the ground... great.\nYou make it through safely.\n");
					--Main.potInventory[1];
				}else if (Main.potInventory[2] > 0) {
					System.out.print("You stumble over and one of your Major main.health Potions falls from your pack and smashes on the ground... great.\nYou make it through safely.\n");
					--Main.potInventory[2];
				}else {
					System.out.print("you stumble over and land into a Poisonous Thorn Bush. Ouch... (-3hp)\n");
					Main.health = Main.health - 3;
					testHealth();
				}
			}else if (dialogue == 2) {
				Random rand = new Random();
				int toll = rand.nextInt(12)+12;
				System.out.println("You pass through an old village that seems run down at best at the gate. A guard stands by.\n'Pay the toll to pass, "+toll+"gp...'\nOdd, I don't remember a toll here. You pull out your coin pouch.\n");
				if (Main.coins >= toll) {
					System.out.println("You pay the toll,");
					Main.coins = Main.coins-toll;
				}else {
					System.out.println("You stare into your pouch... The guard sighs. \n'Just give me what you DO have...'\n You give him your pouch,");
					Main.coins = 0;
				}
				System.out.println(" and you walk through... This village is abandoned...\\nYou turn around to see the guard is gone. Nice...");
			}
			break;
		case 4: //fight
			if (dialogue == 0) {
				enemy = getEnemy("human"); 
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("A "+enemy+" appears out of the forest just ahead... He doesnt seem friendly...");
				fight(enemy, weapon, false);
			}else if (dialogue == 1) {
				enemy = getEnemy("human"); 
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("A "+enemy+" appears from the valley just ahead... He doesnt seem friendly...");
				fight(enemy, weapon, false);
			}else if (dialogue == 2) {
				enemy = getEnemy("orc"); 
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("An "+enemy+" appears out of the cave just ahead... He doesnt seem friendly...");
				fight(enemy, weapon, false);
			}else if (dialogue == 3) {
				enemy = getEnemy("orc"); 
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("An "+enemy+" is just ahead, right where you need to cross... He doesnt seem friendly...");
				fight(enemy, weapon, false);
			}else if (dialogue == 4) {
				enemy = getEnemy("knight"); 
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("What's a "+enemy+" doing here? Well, it looks like he's here for you...");
				fight(enemy, weapon, false);
			}else if (dialogue == 5) {
				enemy = getEnemy("knight"); 
				weapon = WeaponDeprecated.randomItem(6, 6);
				System.out.println("A "+enemy+" approaches you.\n\n'You're under arrest for your crimes against the king!'\nhe says.\n\n What crimes? What king? Well, it doesn't look like he'll take no for an answer...");
				fight(enemy, weapon, false);
			}else if (dialogue == 6) {
				enemy = getEnemy("animal"); 
				if (enemy.equals("boar")) {
					weapon = "tusks";
				}else {
					weapon = "claws";
				}
				System.out.println("Is that a "+enemy+"? Oh no, it's looking right at you! It's getting closer...");
				fight(enemy, weapon, false);
			}else if (dialogue == 7) {
				enemy = getEnemy("animal"); 
				if (enemy.equals("boar")) {
					weapon = "tusks";
				}else {
					weapon = "claws";
				}
				System.out.println("You walk past a "+enemy+" a few meters away. You turn your head to look at it. Uh oh. It looks like it's about to charge!");
				fight(enemy, weapon, false);
			}
			fight(enemy, weapon, false);
			break;
			//TODO finish events.
		case 5: //find item
			if (dialogue == 0) {
				System.out.println("You find a satchel on the side of the path near what looks like an abandondoned camp site.\nYou find a Standard Mana Potion and 8gp!");
				Main.coins = Main.coins + 8;
				++Main.potInventory[4];
			}else if (dialogue == 1) {
				Random rand = new Random();
				int payout = rand.nextInt(10)+(Main.luck*3);
				System.out.println("You find a skeleton in a some old rusty armor. His... or her, arm is around a dusty, old crate. Well, it's not much use to them anymore.\nYou open the crate to find "+payout+"gp!");
				Main.coins = Main.coins + payout;
			}else if (dialogue == 2) {
				System.out.println("You find a dusty container with a door in the side of a mountain. You've never seen something like it before.\nYou grab the handle on the white door, it feels cold..."
						+ " here...\nIt's clearly some sort of elven made magic box.\nYou open it to a cool breeze and discover it emits light from a smokeless torch on the top of the inside, and clear shelves hinged on the sides.\n"
						+ "Inside you find 3 minor mana potions and 2 minor healing potions!");
				Main.potInventory[0] = Main.potInventory[0] + 2;
				Main.potInventory[3] = Main.potInventory[3] + 3;
			}
			break;
		case 6: //heal self
			Random rand = new Random();
			if (dialogue == 0) {
				System.out.println("You come across a round glowing runed stone on the ground surrounded by pillars of old bricks.\nYou stand in the center, hearing a a whirring noise around you.");
				int healChance = rand.nextInt(101)+(Main.luck*3);
				if (healChance >= 50) {
					System.out.println("You feel a warmth surround you as the runed stone glows under you. You recover half of your health!");
					int heal = (int) Math.ceil(Main.maxHealth/2);
					Main.health = Main.health + heal;
					if (Main.health > Main.maxHealth) {
						Main.health = Main.maxHealth;
					}
				}else {
					System.out.println("The runed stone, sparks and crackles... then the glow darkens. Nothing else happens.");
				}
			}else if (dialogue == 1) {
				System.out.println("As you pass under a bridge, you see a standard healing potion hanging from a rope, just out of your reach.\nYou swing your "+Main.inventory[0]+" at the rope.");
				int potChance = rand.nextInt(101)+(Main.luck*3);
				if (potChance >= 50) {
					System.out.println("You slice the rope and you catch the potion in your hands");
					++Main.potInventory[1];
				}else {
					System.out.println("Your blade strikes the potion, spilling onto you, quickly evaporating but healing a quarter of your health");
					int heal = (int) Math.ceil(Main.maxHealth/4);
					Main.health = Main.health + heal;
					if (Main.health > Main.maxHealth) {
						Main.health = Main.maxHealth;
					}
				}
			}else if (dialogue == 2) {
				//TODO
			}
			break;
		case 7: //find sword/ gp
			if (dialogue == 0) {
				//TODO
			}else if (dialogue == 1) {
				//TODO
			}else if (dialogue == 2) {
				String item = WeaponDeprecated.randomItem(Main.sector, Main.sector);
				System.out.println("You found a chest! Inside you find a "+item+" inside!");
				Surroundings.addToInv(item, WeaponDeprecated.atk);
			}
			break;
		}
	}

	private static void response(int event, int dialogue, String enemy, String sword){
		Scanner input = new Scanner(System.in); //[attack] [flee] [main.luck]
		Random rand = new Random(); int roll;
		String in = input.nextLine();
		String response = null;
		if (in.equals("attack")) {
			response = "attack"; 
		}else if (in.equals("flee")) {
			response = "flee";
		}else if (in.equals("main.luck")) {
			response = "main.luck";
		}else {
			System.out.println("In shock, you udder a strange noise that no one understands.");
			++strike;
			if (strike > 3) {
				System.out.println("The "+enemy+" strikes you as you mumble to yourself (-8hp)");
				Main.health = Main.health-8;
				testHealth();
				fight(enemy, sword, false);
			}else {
				response(event, dialogue, enemy, sword);
				return;
			}
		}
		strike = 0;
		if (response.equals("attack")) {
			System.out.println("You unsheath your "+Main.inventory[0]+".");
			if (event == 1 && dialogue == 2) {//special dialogue
				System.out.println("Your blade meets his weapon as you get to your feet.");
			}
			fight(enemy, sword, false);
		}else {
			switch (event) {
			case 1: //ambush
				if (dialogue == 0) {
					if (response.equals("flee")) {
						roll = (int) Math.ceil(rand.nextInt(101)+(Main.luck*1.2));
						if (roll >= 76) { //only a 1/4 chance
							System.out.println("You kick the "+enemy+" back down to the ground.\nHe drops his sword and you make a break for it.\nYou escape safely.");
							return;
						}else {
							System.out.println("You kick the "+enemy+" but he jumps back, avoiding the blow...\nnow he's angry...");
							fight(enemy, sword, true);
						}					
					}else if (response.equals("main.luck")) {
						roll = (int) (rand.nextInt(121)+(Main.luck*1.25));
						if (roll > 100){
							roll = rand.nextInt(12)+6;
							System.out.println("You sheath your sword. The "+enemy+" looks at you, puzzled.\n'I'd call you brave if you knew who I am'\n you say,\n'Ive killed many more for much less than that.'\nThe "+enemy+" looks around, worried. \n'I ugh...'\n he runs off, forgetting his stash of coins on the ground.");
							System.out.println("You find "+roll+"gp.");
							Main.coins = Main.coins+roll;
							return;
						}else{
							System.out.println("You sheath your sword. Before you can speak, the "+enemy+" swings his "+sword+", narrowly missing you.");
							fight(enemy, sword, true);
						}
					}
				}else if (dialogue == 1) {
					if (response.equals("flee")) {
						roll = (int) Math.ceil(rand.nextInt(101)+(Main.luck*1.2));
						if (roll >= 76) { //only a 1/4 chance
							System.out.println("You turn around and sprint as fast as you can, not looking back.\nYou escape safely.");
							return;
						}else {
							System.out.println("You turn around and fall over, your foot is caught in a rope. You slice the rope off, but by the time you get up, the "+enemy+" is right in front of you. Can't run now...");
							fight(enemy, sword, true);
						}
					}else if (response.equals("main.luck")) {
						roll = (int) (rand.nextInt(121)+(Main.luck*1.25));
						if (roll > 100){
							System.out.println("'Stop in the name of the law!' you shout, 'Under kings orders, you are under arrest!'\nThe "+enemy+"'s eyes widen. He turns around and runs into the woods behind the building.\n"
									+ "You go inside the building to find a potion! (+1 Small main.health Potion");
							++Main.potInventory[0];
							return;
						}else{
							System.out.println("'Stop in the name of the law!' you shout, 'Under kings orders, you are under arrest!'\nThe "+enemy+"'s eyes widen. 'You'll have to take me to the king dead!' he screams");
							fight(enemy, sword, true);
						}
					}
				}else if (dialogue == 2) {
					if (response.equals("flee")) {
						roll = (int) Math.ceil(rand.nextInt(101)+(Main.luck*1.2));
						if (roll >= 76) { //only a 1/4 chance
							System.out.println("You roll out of the way as the "+enemy+"'s "+sword+" slams into the rock beside you.\nYou quickly get to your feet and escape safely.");
							return;
						}else {
							System.out.println("You roll out of the way as the "+enemy+"'s "+sword+" slams into the rock beside you.\nYou stumble to your feet, but he picks you up and throws you further into the cave.\nNo running now...");
							fight(enemy, sword, true);
						}
					}else if (response.equals("main.luck")) {
						roll = (int) (rand.nextInt(121)+(Main.luck*1.25));
						if (roll > 100){
							System.out.println("Your "+Main.inventory[0]+" meets his weapon, blocking his attack.\nYou kick his wrist, knocking the sword out of his hand. You raise your blade to his throat.\n'Human never surprise me like this before!'"
									+ "\nHe slowly backs away as you sheath your sword.");
							return;
						}else{
							System.out.println("Your \"+main.inventory[0]+\" meets his weapon, blocking his attack.\\nYou kick his wrist, knocking the sword out of his hand, but dropping yours in the process.\nYou both scramble to pick your weapons back up.\nHe growls. That just made him angry...");
							fight(enemy, sword, true);
						}
					}
				}
				break;
			}
		}
	}

	private static void fight(String enemy, String weapon, boolean fleeIsHard) {
		Random rand = new Random();
		int multiplier = 1;
		if (Main.sector == 0 ) {
			multiplier = 1;
		}else {
			multiplier = (int) Math.floor(Main.sector*1.5);
		}
		WeaponDeprecated.defineItem(weapon);
		int enemyAtk = (int) (multiplier*(rand.nextInt(Main.strength+rand.nextInt(2))+Math.floor(WeaponDeprecated.atk/2)));
		if (enemyAtk == 0) {
			++enemyAtk;
		}//TODO roll main.luck to see who attacks first
		//TODO might want to make an enemy object for easy universal use.
	}

	private static void drinkPotion(String potion) {
		if (Main.health == Main.maxHealth && potion.contains("main.health")) {
			System.out.println("You feel fine. You decide not to waste your potion.");
		}else if (Main.mana == Main.maxMana && potion.contains("main.mana")) {
			System.out.println("You feel mana coursing through your veins. You decide not to waste your potion.");
		}else {
			if (potion.equals("Lhealth")) {
				--Main.potInventory[2];
				Main.health = Main.maxHealth;
			}else if (potion.equals("Mhealth")) {
				--Main.potInventory[1];
				Main.health = (int) Math.ceil(Main.health+(Main.maxHealth/2));
			}else if (potion.equals("Shealth")) {
				--Main.potInventory[0];
				Main.health = (int) Math.ceil(Main.health+(Main.maxHealth/4));
			}else if (potion.equals("Lmana")) {
				--Main.potInventory[5];
				Main.mana = Main.maxMana;
			}else if (potion.equals("Mmana")) {
				--Main.potInventory[4];
				Main.mana = (int) Math.ceil(Main.mana+(Main.maxMana/2));
			}else if (potion.equals("Smana")) {
				--Main.potInventory[3];
				Main.mana = (int) Math.ceil(Main.mana+(Main.maxMana/4));
			}
		}
		if (Main.health > Main.maxHealth) {
			Main.health = Main.maxHealth;
		}
		if (Main.mana > Main.maxMana) {
			Main.mana = Main.maxMana;
		}
	}

	private static String getEnemy(String type) {
		String[] humanList = new String[]{"Beggar", "Thief", "Bandit", "Raider", "Marauder"};
		String[] knightList = new String[]{"Guard", "Bronze Knight", "Wrought Knight", "Royal Knight", "Dark Knight"};
		String[] orcList = new String[]{"Orc Bandit", "Orc Warrior", "Orc Bezerker", "Orc Juggernaught", "Orc Warlord"};
		String[] animalList = new String[]{"Boar", "Wolf", "Alpha Wolf", "Bobcat", "Bear"};
		String enemy = null;
		Random roll = new Random();
		if (type.equals("human")){
			enemy = humanList[roll.nextInt(4)];
		}else if (type.equals("knight")) {
			enemy = knightList[roll.nextInt(4)];
		}else if (type.equals("orc")) {
			enemy = orcList[roll.nextInt(4)];
		}else if (type.equals("animal")) {
			enemy = animalList[roll.nextInt(4)];
		}
		return enemy;
	}

	/*
	private static String getSword(int limit) {
		String sword = null;
		String[] swordList = new String[] {"Bronze Dagger", "Iron Dagger", "Steel Dagger", "Bronze Shortsword", "Iron Shortsword", "Steel Shortsword", "Bronze Mace", "Iron Mace", "Steel Mace", "Bronze Longsword", "Iron Longsword", "Steel Longsword", "Bronze Scimitar", "Iron Scimitar", "Steel Scimitar", "Bronze GreatSword", "Iron GreatSword", "Steel GreatSword"};
		Random roll = new Random();
		sword = swordList[roll.nextInt(limit)];
		return sword;
	}
	*/

	private static void testHealth() {
		int percent = (int) Math.floor((Main.health/Main.maxHealth)*100);
		if (Main.health >0 && percent < 25) { //25% main.health
			System.out.println("Your heart is pounding... You feel weak.");
		}else if (Main.health <= 0) {
			System.out.println("You fall to the ground, the darkness surrounding your vision... You draw your last breath... \n You are dead.");
			death();
		}
	}
	
	private static void giveExp(int xp) {
		System.out.println("You've gained "+xp+"xp.");
		Main.exp = Main.exp + xp;
		if (Main.exp >= Main.levelup) {
			++Main.level;
			System.out.println("You leveled up! You're now level "+Main.level);
			Main.exp = 0;
			Main.levelup = (int) Math.floor(Main.levelup*1.3);
		}
	}
	
	private static void death() {
		System.out.println("-end of stream-"); //this is just a placeholder for now
		//TODO what happens when HP = 0;
	}
}
