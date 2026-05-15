package net.minecraft.src;

public class WorldProviderEnd extends WorldProvider {
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5F, 0.0F);
		this.worldType = 1;
		this.hasNoSky = true;
		this.canSleepInWorld = true;
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderEnd(this.worldObj, this.worldObj.getRandomSeed());
	}

	public float calculateCelestialAngle(long var1, float var3) {
		return 0.0F;
	}

	public boolean canRespawnHere() {
		return false;
	}

	public boolean canCoordinateBeSpawn(int var1, int var2) {
		int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
		return var3 == 0 ? false : Block.blocksList[var3].blockMaterial.getIsSolid();
	}

	public ChunkCoordinates getEntrancePortalLocation() {
		return new ChunkCoordinates(100, 50, 0);
	}

	public int func_46119_e() {
		return 50;
	}
}
