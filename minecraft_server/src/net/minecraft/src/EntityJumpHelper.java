package net.minecraft.src;

public class EntityJumpHelper {
	private EntityLiving field_46118_a;
	private boolean field_46117_b = false;

	public EntityJumpHelper(EntityLiving var1) {
		this.field_46118_a = var1;
	}

	public void func_46115_a() {
		this.field_46117_b = true;
	}

	public void func_46116_b() {
		if(this.field_46117_b) {
			this.field_46118_a.func_46014_e(true);
			this.field_46117_b = false;
		}
	}
}
