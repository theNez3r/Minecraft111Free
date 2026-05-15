package net.minecraft.src;

abstract class EntityAIBase {
	private int field_46093_a = 0;

	public abstract boolean func_46090_a();

	public boolean func_46092_g() {
		return this.func_46090_a();
	}

	public boolean func_46086_f() {
		return true;
	}

	public void func_46088_e() {
	}

	public void func_46085_d() {
	}

	public void func_46089_b() {
	}

	public void func_46087_a(int var1) {
		this.field_46093_a = var1;
	}

	public int func_46091_c() {
		return this.field_46093_a;
	}
}
