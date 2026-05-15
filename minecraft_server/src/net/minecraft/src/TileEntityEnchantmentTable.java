package net.minecraft.src;

import java.util.Random;

public class TileEntityEnchantmentTable extends TileEntity {
	public int field_40071_a;
	public float field_40068_b;
	public float field_40070_c;
	public float field_40066_d;
	public float field_40067_e;
	public float field_40064_f;
	public float field_40065_g;
	public float field_40073_h;
	public float field_40074_i;
	public float field_40072_j;
	private static Random field_40069_r = new Random();

	public void updateEntity() {
		super.updateEntity();
		this.field_40065_g = this.field_40064_f;
		this.field_40074_i = this.field_40073_h;
		EntityPlayer var1 = this.worldObj.getClosestPlayer((double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), 3.0D);
		if(var1 != null) {
			double var2 = var1.posX - (double)((float)this.xCoord + 0.5F);
			double var4 = var1.posZ - (double)((float)this.zCoord + 0.5F);
			this.field_40072_j = (float)Math.atan2(var4, var2);
			this.field_40064_f += 0.1F;
			if(this.field_40064_f < 0.5F || field_40069_r.nextInt(40) == 0) {
				float var6 = this.field_40066_d;

				do {
					this.field_40066_d += (float)(field_40069_r.nextInt(4) - field_40069_r.nextInt(4));
				} while(var6 == this.field_40066_d);
			}
		} else {
			this.field_40072_j += 0.02F;
			this.field_40064_f -= 0.1F;
		}

		while(this.field_40073_h >= (float)Math.PI) {
			this.field_40073_h -= (float)Math.PI * 2.0F;
		}

		while(this.field_40073_h < -((float)Math.PI)) {
			this.field_40073_h += (float)Math.PI * 2.0F;
		}

		while(this.field_40072_j >= (float)Math.PI) {
			this.field_40072_j -= (float)Math.PI * 2.0F;
		}

		while(this.field_40072_j < -((float)Math.PI)) {
			this.field_40072_j += (float)Math.PI * 2.0F;
		}

		float var7;
		for(var7 = this.field_40072_j - this.field_40073_h; var7 >= (float)Math.PI; var7 -= (float)Math.PI * 2.0F) {
		}

		while(var7 < -((float)Math.PI)) {
			var7 += (float)Math.PI * 2.0F;
		}

		this.field_40073_h += var7 * 0.4F;
		if(this.field_40064_f < 0.0F) {
			this.field_40064_f = 0.0F;
		}

		if(this.field_40064_f > 1.0F) {
			this.field_40064_f = 1.0F;
		}

		++this.field_40071_a;
		this.field_40070_c = this.field_40068_b;
		float var3 = (this.field_40066_d - this.field_40068_b) * 0.4F;
		float var8 = 0.2F;
		if(var3 < -var8) {
			var3 = -var8;
		}

		if(var3 > var8) {
			var3 = var8;
		}

		this.field_40067_e += (var3 - this.field_40067_e) * 0.9F;
		this.field_40068_b += this.field_40067_e;
	}
}
