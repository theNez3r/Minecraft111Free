package net.minecraft.src;

class SlotEnchantmentTable extends InventoryBasic {
	final ContainerEnchantment field_40088_a;

	SlotEnchantmentTable(ContainerEnchantment var1, String var2, int var3) {
		super(var2, var3);
		this.field_40088_a = var1;
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public void onInventoryChanged() {
		super.onInventoryChanged();
		this.field_40088_a.onCraftMatrixChanged(this);
	}
}
