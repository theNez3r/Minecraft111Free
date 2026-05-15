# Minecraft 1.1.1 Free - Build Instructions

## Prerequisites

- Java JDK 8 (recommended)
- RetroMCP already set up in project directory
- Windows environment (for BSOD feature)

## Build Process

### 1. Compile the Modified Source

Run the build script from the project root:

```batch
cd C:\Users\nez3r\Desktop\vers\minecraft1_1_1
recompile.bat
```

This script will:
1. Compile both client and server using RetroMCP
2. Extract the compiled classes
3. Repack them into JAR files
4. Output to `C:\Users\nez3r\Desktop\666\`

### 2. Build Output Locations

After successful build:
- **Client**: `C:\Users\nez3r\Desktop\666\Minecraft.jar`
- **Server**: `C:\Users\nez3r\Desktop\666\server\server.jar`

### 3. Required Assets

The following texture files need to be added to the client JAR:

**Missing Textures** (create placeholder or use existing):
- `/mob/entity404.png` - Black stalker entity (dark humanoid)
- `/mob/blackdemon.png` - Attack entity (completely black, 3x size)
- `/mob/error404.png` - Corrupted entity (missing texture pattern)

**Note**: The game will run without these textures but entities will appear as default models.

### 4. Testing the Build

#### Test in Development Environment

From VS Code or IDE:
- **Client**: Use launch configuration in `minecraft/.vscode/launch.json`
- **Server**: Use launch configuration in `minecraft_server/.vscode/launch.json`

#### Test Standalone

```batch
cd C:\Users\nez3r\Desktop\666
java -Xmx1024M -Xms1024M -jar Minecraft.jar
```

### 5. Compilation Errors

If you encounter compilation errors:

1. **Missing imports**: Make sure all new classes are in the correct package
2. **Method signature mismatches**: Check that overridden methods match parent class
3. **Undefined references**: Ensure all referenced classes exist

Common issues:
- `HorrorState` not found → Check it's in `net.minecraft.src` package
- `Entity404` not found → Verify file is in correct location
- Render class errors → Check RenderManager registration

### 6. Build Verification Checklist

After building, verify:
- [ ] Game launches without crashes
- [ ] Window title shows "Minecraft 1.1.1 Free"
- [ ] F3 debug shows correct version
- [ ] Main menu displays correct version
- [ ] World loads successfully
- [ ] Entity404 spawns (check with F3+B hitboxes)

### 7. Distribution Package

For distribution, include:
- `Minecraft.jar` (modified client)
- `libraries/` folder (LWJGL and dependencies)
- Launcher or batch file to run the game
- **Warning text** about horror content

### 8. Safety Notes

⚠️ **IMPORTANT**: 
- The BSOD feature (`powershell wininit`) is **DANGEROUS**
- Only test in a Virtual Machine
- Consider removing or disabling for public release
- The `/safe` command provides emergency exit

### 9. Troubleshooting

**Game crashes on startup**:
- Check console for stack traces
- Verify all new classes compiled correctly
- Check for missing texture errors (non-fatal)

**Entities don't spawn**:
- Check `HorrorState.reset()` is called on world load
- Verify entity registration in `RenderManager`
- Check entity spawn conditions

**Glitches don't trigger**:
- Verify `GlitchManager.update()` is called in `Minecraft.runTick()`
- Check timing conditions in `HorrorState`
- Ensure not in safe mode

**Exit trap doesn't work**:
- Check playtime tracking in `HorrorState.updatePlayTime()`
- Verify 30-minute threshold
- Check `GuiIngameMenu.initGui()` modifications

### 10. Performance Notes

The mod adds:
- 1 additional entity (Entity404) per world
- Timer checks every tick for glitches
- Message spam during attack sequence (high CPU for 10 seconds)
- Window manipulation (may cause flicker)

Expected performance impact: Minimal (<5% FPS drop)

### 11. Known Issues

- Window shake may not work on all window managers
- Cursor drift may conflict with some mouse drivers
- BSOD command requires admin privileges on some systems
- Desktop file creation may fail if Desktop folder doesn't exist

---

## Quick Build Command

```batch
cd C:\Users\nez3r\Desktop\vers\minecraft1_1_1 && recompile.bat
```

---

*Last updated: 2026-05-13*
