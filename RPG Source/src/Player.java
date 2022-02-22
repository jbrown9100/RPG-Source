
public class Player {
	
	//stats
	private static int 
	level,
	exp, 
	points,
	coins,
	health,
	maxHealth,
	strength,
	defense,
	dodge,
	luck,
	magic,
	mana,
	maxMana,
	resistance;

	
	public Player() {
		level = 1;
		exp = 0;
		points = 0;
		coins = 0;
		health = 50;
		maxHealth = 50;
		strength = 1;
		defense = 1;
		dodge = 1;
		luck = 1;
		magic = 1; 
		mana = 50; 
		maxMana = 50; 
		resistance = 1;
	}
	
	protected int getLevel() {
		return level;
	}
	
	protected int getExp() {
		return exp;
	}
	
	protected int getLevelUpXP() {
		return level*81;
	}
	
	protected int getPoints() {
		return points;
	}
	
	protected int getCoins() {
		return coins;
	}
	
	protected int getHealth() {
		return health;
	}
	
	protected int getMaxHealth() {
		return maxHealth;
	}
	
	protected int getStrength() {
		return strength;
	}
	
	protected int getDefense() {
		return defense;
	}
	
	protected int getDodge() {
		return dodge;
	}
	
	protected int getLuck() {
		return luck;
	}
	
	protected int getMagic() {
		return magic;
	}
	
	protected int getMana() {
		return mana;
	}
	
	protected int getMaxMana() {
		return maxMana;
	}
	
	protected int getResistance() {
		return resistance;
	}
}
