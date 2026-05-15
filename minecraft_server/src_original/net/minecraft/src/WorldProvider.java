package net.minecraft.src;

public abstract class WorldProvider {
	public World worldObj;
	public EnumWorldType field_46120_b;
	public WorldChunkManager worldChunkMgr;
	public boolean canSleepInWorld = false;
	public boolean isHellWorld = false;
	public boolean hasNoSky = false;
	public float[] lightBrightnessTable = new float[16];
	public int worldType = 0;
	private float[] colorsSunriseSunset = new float[4];

	public final void registerWorld(World var1) {
		this.worldObj = var1;
		this.field_46120_b = var1.getWorldInfo().func_46069_q();
		this.registerWorldChunkManager();
		this.generateLightBrightnessTable();
	}

	protected void generateLightBrightnessTable() {
		float var1 = 0.0F;

		for(int var2 = 0; var2 <= 15; ++var2) {
			float var3 = 1.0F - (float)var2 / 15.0F;
			this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}

	}

	protected void registerWorldChunkManager() {
		if(this.worldObj.getWorldInfo().func_46069_q() == EnumWorldType.FLAT) {
			this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F, 0.5F);
		} else {
			this.worldChunkMgr = new WorldChunkManager(this.worldObj);
		}

	}

	public IChunkProvider getChunkProvider() {
		return (IChunkProvider)(this.field_46120_b == EnumWorldType.FLAT ? new ChunkProviderFlat(this.worldObj, this.worldObj.getRandomSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()) : new ChunkProviderGenerate(this.worldObj, this.worldObj.getRandomSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled()));
	}

	public boolean canCoordinateBeSpawn(int var1, int var2) {
		int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
		return var3 == Block.grass.blockID;
	}

	public float calculateCelestialAngle(long var1, float var3) {
		int var4 = (int)(var1 % 24000L);
		float var5 = ((float)var4 + var3) / 24000.0F - 0.25F;
		if(var5 < 0.0F) {
			++var5;
		}

		if(var5 > 1.0F) {
			--var5;
		}

		float var6 = var5;
		var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}

	public boolean canRespawnHere() {
		return true;
	}

	public static WorldProvider getProviderForDimension(int var0) {
		return (WorldProvider)(var0 == -1 ? new WorldProviderHell() : (var0 == 0 ? new WorldProviderSurface() : (var0 == 1 ? new WorldProviderEnd() : null)));
	}

	public ChunkCoordinates getEntrancePortalLocation() {
		return null;
	}

	public int func_46119_e() {
		return this.field_46120_b == EnumWorldType.FLAT ? 4 : this.worldObj.worldHeight / 2;
	}
}
