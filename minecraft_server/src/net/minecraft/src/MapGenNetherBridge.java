package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class MapGenNetherBridge extends MapGenStructure {
	private List field_40206_a = new ArrayList();

	public MapGenNetherBridge() {
		this.field_40206_a.add(new SpawnListEntry(EntityBlaze.class, 10, 2, 3));
		this.field_40206_a.add(new SpawnListEntry(EntityPigZombie.class, 10, 4, 4));
		this.field_40206_a.add(new SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
	}

	public List func_40205_b() {
		return this.field_40206_a;
	}

	protected boolean canSpawnStructureAtCoords(int var1, int var2) {
		int var3 = var1 >> 4;
		int var4 = var2 >> 4;
		this.rand.setSeed((long)(var3 ^ var4 << 4) ^ this.worldObj.getRandomSeed());
		this.rand.nextInt();
		return this.rand.nextInt(3) != 0 ? false : (var1 != (var3 << 4) + 4 + this.rand.nextInt(8) ? false : var2 == (var4 << 4) + 4 + this.rand.nextInt(8));
	}

	protected StructureStart getStructureStart(int var1, int var2) {
		return new StructureNetherBridgeStart(this.worldObj, this.rand, var1, var2);
	}
}
