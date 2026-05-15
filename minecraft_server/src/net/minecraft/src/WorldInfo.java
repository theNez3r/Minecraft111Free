package net.minecraft.src;

import java.util.List;

public class WorldInfo {
	private long randomSeed;
	private EnumWorldType field_46070_b = EnumWorldType.DEFAULT;
	private int spawnX;
	private int spawnY;
	private int spawnZ;
	private long worldTime;
	private long lastTimePlayed;
	private long sizeOnDisk;
	private NBTTagCompound playerTag;
	private int dimension;
	private String levelName;
	private int saveVersion;
	private boolean raining;
	private int rainTime;
	private boolean thundering;
	private int thunderTime;
	private int gameType;
	private boolean mapFeaturesEnabled;
	private boolean hardcore = false;

	public WorldInfo(NBTTagCompound var1) {
		this.randomSeed = var1.getLong("RandomSeed");
		if(var1.hasKey("generatorName")) {
			String var2 = var1.getString("generatorName");
			this.field_46070_b = EnumWorldType.func_46049_a(var2);
			if(this.field_46070_b == null) {
				this.field_46070_b = EnumWorldType.DEFAULT;
			}
		}

		this.gameType = var1.getInteger("GameType");
		if(var1.hasKey("MapFeatures")) {
			this.mapFeaturesEnabled = var1.getBoolean("MapFeatures");
		} else {
			this.mapFeaturesEnabled = true;
		}

		this.spawnX = var1.getInteger("SpawnX");
		this.spawnY = var1.getInteger("SpawnY");
		this.spawnZ = var1.getInteger("SpawnZ");
		this.worldTime = var1.getLong("Time");
		this.lastTimePlayed = var1.getLong("LastPlayed");
		this.sizeOnDisk = var1.getLong("SizeOnDisk");
		this.levelName = var1.getString("LevelName");
		this.saveVersion = var1.getInteger("version");
		this.rainTime = var1.getInteger("rainTime");
		this.raining = var1.getBoolean("raining");
		this.thunderTime = var1.getInteger("thunderTime");
		this.thundering = var1.getBoolean("thundering");
		this.hardcore = var1.getBoolean("hardcore");
		if(var1.hasKey("Player")) {
			this.playerTag = var1.getCompoundTag("Player");
			this.dimension = this.playerTag.getInteger("Dimension");
		}

	}

	public WorldInfo(WorldSettings var1, String var2) {
		this.randomSeed = var1.getSeed();
		this.gameType = var1.getGameType();
		this.mapFeaturesEnabled = var1.isMapFeaturesEnabled();
		this.levelName = var2;
		this.hardcore = var1.getHardcoreEnabled();
		this.field_46070_b = var1.func_46128_e();
	}

	public WorldInfo(WorldInfo var1) {
		this.randomSeed = var1.randomSeed;
		this.field_46070_b = var1.field_46070_b;
		this.gameType = var1.gameType;
		this.mapFeaturesEnabled = var1.mapFeaturesEnabled;
		this.spawnX = var1.spawnX;
		this.spawnY = var1.spawnY;
		this.spawnZ = var1.spawnZ;
		this.worldTime = var1.worldTime;
		this.lastTimePlayed = var1.lastTimePlayed;
		this.sizeOnDisk = var1.sizeOnDisk;
		this.playerTag = var1.playerTag;
		this.dimension = var1.dimension;
		this.levelName = var1.levelName;
		this.saveVersion = var1.saveVersion;
		this.rainTime = var1.rainTime;
		this.raining = var1.raining;
		this.thunderTime = var1.thunderTime;
		this.thundering = var1.thundering;
		this.hardcore = var1.hardcore;
	}

	public NBTTagCompound getNBTTagCompound() {
		NBTTagCompound var1 = new NBTTagCompound();
		this.saveNBTTag(var1, this.playerTag);
		return var1;
	}

	public NBTTagCompound storeLevelDataToNBT(List var1) {
		NBTTagCompound var2 = new NBTTagCompound();
		EntityPlayer var3 = null;
		NBTTagCompound var4 = null;
		if(var1.size() > 0) {
			var3 = (EntityPlayer)var1.get(0);
		}

		if(var3 != null) {
			var4 = new NBTTagCompound();
			var3.writeToNBT(var4);
		}

		this.saveNBTTag(var2, var4);
		return var2;
	}

	private void saveNBTTag(NBTTagCompound var1, NBTTagCompound var2) {
		var1.setLong("RandomSeed", this.randomSeed);
		var1.setString("generatorName", this.field_46070_b.name());
		var1.setInteger("GameType", this.gameType);
		var1.setBoolean("MapFeatures", this.mapFeaturesEnabled);
		var1.setInteger("SpawnX", this.spawnX);
		var1.setInteger("SpawnY", this.spawnY);
		var1.setInteger("SpawnZ", this.spawnZ);
		var1.setLong("Time", this.worldTime);
		var1.setLong("SizeOnDisk", this.sizeOnDisk);
		var1.setLong("LastPlayed", System.currentTimeMillis());
		var1.setString("LevelName", this.levelName);
		var1.setInteger("version", this.saveVersion);
		var1.setInteger("rainTime", this.rainTime);
		var1.setBoolean("raining", this.raining);
		var1.setInteger("thunderTime", this.thunderTime);
		var1.setBoolean("thundering", this.thundering);
		var1.setBoolean("hardcore", this.hardcore);
		if(var2 != null) {
			var1.setCompoundTag("Player", var2);
		}

	}

	public long getRandomSeed() {
		return this.randomSeed;
	}

	public int getSpawnX() {
		return this.spawnX;
	}

	public int getSpawnY() {
		return this.spawnY;
	}

	public int getSpawnZ() {
		return this.spawnZ;
	}

	public long getWorldTime() {
		return this.worldTime;
	}

	public long getSizeOnDisk() {
		return this.sizeOnDisk;
	}

	public int getDimension() {
		return this.dimension;
	}

	public void setWorldTime(long var1) {
		this.worldTime = var1;
	}

	public void setSizeOnDisk(long var1) {
		this.sizeOnDisk = var1;
	}

	public void setSpawnPosition(int var1, int var2, int var3) {
		this.spawnX = var1;
		this.spawnY = var2;
		this.spawnZ = var3;
	}

	public void setWorldName(String var1) {
		this.levelName = var1;
	}

	public int getSaveVersion() {
		return this.saveVersion;
	}

	public void setSaveVersion(int var1) {
		this.saveVersion = var1;
	}

	public boolean getIsThundering() {
		return this.thundering;
	}

	public void setIsThundering(boolean var1) {
		this.thundering = var1;
	}

	public int getThunderTime() {
		return this.thunderTime;
	}

	public void setThunderTime(int var1) {
		this.thunderTime = var1;
	}

	public boolean getIsRaining() {
		return this.raining;
	}

	public void setIsRaining(boolean var1) {
		this.raining = var1;
	}

	public int getRainTime() {
		return this.rainTime;
	}

	public void setRainTime(int var1) {
		this.rainTime = var1;
	}

	public int getGameType() {
		return this.gameType;
	}

	public boolean isMapFeaturesEnabled() {
		return this.mapFeaturesEnabled;
	}

	public void setGameType(int var1) {
		this.gameType = var1;
	}

	public boolean isHardcoreModeEnabled() {
		return this.hardcore;
	}

	public EnumWorldType func_46069_q() {
		return this.field_46070_b;
	}
}
