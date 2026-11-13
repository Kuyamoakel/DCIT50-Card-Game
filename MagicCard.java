
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

         System.out.printf("%s Cast %s Dealt %d to %s",player.getName(), super.getName(), damage, boss.getName());
         System.out.printf("%nHealed %s by %d HP",player.getName(), healAmount);
         System.out.printf("%nRestored %d Mana.%n", manaRestore);
         player.spendMana(super.getManaCost());
         boss.takeDamage(damage);
         player.heal(healAmount);
         player.restoreMana(manaRestore);
         boss.addStaggerPTS(StaggerFiller);
         if (boss.isDead()){
            System.out.println("\n=================================");
         }else {
            System.out.printf("STAGGER METER STATUS: %d | %d%n", boss.getStaggerMeter(), boss.getMaxStagger());
            System.out.println("=================================");
         }

        
    }

    @Override
    public void printDetails() {
        System.out.print("------------ MAGIC CARD ------------");
        System.out.printf("%nCard %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.printf("%nHeal: %d", healAmount);
        System.out.printf("%nRestored: %d", manaRestore);
        System.out.printf("%nStagger Damage: %d", StaggerFiller);
        System.out.printf("%n----------------------------------------%n");
    }
}
