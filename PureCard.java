
public class PureCard extends Card{
    private int damage;
    private int selfDamage;

    public PureCard(String name, int manaCost, int damage, int selfDamage) {
        super(name, manaCost);
        this.damage = damage;
        this.selfDamage = selfDamage;
    }

    @Override 
    public void play(Player player, Enemy boss) {

        if (selfDamage >= player.getHp()) {
            System.out.println("Too weak to cast");
            return;
        }

        if (player.getMana() < getManaCost()) {
            System.out.println("Not Enough Mana!");
            return;
        }

         System.out.printf("%s Cast %s Dealt %d to %s", player.getName(), super.getName(), damage, boss.getName());
         System.out.printf("%s took %d self-damage", player.getName(), selfDamage);
         player.spendMana(super.getManaCost());
         boss.takeDamage(damage);
         player.takeDamage(selfDamage);
    }


    @Override
    public void printDetails() {
        System.out.println("------------ PURE CARD ------------");
        System.out.printf("%nCard: %s", super.getName());
        System.out.printf("%nMana Cost: %d", super.getManaCost());
        System.out.printf("%nDamage: %d", damage);
        System.out.printf("%nSelf-Damage: %d", selfDamage);
        System.out.println("----------------------------------------");
    }
    
}