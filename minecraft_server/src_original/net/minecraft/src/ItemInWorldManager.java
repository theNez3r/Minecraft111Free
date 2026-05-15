package net.minecraft.src;

public class ItemInWorldManager {
	public World thisWorld;
	public EntityPlayer thisPlayer;
	private int gameType = -1;
	private float field_672_d = 0.0F;
	private int initialDamage;
	private int curBlockX;
	private int curBlockY;
	private int curBlockZ;
	private int curblockDamage;
	private boolean field_22050_k;
	private int field_22049_l;
	private int field_22048_m;
	private int field_22047_n;
	private int field_22046_o;

	public ItemInWorldManager(World var1) {
		this.thisWorld = var1;
	}

	public void toggleGameType(int var1) {
		this.gameType = var1;
		if(var1 == 0) {
			this.thisPlayer.capabilities.allowFlying = false;
			this.thisPlayer.capabilities.isFlying = false;
			this.thisPlayer.capabilities.depleteBuckets = false;
			this.thisPlayer.capabilities.disableDamage = false;
		} else {
			this.thisPlayer.capabilities.allowFlying = true;
			this.thisPlayer.capabilities.depleteBuckets = true;
			this.thisPlayer.capabilities.disableDamage = true;
		}

	}

	public int getGameType() {
		return this.gameType;
	}

	public boolean isCreative() {
		return this.gameType == 1;
	}

	public void func_35695_b(int var1) {
		if(this.gameType == -1) {
			this.gameType = var1;
		}

		this.toggleGameType(this.gameType);
	}

	public void updateBlockRemoving() {
		++this.curblockDamage;
		if(this.field_22050_k) {
			int var1 = this.curblockDamage - this.field_22046_o;
			int var2 = this.thisWorld.getBlockId(this.field_22049_l, this.field_22048_m, this.field_22047_n);
			if(var2 != 0) {
				Block var3 = Block.blocksList[var2];
				float var4 = var3.blockStrength(this.thisPlayer) * (float)(var1 + 1);
				if(var4 >= 1.0F) {
					this.field_22050_k = false;
					this.blockHarvessted(this.field_22049_l, this.field_22048_m, this.field_22047_n);
				}
			} else {
				this.field_22050_k = false;
			}
		}

	}

	public void blockClicked(int var1, int var2, int var3, int var4) {
		this.thisWorld.onBlockHit((EntityPlayer)null, var1, var2, var3, var4);
		if(this.isCreative()) {
			this.blockHarvessted(var1, var2, var3);
		} else {
			this.initialDamage = this.curblockDamage;
			int var5 = this.thisWorld.getBlockId(var1, var2, var3);
			if(var5 > 0) {
				Block.blocksList[var5].onBlockClicked(this.thisWorld, var1, var2, var3, this.thisPlayer);
			}

			if(var5 > 0 && Block.blocksList[var5].blockStrength(this.thisPlayer) >= 1.0F) {
				this.blockHarvessted(var1, var2, var3);
			} else {
				this.curBlockX = var1;
				this.curBlockY = var2;
				this.curBlockZ = var3;
			}

		}
	}

	public void blockRemoving(int var1, int var2, int var3) {
		if(var1 == this.curBlockX && var2 == this.curBlockY && var3 == this.curBlockZ) {
			int var4 = this.curblockDamage - this.initialDamage;
			int var5 = this.thisWorld.getBlockId(var1, var2, var3);
			if(var5 != 0) {
				Block var6 = Block.blocksList[var5];
				float var7 = var6.blockStrength(this.thisPlayer) * (float)(var4 + 1);
				if(var7 >= 0.7F) {
					this.blockHarvessted(var1, var2, var3);
				} else if(!this.field_22050_k) {
					this.field_22050_k = true;
					this.field_22049_l = var1;
					this.field_22048_m = var2;
					this.field_22047_n = var3;
					this.field_22046_o = this.initialDamage;
				}
			}
		}

		this.field_672_d = 0.0F;
	}

	public boolean removeBlock(int var1, int var2, int var3) {
		Block var4 = Block.blocksList[this.thisWorld.getBlockId(var1, var2, var3)];
		int var5 = this.thisWorld.getBlockMetadata(var1, var2, var3);
		boolean var6 = this.thisWorld.setBlockWithNotify(var1, var2, var3, 0);
		if(var4 != null && var6) {
			var4.onBlockDestroyedByPlayer(this.thisWorld, var1, var2, var3, var5);
		}

		return var6;
	}

	public boolean blockHarvessted(int var1, int var2, int var3) {
		int var4 = this.thisWorld.getBlockId(var1, var2, var3);
		int var5 = this.thisWorld.getBlockMetadata(var1, var2, var3);
		this.thisWorld.playAuxSFXAtEntity(this.thisPlayer, 2001, var1, var2, var3, var4 + this.thisWorld.getBlockMetadata(var1, var2, var3) * 256);
		boolean var6 = this.removeBlock(var1, var2, var3);
		if(this.isCreative()) {
			((EntityPlayerMP)this.thisPlayer).playerNetServerHandler.sendPacket(new Packet53BlockChange(var1, var2, var3, this.thisWorld));
		} else {
			ItemStack var7 = this.thisPlayer.getCurrentEquippedItem();
			boolean var8 = this.thisPlayer.canHarvestBlock(Block.blocksList[var4]);
			if(var7 != null) {
				var7.onDestroyBlock(var4, var1, var2, var3, this.thisPlayer);
				if(var7.stackSize == 0) {
					var7.onItemDestroyedByUse(this.thisPlayer);
					this.thisPlayer.destroyCurrentEquippedItem();
				}
			}

			if(var6 && var8) {
				Block.blocksList[var4].harvestBlock(this.thisWorld, this.thisPlayer, var1, var2, var3, var5);
			}
		}

		return var6;
	}

	public boolean itemUsed(EntityPlayer var1, World var2, ItemStack var3) {
		int var4 = var3.stackSize;
		int var5 = var3.getItemDamage();
		ItemStack var6 = var3.useItemRightClick(var2, var1);
		if(var6 != var3 || var6 != null && var6.stackSize != var4 || var6 != null && var6.getMaxItemUseDuration() > 0) {
			var1.inventory.mainInventory[var1.inventory.currentItem] = var6;
			if(this.isCreative()) {
				var6.stackSize = var4;
				var6.setItemDamage(var5);
			}

			if(var6.stackSize == 0) {
				var1.inventory.mainInventory[var1.inventory.currentItem] = null;
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean activeBlockOrUseItem(EntityPlayer var1, World var2, ItemStack var3, int var4, int var5, int var6, int var7) {
		int var8 = var2.getBlockId(var4, var5, var6);
		if(var8 > 0 && Block.blocksList[var8].blockActivated(var2, var4, var5, var6, var1)) {
			return true;
		} else if(var3 == null) {
			return false;
		} else if(this.isCreative()) {
			int var9 = var3.getItemDamage();
			int var10 = var3.stackSize;
			boolean var11 = var3.useItem(var1, var2, var4, var5, var6, var7);
			var3.setItemDamage(var9);
			var3.stackSize = var10;
			return var11;
		} else {
			return var3.useItem(var1, var2, var4, var5, var6, var7);
		}
	}

	public void setWorld(WorldServer var1) {
		this.thisWorld = var1;
	}
}
