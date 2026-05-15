package net.minecraft.src;

public class EntityAIWatchClosest extends EntityAIBase {
	private EntityLiving field_46110_a;
	private EntityLiving field_46108_b;
	private World field_46109_c;
	private float field_46106_d;
	private int field_46107_e;

	public EntityAIWatchClosest(EntityLiving var1, World var2, float var3) {
		this.field_46110_a = var1;
		this.field_46109_c = var2;
		this.field_46106_d = var3;
		this.func_46087_a(3);
	}

	public boolean func_46090_a() {
		if(this.field_46110_a.func_46019_ai().nextFloat() >= 0.02F) {
			return false;
		} else {
			this.field_46108_b = this.field_46109_c.getClosestPlayerToEntity(this.field_46110_a, (double)this.field_46106_d);
			return this.field_46108_b != null;
		}
	}

	public boolean func_46092_g() {
		return !this.field_46108_b.isEntityAlive() ? false : (this.field_46110_a.getDistanceSqToEntity(this.field_46108_b) > (double)(this.field_46106_d * this.field_46106_d) ? false : this.field_46107_e > 0);
	}

	public void func_46088_e() {
		this.field_46107_e = 40 + this.field_46110_a.func_46019_ai().nextInt(40);
	}

	public void func_46085_d() {
		this.field_46108_b = null;
	}

	public void func_46089_b() {
		this.field_46110_a.func_46021_ae().func_46060_a(this.field_46108_b.posX, this.field_46108_b.posY + (double)this.field_46108_b.getEyeHeight(), this.field_46108_b.posZ, 10.0F, (float)this.field_46110_a.getVerticalFaceSpeed());
		--this.field_46107_e;
	}

	public int func_46091_c() {
		return super.func_46091_c();
	}

	public void func_46087_a(int var1) {
		super.func_46087_a(var1);
	}

	public boolean func_46086_f() {
		return super.func_46086_f();
	}
}
