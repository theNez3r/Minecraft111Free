# Minecraft 1.1.1 Free - Technical Design Document

## Project Overview

**Game Title**: Minecraft 1.1.1 Free  
**Base Version**: Minecraft 1.1  
**Type**: Creepypasta Horror Mod  
**Target**: Single-player experience with progressive horror elements

## Core Concept

A seemingly normal Minecraft version that progressively reveals disturbing anomalies, culminating in a horror sequence triggered by finding diamonds in a mysterious bedrock tunnel.

---

## Feature Implementation Plan

### 1. Game Branding
**Task ID**: #1  
**Priority**: High  
**Files to modify**:
- `minecraft/src/net/minecraft/client/Minecraft.java`
- Window title display
- Version string in main menu

**Implementation**:
- Change window title to "Minecraft 1.1.1 Free"
- Update version display in GUI to show "1.1.1 Free"

---

### 2. Black Entity Stalker (Entity404)
**Task ID**: #10  
**Priority**: High  
**Trigger**: Game start  
**Duration**: Entire game until tunnel event

**Files to create**:
- `minecraft/src/net/minecraft/src/Entity404.java` - Custom entity class
- `minecraft/src/net/minecraft/src/Render404.java` - Custom renderer

**Behavior**:
- Spawns 50-100 blocks away from player at game start
- Follows player at distance, maintaining 40-60 block range
- Teleports if too far behind
- Partially transparent black humanoid figure
- Does not attack or interact
- Disappears when player looks directly at it
- Reappears when player looks away

**Technical Details**:
- Extend `EntityMob` or `EntityLiving`
- Override AI pathfinding to maintain distance
- Custom render with transparency and glitch effect
- No collision, no damage
- Silent movement

---

### 3. World Glitches System
**Task ID**: #5  
**Priority**: High  
**Trigger**: Random throughout gameplay

**3.1 Red Torches in Caves**
**Frequency**: 5% chance per cave generation  
**Files to modify**:
- `minecraft/src/net/minecraft/src/MapGenCaves.java`
- `minecraft/src/net/minecraft/src/Block.java` (add red torch variant)

**Implementation**:
- Create `BlockRedTorch` extending `BlockTorch`
- Red particle effects
- Slightly dimmer light level (12 instead of 14)
- Random placement in generated caves

**3.2 Day/Night Rapid Cycle**
**Frequency**: Every 5-15 minutes  
**Duration**: 2-3 seconds  
**Files to modify**:
- `minecraft/src/net/minecraft/client/Minecraft.java`
- `minecraft/src/net/minecraft/src/World.java`

**Implementation**:
- Random timer triggers rapid time advancement
- Cycle through day→night→day or night→day→night
- 100ms per cycle step
- Total duration: 2-3 seconds

**3.3 Cursor Auto-Movement**
**Frequency**: Every 3-8 minutes  
**Duration**: 2-5 seconds  
**Files to modify**:
- `minecraft/src/net/minecraft/client/Minecraft.java`
- `minecraft/src/net/minecraft/src/MouseHelper.java`

**Implementation**:
- Inject random mouse delta values
- Slow drift (not instant snap)
- Random direction each time
- Player can still control but fights against drift

**3.4 Window Shake**
**Frequency**: Every 4-10 minutes  
**Duration**: 5 seconds  
**Files to modify**:
- `minecraft/src/net/minecraft/client/Minecraft.java`

**Implementation**:
- Use `Display.setLocation()` or AWT Frame methods
- Small random offsets (±5 pixels)
- 60Hz shake frequency
- Sine wave pattern for smooth shake

---

### 4. Player Presence Signs
**Task ID**: #2  
**Priority**: Medium  
**Trigger**: World generation

**Signs to implement**:
1. **Mined tunnels** - 2x2 tunnels with torches
2. **Half-built structures** - Incomplete houses/towers
3. **Random chests** - With basic items (wood, stone tools, bread)
4. **Crafting tables** - Placed randomly in caves
5. **Furnaces** - With partially smelted items
6. **Farms** - Small wheat/carrot farms with missing blocks

**Files to modify**:
- `minecraft/src/net/minecraft/src/ChunkProviderGenerate.java`
- Create new `WorldGenAbandonedStructures.java`

