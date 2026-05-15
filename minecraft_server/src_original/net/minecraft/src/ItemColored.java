package net.minecraft.src;

public class ItemColored extends ItemBlock {
	private final Block field_35421_a = Block.blocksList[this.getBlockID()];
	private String[] field_41041_b;

	public ItemColored(int var1, boolean var2) {
		super(var1);
		if(var2) {
			this.setMaxDamage(0);
			this.setHasSubtypes(true);
		}

	}

	public int getMetadata(int var1) {
		return var1;
	}

	public ItemColored setBlockNames(String[] var1) {
		this.field_41041_b = var1;
		return this;
	}

	public String getItemNameIS(ItemStack var1) {
		if(this.field_41041_b == null) {
			return super.getItemNameIS(var1);
		} else {
			int var2 = var1.getItemDamage();
			return var2 >= 0 && var2 < this.field_41041_b.length ? super.getItemNameIS(var1) + "." + this.field_41041_b[var2] : super.getItemNameIS(var1);
		}
	}
}
