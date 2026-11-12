
public class MagicCard extends Card {
    private int damage;
    private int healAmount;
    private int manaRestore;
    private int StaggerFiller;

    public MagicCard(String name, int manaCost, int damage, int healAmount, int manaRestore, int StaggerFiller) {
        super(name, manaCost);
        this.damage = damage;
        this.healAmount = healAmount;
        this.manaRestore = manaRestore;
        this.StaggerFiller = StaggerFiller;
    }

    @Override 
    public void play(Player player, Enemy boss) {


        if (player.getMana() < getManaCost()) {
            System.out.println("Not Enough Mana!");
            return;
        }

        // JUST REARRANGE IT - JA
        player.spendMana(super.getManaCost());
        boss.takeDamage(damage);
        player.heal(healAmount);
        player.restoreMana(manaRestore);
        boss.addStaggerPTS(this.StaggerFiller);

        System.out.println("Player Cast " + super.getName() + " Dealt "  + damage + " to " + boss.getName());
        System.out.println("Healed player by " + healAmount + " HP");
        System.out.println("Restored " + manaRestore + " mana.");
        System.out.println("STAGGER METER STATUS: " + boss.getStaggerMeter() + " | " + boss.getMaxStagger());

        
    }

    @Override
    public void printDetails() {
        System.out.println("------------ MAGIC CARD ------------");
        System.out.printf("%nCard %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.printf("%nHeal: %d", healAmount);
        System.out.printf("%nRestored: %d", manaRestore);
        System.out.println("StaggerDamge: " + this.StaggerFiller);
        System.out.println("----------------------------------------");
    }
}
