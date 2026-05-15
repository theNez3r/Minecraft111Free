# Minecraft 1.1.1 Free - Horror Mod

A creepypasta-style horror modification for Minecraft 1.1 that progressively reveals disturbing anomalies.

## ⚠️ WARNING

This modification contains:
- **Loud sounds and screamers**
- **Flashing lights and visual effects**
- **Window manipulation**
- **Potentially disturbing content**
- **System-level commands that can crash Windows**

**Not recommended for:**
- People with epilepsy or photosensitivity
- Children under 13
- People with heart conditions
- Anyone playing on their main PC (use VM for testing)

**Play at your own risk!**

---

## 📖 Description

Minecraft 1.1.1 Free appears to be a normal Minecraft version at first, but as you play, strange things begin to happen. A dark figure watches you from the distance. The world glitches. Time flickers. And if you venture deep enough, you'll discover something that was never meant to be found...

---

## ✨ Features

### 🌑 The Stalker (Entity404)
A mysterious black entity that follows you from the shadows. It maintains distance but never truly leaves. Look directly at it, and it vanishes... but turn away, and it returns.

### 🌀 World Anomalies
- **Time Distortions**: Day and night cycle rapidly
- **Cursor Possession**: Your mouse moves on its own
- **Reality Breaks**: The game window shakes violently
- **Red Torches**: Strange torches appear in caves

### 😱 Screamers
After 10 minutes of play, the game begins to fight back. Audio distortions, visual flashes, and entity manifestations increase in intensity over time.

### 🚪 The Trap
After 30 minutes, you realize you can't leave. The exit button stops working. Closing the window shows errors. You're stuck... unless you know the secret command.

### 💎 The Discovery
Somewhere in the world lies a portal (500-2000 blocks from spawn). Beyond it, a long tunnel of bedrock. At the end, a chest with diamonds. But taking them triggers something terrible...

### 🏚️ Abandoned Structures
As you explore caves, you'll find signs that someone was here before you. Half-built structures, random chests, crafting tables left behind. You're not alone...

### 🏠 The Error404 House
Rarely (1 in 500 chunks), you might stumble upon a wooden house with a sign reading "ERROR 404". Enter at your own risk...

### 🖥️ The Aftermath
When you finally escape (or the game crashes), a file appears on your desktop: `STAYAWAY.txt`

---

## 🎮 Installation

### Requirements
- Java 8 or higher
- Windows OS (for full features)
- Minecraft 1.1 compatible launcher

### Steps
1. Download the modified `Minecraft.jar`
2. Place in your Minecraft directory
3. Launch with your preferred launcher
4. Create a new world and play

---

## 🎯 Commands

### `/safe`
Disables all horror features and safely closes the game. Use this if you need to exit immediately.

### `powershell wininit`
⚠️ **EXTREMELY DANGEROUS** - Triggers Windows Blue Screen of Death. **NEVER USE ON REAL PC!** Only for testing in Virtual Machines.

---

## 📊 Implementation Status

**Completed Features (92%):**
- ✅ Game branding and title
- ✅ Entity404 stalker system
- ✅ World glitch effects
- ✅ Screamer system
- ✅ Exit trap mechanics
- ✅ BSOD trigger and safe mode
- ✅ Desktop file creation
- ✅ Black demon attack sequence
- ✅ Diamond chest trigger
- ✅ Bedrock tunnel dimension
- ✅ Custom portal generation
- ✅ Player presence signs
- ✅ Error404.png house

**Pending Features (8%):**
- ❌ Red torch cave placement (optional cosmetic feature)

---

## 🔧 Building from Source

See [BUILD.md](doc/BUILD.md) for detailed build instructions.

Quick build:
```batch
cd C:\Users\nez3r\Desktop\vers\minecraft1_1_1
recompile.bat
```

Output: `C:\Users\nez3r\Desktop\666\Minecraft.jar`

---

## 📚 Documentation

