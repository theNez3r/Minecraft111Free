package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ItemPotion extends Item {
	private HashMap effectCache = new HashMap();

	public ItemPotion(int var1) {
		super(var1);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	public List getPotionEffectsForItemStack(ItemStack var1) {
		return this.getPotionEffectsForDamage(var1.getItemDamage());
	}

	public List getPotionEffectsForDamage(int var1) {
		List var2 = (List)this.effectCache.get(Integer.valueOf(var1));
		if(var2 == null) {
			var2 = PotionHelper.getPotionEffects(var1, false);
			this.effectCache.put(Integer.valueOf(var1), var2);
		}

		return var2;
	}

	public ItemStack onFoodEaten(ItemStack var1, World var2, EntityPlayer var3) {
		--var1.stackSize;
		if(!var2.singleplayerWorld) {
			List var4 = this.getPotionEffectsForItemStack(var1);
			if(var4 != null) {
				Iterator var5 = var4.iterator();

				while(var5.hasNext()) {
					PotionEffect var6 = (PotionEffect)var5.next();
					var3.addPotionEffect(new PotionEffect(var6));
				}
			}
		}

		if(var1.stackSize <= 0) {
			return new ItemStack(Item.glassBottle);
		} else {
			var3.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
			return var1;
		}
	}

	public int getMaxItemUseDuration(ItemStack var1) {
		return 32;
	}

	public EnumAction getAction(ItemStack var1) {
		return EnumAction.drink;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		if(isSplash(var1.getItemDamage())) {
			--var1.stackSize;
			var2.playSoundAtEntity(var3, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if(!var2.singleplayerWorld) {
				var2.spawnEntityInWorld(new EntityPotion(var2, var3, var1.getItemDamage()));
			}

			return var1;
		} else {
			var3.setItemInUse(var1, this.getMaxItemUseDuration(var1));
			return var1;
		}
	}

	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
		return false;
	}

	public static boolean isSplash(int var0) {
		return (var0 & 16384) != 0;
	}
}
