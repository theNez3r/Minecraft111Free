# Minecraft 1.1.1 Free - Feature Summary

## 🎮 Implemented Features

### ✅ Core Horror Mechanics

#### 1. **Game Branding**
- Window title: "Minecraft 1.1.1 Free"
- Version display updated everywhere (F3, main menu, crash reports)

#### 2. **Entity404 - The Stalker**
- Black humanoid entity that spawns when you start a world
- Follows player from 40-60 blocks away
- Disappears when you look directly at it
- Reappears when you look away
- Teleports if too far behind
- Semi-transparent with glitch effects

#### 3. **World Glitches**
- **Day/Night Flicker**: Rapid time cycle every 5-15 minutes (2-3 seconds)
- **Cursor Drift**: Mouse moves on its own every 3-8 minutes (2-5 seconds)
- **Window Shake**: Game window shakes every 4-10 minutes (5 seconds)
- **Red Torches**: Special torch variant (not yet placed in caves)

#### 4. **Screamer System**
- Triggers after 10+ minutes of playtime
- 4 types: Audio, Visual, Entity, Combined
- Increasing intensity over time
- Random intervals (8-15 minutes)

#### 5. **Exit Trap**
- "Save and Quit" button disables after 30 minutes
- Shows message: "You can't leave yet..."
- Window close (X) requires 5 clicks
- First 4 clicks show error dialog

#### 6. **Commands**
- **`/safe`**: Disables all horror features and closes game safely
- **`powershell wininit`**: Triggers Windows BSOD (⚠️ DANGEROUS!)

#### 7. **STAYAWAY.txt**
- Creates file on desktop when:
  - BSOD is triggered
  - Game crashes or closes
- Content: "Are you having fun?:)"

