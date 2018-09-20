package paul.TextQuest.entities;

import paul.TextQuest.enums.BehaviorTiming;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Paul Dennis on 8/8/2017.
 */
public class Monster extends DungeonRoomEntity {

    private int health;
    private int might;
    private int defense;
    private String name;
    
    private String description;

    private transient int disabledForRounds = 0;

    //Not sure what this is for/what it was intended for
    //private static Map<String, VoidAction> actionMap;

    private Map<BehaviorTiming, String> behavior;
    private boolean isMiniboss;
    
    private String onTakeDamage;
    private String onDealDamage;
    private String onDeath;
    private String onDisable;
    
    private PatrolRoute patrolRoute;
    
    private transient DungeonRoom location;

    public Monster () {
        name = "Biff the Understudy";
        behavior = new HashMap<>();
    }

    public Monster(int health, int might, String name) {
        this.health = health;
        this.might = might;
        this.name = name;
        
        behavior = new HashMap<>();
    }
    
    /**
     * Constructor used to build a monster from a "template".
     * Randomly adjusts stats
     * @param monsterTemplate
     */
    public Monster (Monster monsterTemplate) {
    	this.name = monsterTemplate.name;
    	this.might = monsterTemplate.might;
    	this.health = monsterTemplate.health;
    	this.defense = monsterTemplate.defense;
    	this.behavior = monsterTemplate.behavior;
    	
    	this.onTakeDamage = monsterTemplate.onTakeDamage;
    	this.onDealDamage = monsterTemplate.onDealDamage;
    	this.onDeath = monsterTemplate.onDeath;
    	this.onDisable = monsterTemplate.onDisable;
    	
    	might = getModdedStat(might, 0);
    	health = getModdedStat(health, 1);
    	defense = getModdedStat(defense, 0);
    	
    	behavior = new HashMap<>();
    }
    
    /**
     * Randomly modifies a stat to increase or decrease 50%.
     * If the result is less than min, returns min.
     * @param stat
     * @param min
     * @return
     */
    public static int getModdedStat (int stat, int min) {
    	Random random = new Random();
    	if (stat >= 2) {
    		if (random.nextBoolean()) {
    			stat += random.nextInt(stat / 2);
    		} else {
    			stat -= random.nextInt(stat / 2);
    			if (stat < min) {
    				stat = min;
    			}
    		}
    	}
    	return stat;
    }

    public void disable (int rounds) {
        disabledForRounds += rounds;
        if (onDisable != null) {
        	location.doAction(onDisable);
        }
    }

    public boolean isDisabled () {
        return disabledForRounds > 0;
    }

    public void nextRound () {
        if (disabledForRounds > 0) {
            disabledForRounds--;
        }
    }

    public int takeDamage (int damageAmt) {
        damageAmt -= (defense / 5) * 2;
        health -= damageAmt;
        if (onTakeDamage != null) {
        	location.doAction(onTakeDamage);
        }
        if (health < 0) {
            health = 0;
            if (onDeath != null) {
            	location.doAction(onDeath);
            }
        }
        return damageAmt;
    }

    @Override
    public String toString () {
        return name;
    }

    public int getHealth () {
        return health;
    }

    public int getMight () {
        return might;
    }

    public void setMight (int might) {
        this.might = might;
    }

    public int getExp () {
        return health + 2 * might + 3;
    }

    public String getName () {
        return name;
    }

    public void setHealth (int health) {
        this.health = health;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getDefense () {
        return defense;
    }

    public void setDefense (int defense) {
        this.defense = defense;
    }

    public Map<BehaviorTiming, String> getBehavior () {
        return behavior;
    }

    public void setBehavior (Map<BehaviorTiming, String> behavior) {
        this.behavior = behavior;
    }

    public boolean isMiniboss () {
        return isMiniboss;
    }

    public void setIsMiniboss (boolean miniboss) {
        isMiniboss = miniboss;
    }

	public String getOnTakeDamage () {
		return onTakeDamage;
	}

	public void setOnTakeDamage (String onTakeDamage) {
		this.onTakeDamage = onTakeDamage;
	}

	public String getOnDealDamage () {
		return onDealDamage;
	}

	public void setOnDealDamage (String onDealDamage) {
		this.onDealDamage = onDealDamage;
	}

	public String getOnDeath () {
		return onDeath;
	}

	public void setOnDeath (String onDeath) {
		this.onDeath = onDeath;
	}

	public String getOnDisable () {
		return onDisable;
	}

	public void setOnDisable (String onDisabled) {
		this.onDisable = onDisabled;
	}
    
    public void addRoomReference (DungeonRoom room) {
    	this.location = room;
    }
    
    public void setDescription (String description) {
    	this.description = description;
    }
    
    public String getDescription () {
    	return description;
    }
    
    public boolean hasDescription () {
    	return description != null && !description.trim().equals("");
    }

	public PatrolRoute getPatrolRoute() {
		return patrolRoute;
	}

	public void setPatrolRoute(PatrolRoute patrolRoute) {
		this.patrolRoute = patrolRoute;
	}
	
	public Integer getPatrollerId () {
		if (patrolRoute != null) {
			return patrolRoute.getPatrollerId();
		}
		return null;
	}
    
}
