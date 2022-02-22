import java.util.Random;


public class Weapon {

	//Test to see if file is overwritten when 2 changes are made
	
	// get/set Variables
	public static String name; //name of the weapon that info is being grabbed for.
	public static String material;
	public static String type;
	public static int cost;
	
	//Attributes
	public static int atk;
	//public static String[] skillSets; dagger = stab, slice, hack     sword = swing, slice,   Use later?
	
	public static void defineItem(String item) { //splits name to 2 words, and sorts accordingly
		String word[] = item.split(" ", 2);
		String a = word[0].toLowerCase(); //material
		String b = word[1].toLowerCase(); //type
		setAttributes(a, b);
	}
	
	
	public static void setAttributes(String a, String b) { //material, type
		int matAtk = 0;
		if (a.equals("bronze")) {
			matAtk = 3;
			material = "Bronze";
		}else if (a.equals("pig-iron")) {
			matAtk = 4;
			material = "Pig-Iron";
		}else if (a.equals("iron")) {
			matAtk = 5;
			material = "Iron";
		}else if (a.equals("steel")) {
			matAtk = 7;
			material = "Steel";
		}else if (a.equals("blue-steel")) {
			matAtk = 9;
			material = "Blue-Steel";
		}else if (a.equals("blood-iron")) {
			matAtk = 10;
			material = "Blood-Iron";
		}else if (a.equals("shadow-steel")) {
			matAtk = 11;
			material = "Shadow-Steel";
		}
		//TODO add more materials - Celestial Steel, Demonic Steel, Luminous Steel, Death Steel (added bonus of leaching health)
		
		int typeAtk = 0;
		if (b.equals("dagger")) {
			typeAtk = 1;
			type = "Dagger";
		}else if (b.equals("rapier")) {
			typeAtk = 2;
			type = "Rapier";
		}else if (b.equals("shortsword")) {
			typeAtk = 2;
			type = "Shortsword";
		}else if (b.equals("mace")) {
			typeAtk = 3;
			type = "Mace";
		}else if (b.equals("scimitar")) {
			typeAtk = 4;
			type = "Scimitar";
		}else if (b.equals("longsword")) {
			typeAtk = 5;
			type = "Longsword";
		}else if (b.equals("greatsword")) {
			typeAtk = 8;
			type = "Great Sword";
		}else if (b.equals("katana")) {
			typeAtk = 6;
			type = "Katana";
		}else if (b.equals("hatchet")) {
			typeAtk = 3;
			type = "Hatchet";//TODO add more weapon types - katana, hatchet, battleaxe, Great hammer
		
		atk = matAtk+typeAtk;
		name = material+" "+type;
	}
	
	public static int getCost(String a, String b) { //material, type
		int matCost = 0;
		a = a.toLowerCase();
		if (a.equals("bronze")) {
			matCost = 8;
		}else if (a.equals("pig-iron")) {
			matCost = 12;
		}else if (a.equals("iron")) {
			matCost = 16;
		}else if (a.equals("steel")) {
			matCost = 20;
		}else if (a.equals("blue-steel")) {
			matCost = 26;
		}else if (a.equals("blood-Iron")) {
			matCost = 33;
		}else if (a.equals("shadow-steel")) {
			matCost = 41;
		}
		
		int typeCost = 0;
		b = b.toLowerCase();
		if (b.equals("dagger")) {
			typeCost = 3;
		}else if (b.equals("rapier")) {
			typeCost = 6;
		}else if (b.equals("shortsword")) {
			typeCost = 10;
		}else if (b.equals("mace")) {
			typeCost = 14;
		}else if (b.equals("scimitar")) {
			typeCost = 15;
		}else if (b.equals("longsword")) {
			typeCost = 21;
		}else if (b.equals("greatsword")) {
			typeCost = 32;
		}//TODO add more weapon types
		
		cost = matCost+typeCost;
		return cost;
	}
	
	public static String randomItem(int materialCap, int typeCap) { //0-6
		String[] material = {"bronze", "pig-iron", "iron", "steel", "blue-steel", "blood-iron", "shadow-steel"}; //0-6  will need 9 so that this will work in all areas
		String[] type = {"dagger", "rapier", "shortsword", "mace", "scimitar", "longsword", "greatsword"}; //0-6  will need 9 so that this will work in all areas
		Random rand = new Random();
		String metal = material[rand.nextInt(materialCap)];
		String weapon = type[rand.nextInt(typeCap)];
		String outcome = metal + " " + weapon;
		defineItem(outcome);
		return outcome;
	}
	
	

}
