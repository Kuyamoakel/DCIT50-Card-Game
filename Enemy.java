
public class Enemy {
    private String name;
    private int hp;
    private int damage;
    private int maxHp;
    private boolean isActivated = false;
    private int turns;
    private int inflictDamage;

    private int staggerMeter = 0;
    private int MaxStagger = 30;
    private int staggerTurns = 0;
    private boolean skipTurn = false;
    private int bonusDamage = 20;
    private boolean BonusDamageUsed = false;

    public Enemy(String name, int hp, int damage) {
        if (hp <= 0) {
            throw new IllegalArgumentException("Enemy hp must not be negative or zero");
        }   

        if (damage <= 0) {
            throw new IllegalArgumentException("Enemy damage must not be negative or zero");
        }
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.maxHp = hp; // whatever hp the user place, it becomes the maxHp
    }

    // getters
    public int getStaggerMeter() {
        return this.staggerMeter;
    }
    public int getMaxStagger() {
        return this.MaxStagger;
    }
    public int getbonusDamage() {
        return this.bonusDamage;
    }

    public int getTurns() {return turns; }
    public String getName(){
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDamage() {
        return damage;
    }
    // methods
    public void takeDamage1(int amount) {
        hp = Math.max(0, hp - amount);

    }

    public void takeDamage (int amount){
        if (amount < 0) {
            throw new IllegalArgumentException("Take Damage cannot be negative");
        }

        this.BonusDamageUsed = false; 
        if (amount == 0){ 
            return;
        } 

        int finalDamage = amount;
        // STAGGER METER APPLIED BONUS DAMAGE
        if (this.staggerTurns > 0) {
            this.staggerTurns = 0;
            this.staggerMeter = 0;

            this.BonusDamageUsed = true; // THIS CARD IS ONE THAT USED THE BONUS DAMAGE

            finalDamage = amount + this.bonusDamage;
            System.out.println("\n=================================");
            System.out.println("STAGGER BONUS USED! +" + bonusDamage + " damage: " + amount + " -> " + finalDamage);
            System.out.println("The boss's stagger has been spent. Meter reset to 0/" + MaxStagger);
            System.out.println("=================================");
            
        }
        
        takeDamage1(finalDamage);
        if (isDead()){
            System.out.println("\nBOSS IS DEADD");
        }
    }



    public void attack(Player p) {
        if (damage < 0) {
            System.out.println("No damage is dealt");
            return;
        }


        if (this.skipTurn) {
        this.skipTurn = false;
        System.out.println("=================================");
        System.out.println(name + " IS STAGGERED AND CANNOT ATTACK!");
        System.out.println( this.name + " RECOVERS! Stagger meter: 0/" + MaxStagger);
        System.out.println("=================================");
        return;
    }

        p.takeDamage(damage);

        System.out.println(this.name + " Dealt " + damage + " to " + p.getName());
    }
    // ADDED INFLICTION DAMAGE FEATURE
    public void applyInflictDmg(int damage, int duration) {
        this.inflictDamage = damage;
        this.turns = duration;
    }
    public void isInflicted() {
        if (turns > 0) {
            System.out.println(getName() + " gets inflicted " + inflictDamage + " damage");
            takeDamage(inflictDamage);
            turns--;
        }
    }

    public void rageMode() {
        if (hp <= 0.25 * maxHp && !isActivated && !isDead()) {
            System.out.println("\n========= RAGE MODE ===========");
            this.damage = 2 * damage;
            isActivated = true;
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }


    // STAGGER EXTRA METHODS
    public void endTurn(){
        this.BonusDamageUsed = false; // RESET THE BONUS DAMAGE IN EACH TURN
        if (this.staggerTurns > 0) {
            this.staggerMeter = 0;
            this.staggerTurns = 0;

            if (isDead()) return;
            if (staggerTurns == 0) {
                System.out.println("\n=================================");
                System.out.println("THE BOSS RECOVERS! The stagger bonus expired unused.");
                System.out.println("=================================");
            }

        }
    }

    public void addStaggerPTS(int amount){
        if (isDead()) {
            return;
        }

        if (this.BonusDamageUsed){
            this.BonusDamageUsed = false;
            System.out.println("This card broke the stagger, so it adds no stagger points.");
            return;
        }

        if (this.staggerTurns > 0) {
            System.out.println("Boss is already staggered! Stagger points wasted.");
            return;
        }
        staggerMeter += amount; // STAGGER METER ADDER
        // OVERFLOW HANDLER
        if (this.staggerMeter >= this.MaxStagger) {
            this.staggerMeter = this.MaxStagger;
            System.out.println("\n=================================");
            System.out.println("STAGGER METER FULL: " + staggerMeter + "/" + MaxStagger);
            System.out.println("THE BOSS IS STAGGERED!");
            System.out.println("Your next damaging card deals +" + bonusDamage + " bonus damage!");
            System.out.println("=================================");
            this.skipTurn = true;
            this.staggerTurns = 1;
        }
    }


}
