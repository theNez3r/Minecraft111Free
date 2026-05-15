package net.minecraft.src;

public class BiomeEndDecorator extends BiomeDecorator {
	protected WorldGenerator spikeGen = new WorldGenSpikes(Block.whiteStone.blockID);

	public BiomeEndDecorator(BiomeGenBase var1) {
		super(var1);
	}

	protected void decorate() {
		this.generateOres();
		if(this.randomGenerator.nextInt(5) == 0) {
			int var1 = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			int var2 = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			int var3 = this.curWorldObj.findTopSolidBlock(var1, var2);
			if(var3 > 0) {
			}

			this.spikeGen.generate(this.curWorldObj, this.randomGenerator, var1, var3, var2);
		}

		if(this.chunk_X == 0 && this.chunk_Z == 0) {
			EntityDragon var4 = new EntityDragon(this.curWorldObj);
			var4.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
			this.curWorldObj.spawnEntityInWorld(var4);
		}

	}
}
