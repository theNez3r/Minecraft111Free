package net.minecraft.src;

public class BlockEnchantmentTable extends BlockContainer {
	protected BlockEnchantmentTable(int var1) {
		super(var1, 166, Material.rock);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 12.0F / 16.0F, 1.0F);
		this.setLightOpacity(0);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.getBlockTextureFromSide(var1);
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 0 ? this.blockIndexInTexture + 17 : (var1 == 1 ? this.blockIndexInTexture : this.blockIndexInTexture + 16);
	}

	public TileEntity getBlockEntity() {
		return new TileEntityEnchantmentTable();
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		if(var1.singleplayerWorld) {
			return true;
		} else {
			var5.displayGUIEnchantment(var2, var3, var4);
			return true;
		}
	}
}
