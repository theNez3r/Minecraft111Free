package net.minecraft.src;

public class ItemMonsterPlacer extends Item {
	public ItemMonsterPlacer(int var1) {
		super(var1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}

	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
		if(var3.singleplayerWorld) {
			return true;
		} else {
			var4 += Facing.offsetsXForSide[var7];
			var5 += Facing.offsetsYForSide[var7];
			var6 += Facing.offsetsZForSide[var7];
			Entity var8 = EntityList.func_44014_a(var1.getItemDamage(), var3);
			if(var8 != null) {
				if(!var2.capabilities.depleteBuckets) {
					--var1.stackSize;
				}

				var8.setLocationAndAngles((double)var4 + 0.5D, (double)var5, (double)var6 + 0.5D, 0.0F, 0.0F);
				var3.spawnEntityInWorld(var8);
			}

			return true;
		}
	}
}
