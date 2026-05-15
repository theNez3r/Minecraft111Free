package net.minecraft.src;

public class EntityAIAttackOnCollide extends EntityAIBase {
	World field_46099_a;
	EntityMob field_46097_b;
	EntityLiving field_46098_c;
	int field_46095_d = 0;
	float field_46096_e;

	public EntityAIAttackOnCollide(EntityMob var1, World var2, float var3) {
		this.field_46097_b = var1;
		this.field_46099_a = var2;
		this.field_46096_e = var3;
		this.func_46087_a(3);
	}

	public boolean func_46090_a() {
		this.field_46098_c = this.func_46094_h();
		return this.field_46098_c != null;
	}

	public void func_46089_b() {
		this.field_46097_b.func_46023_ah().func_46035_a(this.field_46098_c, this.field_46097_b.func_46016_ar());
		this.field_46097_b.func_46021_ae().func_46058_a(this.field_46098_c, 30.0F, 30.0F);
		this.field_46095_d = Math.max(this.field_46095_d - 1, 0);
		double var1 = 4.0D;
		if(this.field_46097_b.getDistanceSqToEntity(this.field_46098_c) <= var1) {
			if(this.field_46095_d <= 0) {
				this.field_46095_d = 20;
				this.field_46097_b.attackEntityAsMob(this.field_46098_c);
			}
		}
	}

	private EntityLiving func_46094_h() {
		Object var1 = this.field_46097_b.func_46020_aj();
		if(var1 == null) {
			var1 = this.field_46099_a.getClosestVulnerablePlayerToEntity(this.field_46097_b, (double)this.field_46096_e);
		}

		return (EntityLiving)(var1 == null ? null : (((EntityLiving)var1).boundingBox.maxY > this.field_46097_b.boundingBox.minY && ((EntityLiving)var1).boundingBox.minY < this.field_46097_b.boundingBox.maxY ? (!this.field_46097_b.canEntityBeSeen((Entity)var1) ? null : var1) : null));
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

	public void func_46088_e() {
		super.func_46088_e();
	}

	public boolean func_46086_f() {
		return super.func_46086_f();
	}

	public boolean func_46092_g() {
		return super.func_46092_g();
	}
}
