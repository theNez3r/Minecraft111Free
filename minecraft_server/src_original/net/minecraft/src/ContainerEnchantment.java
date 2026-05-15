package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ContainerEnchantment extends Container {
	public IInventory tableInventory = new SlotEnchantmentTable(this, "Enchant", 1);
	private World worldPointer;
	private int field_40339_i;
	private int field_40336_j;
	private int field_40337_k;
	private Random field_40335_l = new Random();
	public long nameSeed;
	public int[] enchantLevels = new int[3];

	public ContainerEnchantment(InventoryPlayer var1, World var2, int var3, int var4, int var5) {
		this.worldPointer = var2;
		this.field_40339_i = var3;
		this.field_40336_j = var4;
		this.field_40337_k = var5;
		this.addSlot(new SlotEnchantment(this, this.tableInventory, 0, 25, 47));

		int var6;
		for(var6 = 0; var6 < 3; ++var6) {
			for(int var7 = 0; var7 < 9; ++var7) {
				this.addSlot(new Slot(var1, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}

		for(var6 = 0; var6 < 9; ++var6) {
			this.addSlot(new Slot(var1, var6, 8 + var6 * 18, 142));
		}

	}

	public void onCraftGuiOpened(ICrafting var1) {
		super.onCraftGuiOpened(var1);
		var1.updateCraftingInventoryInfo(this, 0, this.enchantLevels[0]);
		var1.updateCraftingInventoryInfo(this, 1, this.enchantLevels[1]);
		var1.updateCraftingInventoryInfo(this, 2, this.enchantLevels[2]);
	}

	public void updateCraftingResults() {
		super.updateCraftingResults();

		for(int var1 = 0; var1 < this.crafters.size(); ++var1) {
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			var2.updateCraftingInventoryInfo(this, 0, this.enchantLevels[0]);
			var2.updateCraftingInventoryInfo(this, 1, this.enchantLevels[1]);
			var2.updateCraftingInventoryInfo(this, 2, this.enchantLevels[2]);
		}

	}

	public void onCraftMatrixChanged(IInventory var1) {
		if(var1 == this.tableInventory) {
			ItemStack var2 = var1.getStackInSlot(0);
			int var3;
			if(var2 != null && var2.isItemEnchantable()) {
				this.nameSeed = this.field_40335_l.nextLong();
				if(!this.worldPointer.singleplayerWorld) {
					var3 = 0;

					int var4;
					for(var4 = -1; var4 <= 1; ++var4) {
						for(int var5 = -1; var5 <= 1; ++var5) {
							if((var4 != 0 || var5 != 0) && this.worldPointer.isAirBlock(this.field_40339_i + var5, this.field_40336_j, this.field_40337_k + var4) && this.worldPointer.isAirBlock(this.field_40339_i + var5, this.field_40336_j + 1, this.field_40337_k + var4)) {
								if(this.worldPointer.getBlockId(this.field_40339_i + var5 * 2, this.field_40336_j, this.field_40337_k + var4 * 2) == Block.bookShelf.blockID) {
									++var3;
								}

								if(this.worldPointer.getBlockId(this.field_40339_i + var5 * 2, this.field_40336_j + 1, this.field_40337_k + var4 * 2) == Block.bookShelf.blockID) {
									++var3;
								}

								if(var5 != 0 && var4 != 0) {
									if(this.worldPointer.getBlockId(this.field_40339_i + var5 * 2, this.field_40336_j, this.field_40337_k + var4) == Block.bookShelf.blockID) {
										++var3;
									}

									if(this.worldPointer.getBlockId(this.field_40339_i + var5 * 2, this.field_40336_j + 1, this.field_40337_k + var4) == Block.bookShelf.blockID) {
										++var3;
									}

									if(this.worldPointer.getBlockId(this.field_40339_i + var5, this.field_40336_j, this.field_40337_k + var4 * 2) == Block.bookShelf.blockID) {
										++var3;
									}

									if(this.worldPointer.getBlockId(this.field_40339_i + var5, this.field_40336_j + 1, this.field_40337_k + var4 * 2) == Block.bookShelf.blockID) {
										++var3;
									}
								}
							}
						}
					}

					for(var4 = 0; var4 < 3; ++var4) {
						this.enchantLevels[var4] = EnchantmentHelper.calcItemStackEnchantability(this.field_40335_l, var4, var3, var2);
					}

					this.updateCraftingResults();
				}
			} else {
				for(var3 = 0; var3 < 3; ++var3) {
					this.enchantLevels[var3] = 0;
				}
			}
		}

	}

	public boolean enchantItem(EntityPlayer var1, int var2) {
		ItemStack var3 = this.tableInventory.getStackInSlot(0);
		if(this.enchantLevels[var2] > 0 && var3 != null && var1.experienceLevel >= this.enchantLevels[var2]) {
			if(!this.worldPointer.singleplayerWorld) {
				List var4 = EnchantmentHelper.buildEnchantmentList(this.field_40335_l, var3, this.enchantLevels[var2]);
				if(var4 != null) {
					var1.removeExperience(this.enchantLevels[var2]);
					Iterator var5 = var4.iterator();

					while(var5.hasNext()) {
						EnchantmentData var6 = (EnchantmentData)var5.next();
						var3.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
					}

					this.onCraftMatrixChanged(this.tableInventory);
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public void onCraftGuiClosed(EntityPlayer var1) {
		super.onCraftGuiClosed(var1);
		if(!this.worldPointer.singleplayerWorld) {
			ItemStack var2 = this.tableInventory.getStackInSlot(0);
			if(var2 != null) {
				var1.dropPlayerItem(var2);
			}

		}
	}

	public boolean canInteractWith(EntityPlayer var1) {
		return this.worldPointer.getBlockId(this.field_40339_i, this.field_40336_j, this.field_40337_k) != Block.enchantmentTable.blockID ? false : var1.getDistanceSq((double)this.field_40339_i + 0.5D, (double)this.field_40336_j + 0.5D, (double)this.field_40337_k + 0.5D) <= 64.0D;
	}

	public ItemStack transferStackInSlot(int var1) {
		ItemStack var2 = null;
		Slot var3 = (Slot)this.inventorySlots.get(var1);
		if(var3 != null && var3.getHasStack()) {
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();
			if(var1 != 0) {
				return null;
			}

			if(!this.mergeItemStack(var4, 1, 37, true)) {
				return null;
			}

			if(var4.stackSize == 0) {
				var3.putStack((ItemStack)null);
			} else {
				var3.onSlotChanged();
			}

			if(var4.stackSize == var2.stackSize) {
				return null;
			}

			var3.onPickupFromSlot(var4);
		}

		return var2;
	}
}