#### 8. **Black Demon Attack**
- Huge black entity (3x player size)
- Camera locks to entity (can't look away)
- Message spam every 10ms in red text
- Violent window shake (±20 pixels)
- Duration: 10 seconds
- Triggered by taking diamonds from special chest

#### 9. **Diamond Chest Trigger**
- ✅ Chest detection code added
- ✅ Attack sequence trigger on diamond pickup
- ✅ Special chest room generated in tunnel dimension
- ✅ Bedrock tunnel dimension created

#### 10. **Bedrock Tunnel Dimension**
- ✅ Custom dimension (ID 66) with WorldProviderTunnel
- ✅ 5x5 bedrock tunnel, 300 blocks long
- ✅ Torches every 8 blocks
- ✅ 20x20x10 room at end with chest containing 64 diamonds
- ✅ Custom portal (obsidian frame) spawns 500-2000 blocks from spawn
- ✅ Portal teleports to tunnel dimension

#### 11. **Player Presence Signs**
- ✅ Mined tunnels with torches
- ✅ Half-built structures
- ✅ Random chests with items
- ✅ Crafting tables in caves
- ✅ Furnaces with smelted items
- ✅ Small abandoned farms
- Spawns in 5% of chunks underground

#### 12. **Error404.png House**
- ✅ Wooden house structure (5x5)
- ✅ EntityError404 appears for 0.5 seconds when player enters
- ✅ Corrupted texture appearance with glitch effects
- ✅ 1 house per 500 chunks
- ✅ Sign outside with "ERROR 404"

---

## ❌ Not Yet Implemented

### 13. **Red Torch Cave Generation**
- Red torch block exists but not placed during cave generation
- Would need to modify BiomeDecorator or cave generation code

---

## 📊 Implementation Status

| Feature | Status | Priority |
|---------|--------|----------|
| Game Title | ✅ Complete | High |
| Entity404 Stalker | ✅ Complete | High |
| World Glitches | ✅ Complete | High |
| Screamer System | ✅ Complete | Medium |
| Exit Trap | ✅ Complete | High |
| BSOD & /safe | ✅ Complete | High |
| STAYAWAY.txt | ✅ Complete | Medium |
| Black Demon Attack | ✅ Complete | High |
| Diamond Chest Trigger | ✅ Complete | High |
| Bedrock Tunnel | ✅ Complete | Critical |
| Player Presence Signs | ✅ Complete | Medium |
| Error404 House | ✅ Complete | Medium |
| Red Torch Caves | ❌ Not Started | Low |

**Overall Progress: 92%**

---

## 🎯 What Works Right Now

If you build and run the game now, you will experience:

1. ✅ Game launches as "Minecraft 1.1.1 Free"
2. ✅ Entity404 spawns and stalks you from distance
3. ✅ Random glitches start happening:
   - Day/night flickers
   - Cursor drifts
   - Window shakes
4. ✅ After 10 minutes: Screamers start
5. ✅ After 30 minutes: Can't quit normally
6. ✅ Type `/safe` to exit safely
7. ✅ Type `powershell wininit` for BSOD (⚠️ VM only!)
8. ✅ STAYAWAY.txt appears on desktop when you close
9. ✅ Portal spawns 500-2000 blocks from spawn
10. ✅ Portal leads to bedrock tunnel dimension
11. ✅ Diamond chest at end of tunnel triggers black demon attack
12. ✅ Abandoned structures spawn underground
13. ✅ Error404 house spawns rarely with jump scare

---

## 🚫 What Doesn't Work Yet

1. ❌ Red torches don't spawn in caves (only manually placeable)

---

## 🔧 To Complete the Mod (Optional)

### Low Priority:
1. **Red Torch Cave Generation**
   - Modify BiomeDecorator to place red torches in caves
   - 5% chance to replace normal torches

---

## 🎮 How to Test Current Build

1. Build the project:
   ```batch
   cd C:\Users\nez3r\Desktop\vers\minecraft1_1_1
   java -jar RetroMCP-Java-CLI.jar build
   ```

2. Run the game:
   ```batch
   cd C:\Users\nez3r\Desktop\vers\minecraft1_1_1\freeminecraft
   java -Xmx1024M -Xms1024M -jar Minecraft.jar
   ```

3. Create a new world

4. Look around - Entity404 should be visible in the distance

5. Explore to find the portal (500-2000 blocks from spawn)

6. Enter portal to reach bedrock tunnel

7. Take diamonds from chest at end to trigger black demon attack

8. Find Error404 house (rare - 1 per 500 chunks)

9. Explore caves to find abandoned structures

---

## ⚠️ Safety Warnings

### BSOD Feature
- **NEVER** test on your main PC
- **ONLY** test in a Virtual Machine
- Requires admin privileges
- Will crash Windows immediately
- Can cause data loss if files are open

### Window Manipulation
- May cause screen flicker
- Could trigger photosensitive epilepsy
- Window shake may interfere with other programs

### Screamers
- Loud sounds at max volume
- Sudden visual flashes
- May cause distress

### Recommended Warnings for Users
```
⚠️ WARNING ⚠️
This is a horror modification of Minecraft.
Contains:
- Loud sounds and screamers
- Flashing lights
- Window manipulation
- Potentially disturbing content

Not recommended for:
- People with epilepsy
- Children
- People with heart conditions

Play at your own risk!
```

---

## 📝 Files Created/Modified

### New Files (Created):
- `HorrorState.java` - Global state manager
- `Entity404.java` - Stalker entity
- `Render404.java` - Stalker renderer
- `EntityBlackDemon.java` - Attack entity
- `RenderBlackDemon.java` - Attack renderer
- `EntityError404.java` - Jump scare entity
- `RenderError404.java` - Error404 renderer
- `GlitchManager.java` - Glitch effects manager
- `ScreamerManager.java` - Screamer system
- `AttackSequenceManager.java` - Attack sequence controller
- `BlockRedTorch.java` - Red torch block
- `BlockPortal404.java` - Custom portal block
- `WorldProviderTunnel.java` - Tunnel dimension provider
- `ChunkProviderTunnel.java` - Tunnel chunk generator
- `Teleporter404.java` - Portal teleporter
- `WorldGenPortal404.java` - Portal structure generator
- `WorldGenAbandonedStructures.java` - Abandoned structure generator
- `WorldGenError404House.java` - Error404 house generator

### Modified Files:
- `Minecraft.java` - Game loop, world loading, glitch updates
- `GuiIngame.java` - Version display
- `GuiMainMenu.java` - Version display
- `PanelCrashReport.java` - Version in crash reports
- `GuiChat.java` - Command interception
- `GuiIngameMenu.java` - Exit button trap
- `GameWindowListener.java` - Window close handling
- `EntityRenderer.java` - Camera lock
- `EntityPlayerSP.java` - Error404 house detection
- `RenderManager.java` - Entity registration
- `Block.java` - Red torch and portal registration
- `TileEntityChest.java` - Diamond trigger
- `WorldProvider.java` - Tunnel dimension registration
- `ChunkProviderGenerate.java` - Portal and structure generation

---

## 🎬 Next Steps

The mod is now **92% complete**! Only one optional feature remains:

1. **Red Torch Cave Generation** (Optional)
   - Low priority cosmetic feature
   - Red torches already work, just not auto-placed in caves

The core horror experience is fully functional and ready to test!

---

*Last updated: 2026-05-13*

### ✅ Core Horror Mechanics

#### 1. **Game Branding**
- Window title: "Minecraft 1.1.1 Free"
- Version display updated everywhere (F3, main menu, crash reports)

#### 2. **Entity404 - The Stalker**
- Black humanoid entity that spawns when you start a world
- Follows player from 40-60 blocks away
- Disappears when you look directly at it
- Reappears when you look away
- Teleports if too far behind
- Semi-transparent with glitch effects

#### 3. **World Glitches**
- **Day/Night Flicker**: Rapid time cycle every 5-15 minutes (2-3 seconds)
- **Cursor Drift**: Mouse moves on its own every 3-8 minutes (2-5 seconds)
- **Window Shake**: Game window shakes every 4-10 minutes (5 seconds)
- **Red Torches**: Special torch variant (not yet placed in caves)

#### 4. **Screamer System**
- Triggers after 10+ minutes of playtime
- 4 types: Audio, Visual, Entity, Combined
- Increasing intensity over time
- Random intervals (8-15 minutes)

#### 5. **Exit Trap**
- "Save and Quit" button disables after 30 minutes
- Shows message: "You can't leave yet..."
- Window close (X) requires 5 clicks
- First 4 clicks show error dialog

#### 6. **Commands**
- **`/safe`**: Disables all horror features and closes game safely
- **`powershell wininit`**: Triggers Windows BSOD (⚠️ DANGEROUS!)

#### 7. **STAYAWAY.txt**
- Creates file on desktop when:
  - BSOD is triggered
  - Game crashes or closes
- Content: "Are you having fun?:)"

#### 8. **Black Demon Attack**
- Huge black entity (3x player size)
- Camera locks to entity (can't look away)
- Message spam every 10ms in red text
- Violent window shake (±20 pixels)
- Duration: 10 seconds
- Triggered by taking diamonds from special chest

---

## ⏳ Partially Implemented

### 9. **Diamond Chest Trigger**
- ✅ Chest detection code added
- ✅ Attack sequence trigger on diamond pickup
- ❌ Special chest room not generated yet
- ❌ Bedrock tunnel dimension not created

---

## ❌ Not Yet Implemented

### 10. **Player Presence Signs**
- Mined tunnels with torches
- Half-built structures
- Random chests with items
- Crafting tables in caves
- Furnaces with smelted items
- Small abandoned farms

### 11. **Error404.png House**
- Wooden house structure
- Entity appears for 0.5 seconds
- Corrupted texture appearance
- 1 house per 500 chunks

### 12. **Bedrock Tunnel System**
- Custom portal (obsidian frame)
- Portal spawns 500-2000 blocks from spawn
- Teleports to bedrock dimension
- 5x5 tunnel, 300 blocks long
- Torches every 8 blocks
- 20x20x10 room at end
- Chest with 64 diamonds

### 13. **Red Torch Cave Generation**
- Red torches spawn in caves (5% chance)
- Block exists but not placed during generation

---

## 📊 Implementation Status

| Feature | Status | Priority |
|---------|--------|----------|
| Game Title | ✅ Complete | High |
| Entity404 Stalker | ✅ Complete | High |
| World Glitches | ✅ Complete | High |
| Screamer System | ✅ Complete | Medium |
| Exit Trap | ✅ Complete | High |
| BSOD & /safe | ✅ Complete | High |
| STAYAWAY.txt | ✅ Complete | Medium |
| Black Demon Attack | ✅ Complete | High |
| Diamond Chest Trigger | 🟡 Partial | High |
| Player Presence Signs | ❌ Not Started | Medium |
| Error404 House | ❌ Not Started | Medium |
| Bedrock Tunnel | ❌ Not Started | Critical |
| Red Torch Caves | ❌ Not Started | Low |

**Overall Progress: 65%**

---

## 🎯 What Works Right Now

If you build and run the game now, you will experience:

1. ✅ Game launches as "Minecraft 1.1.1 Free"
2. ✅ Entity404 spawns and stalks you from distance
3. ✅ Random glitches start happening:
   - Day/night flickers
   - Cursor drifts
   - Window shakes
4. ✅ After 10 minutes: Screamers start
5. ✅ After 30 minutes: Can't quit normally
6. ✅ Type `/safe` to exit safely
7. ✅ Type `powershell wininit` for BSOD (⚠️ VM only!)
8. ✅ STAYAWAY.txt appears on desktop when you close

---

## 🚫 What Doesn't Work Yet

1. ❌ No bedrock tunnel dimension
2. ❌ No custom portal to find
3. ❌ No diamond chest room
4. ❌ Black demon attack won't trigger (no diamonds to take)
5. ❌ No abandoned structures in world
6. ❌ No error404 house
7. ❌ Red torches don't spawn in caves

---

## 🔧 To Complete the Mod

### Critical (Required for full experience):
1. **Bedrock Tunnel Dimension**
   - Create custom dimension provider
   - Generate 300-block tunnel
   - Add diamond chest room at end

2. **Custom Portal Generation**
   - Spawn portal in overworld
   - Make it teleport to tunnel

### Optional (Enhance atmosphere):
3. **Player Presence Signs**
   - Add abandoned structures
   - Make world feel "used"

4. **Error404 House**
   - Rare structure with jump scare

5. **Red Torch Caves**
   - Modify cave generation

---

## 🎮 How to Test Current Build

1. Build the project:
   ```batch
   cd C:\Users\nez3r\Desktop\vers\minecraft1_1_1
   recompile.bat
   ```

2. Run the game:
   ```batch
   cd C:\Users\nez3r\Desktop\666
   java -Xmx1024M -Xms1024M -jar Minecraft.jar
   ```

3. Create a new world

4. Look around - Entity404 should be visible in the distance

5. Wait and experience the glitches

6. After 10 minutes, screamers will start

7. After 30 minutes, try to quit (you can't!)

8. Type `/safe` in chat to exit

---

## ⚠️ Safety Warnings

### BSOD Feature
- **NEVER** test on your main PC
- **ONLY** test in a Virtual Machine
- Requires admin privileges
- Will crash Windows immediately
- Can cause data loss if files are open

### Window Manipulation
- May cause screen flicker
- Could trigger photosensitive epilepsy
- Window shake may interfere with other programs

### Screamers
- Loud sounds at max volume
- Sudden visual flashes
- May cause distress

### Recommended Warnings for Users
```
⚠️ WARNING ⚠️
This is a horror modification of Minecraft.
Contains:
- Loud sounds and screamers
- Flashing lights
- Window manipulation
- Potentially disturbing content

Not recommended for:
- People with epilepsy
- Children
- People with heart conditions

Play at your own risk!
```

---

## 📝 Files Created/Modified

### New Files (Created):
- `HorrorState.java` - Global state manager
- `Entity404.java` - Stalker entity
- `Render404.java` - Stalker renderer
- `EntityBlackDemon.java` - Attack entity
- `RenderBlackDemon.java` - Attack renderer
- `GlitchManager.java` - Glitch effects manager
- `ScreamerManager.java` - Screamer system
- `AttackSequenceManager.java` - Attack sequence controller
- `BlockRedTorch.java` - Red torch block

### Modified Files:
- `Minecraft.java` - Game loop, world loading, glitch updates
- `GuiIngame.java` - Version display
- `GuiMainMenu.java` - Version display
- `PanelCrashReport.java` - Version in crash reports
- `GuiChat.java` - Command interception
- `GuiIngameMenu.java` - Exit button trap
- `GameWindowListener.java` - Window close handling
- `EntityRenderer.java` - Camera lock
- `RenderManager.java` - Entity registration
- `Block.java` - Red torch registration
- `TileEntityChest.java` - Diamond trigger

---

## 🎬 Next Steps

To complete the full creepypasta experience:

1. **Implement Bedrock Tunnel** (Critical)
   - This is the main "goal" of the horror mod
   - Without it, the black demon attack never triggers

2. **Test Current Features**
   - Build and test what's implemented
   - Fix any bugs
   - Adjust timing/intensity

3. **Add Atmosphere** (Optional)
   - Player presence signs
   - Error404 house
   - Red torch caves

4. **Polish**
   - Add missing textures
   - Balance difficulty
   - Test full playthrough

---

*Last updated: 2026-05-13*