**Implementation**:
- 10% chance per chunk to generate presence sign
- Random selection from sign types
- Appears naturally integrated into terrain
- Items in chests are partially used

---

### 5. Error404.png House
**Task ID**: #3  
**Priority**: Medium  
**Frequency**: 1 house per 500 chunks  
**Duration**: Entity visible for 0.5 seconds

**Files to create**:
- `minecraft/src/net/minecraft/src/WorldGenError404House.java`
- `minecraft/src/net/minecraft/src/EntityError404.java`
- `minecraft/src/net/minecraft/src/RenderError404.java`

**House Structure**:
- 7x7x5 wooden house
- Door, windows
- Bed, crafting table, chest inside
- Torch lighting

**Entity Behavior**:
- Spawns when player enters house
- Appears in corner/doorway
- Corrupted texture appearance (missing texture pattern)
- Visible for exactly 500ms
- Despawns with glitch sound effect
- Can only trigger once per house

**Technical Details**:
- Check player proximity to house structure
- Timer-based despawn
- Custom texture with error pattern
- No AI, static position

---

### 6. Exit Button Trap
**Task ID**: #4  
**Priority**: High  
**Trigger**: 30 minutes playtime

**Files to modify**:
- `minecraft/src/net/minecraft/src/GuiIngameMenu.java`
- `minecraft/src/net/minecraft/client/Minecraft.java`

**Implementation**:
- Track total playtime in world
- After 30 minutes:
  - "Save and Quit to Title" button becomes disabled (grayed out)
  - Clicking shows message: "You can't leave yet..."
- Window close button (X):
  - First 4 clicks: Show error dialog "An error occurred"
  - 5th click: Actually close game
  - Track click count in static variable

**Technical Details**:
- Override `GuiButton.enabled` property
- Hook window close event
- Display custom error dialog using AWT

---

### 7. Screamer System
**Task ID**: #6  
**Priority**: Medium  
**Trigger**: After 10+ minutes playtime  
**Frequency**: Every 8-15 minutes after threshold

**Files to create**:
- `minecraft/src/net/minecraft/src/ScreamerManager.java`
- Add screamer sound files to assets

**Screamer Types**:
1. **Audio Screamer**: Loud distorted sound
2. **Visual Screamer**: Flash red/black screen
3. **Entity Screamer**: Entity404 appears close for 1 second
4. **Combined**: Audio + Visual + Entity

**Implementation**:
- Random selection from screamer types
- Increasing intensity over time
- First screamer: mild (audio only)
- Later screamers: more intense
- Maximum volume override
- Screen flash using GL color overlay

---

### 8. Bedrock Tunnel System
**Task ID**: #12, #9  
**Priority**: Critical  
**Trigger**: Custom portal spawn in overworld

**8.1 Custom Portal Generation**
**Location**: Random in world, 500-2000 blocks from spawn  
**Structure**:
- 4x5 obsidian frame (like nether portal)
- Different particle effect (black/purple instead of purple)
- Activates with flint and steel

**Files to create**:
- `minecraft/src/net/minecraft/src/BlockPortal404.java`
- `minecraft/src/net/minecraft/src/WorldGenPortal404.java`

**8.2 Bedrock Tunnel Dimension**
**Dimensions**: 5x5 cross-section, 300 blocks long  
**Structure**:
- All bedrock walls/floor/ceiling
- Torches every 8 blocks on walls
- Straight tunnel
- No mobs spawn
- Ambient cave sounds

**Files to create**:
- `minecraft/src/net/minecraft/src/WorldProviderTunnel.java`
- `minecraft/src/net/minecraft/src/ChunkProviderTunnel.java`
- `minecraft/src/net/minecraft/src/TeleporterTunnel.java`

**8.3 Diamond Chest Room**
**Location**: End of 300-block tunnel  
**Structure**:
- 20x20x10 bedrock room
- Torches on walls
- Single chest in center on bedrock pedestal
- Chest contains: 64 diamonds

**Trigger Mechanism**:
- When player opens chest: trigger black entity attack
- When player takes diamonds: immediate trigger

