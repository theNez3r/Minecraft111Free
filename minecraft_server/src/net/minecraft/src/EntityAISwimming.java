package net.minecraft.src;

public class EntityAISwimming extends EntityAIBase {
	private EntityLiving field_46105_a;

	public EntityAISwimming(EntityLiving var1) {
		this.field_46105_a = var1;
		this.func_46087_a(4);
	}

	public boolean func_46090_a() {
		return this.field_46105_a.func_46019_ai().nextFloat() < 0.8F && (this.field_46105_a.isInWater() || this.field_46105_a.handleLavaMovement());
	}

	public void func_46088_e() {
		this.field_46105_a.func_46013_ag().func_46115_a();
	}

	public int func_46091_c() {
		return super.func_46091_c();
	}

	public void func_46087_a(int var1) {
		super.func_46087_a(var1);
	}

	public void func_46089_b() {
		super.func_46089_b();
	}

	public void func_46085_d() {
		super.func_46085_d();
	}

	public boolean func_46086_f() {
		return super.func_46086_f();
	}

	public boolean func_46092_g() {
		return super.func_46092_g();
	}
}
