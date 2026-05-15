package net.minecraft.src;

public class StructureVillagePieceWeight {
	public Class villageComponentClass;
	public final int villagePieceWeight;
	public int villagePiecesSpawned;
	public int villagePiecesLimit;

	public StructureVillagePieceWeight(Class var1, int var2, int var3) {
		this.villageComponentClass = var1;
		this.villagePieceWeight = var2;
		this.villagePiecesLimit = var3;
	}

	public boolean canSpawnMoreVillagePiecesOfType(int var1) {
		return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
	}

	public boolean canSpawnMoreVillagePieces() {
		return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
	}
}
