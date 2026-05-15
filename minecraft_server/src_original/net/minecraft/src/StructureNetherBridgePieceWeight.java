package net.minecraft.src;

class StructureNetherBridgePieceWeight {
	public Class field_40655_a;
	public final int field_40653_b;
	public int field_40654_c;
	public int field_40651_d;
	public boolean field_40652_e;

	public StructureNetherBridgePieceWeight(Class var1, int var2, int var3, boolean var4) {
		this.field_40655_a = var1;
		this.field_40653_b = var2;
		this.field_40651_d = var3;
		this.field_40652_e = var4;
	}

	public StructureNetherBridgePieceWeight(Class var1, int var2, int var3) {
		this(var1, var2, var3, false);
	}

	public boolean func_40649_a(int var1) {
		return this.field_40651_d == 0 || this.field_40654_c < this.field_40651_d;
	}

	public boolean func_40650_a() {
		return this.field_40651_d == 0 || this.field_40654_c < this.field_40651_d;
	}
}
