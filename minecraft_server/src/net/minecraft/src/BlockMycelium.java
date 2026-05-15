package net.minecraft.src;

import java.util.Random;

public class BlockMycelium extends Block {
	protected BlockMycelium(int var1) {
		super(var1, Material.grass);
		this.blockIndexInTexture = 77;
		this.setTickOnLoad(true);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return var1 == 1 ? 78 : (var1 == 0 ? 2 : 77);
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		if(!var1.singleplayerWorld) {
			if(var1.getBlockLightValue(var2, var3 + 1, var4) < 4 && Block.lightOpacity[var1.getBlockId(var2, var3 + 1, var4)] > 2) {
				var1.setBlockWithNotify(var2, var3, var4, Block.dirt.blockID);
			} else if(var1.getBlockLightValue(var2, var3 + 1, var4) >= 9) {
				for(int var6 = 0; var6 < 4; ++var6) {
					int var7 = var2 + var5.nextInt(3) - 1;
					int var8 = var3 + var5.nextInt(5) - 3;
					int var9 = var4 + var5.nextInt(3) - 1;
					int var10 = var1.getBlockId(var7, var8 + 1, var9);
					if(var1.getBlockId(var7, var8, var9) == Block.dirt.blockID && var1.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2) {
						var1.setBlockWithNotify(var7, var8, var9, this.blockID);
					}
				}
			}

		}
	}

	public int idDropped(int var1, Random var2, int var3) {
		return Block.dirt.idDropped(0, var2, var3);
	}
}
