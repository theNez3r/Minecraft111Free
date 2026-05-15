package net.minecraft.src;

public class EntityDamageSource extends DamageSource {
	protected Entity damageSourceEntity;

	public EntityDamageSource(String var1, Entity var2) {
		super(var1);
		this.damageSourceEntity = var2;
	}

	public Entity getEntity() {
		return this.damageSourceEntity;
	}

	public String func_35075_a(EntityPlayer var1) {
		return StatCollector.translateToLocalFormatted("death." + this.damageType, new Object[]{var1.username, this.damageSourceEntity.func_35150_Y()});
	}
}
