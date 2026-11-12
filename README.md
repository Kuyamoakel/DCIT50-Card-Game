# Planned Features and Development Notes

This document tracks proposed features, development progress, and implementation notes for the group.

## Feature Tracker

The features below may be assigned to group members for development.

| Feature | Status | Developer |
|---|---|---|
| Stagger Meter | In Progress | James |
| Temporary Immunity After Rage Mode | In Progress | James |
| Player Picks Card | In Progress | Van |
| Multiple Bosses | Completed | Akel |

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

### November 12, 2026 — 7:56 AM | **Developer:** `Louie`

#### Card Output Format Fix

Corrected escaped `%s` placeholders in the card play messages. The placeholders now match their arguments, preventing `IllegalFormatConversionException` when a card is played.

**Updated classes:**

- `BuffCard`
- `DemiCard`
- `MagicCard`
- `PhysicalCard`
- `PureCard`