package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class PlayerManager {
	public List players = new ArrayList();
	private LongHashMap playerInstances = new LongHashMap();
	private List playerInstancesToUpdate = new ArrayList();
	private MinecraftServer mcServer;
	private int playerDimension;
	private int playerViewRadius;
	private final int[][] xzDirectionsConst = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

	public PlayerManager(MinecraftServer var1, int var2, int var3) {
		if(var3 > 15) {
			throw new IllegalArgumentException("Too big view radius!");
		} else if(var3 < 3) {
			throw new IllegalArgumentException("Too small view radius!");
		} else {
			this.playerViewRadius = var3;
			this.mcServer = var1;
			this.playerDimension = var2;
		}
	}

	public WorldServer getMinecraftServer() {
		return this.mcServer.getWorldManager(this.playerDimension);
	}

	public void updatePlayerInstances() {
		for(int var1 = 0; var1 < this.playerInstancesToUpdate.size(); ++var1) {
			((PlayerInstance)this.playerInstancesToUpdate.get(var1)).onUpdate();
		}

		this.playerInstancesToUpdate.clear();
		if(this.players.isEmpty()) {
			WorldServer var3 = this.mcServer.getWorldManager(this.playerDimension);
			WorldProvider var2 = var3.worldProvider;
			if(!var2.canRespawnHere()) {
				var3.chunkProviderServer.func_46041_c();
			}
		}

	}

	private PlayerInstance getPlayerInstance(int var1, int var2, boolean var3) {
		long var4 = (long)var1 + 2147483647L | (long)var2 + 2147483647L << 32;
		PlayerInstance var6 = (PlayerInstance)this.playerInstances.getValueByKey(var4);
		if(var6 == null && var3) {
			var6 = new PlayerInstance(this, var1, var2);
			this.playerInstances.add(var4, var6);
		}

		return var6;
	}

	public void markBlockNeedsUpdate(int var1, int var2, int var3) {
		int var4 = var1 >> 4;
		int var5 = var3 >> 4;
		PlayerInstance var6 = this.getPlayerInstance(var4, var5, false);
		if(var6 != null) {
			var6.markBlockNeedsUpdate(var1 & 15, var2, var3 & 15);
		}

	}

	public void addPlayer(EntityPlayerMP var1) {
		int var2 = (int)var1.posX >> 4;
		int var3 = (int)var1.posZ >> 4;
		var1.managedPosX = var1.posX;
		var1.managedPosZ = var1.posZ;
		int var4 = 0;
		int var5 = this.playerViewRadius;
		int var6 = 0;
		int var7 = 0;
		this.getPlayerInstance(var2, var3, true).addPlayer(var1);

		int var8;
		for(var8 = 1; var8 <= var5 * 2; ++var8) {
			for(int var9 = 0; var9 < 2; ++var9) {
				int[] var10 = this.xzDirectionsConst[var4++ % 4];

				for(int var11 = 0; var11 < var8; ++var11) {
					var6 += var10[0];
					var7 += var10[1];
					this.getPlayerInstance(var2 + var6, var3 + var7, true).addPlayer(var1);
				}
			}
		}

		var4 %= 4;

		for(var8 = 0; var8 < var5 * 2; ++var8) {
			var6 += this.xzDirectionsConst[var4][0];
			var7 += this.xzDirectionsConst[var4][1];
			this.getPlayerInstance(var2 + var6, var3 + var7, true).addPlayer(var1);
		}

		this.players.add(var1);
	}

	public void removePlayer(EntityPlayerMP var1) {
		int var2 = (int)var1.managedPosX >> 4;
		int var3 = (int)var1.managedPosZ >> 4;

		for(int var4 = var2 - this.playerViewRadius; var4 <= var2 + this.playerViewRadius; ++var4) {
			for(int var5 = var3 - this.playerViewRadius; var5 <= var3 + this.playerViewRadius; ++var5) {
				PlayerInstance var6 = this.getPlayerInstance(var4, var5, false);
				if(var6 != null) {
					var6.removePlayer(var1);
				}
			}
		}

		this.players.remove(var1);
	}

	private boolean isOutsidePlayerViewRadius(int var1, int var2, int var3, int var4) {
		int var5 = var1 - var3;
		int var6 = var2 - var4;
		return var5 >= -this.playerViewRadius && var5 <= this.playerViewRadius ? var6 >= -this.playerViewRadius && var6 <= this.playerViewRadius : false;
	}

	public void updateMountedMovingPlayer(EntityPlayerMP var1) {
		int var2 = (int)var1.posX >> 4;
		int var3 = (int)var1.posZ >> 4;
		double var4 = var1.managedPosX - var1.posX;
		double var6 = var1.managedPosZ - var1.posZ;
		double var8 = var4 * var4 + var6 * var6;
		if(var8 >= 64.0D) {
			int var10 = (int)var1.managedPosX >> 4;
			int var11 = (int)var1.managedPosZ >> 4;
			int var12 = var2 - var10;
			int var13 = var3 - var11;
			if(var12 != 0 || var13 != 0) {
				for(int var14 = var2 - this.playerViewRadius; var14 <= var2 + this.playerViewRadius; ++var14) {
					for(int var15 = var3 - this.playerViewRadius; var15 <= var3 + this.playerViewRadius; ++var15) {
						if(!this.isOutsidePlayerViewRadius(var14, var15, var10, var11)) {
							this.getPlayerInstance(var14, var15, true).addPlayer(var1);
						}

						if(!this.isOutsidePlayerViewRadius(var14 - var12, var15 - var13, var2, var3)) {
							PlayerInstance var16 = this.getPlayerInstance(var14 - var12, var15 - var13, false);
							if(var16 != null) {
								var16.removePlayer(var1);
							}
						}
					}
				}

				var1.managedPosX = var1.posX;
				var1.managedPosZ = var1.posZ;
			}
		}
	}

	public int getMaxTrackingDistance() {
		return this.playerViewRadius * 16 - 16;
	}

	static LongHashMap getPlayerInstances(PlayerManager var0) {
		return var0.playerInstances;
	}

	static List getPlayerInstancesToUpdate(PlayerManager var0) {
		return var0.playerInstancesToUpdate;
	}
}
