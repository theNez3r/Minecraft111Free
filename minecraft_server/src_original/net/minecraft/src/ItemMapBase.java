package net.minecraft.src;

public class ItemMapBase extends Item {
	protected ItemMapBase(int var1) {
		super(var1);
	}

	public boolean func_28019_b() {
		return true;
	}

	public Packet getUpdatePacket(ItemStack var1, World var2, EntityPlayer var3) {
		return null;
	}
}
