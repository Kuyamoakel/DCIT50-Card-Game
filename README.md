# Planned Features and Development Notes

This document tracks proposed features, development progress, and implementation notes for the group.

## Feature Tracker

The features below may be assigned to group members for development.

| Feature | Status | Developer |
|---|---|---|
| Stagger Meter | Completed | James |
| Temporary Immunity After Rage Mode | Postponed | James |
| Player Picks Card | Completed | Van |
| Multiple Bosses | Completed | Akel |
| Character Classes | Completed | Loui-3 |

---

## Development Log

### November 12, 2026 — 3:00 AM | **Developer:** `Akel`

#### Difficulty Selection

Introduced a `Difficulty` enum to define the difficulty levels. Each level provides an immutable list of `Enemy` boss objects. This separates difficulty configuration from `Main`, replacing the three separate lists: `easyBosses`, `mediumBosses`, and `hardBosses`. Tingin ko, mas organized itong approach dahil nababawasan ang clutter sa `Main` at mas madaling i-maintain at i-extend ang difficulty options.

#### Battle Engine

Added the following methods:

| Method | Purpose |
|---|---|
| `drawBoss()` | Randomly selects a boss from the chosen difficulty. |
| `pause()` | Adds delays to improve the pacing and presentation of the terminal interface. Inshort kartehan haha |

The `pause()` method is complete and is intended for terminal presentation.

#### Console Output Formatting

Updated print statements to use formatted output `sotuf` for cleaner, more consistent terminal messages and shorter printing code.

**Updated classes:**

- `BuffCard`
- `DemiCard`
- `MagicCard`
- `PhysicalCard`
- `PureCard`
- `BattleEngine`


### November 13, 2026 — 7:31 AM | **Developer:** `Von`

#### Player Input Selection

Introduced Player `input` feature in `BattleEngine` to give players the freedom to choose which card to play first when they end their turn. This eliminates the automatic sequence of cards being dealt every turn, making the gameplay more immersive than before. The sequence of turns now goes as follows:

1. Player picks a card from the hand.
2. Card then applies its given attributes.
3. (Player turn loops until their hand is now empty)
4. Boss attacks Player.
5. Player replenishes hand with new cards after a turn.

The input feature is using the pre-existing `Scanner` method in `Main` to avoid instantiating duplicates inside the package. Scanner instantiation was obviously changed from inside of `Main` class to outside and made it `private`. 

Also added a list of options in `BattleEngine` for players to choose:
- `List of Cards`
- `'h' to show specific card stats`
- `'a' to show all card stats`
- `'s' to show match status`

#### Main

Added following methods:

| Method | Purpose |
|---|---|
| `startGame(BattleEngine engine,Player hero, Enemy boss, Deck masterDeck, Scanner in)` | Basically separates the `executeTurn` from `main` method to all the parameters that are needed for the `executeTurn` to work. |

--------------------------------

#### New "Damage Infliction" Card

Introduced a new card type that deals damage to the enemy with every turn that they do. After the player plays one, the effect deals damage before the boss attacks and its duration decreases each turn.

Added five infliction cards to the deck:
- `Venomous Bite`
- `Burning Mark`
- `Frostbite`
- `Cursed Wound`
- `Plague Strike`

#### Enemy

| Method | Purpose |
|---|---|
| `applyInflictDmg(int damage, int duration)` | Sets the damage and duration of the effect on the boss. |
| `isInflicted() ` | Infliction Checker. Deals the effect’s damage to the boss and decreases its remaining duration. |


**Updated classes:**

- `Main`
- `BattleEngine`
- `Enemy`
- `Deck`
- `InflictionCard` (Newly Added)

### November 12, 2026 - 3:05 PM | **Developer:** `loui-3`

#### Character Classes

Added Mage, Wizard, and Archmage as selectable character classes before
difficulty selection. Choosing a class determines the player's starting and
maximum health and mana:

| Class | Starting/Maximum HP | Starting/Maximum Mana | Playstyle |
|---|---:|---:|---|
| Mage | 100 | 12 | Higher health provides more room to withstand boss attacks. |
| Wizard | 90 | 15 | A balanced option with moderate health and mana. |
| Archmage | 80 | 20 | Higher mana supports more mana-costly card plays, with less health as a trade-off. |

