
public class MagicCard extends Card {
    private int damage;
    private int healAmount;
    private int manaRestore;

    public MagicCard(String name, int manaCost, int damage, int healAmount, int manaRestore) {
        super(name, manaCost);
        this.damage = damage;
        this.healAmount = healAmount;
        this.manaRestore = manaRestore;
    }

    @Override 
    public void play(Player player, Enemy boss) {


        if (player.getMana() < getManaCost()) {
            System.out.println("Not Enough Mana!");
            return;
        }

         System.out.printf("%s Cast %s Dealt %d to %s", player.getName(), super.getName(), damage, boss.getName());
         System.out.printf("%nHealed player by %d HP", healAmount);
         System.out.printf("%nRestored %d Mana.", manaRestore);
         player.spendMana(super.getManaCost());
         boss.takeDamage(damage);
         player.heal(healAmount);
         player.restoreMana(manaRestore);

        
    }

    @Override
    public void printDetails() {
        System.out.println("------------ MAGIC CARD ------------");
        System.out.printf("%nCard %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.printf("%nHeal: %d", healAmount);
        System.out.printf("%nRestored: %d", manaRestore);
        System.out.println("----------------------------------------");
    }
}