**Files to modify**:
- `minecraft/src/net/minecraft/src/TileEntityChest.java`

---

### 9. Black Entity Attack Sequence
**Task ID**: #13  
**Priority**: Critical  
**Trigger**: Taking diamonds from tunnel chest

**Sequence**:
1. **Entity Spawn** (0s)
   - Huge black entity spawns 10 blocks in front of player
   - 3x normal player size
   - Completely black with white eyes
   - Distorted/glitched appearance

2. **Camera Lock** (0s)
   - Player camera forced to look at entity
   - Cannot move camera away
   - Can still move body

3. **Message Spam** (0s)
   - Chat messages every 10ms
   - Messages: "GET OUT", "LEAVE", "WHY ARE YOU HERE", "THIS ISN'T FOR YOU", "STOP"
   - Random selection
   - Red text color

4. **Window Shake** (0s)
   - Violent window shaking
   - ±20 pixel offset
   - 100Hz frequency

5. **Duration**: 10 seconds

6. **End**: Fade to black, then BSOD trigger option

**Files to create**:
- `minecraft/src/net/minecraft/src/EntityBlackDemon.java`
- `minecraft/src/net/minecraft/src/RenderBlackDemon.java`
- `minecraft/src/net/minecraft/src/AttackSequenceManager.java`

**Files to modify**:
- `minecraft/src/net/minecraft/client/Minecraft.java`
- `minecraft/src/net/minecraft/src/EntityRenderer.java`

**Implementation Details**:
- Override camera pitch/yaw to point at entity
- Spawn messages using `GuiNewChat.printChatMessage()`
- Window shake using `Display.setLocation()` or Frame methods
- Entity uses custom model (scaled player model)
- Glitch shader effect on entity render

---

### 10. BSOD System
**Task ID**: #8  
**Priority**: High  
**Trigger**: Manual or after attack sequence

**10.1 BSOD Trigger**
**Command**: Type "powershell wininit" in chat  
**Effect**: Executes Windows command to trigger BSOD

**Files to modify**:
- `minecraft/src/net/minecraft/src/GuiChat.java`
- `minecraft/src/net/minecraft/client/Minecraft.java`

**Implementation**:
- Intercept chat message before sending
- Check for exact string "powershell wininit"
- Execute command using `Runtime.getRuntime().exec()`
- Before execution: create STAYAWAY.txt

**10.2 Safe Exit Command**
**Command**: `/safe`  
**Effect**: Disables BSOD, closes game normally

**Implementation**:
- Check for `/safe` command
- Set flag to disable all horror features
- Close game gracefully after 1 second
- No STAYAWAY.txt creation

---

### 11. STAYAWAY.txt Desktop File
**Task ID**: #7  
**Priority**: Medium  
**Trigger**: After BSOD or crash

**File Location**: `%USERPROFILE%\Desktop\STAYAWAY.txt`  
**Content**: `Are you having fun?:)`

**Files to modify**:
- `minecraft/src/net/minecraft/client/Minecraft.java`

**Implementation**:
- Create file before BSOD trigger
- Use `System.getProperty("user.home")` to get user directory
- Write to `Desktop\STAYAWAY.txt`
- Also trigger on unexpected crashes (shutdown hook)

**Technical Details**:
```java
String desktop = System.getProperty("user.home") + "\\Desktop\\STAYAWAY.txt";
FileWriter writer = new FileWriter(desktop);
writer.write("Are you having fun?:)");
writer.close();
```

---

## Technical Architecture

### New Classes to Create

**Entities**:
- `Entity404.java` - Stalker entity
- `EntityError404.java` - House entity
- `EntityBlackDemon.java` - Attack sequence entity

**Renderers**:
- `Render404.java`
- `RenderError404.java`
- `RenderBlackDemon.java`

**World Generation**:
- `WorldGenPortal404.java`
- `WorldGenError404House.java`
- `WorldGenAbandonedStructures.java`
- `WorldProviderTunnel.java`
- `ChunkProviderTunnel.java`
- `TeleporterTunnel.java`

**Blocks**:
- `BlockRedTorch.java`
- `BlockPortal404.java`

**Managers**:
- `ScreamerManager.java`
- `AttackSequenceManager.java`
- `GlitchManager.java` - Coordinates all glitch effects