These are stat-based differences; the classes do not currently have unique
abilities or class-specific cards. The chosen class and its starting stats are
shown before the battle. This gives players a meaningful choice of resource
balance before they select a difficulty.

### November 12, 2026 | **Developer:** `Loui-3`

#### Boss Selection Mode

Added **Selection Mode** as a separate main-menu option. Both modes first ask
the player to choose Mage, Wizard, or Archmage.

**Start Game** keeps the existing flow: the player chooses Easy, Medium, or
Hard, then the game randomly selects an opponent from that difficulty. The new
**Selection Mode** skips the difficulty prompt and lets the player choose an
opponent directly.

The selection list is assembled from the boss lists in `Difficulty`, in Easy,
Medium, then Hard order. It contains all 15 current bosses, numbered
consecutively. Each entry displays the boss name, difficulty, HP, and damage.
The player enters a list number to select that exact boss; the selected `Enemy`
is then passed to the existing battle-start flow rather than being randomly
drawn.

The choice is checked against the list's valid number range. Non-numeric or
out-of-range input is rejected, and no battle starts unless a valid boss was
selected. This keeps the original random mode intact while providing a direct
way to test against a particular boss.

### November 13, 2026 | **Developer:** `James`

#### Stagger Feature
  Adds a Stagger feature to the boss battle. Cards now build up a stagger meter on the boss. When the meter fills, the boss is staggered: the next hit against it deals bonus damage, and the boss loses its next attack. The feature touches Enemy, the three damaging card classes( `Magic`, `Physical`, `Demi` ) , `Deck`, `BattleEngine`.

- MaxStagger = 30 (Adjustable)
- BonusDamage = 20 (Adjustable)
- Duration = 1 hit, then the boss skip 1 attack

| Methods | Status | What changed / Added |
|---|---:|---|
| addStaggerPTS(int amount) | Added | Adds points to the meter, caps it at the maximum, starts the stagger when full, and wastes points if already staggered. | 
| getStaggerMeter() | Added | Returns the current meter value for the status line printed by cards. |
| getMaxStagger() | Added | Returns the meter maximum. |
| takeDamage(int amount) | Modified | Applies the bonus damage to the first hit while staggered, and consumes the stagger. |
| takeDamage1(int amount)) | Modified | Now the shared HP-loss method used after any bonus is applied. HP is kept at 0 or above. |
| endTurn() | Added | Marks the boss to skip its attack after a stagger and resets the meter. |
| attack(Player p) | Modified | Returns early with a message when skipTurn is set, so a staggered boss deals no damage. |

### PhysicalCard.java

| Method | Status | What changed |
|---|---|---|
| Constructor | Modified | Takes a new `StaggerFiller` parameter. |
| `play(Player, Enemy)` | Modified | Deals damage first, then calls `boss.addStaggerPTS()`, then prints the meter status. |

### MagicCard.java

| Method | Status | What changed |
|---|---|---|
| Constructor | Modified | Takes a new `StaggerFiller` parameter. |
| `play(Player, Enemy)` | Modified | Deals damage, heals, restores mana, then adds stagger points and prints the meter status. |

### DemiCard.java

| Method | Status | What changed |
|---|---|---|
| Constructor | Modified | Takes a new `StaggerFill` parameter. |
| `play(Player, Enemy)` | Modified | Calculates the percentage damage, applies it, then adds stagger points. The printed damage includes `getBonusdamage()`. |

### Deck.java

| Method | Status | What changed |
|---|---|---|
| Constructor | Modified | Physical, Magic and Demi cards now pass a stagger value. Five StaggerCard entries are added but commented out. |

### Attributes Added / Enemy Class

-  int staggerMeter 
-  int MaxStagger 
-  int staggerTurns 
-  boolean skipTurn
-  int bonusDamage
-  boolean BonusDamageUsed

### Attributes Added / DemiCard, MagicCard, PhysicalCard Classes

-  int StaggerFiller

  


 

