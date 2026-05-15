package net.minecraft.src;

public class GenLayerDownfallMix extends GenLayer {
	private GenLayer field_35035_b;
	private int field_35036_c;

	public GenLayerDownfallMix(GenLayer var1, GenLayer var2, int var3) {
		super(0L);
		this.parent = var2;
		this.field_35035_b = var1;
		this.field_35036_c = var3;
	}

	public int[] getInts(int var1, int var2, int var3, int var4) {
		int[] var5 = this.parent.getInts(var1, var2, var3, var4);
		int[] var6 = this.field_35035_b.getInts(var1, var2, var3, var4);
		int[] var7 = IntCache.getIntCache(var3 * var4);

		for(int var8 = 0; var8 < var3 * var4; ++var8) {
			var7[var8] = var6[var8] + (BiomeGenBase.biomeList[var5[var8]].getIntRainfall() - var6[var8]) / (this.field_35036_c + 1);
		}

		return var7;
	}
}
