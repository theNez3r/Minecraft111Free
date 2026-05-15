package net.minecraft.src;

public class EntityMoveHelper {
	private EntityLiving field_46079_a;
	private double field_46077_b;
	private double field_46078_c;
	private double field_46075_d;
	private float field_46076_e;
	private boolean field_46074_f = false;

	public EntityMoveHelper(EntityLiving var1, float var2) {
		this.field_46079_a = var1;
		this.field_46077_b = var1.posX;
		this.field_46078_c = var1.posY;
		this.field_46075_d = var1.posZ;
		this.field_46076_e = var2;
	}

	public void func_46073_a(double var1, double var3, double var5) {
		this.field_46077_b = var1;
		this.field_46078_c = var3;
		this.field_46075_d = var5;
		this.field_46074_f = true;
	}

	public void func_46071_a(float var1) {
		this.field_46076_e = var1;
	}

	public void func_46072_a() {
		this.field_46079_a.func_46017_d(0.0F);
		if(this.field_46074_f) {
			this.field_46074_f = false;
			int var1 = MathHelper.floor_double(this.field_46079_a.boundingBox.minY + 0.5D);
			double var2 = this.field_46077_b - this.field_46079_a.posX;
			double var4 = this.field_46075_d - this.field_46079_a.posZ;
			double var6 = this.field_46078_c - (double)var1;
			float var8 = (float)(Math.atan2(var4, var2) * 180.0D / (double)((float)Math.PI)) - 90.0F;

			float var9;
			for(var9 = var8 - this.field_46079_a.rotationYaw; var9 < -180.0F; var9 += 360.0F) {
			}

			while(var9 >= 180.0F) {
				var9 -= 360.0F;
			}

			if(var9 > 30.0F) {
				var9 = 30.0F;
			}

			if(var9 < -30.0F) {
				var9 = -30.0F;
			}

			this.field_46079_a.rotationYaw += var9;
			this.field_46079_a.func_46017_d(this.field_46076_e);
			this.field_46079_a.func_46014_e(var6 > 0.0D);
		}
	}
}
