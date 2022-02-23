package com.rech.rpg;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class FileManager{

	/*
	 * 
	 * Saving can be handled by each class, they can could all have a save method that returns a string with all the info, where this class collects
	 * then writes to a file. This would make things a little cleaner, and keep all info within each class
	 */

	public static void saveGame() {//TODO autosave? bool autosave - asks to overwrite or not when saving.
		System.out.println("Saving you game will write over a previous save with the same name. Save anyway?");
		Scanner input = new Scanner(System.in);
		String in = input.nextLine().toString();
		
		if (in.equals("yes")) {
			System.out.println("Saving game...");
			try {
				FileWriter saveName = new FileWriter(Main.player.getName()+".txt");
				saveName.write(Main.player.getName()+"\n"+Main.player.getLevel()+"\n"+Main.player.getExp()+"\n"+Main.player.getPoints()+"\n"+Main.player.getLocation()+"\n"+Main.player.getSector()+"\n"+Main.player.getHealth()+"\n"+Main.player.getMaxHealth()+"\n"+Main.player.getMana()+"\n"+Main.player.getMaxMana()
						+"\n"+Main.player.getStrength()+"\n"+Main.player.getDefense()+"\n"+Main.player.getDodge()+"\n"+Main.player.getLuck()+"\n"+Main.player.getMagic()+"\n"+Main.player.getResistance()+"\n"+Main.player.getCoins());
						for(int i = 0; i < 10; ++i) {
							//Inventory can not be saved until i fix potions, spellbooks etc
							//saveName.write("\n"+Main.player.getInventory().getSlot(i)+"\n"+"\n"+Main.player.spellBooks[i]+"\n"+Main.player.mgcInventory[i]+"\n"+Main.player.mgcInvElement[i]);
						}
				saveName.close();
				input.close();
				System.out.println("Save Complete.");	
				Main.teleport(Main.player.getLocation());
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
						Main.player = new Player(					// this is not the best way to do things, but this functions for the time
								save.nextLine(),				   // name
								Integer.parseInt(save.nextLine()), // level
								Integer.parseInt(save.nextLine()), // exp
								Integer.parseInt(save.nextLine()), // points
								save.nextLine(),				   // location
								Integer.parseInt(save.nextLine()), // sector
								Integer.parseInt(save.nextLine()), // health
								Integer.parseInt(save.nextLine()), // maxHealth
								Integer.parseInt(save.nextLine()), // mana
								Integer.parseInt(save.nextLine()), // maxMana
								Integer.parseInt(save.nextLine()), // strength
								Integer.parseInt(save.nextLine()), // defense
								Integer.parseInt(save.nextLine()), // dodge
								Integer.parseInt(save.nextLine()), // luck
								Integer.parseInt(save.nextLine()), // magic
								Integer.parseInt(save.nextLine()), // resistance
								Integer.parseInt(save.nextLine())  // coins
								);
						
						/**
						for(int i = 0; i < 10; ++i) {
							Main.inventory[i] = save.nextLine();
							Main.atkInventory[i] = Integer.parseInt(save.nextLine());;
							Main.spellBooks[i] = save.nextLine();
							Main.mgcInventory[i] = Integer.parseInt(save.nextLine());;
							Main.mgcInvElement[i] = save.nextLine();
						}
						for (int i = 0; i < 6; ++i) {
							Main.potInventory[i] = Integer.parseInt(save.nextLine());;
						}
						**/
						System.out.println("Save game '"+in+"' loaded.");
						save.close();
						Main.showMenu = true;
						Main.teleport(Main.player.getLocation());					
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