- [DESIGN.md](doc/DESIGN.md) - Technical design document
- [BUILD.md](doc/BUILD.md) - Build instructions
- [PROGRESS.md](doc/PROGRESS.md) - Implementation progress
- [SUMMARY.md](doc/SUMMARY.md) - Feature summary
- [CLAUDE.md](CLAUDE.md) - Codebase documentation

---

## 🐛 Known Issues

1. Window shake may not work on all window managers
2. Cursor drift may conflict with some mouse drivers
3. BSOD command requires admin privileges
4. Desktop file creation may fail if Desktop folder doesn't exist
5. Entity textures are missing (will use default model)

---

## 🎬 Gameplay Tips

### Survival Tips
1. Don't look at Entity404 for too long
2. The `/safe` command is your emergency exit
3. After 30 minutes, you're trapped - plan accordingly
4. Taking diamonds triggers the final sequence

### Testing Tips
1. Use a Virtual Machine for BSOD testing
2. Keep volume low for screamer testing
3. Test in windowed mode to see window effects
4. Use F3+B to see entity hitboxes

---

## ⚙️ Technical Details

**Base Version:** Minecraft 1.1  
**Mod Tool:** RetroMCP  
**Language:** Java  
**Target Platform:** Windows (some features work on other OS)

**New Classes:**
- `HorrorState` - Global state manager
- `Entity404` - Stalker entity
- `Render404` - Stalker renderer
- `EntityBlackDemon` - Attack entity
- `RenderBlackDemon` - Attack renderer
- `EntityError404` - Jump scare entity
- `RenderError404` - Error404 renderer
- `GlitchManager` - Glitch effects
- `ScreamerManager` - Screamer system
- `AttackSequenceManager` - Attack controller
- `BlockRedTorch` - Red torch block
- `BlockPortal404` - Custom portal block
- `WorldProviderTunnel` - Tunnel dimension provider
- `ChunkProviderTunnel` - Tunnel chunk generator
- `Teleporter404` - Portal teleporter
- `WorldGenPortal404` - Portal structure generator
- `WorldGenAbandonedStructures` - Abandoned structure generator
- `WorldGenError404House` - Error404 house generator

**Modified Classes:**
- `Minecraft` - Main game loop
- `EntityRenderer` - Camera control
- `EntityPlayerSP` - Error404 house detection
- `GuiChat` - Command handling
- `GuiIngameMenu` - Exit trap
- `GameWindowListener` - Window close handling
- `TileEntityChest` - Diamond trigger
- `RenderManager` - Entity registration
- `Block` - Block registration
- `WorldProvider` - Dimension registration
- `ChunkProviderGenerate` - Structure generation
- `TileEntityChest` - Diamond trigger

---

## 🤝 Credits

**Original Concept:** Based on Minecraft creepypasta themes  
**Development:** Claude Code (Anthropic)  
**Base Game:** Minecraft 1.1 by Mojang  
**Decompiler:** RetroMCP

---

## ⚖️ Legal

This is a fan-made modification for educational and entertainment purposes.  
Minecraft is a trademark of Mojang Studios.  
This mod is not affiliated with or endorsed by Mojang.

**Use at your own risk.** The developers are not responsible for:
- System crashes or data loss
- Psychological distress
- Hardware damage
- Any other consequences of using this mod

---

## 🔒 Safety Recommendations

### For Players
1. **Use a Virtual Machine** if testing BSOD features
2. **Lower your volume** before playing
3. **Take breaks** if you feel uncomfortable
4. **Know the `/safe` command** for emergency exit
5. **Don't play in the dark** if easily scared

### For Developers
1. **Test in isolated environment**
2. **Backup your work** before building
3. **Document all changes**
4. **Warn users** about dangerous features
5. **Provide safe exit options**

---

## 📞 Support

For issues or questions:
- Check [SUMMARY.md](doc/SUMMARY.md) for feature status
- Review [BUILD.md](doc/BUILD.md) for build problems
- See [Known Issues](#-known-issues) section

---

## 🎮 Enjoy... if you dare.

*"Are you having fun?:)"*

---

**Version:** 1.1.1 Free  
**Status:** Release Candidate (92% complete)  
**Last Updated:** 2026-05-13
