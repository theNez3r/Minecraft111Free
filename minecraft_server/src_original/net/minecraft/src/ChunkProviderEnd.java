package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ChunkProviderEnd implements IChunkProvider {
	private Random endRNG;
	private NoiseGeneratorOctaves field_40198_j;
	private NoiseGeneratorOctaves field_40199_k;
	private NoiseGeneratorOctaves field_40196_l;
	public NoiseGeneratorOctaves field_40193_a;
	public NoiseGeneratorOctaves field_40191_b;
	private World endWorld;
	private double[] field_40194_n;
	private BiomeGenBase[] field_40195_o;
	double[] field_40192_c;
	double[] field_40189_d;
	double[] field_40190_e;
	double[] field_40187_f;
	double[] field_40188_g;
	int[][] field_40200_h = new int[32][32];

	public ChunkProviderEnd(World var1, long var2) {
		this.endWorld = var1;
		this.endRNG = new Random(var2);
		this.field_40198_j = new NoiseGeneratorOctaves(this.endRNG, 16);
		this.field_40199_k = new NoiseGeneratorOctaves(this.endRNG, 16);
		this.field_40196_l = new NoiseGeneratorOctaves(this.endRNG, 8);
		this.field_40193_a = new NoiseGeneratorOctaves(this.endRNG, 10);
		this.field_40191_b = new NoiseGeneratorOctaves(this.endRNG, 16);
	}

	public void func_40184_a(int var1, int var2, byte[] var3, BiomeGenBase[] var4) {
		byte var5 = 2;
		int var6 = var5 + 1;
		int var7 = this.endWorld.worldHeight / 4 + 1;
		int var8 = var5 + 1;
		this.field_40194_n = this.func_40186_a(this.field_40194_n, var1 * var5, 0, var2 * var5, var6, var7, var8);

		for(int var9 = 0; var9 < var5; ++var9) {
			for(int var10 = 0; var10 < var5; ++var10) {
				for(int var11 = 0; var11 < this.endWorld.worldHeight / 4; ++var11) {
					double var12 = 0.25D;
					double var14 = this.field_40194_n[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
					double var16 = this.field_40194_n[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
					double var18 = this.field_40194_n[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
					double var20 = this.field_40194_n[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
					double var22 = (this.field_40194_n[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14) * var12;
					double var24 = (this.field_40194_n[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
					double var26 = (this.field_40194_n[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18) * var12;
					double var28 = (this.field_40194_n[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20) * var12;

					for(int var30 = 0; var30 < 4; ++var30) {
						double var31 = 0.125D;
						double var33 = var14;
						double var35 = var16;
						double var37 = (var18 - var14) * var31;
						double var39 = (var20 - var16) * var31;

						for(int var41 = 0; var41 < 8; ++var41) {
							int var42 = var41 + var9 * 8 << this.endWorld.xShift | 0 + var10 * 8 << this.endWorld.worldYBits | var11 * 4 + var30;
							int var43 = 1 << this.endWorld.worldYBits;
							double var44 = 0.125D;
							double var46 = var33;
							double var48 = (var35 - var33) * var44;

							for(int var50 = 0; var50 < 8; ++var50) {
								int var51 = 0;
								if(var46 > 0.0D) {
									var51 = Block.whiteStone.blockID;
								}

								var3[var42] = (byte)var51;
								var42 += var43;
								var46 += var48;
							}

							var33 += var37;
							var35 += var39;
						}

						var14 += var22;
						var16 += var24;
						var18 += var26;
						var20 += var28;
					}
				}
			}
		}

	}

	public void func_40185_b(int var1, int var2, byte[] var3, BiomeGenBase[] var4) {
		for(int var5 = 0; var5 < 16; ++var5) {
			for(int var6 = 0; var6 < 16; ++var6) {
				byte var7 = 1;
				int var8 = -1;
				byte var9 = (byte)Block.whiteStone.blockID;
				byte var10 = (byte)Block.whiteStone.blockID;

				for(int var11 = this.endWorld.worldYMask; var11 >= 0; --var11) {
					int var12 = (var6 * 16 + var5) * this.endWorld.worldHeight + var11;
					byte var13 = var3[var12];
					if(var13 == 0) {
						var8 = -1;
					} else if(var13 == Block.stone.blockID) {
						if(var8 == -1) {
							if(var7 <= 0) {
								var9 = 0;
								var10 = (byte)Block.whiteStone.blockID;
							}

							var8 = var7;
							if(var11 >= 0) {
								var3[var12] = var9;
							} else {
								var3[var12] = var10;
							}
						} else if(var8 > 0) {
							--var8;
							var3[var12] = var10;
						}
					}
				}
			}
		}

	}

	public Chunk loadChunk(int var1, int var2) {
		return this.provideChunk(var1, var2);
	}

	public Chunk provideChunk(int var1, int var2) {
		this.endRNG.setSeed((long)var1 * 341873128712L + (long)var2 * 132897987541L);
		byte[] var3 = new byte[16 * this.endWorld.worldHeight * 16];
		Chunk var4 = new Chunk(this.endWorld, var3, var1, var2);
		this.field_40195_o = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.field_40195_o, var1 * 16, var2 * 16, 16, 16);
		this.func_40184_a(var1, var2, var3, this.field_40195_o);
		this.func_40185_b(var1, var2, var3, this.field_40195_o);
		var4.generateSkylightMap();
		return var4;
	}

	private double[] func_40186_a(double[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		if(var1 == null) {
			var1 = new double[var5 * var6 * var7];
		}

		double var8 = 684.412D;
		double var10 = 684.412D;
		this.field_40187_f = this.field_40193_a.func_4103_a(this.field_40187_f, var2, var4, var5, var7, 1.121D, 1.121D, 0.5D);
		this.field_40188_g = this.field_40191_b.func_4103_a(this.field_40188_g, var2, var4, var5, var7, 200.0D, 200.0D, 0.5D);
		var8 *= 2.0D;
		this.field_40192_c = this.field_40196_l.generateNoiseOctaves(this.field_40192_c, var2, var3, var4, var5, var6, var7, var8 / 80.0D, var10 / 160.0D, var8 / 80.0D);
		this.field_40189_d = this.field_40198_j.generateNoiseOctaves(this.field_40189_d, var2, var3, var4, var5, var6, var7, var8, var10, var8);
		this.field_40190_e = this.field_40199_k.generateNoiseOctaves(this.field_40190_e, var2, var3, var4, var5, var6, var7, var8, var10, var8);
		int var12 = 0;
		int var13 = 0;

		for(int var14 = 0; var14 < var5; ++var14) {
			for(int var15 = 0; var15 < var7; ++var15) {
				double var16 = (this.field_40187_f[var13] + 256.0D) / 512.0D;
				if(var16 > 1.0D) {
					var16 = 1.0D;
				}

				double var18 = this.field_40188_g[var13] / 8000.0D;
				if(var18 < 0.0D) {
					var18 = -var18 * 0.3D;
				}

				var18 = var18 * 3.0D - 2.0D;
				float var20 = (float)(var14 + var2 - 0) / 1.0F;
				float var21 = (float)(var15 + var4 - 0) / 1.0F;
				float var22 = 100.0F - MathHelper.sqrt_float(var20 * var20 + var21 * var21) * 8.0F;
				if(var22 > 80.0F) {
					var22 = 80.0F;
				}

				if(var22 < -100.0F) {
					var22 = -100.0F;
				}

				if(var18 > 1.0D) {
					var18 = 1.0D;
				}

				var18 /= 8.0D;
				var18 = 0.0D;
				if(var16 < 0.0D) {
					var16 = 0.0D;
				}

				var16 += 0.5D;
				var18 = var18 * (double)var6 / 16.0D;
				++var13;
				double var23 = (double)var6 / 2.0D;

				for(int var25 = 0; var25 < var6; ++var25) {
					double var26 = 0.0D;
					double var28 = ((double)var25 - var23) * 8.0D / var16;
					if(var28 < 0.0D) {
						var28 *= -1.0D;
					}

					double var30 = this.field_40189_d[var12] / 512.0D;
					double var32 = this.field_40190_e[var12] / 512.0D;
					double var34 = (this.field_40192_c[var12] / 10.0D + 1.0D) / 2.0D;
					if(var34 < 0.0D) {
						var26 = var30;
					} else if(var34 > 1.0D) {
						var26 = var32;
					} else {
						var26 = var30 + (var32 - var30) * var34;
					}

					var26 -= 8.0D;
					var26 += (double)var22;
					byte var36 = 2;
					double var37;
					if(var25 > var6 / 2 - var36) {
						var37 = (double)((float)(var25 - (var6 / 2 - var36)) / 64.0F);
						if(var37 < 0.0D) {
							var37 = 0.0D;
						}

						if(var37 > 1.0D) {
							var37 = 1.0D;
						}

						var26 = var26 * (1.0D - var37) + -3000.0D * var37;
					}

					var36 = 8;
					if(var25 < var36) {
						var37 = (double)((float)(var36 - var25) / ((float)var36 - 1.0F));
						var26 = var26 * (1.0D - var37) + -30.0D * var37;
					}

					var1[var12] = var26;
					++var12;
				}
			}
		}

		return var1;
	}

	public boolean chunkExists(int var1, int var2) {
		return true;
	}

	public void populate(IChunkProvider var1, int var2, int var3) {
		BlockSand.fallInstantly = true;
		int var4 = var2 * 16;
		int var5 = var3 * 16;
		BiomeGenBase var6 = this.endWorld.getWorldChunkManager().getBiomeGenAt(var4 + 16, var5 + 16);
		var6.func_35513_a(this.endWorld, this.endWorld.rand, var4, var5);
		BlockSand.fallInstantly = false;
	}

	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		return true;
	}

	public boolean unload100OldestChunks() {
		return false;
	}

	public boolean canSave() {
		return true;
	}

	public List func_40181_a(EnumCreatureType var1, int var2, int var3, int var4) {
		WorldChunkManager var5 = this.endWorld.getWorldChunkManager();
		if(var5 == null) {
			return null;
		} else {
			BiomeGenBase var6 = var5.getBiomeGenAtChunkCoord(new ChunkCoordIntPair(var2 >> 4, var4 >> 4));
			return var6 == null ? null : var6.getSpawnableList(var1);
		}
	}

	public ChunkPosition func_40182_a(World var1, String var2, int var3, int var4, int var5) {
		return null;
	}
}
