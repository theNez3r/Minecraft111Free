package net.minecraft.src;

public class PathNavigate implements INavigate {
	private EntityLiving field_46039_a;
	private World field_46037_b;
	private PathEntity field_46038_c;
	private float field_46036_d;

	public PathNavigate(EntityLiving var1, World var2) {
		this.field_46039_a = var1;
		this.field_46037_b = var2;
	}

	public void func_46033_a(double var1, double var3, double var5, float var7) {
		this.field_46038_c = this.field_46037_b.getEntityPathToXYZ(this.field_46039_a, (int)var1, (int)var3, (int)var5, 10.0F);
		this.field_46036_d = var7;
	}

	public void func_46035_a(EntityLiving var1, float var2) {
		this.field_46038_c = this.field_46037_b.getPathToEntity(this.field_46039_a, var1, 16.0F);
		this.field_46036_d = var2;
	}

	public void func_46032_a() {
		if(this.field_46038_c != null) {
			float var1 = this.field_46039_a.width;
			Vec3D var2 = this.field_46038_c.getPosition(this.field_46039_a);

			while(var2 != null && var2.squareDistanceTo(this.field_46039_a.posX, var2.yCoord, this.field_46039_a.posZ) < (double)(var1 * var1)) {
				this.field_46038_c.incrementPathIndex();
				if(this.field_46038_c.isFinished()) {
					var2 = null;
					this.field_46038_c = null;
				} else {
					var2 = this.field_46038_c.getPosition(this.field_46039_a);
				}
			}

			if(var2 != null) {
				this.field_46039_a.func_46012_af().func_46071_a(this.field_46036_d);
				this.field_46039_a.func_46012_af().func_46073_a(var2.xCoord, var2.yCoord, var2.zCoord);
			}
		}
	}

	public boolean func_46034_b() {
		return this.field_46038_c == null || this.field_46038_c.isFinished();
	}
}
