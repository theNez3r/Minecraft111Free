package net.minecraft.src;

import java.util.Random;

public class EntityAIWander extends EntityAIBase {
	private EntityCreature field_46104_a;
	private double field_46102_b;
	private double field_46103_c;
	private double field_46101_d;

	public EntityAIWander(EntityCreature var1) {
		this.field_46104_a = var1;
		this.func_46087_a(3);
	}

	public boolean func_46090_a() {
		if(this.field_46104_a.func_46018_ak() >= 100) {
			return false;
		} else if(this.field_46104_a.func_46019_ai().nextInt(120) != 0) {
			return false;
		} else {
			Vec3D var1 = this.func_46100_h();
			if(var1 == null) {
				return false;
			} else {
				this.field_46102_b = var1.xCoord;
				this.field_46103_c = var1.yCoord;
				this.field_46101_d = var1.zCoord;
				return true;
			}
		}
	}

	public boolean func_46092_g() {
		return !this.field_46104_a.func_46023_ah().func_46034_b();
	}

	public void func_46088_e() {
		this.field_46104_a.func_46023_ah().func_46033_a(this.field_46102_b, this.field_46103_c, this.field_46101_d, this.field_46104_a.func_46016_ar());
	}

	private Vec3D func_46100_h() {
		Random var1 = this.field_46104_a.func_46019_ai();
		boolean var2 = false;
		int var3 = -1;
		int var4 = -1;
		int var5 = -1;
		float var6 = -99999.0F;

		for(int var7 = 0; var7 < 10; ++var7) {
			int var8 = MathHelper.floor_double(this.field_46104_a.posX + (double)var1.nextInt(13) - 6.0D);
			int var9 = MathHelper.floor_double(this.field_46104_a.posY + (double)var1.nextInt(7) - 3.0D);
			int var10 = MathHelper.floor_double(this.field_46104_a.posZ + (double)var1.nextInt(13) - 6.0D);
			float var11 = this.field_46104_a.getBlockPathWeight(var8, var9, var10);
			if(var11 > var6) {
				var6 = var11;
				var3 = var8;
				var4 = var9;
				var5 = var10;
				var2 = true;
			}
		}

		if(var2) {
			return Vec3D.createVector((double)var3, (double)var4, (double)var5);
		} else {
			return null;
		}
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
}
