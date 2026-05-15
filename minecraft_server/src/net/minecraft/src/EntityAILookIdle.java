package net.minecraft.src;

public class EntityAILookIdle extends EntityAIBase {
	private EntityLiving field_46114_a;
	private double field_46112_b;
	private double field_46113_c;
	private int field_46111_d = 0;

	public EntityAILookIdle(EntityLiving var1) {
		this.field_46114_a = var1;
		this.func_46087_a(3);
	}

	public boolean func_46090_a() {
		return this.field_46114_a.func_46019_ai().nextFloat() < 0.02F;
	}

	public boolean func_46092_g() {
		return this.field_46111_d >= 0;
	}

	public void func_46088_e() {
		double var1 = Math.PI * 2.0D * this.field_46114_a.func_46019_ai().nextDouble();
		this.field_46112_b = Math.cos(var1);
		this.field_46113_c = Math.sin(var1);
		this.field_46111_d = 20 + this.field_46114_a.func_46019_ai().nextInt(20);
	}

	public void func_46089_b() {
		--this.field_46111_d;
		this.field_46114_a.func_46021_ae().func_46060_a(this.field_46114_a.posX + this.field_46112_b, this.field_46114_a.posY + (double)this.field_46114_a.getEyeHeight(), this.field_46114_a.posZ + this.field_46113_c, 10.0F, (float)this.field_46114_a.getVerticalFaceSpeed());
	}

	public int func_46091_c() {
		return super.func_46091_c();
	}

	public void func_46087_a(int var1) {
		super.func_46087_a(var1);
	}

	public void func_46085_d() {
		super.func_46085_d();
	}

	public boolean func_46086_f() {
		return super.func_46086_f();
	}
}
