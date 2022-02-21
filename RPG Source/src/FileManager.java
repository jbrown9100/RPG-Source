import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class FileManager{


	public static void saveGame() {//TODO autosave? bool autosave - asks to overwrite or not when saving.
		System.out.println("Saving you game will write over a previous save with the same name. Save anyway?");
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();
		
		if (in.equals("yes")) {
			System.out.println("Saving game...");
			try {
				FileWriter saveName = new FileWriter(main.name+".txt");
				saveName.write(main.name+"\n"+main.level+"\n"+main.exp+"\n"+main.levelup+"\n"+main.points+"\n"+main.location+"\n"+main.sector+"\n"+main.health+"\n"+main.maxHealth+"\n"+main.mana+"\n"+main.maxMana
						+"\n"+main.strength+"\n"+main.defense+"\n"+main.dodge+"\n"+main.luck+"\n"+main.magic+"\n"+main.resistance+"\n"+main.coins);
						for(int i = 0; i < 10; ++i) {
							saveName.write("\n"+main.inventory[i]+"\n"+main.atkInventory[i]+"\n"+main.spellBooks[i]+"\n"+main.mgcInventory[i]+"\n"+main.mgcInvElement[i]);
						}
						for (int i = 0; i < 6; ++i) {
							saveName.write("\n"+main.potInventory[i]);
						}
				saveName.close();
				input.close();
				System.out.println("Save Complete.");	
				main.teleport(main.location);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else if (in.equals("no")){
			System.out.println("Game save was canceled.");
			input.close();
			return;
		}else {
			saveGame();
			input.close();
			return;
		}
		
	}
	
	public static void loadGame() {
		System.out.println("Enter the name of the Travelor you want to use, or [cancel]");
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();
		
		if (in.equals("cancel")) {
			System.out.println("No save loaded.");
			return;
		}else {
			try {
				File saveGame = new File(in+".txt");
				if (saveGame.exists()) {
					Scanner save = new Scanner(saveGame);
					while (save.hasNextLine()) {
						main.name = save.nextLine(); 
						main.level = Integer.parseInt(save.nextLine());
						main.exp = Integer.parseInt(save.nextLine());
						main.levelup = Integer.parseInt(save.nextLine());
						main.points = Integer.parseInt(save.nextLine());
						main.location = save.nextLine();
						main.sector = Integer.parseInt(save.nextLine());
						main.health = Integer.parseInt(save.nextLine());
						main.maxHealth = Integer.parseInt(save.nextLine());
						main.mana = Integer.parseInt(save.nextLine());
						main.maxMana = Integer.parseInt(save.nextLine());
						main.strength = Integer.parseInt(save.nextLine());
						main.defense = Integer.parseInt(save.nextLine());
						main.dodge = Integer.parseInt(save.nextLine());
						main.luck = Integer.parseInt(save.nextLine());
						main.magic = Integer.parseInt(save.nextLine());
						main.resistance = Integer.parseInt(save.nextLine());
						main.coins = Integer.parseInt(save.nextLine());
						for(int i = 0; i < 10; ++i) {
							main.inventory[i] = save.nextLine();
							main.atkInventory[i] = Integer.parseInt(save.nextLine());;
							main.spellBooks[i] = save.nextLine();
							main.mgcInventory[i] = Integer.parseInt(save.nextLine());;
							main.mgcInvElement[i] = save.nextLine();
						}
						for (int i = 0; i < 6; ++i) {
							main.potInventory[i] = Integer.parseInt(save.nextLine());;
						}
						System.out.println("Save game '"+in+"' loaded.");
						save.close();
						main.showMenu = true;
						main.teleport(main.location);					
					}
				}else {
					System.out.println("Save game, "+in+" does not exist.");
					loadGame();
					return;
				}
			}catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
		System.out.println("Saved game '"+in+"' loaded.");
	}
	
}