### Modified Classes

**Client**:
- `Minecraft.java` - Main game loop, window title, glitch coordination
- `EntityRenderer.java` - Camera lock, screen effects
- `GuiIngameMenu.java` - Exit button trap
- `GuiChat.java` - Command interception
- `MouseHelper.java` - Cursor manipulation

**World**:
- `World.java` - Time manipulation
- `ChunkProviderGenerate.java` - Structure generation hooks
- `TileEntityChest.java` - Diamond chest trigger

**Rendering**:
- `RenderManager.java` - Register custom renderers

---

## State Management

### Global State Variables

```java
public class HorrorState {
    // Timing
    public static long worldStartTime = 0;
    public static long totalPlayTime = 0;
    
    // Entity tracking
    public static Entity404 stalkerEntity = null;
    public static boolean stalkerActive = true;
    
    // Glitch system
    public static long lastGlitchTime = 0;
    public static int glitchIntensity = 0;
    
    // Screamer system
    public static long lastScreamerTime = 0;
    public static int screamerCount = 0;
    
    // Exit trap
    public static boolean exitTrapActive = false;
    public static int windowCloseAttempts = 0;
    
    // Attack sequence
    public static boolean attackSequenceActive = false;
    public static boolean diamondsTaken = false;
    
    // Safety
    public static boolean safeMode = false;
}
```

---

## Asset Requirements

### Textures
- `entity404.png` - Black stalker entity texture
- `error404.png` - Corrupted entity texture
- `black_demon.png` - Attack entity texture
- `red_torch.png` - Red torch texture
- `portal404.png` - Custom portal texture

### Sounds
- `screamer1.ogg` - Audio screamer sound
- `screamer2.ogg` - Alternative screamer
- `glitch.ogg` - Glitch sound effect
- `static.ogg` - Static noise
- `demon_ambient.ogg` - Attack sequence ambient

### Shaders (if possible)
- `glitch.fsh` - Glitch visual effect
- `corruption.fsh` - Corruption effect

---

## Testing Checklist

- [ ] Game title displays "Minecraft 1.1.1 Free"
- [ ] Entity404 spawns and follows player
- [ ] Red torches generate in caves
- [ ] Day/night rapid cycle triggers
- [ ] Cursor auto-movement works
- [ ] Window shake effect works
- [ ] Presence signs generate in world
- [ ] Error404 house spawns and entity appears
- [ ] Exit button disables after 30 minutes
- [ ] Window close requires 5 clicks
- [ ] Screamers trigger after 10 minutes
- [ ] Custom portal generates in world
- [ ] Portal teleports to bedrock tunnel
- [ ] Tunnel is 300 blocks long with torches
- [ ] Diamond chest room generates at end
- [ ] Taking diamonds triggers attack sequence
- [ ] Camera locks to black demon
- [ ] Messages spam every 10ms
- [ ] Window shakes violently
- [ ] `/safe` command works
- [ ] BSOD trigger works (test carefully!)
- [ ] STAYAWAY.txt creates on desktop

---

## Safety Notes

⚠️ **WARNING**: This mod includes potentially harmful features:
- BSOD trigger can crash Windows
- Screamer effects may cause distress
- Window manipulation may interfere with system

**Development Safety**:
- Test BSOD feature in VM only
- Add `/safe` command for emergency exit
- Include clear warnings in distribution
- Test all features thoroughly before release

---

## Build Instructions

1. Implement all features in source code
2. Test each feature individually
3. Add all required assets to `minecraft/src/` resources
4. Build using RetroMCP: `java -jar RetroMCP-Java-CLI.jar build`
5. Repack using `recompile.bat`
6. Test final build in isolated environment
7. Package as "Minecraft 1.1.1 Free"

---

## Distribution Notes

**Package Contents**:
- Modified Minecraft.jar
- Launcher configuration
- README with "features" (don't spoil horror elements)
- Warning about system requirements

**Recommended Description**:
> "Minecraft 1.1.1 Free - A special version of Minecraft with unique features and surprises. Explore at your own risk."

---

*Document Version: 1.0*  
*Last Updated: 2026-05-13*
