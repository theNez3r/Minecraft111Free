package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockEndPortal extends BlockContainer {
	public static boolean bossDefeated = false;

	protected BlockEndPortal(int var1, Material var2) {
		super(var1, 0, var2);
		this.setLightValue(1.0F);
	}

	public TileEntity getBlockEntity() {
		return new TileEntityEndPortal();
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		float var5 = 1.0F / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var5, 1.0F);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5) {
		if(var5.ridingEntity == null && var5.riddenByEntity == null && var5 instanceof EntityPlayer && !var1.singleplayerWorld) {
			((EntityPlayer)var5).func_40107_e(1);
		}

	}

	public int getRenderType() {
		return -1;
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if(!bossDefeated) {
			if(var1.worldProvider.worldType != 0) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
			}
		}
	}
}
