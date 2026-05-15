package net.minecraft.src;

public class EntityDamageSourceIndirect extends EntityDamageSource {
	private Entity damageSourceEntity2;

	public EntityDamageSourceIndirect(String var1, Entity var2, Entity var3) {
		super(var1, var2);
		this.damageSourceEntity2 = var3;
	}

	public Entity getSourceOfDamage() {
		return this.damageSourceEntity;
	}

	public Entity getEntity() {
		return this.damageSourceEntity2;
	}

	public String func_35075_a(EntityPlayer var1) {
		return StatCollector.translateToLocalFormatted("death." + this.damageType, new Object[]{var1.username, this.damageSourceEntity2.func_35150_Y()});
	}
}
