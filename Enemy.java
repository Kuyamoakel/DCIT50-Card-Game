
public class Enemy {
    private String name;
    private int hp;
    private int damage;
    private int maxHp;
    private boolean isActivated = false;

    // NEW STAGGER ATTRIBUTES 
    private int staggerMeter = 0;
    private int MaxStagger = 30;
    private int  staggerTurns = 0;
    private boolean skipTurn = false;
    private int bonusdamage = 20;

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
    public void takeDamage(int amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("Take Damage cannot be negative");
        }


        hp = Math.max(0, hp - amount);
    }

    public void attack(Player p) {
        if (damage < 0) {
            System.out.println("No damage is dealt");
            return;
        }

        // SKIP TURN WHEN THE BOSS IS STAGGERED - JA
        if (this.skipTurn){
            this.skipTurn = false;
            System.out.println("BOSS IS STAGGERED/STUN CANNOT ATTACK");
            return;
        }
        
        p.takeDamage(damage);
        System.out.println(name + " Dealt " + damage + " to player");
    }

    public void rageMode() {
        if (hp <= 0.25 * maxHp && !isActivated && !isDead()) {
            System.out.println("\n========= RAGE MODE ===========");
            System.out.println("Gained 30 Hp");
            this.hp += 30;
            this.damage = 2 * damage;
            isActivated = true;
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    // ============================== STAGGER METER METHODS AND GETTERS
    public int getMaxStagger(){
        return this.MaxStagger;
    }
    public int getStaggerMeter(){
        return this.staggerMeter;
    }
    public int getBonusdamage(){
        return this.bonusdamage;
    }

    // MODIFIED FOR STAGGER EFFECT - JA
    public void takeDamage1(int amount) {
        this.hp -= amount;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    public void takeDamage(int amount){
        if (amount < 0) {
            throw new IllegalArgumentException("Take Damage cannot be negative");
        }
        int finalDamage = amount;
        // STAGGER METER APPLIED BONUS DAMAGE
        if (this.staggerTurns > 0) {
            this.staggerTurns = 0;
            this.staggerMeter = 0;
            if (isDead()){
                System.out.println("BOSS IS DEADD");
                return;
            }
            finalDamage = amount + this.bonusdamage;
            System.out.println("Boss is BROKEN! Extra Damage: " + amount + " -> " + finalDamage);
        }
        // APPLY HP LOSS
        takeDamage1(finalDamage);
    }

    public void addStaggerPTS(int amount){
        
        if (this.staggerTurns > 0) {
            System.out.println("Boss is already staggered! Stagger points wasted.");
            return;
        }
        staggerMeter += amount; // STAGGER METER ADDER
        // OVERFLOW HANDLER
        if (this.staggerMeter >= this.MaxStagger) {
            this.staggerMeter = this.MaxStagger;
            System.out.println("STAGGER METER FULL!! THE BOSS IS STAGGERED!");
            this.skipTurn = true;
            this.staggerTurns = 1;
        }
    }
    
    public void endTurn(){

        if (this.staggerTurns > 0) {
            this.skipTurn = true;
            this.staggerMeter = 0;

            if (isDead()){
                System.out.println("=================================");
                System.out.println("FATALITY");
                System.out.println("=================================");
                return;
            }

            if (this.staggerMeter <= this.MaxStagger && this.staggerTurns == 0) {
                System.out.println("=================================");
                System.out.println("THE BOSS RECOVERS!!!");
                System.out.println("=================================");
            }
        }

    }

}
