package net.minecraft.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class EntityLiving extends Entity {
	public int heartsHalvesLife = 20;
	public float field_9098_aw;
	public float field_9096_ay;
	public float renderYawOffset = 0.0F;
	public float prevRenderYawOffset = 0.0F;
	public float field_46027_X = 0.0F;
	public float field_46026_Y = 0.0F;
	protected float field_9124_aB;
	protected float field_9123_aC;
	protected float field_9122_aD;
	protected float field_9121_aE;
	protected boolean field_9120_aF = true;
	protected String texture = "/mob/char.png";
	protected boolean field_9118_aH = true;
	protected float field_9117_aI = 0.0F;
	protected String entityType = null;
	protected float field_9115_aK = 1.0F;
	protected int scoreValue = 0;
	protected float field_9113_aM = 0.0F;
	public float landMovementFactor = 0.1F;
	public float jumpMovementFactor = 0.02F;
	public float prevSwingProgress;
	public float swingProgress;
	protected int health = this.getMaxHealth();
	public int prevHealth;
	protected int carryoverDamage;
	private int livingSoundTime;
	public int hurtTime;
	public int maxHurtTime;
	public float attackedAtYaw = 0.0F;
	public int deathTime = 0;
	public int attackTime = 0;
	public float prevCameraPitch;
	public float cameraPitch;
	protected boolean unused_flag = false;
	protected int experienceValue;
	public int field_9144_ba = -1;
	public float field_9143_bb = (float)(Math.random() * (double)0.9F + (double)0.1F);
	public float field_9142_bc;
	public float field_9141_bd;
	public float field_386_ba;
	protected EntityPlayer attackingPlayer = null;
	protected int field_34904_c = 0;
	protected EntityLiving field_46024_aI = null;
	public int recentlyHit = 0;
	public int arrowHitTimer = 0;
	protected HashMap activePotionsMap = new HashMap();
	private boolean field_39002_b = true;
	private int field_39003_c;
	private EntityLookHelper field_46030_d;
	private EntityMoveHelper field_46031_e;
	private EntityJumpHelper field_46028_f;
	private INavigate field_46029_g;
	protected EntityAITasks field_46025_aM = new EntityAITasks();
	protected int newPosRotationIncrements;
	protected double newPosX;
	protected double newPosY;
	protected double newPosZ;
	protected double newRotationYaw;
	protected double newRotationPitch;
	float field_9134_bl = 0.0F;
	protected int naturalArmorRating = 0;
	protected int entityAge = 0;
	protected float moveStrafing;
	protected float moveForward;
	protected float randomYawVelocity;
	protected boolean isJumping = false;
	protected float defaultPitch = 0.0F;
	protected float moveSpeed = 0.7F;
	private int field_39004_d = 0;
	private Entity currentTarget;
	protected int numTicksToChaseTarget = 0;

	public EntityLiving(World var1) {
		super(var1);
		this.preventEntitySpawning = true;
		this.field_46030_d = new EntityLookHelper(this);
		this.field_46031_e = new EntityMoveHelper(this, this.moveSpeed);
		this.field_46028_f = new EntityJumpHelper(this);
		this.field_46029_g = new PathNavigate(this, var1);
		this.field_9096_ay = (float)(Math.random() + 1.0D) * 0.01F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.field_9098_aw = (float)Math.random() * 12398.0F;
		this.rotationYaw = (float)(Math.random() * (double)((float)Math.PI) * 2.0D);
		this.field_46027_X = this.rotationYaw;
		this.stepHeight = 0.5F;
	}

	public EntityLookHelper func_46021_ae() {
		return this.field_46030_d;
	}

	public EntityMoveHelper func_46012_af() {
		return this.field_46031_e;
	}

	public EntityJumpHelper func_46013_ag() {
		return this.field_46028_f;
	}

	public INavigate func_46023_ah() {
		return this.field_46029_g;
	}

	public Random func_46019_ai() {
		return this.rand;
	}

	public EntityLiving func_46020_aj() {
		return this.field_46024_aI;
	}

	public int func_46018_ak() {
		return this.entityAge;
	}

	protected void entityInit() {
		this.dataWatcher.addObject(8, Integer.valueOf(this.field_39003_c));
	}

	public boolean canEntityBeSeen(Entity var1) {
		return this.worldObj.rayTraceBlocks(Vec3D.createVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3D.createVector(var1.posX, var1.posY + (double)var1.getEyeHeight(), var1.posZ)) == null;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public boolean canBePushed() {
		return !this.isDead;
	}

	public float getEyeHeight() {
		return this.height * 0.85F;
	}

	public int getTalkInterval() {
		return 80;
	}

	public void playLivingSound() {
		String var1 = this.getLivingSound();
		if(var1 != null) {
			this.worldObj.playSoundAtEntity(this, var1, this.getSoundVolume(), this.getSoundPitch());
		}

	}

	public void onEntityUpdate() {
		this.prevSwingProgress = this.swingProgress;
		super.onEntityUpdate();
		Profiler.startSection("mobBaseTick");
		if(this.rand.nextInt(1000) < this.livingSoundTime++) {
			this.livingSoundTime = -this.getTalkInterval();
			this.playLivingSound();
		}

		if(this.isEntityAlive() && this.isEntityInsideOpaqueBlock() && this.attackEntityFrom(DamageSource.inWall, 1)) {
		}

		if(this.isImmuneToFire() || this.worldObj.singleplayerWorld) {
			this.extinguish();
		}

		if(this.isEntityAlive() && this.isInsideOfMaterial(Material.water) && !this.canBreatheUnderwater() && !this.activePotionsMap.containsKey(Integer.valueOf(Potion.waterBreathing.id))) {
			this.setAir(this.decreaseAirSupply(this.getAir()));
			if(this.getAir() == -20) {
				this.setAir(0);

				for(int var1 = 0; var1 < 8; ++var1) {
					float var2 = this.rand.nextFloat() - this.rand.nextFloat();
					float var3 = this.rand.nextFloat() - this.rand.nextFloat();
					float var4 = this.rand.nextFloat() - this.rand.nextFloat();
					this.worldObj.spawnParticle("bubble", this.posX + (double)var2, this.posY + (double)var3, this.posZ + (double)var4, this.motionX, this.motionY, this.motionZ);
				}

				this.attackEntityFrom(DamageSource.drown, 2);
			}

			this.extinguish();
		} else {
			this.setAir(300);
		}

		this.prevCameraPitch = this.cameraPitch;
		if(this.attackTime > 0) {
			--this.attackTime;
		}

		if(this.hurtTime > 0) {
			--this.hurtTime;
		}

		if(this.heartsLife > 0) {
			--this.heartsLife;
		}

		if(this.health <= 0) {
			this.onDeathUpdate();
		}

		if(this.field_34904_c > 0) {
			--this.field_34904_c;
		} else {
			this.attackingPlayer = null;
		}

		this.updatePotionEffects();
		this.field_9121_aE = this.field_9122_aD;
		this.prevRenderYawOffset = this.renderYawOffset;
		this.field_46026_Y = this.field_46027_X;
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		Profiler.endSection();
	}

	protected void onDeathUpdate() {
		++this.deathTime;
		if(this.deathTime == 20) {
			int var1;
			if(!this.worldObj.singleplayerWorld && (this.field_34904_c > 0 || this.isPlayer()) && !this.isChild()) {
				var1 = this.getExperiencePoints(this.attackingPlayer);

				while(var1 > 0) {
					int var2 = EntityXPOrb.getMore(var1);
					var1 -= var2;
					this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var2));
				}
			}

			this.onEntityDeath();
			this.setEntityDead();

			for(var1 = 0; var1 < 20; ++var1) {
				double var8 = this.rand.nextGaussian() * 0.02D;
				double var4 = this.rand.nextGaussian() * 0.02D;
				double var6 = this.rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var8, var4, var6);
			}
		}

	}

	protected int decreaseAirSupply(int var1) {
		return var1 - 1;
	}

	protected int getExperiencePoints(EntityPlayer var1) {
		return this.experienceValue;
	}

	protected boolean isPlayer() {
		return false;
	}

	public void spawnExplosionParticle() {
		for(int var1 = 0; var1 < 20; ++var1) {
			double var2 = this.rand.nextGaussian() * 0.02D;
			double var4 = this.rand.nextGaussian() * 0.02D;
			double var6 = this.rand.nextGaussian() * 0.02D;
			double var8 = 10.0D;
			this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var2 * var8, this.posY + (double)(this.rand.nextFloat() * this.height) - var4 * var8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - var6 * var8, var2, var4, var6);
		}

	}

	public void updateRidden() {
		super.updateRidden();
		this.field_9124_aB = this.field_9123_aC;
		this.field_9123_aC = 0.0F;
		this.fallDistance = 0.0F;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.recentlyHit > 0) {
			if(this.arrowHitTimer <= 0) {
				this.arrowHitTimer = 60;
			}

			--this.arrowHitTimer;
			if(this.arrowHitTimer <= 0) {
				--this.recentlyHit;
			}
		}

		this.onLivingUpdate();
		double var1 = this.posX - this.prevPosX;
		double var3 = this.posZ - this.prevPosZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		float var6 = this.renderYawOffset;
		float var7 = 0.0F;
		this.field_9124_aB = this.field_9123_aC;
		float var8 = 0.0F;
		if(var5 > 0.05F) {
			var8 = 1.0F;
			var7 = var5 * 3.0F;
			var6 = (float)Math.atan2(var3, var1) * 180.0F / (float)Math.PI - 90.0F;
		}

		if(this.swingProgress > 0.0F) {
			var6 = this.rotationYaw;
		}

		if(!this.onGround) {
			var8 = 0.0F;
		}

		this.field_9123_aC += (var8 - this.field_9123_aC) * 0.3F;

		float var9;
		for(var9 = var6 - this.renderYawOffset; var9 < -180.0F; var9 += 360.0F) {
		}

		while(var9 >= 180.0F) {
			var9 -= 360.0F;
		}

		this.renderYawOffset += var9 * 0.3F;

		float var10;
		for(var10 = this.rotationYaw - this.renderYawOffset; var10 < -180.0F; var10 += 360.0F) {
		}

		while(var10 >= 180.0F) {
			var10 -= 360.0F;
		}

		boolean var11 = var10 < -90.0F || var10 >= 90.0F;
		if(var10 < -75.0F) {
			var10 = -75.0F;
		}

		if(var10 >= 75.0F) {
			var10 = 75.0F;
		}

		this.renderYawOffset = this.rotationYaw - var10;
		if(var10 * var10 > 2500.0F) {
			this.renderYawOffset += var10 * 0.2F;
		}

		if(var11) {
			var7 *= -1.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		while(this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
			this.prevRenderYawOffset -= 360.0F;
		}

		while(this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
			this.prevRenderYawOffset += 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch < -180.0F) {
			this.prevRotationPitch -= 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		this.field_9122_aD += var7;
	}

	protected void setSize(float var1, float var2) {
		super.setSize(var1, var2);
	}

	public void heal(int var1) {
		if(this.health > 0) {
			this.health += var1;
			if(this.health > this.getMaxHealth()) {
				this.health = this.getMaxHealth();
			}

			this.heartsLife = this.heartsHalvesLife / 2;
		}
	}

	public abstract int getMaxHealth();

	public int getEntityHealth() {
		return this.health;
	}

	public void setEntityHealth(int var1) {
		this.health = var1;
		if(var1 > this.getMaxHealth()) {
			var1 = this.getMaxHealth();
		}

	}

	public boolean attackEntityFrom(DamageSource var1, int var2) {
		if(this.worldObj.singleplayerWorld) {
			return false;
		} else {
			this.entityAge = 0;
			if(this.health <= 0) {
				return false;
			} else if(var1.fireDamage() && this.isPotionActive(Potion.fireResistance)) {
				return false;
			} else {
				this.field_9141_bd = 1.5F;
				boolean var3 = true;
				if((float)this.heartsLife > (float)this.heartsHalvesLife / 2.0F) {
					if(var2 <= this.naturalArmorRating) {
						return false;
					}

					this.damageEntity(var1, var2 - this.naturalArmorRating);
					this.naturalArmorRating = var2;
					var3 = false;
				} else {
					this.naturalArmorRating = var2;
					this.prevHealth = this.health;
					this.heartsLife = this.heartsHalvesLife;
					this.damageEntity(var1, var2);
					this.hurtTime = this.maxHurtTime = 10;
				}

				this.attackedAtYaw = 0.0F;
				Entity var4 = var1.getEntity();
				if(var4 != null) {
					if(var4 instanceof EntityPlayer) {
						this.field_34904_c = 60;
						this.attackingPlayer = (EntityPlayer)var4;
					} else if(var4 instanceof EntityWolf) {
						EntityWolf var5 = (EntityWolf)var4;
						if(var5.isTamed()) {
							this.field_34904_c = 60;
							this.attackingPlayer = null;
						}
					}
				}

				if(var3) {
					this.worldObj.sendTrackedEntityStatusUpdatePacket(this, (byte)2);
					this.setBeenAttacked();
					if(var4 != null) {
						double var9 = var4.posX - this.posX;

						double var7;
						for(var7 = var4.posZ - this.posZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {
							var9 = (Math.random() - Math.random()) * 0.01D;
						}

						this.attackedAtYaw = (float)(Math.atan2(var7, var9) * 180.0D / (double)((float)Math.PI)) - this.rotationYaw;
						this.knockBack(var4, var2, var9, var7);
					} else {
						this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
					}
				}

				if(this.health <= 0) {
					if(var3) {
						this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
					}

					this.onDeath(var1);
				} else if(var3) {
					this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
				}

				return true;
			}
		}
	}

	private float getSoundPitch() {
		return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
	}

	public int getTotalArmorValue() {
		return 0;
	}

	protected void func_40101_g(int var1) {
	}

	protected int applyArmorCalculations(DamageSource var1, int var2) {
		if(!var1.isUnblockable()) {
			int var3 = 25 - this.getTotalArmorValue();
			int var4 = var2 * var3 + this.carryoverDamage;
			this.func_40101_g(var2);
			var2 = var4 / 25;
			this.carryoverDamage = var4 % 25;
		}

		return var2;
	}

	protected int applyPotionDamageCalculations(DamageSource var1, int var2) {
		if(this.isPotionActive(Potion.resistance)) {
			int var3 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
			int var4 = 25 - var3;
			int var5 = var2 * var4 + this.carryoverDamage;
			var2 = var5 / 25;
			this.carryoverDamage = var5 % 25;
		}

		return var2;
	}

	protected void damageEntity(DamageSource var1, int var2) {
		var2 = this.applyArmorCalculations(var1, var2);
		var2 = this.applyPotionDamageCalculations(var1, var2);
		this.health -= var2;
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return "damage.hurtflesh";
	}

	protected String getDeathSound() {
		return "damage.hurtflesh";
	}

	public void knockBack(Entity var1, int var2, double var3, double var5) {
		this.isAirBorne = true;
		float var7 = MathHelper.sqrt_double(var3 * var3 + var5 * var5);
		float var8 = 0.4F;
		this.motionX /= 2.0D;
		this.motionY /= 2.0D;
		this.motionZ /= 2.0D;
		this.motionX -= var3 / (double)var7 * (double)var8;
		this.motionY += (double)var8;
		this.motionZ -= var5 / (double)var7 * (double)var8;
		if(this.motionY > (double)0.4F) {
			this.motionY = (double)0.4F;
		}

	}

	public void onDeath(DamageSource var1) {
		Entity var2 = var1.getEntity();
		if(this.scoreValue >= 0 && var2 != null) {
			var2.addToPlayerScore(this, this.scoreValue);
		}

		if(var2 != null) {
			var2.onKillEntity(this);
		}

		this.unused_flag = true;
		if(!this.worldObj.singleplayerWorld) {
			int var3 = 0;
			if(var2 instanceof EntityPlayer) {
				var3 = EnchantmentHelper.getLootingModifier(((EntityPlayer)var2).inventory);
			}

			if(!this.isChild()) {
				this.dropFewItems(this.field_34904_c > 0, var3);
			}
		}

		this.worldObj.sendTrackedEntityStatusUpdatePacket(this, (byte)3);
	}

	protected void dropFewItems(boolean var1, int var2) {
		int var3 = this.getDropItemId();
		if(var3 > 0) {
			int var4 = this.rand.nextInt(3);
			if(var2 > 0) {
				var4 += this.rand.nextInt(var2 + 1);
			}

			for(int var5 = 0; var5 < var4; ++var5) {
				this.dropItem(var3, 1);
			}
		}

	}

	protected int getDropItemId() {
		return 0;
	}

	protected void fall(float var1) {
		super.fall(var1);
		int var2 = (int)Math.ceil((double)(var1 - 3.0F));
		if(var2 > 0) {
			if(var2 > 4) {
				this.worldObj.playSoundAtEntity(this, "damage.fallbig", 1.0F, 1.0F);
			} else {
				this.worldObj.playSoundAtEntity(this, "damage.fallsmall", 1.0F, 1.0F);
			}

			this.attackEntityFrom(DamageSource.fall, var2);
			int var3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - (double)0.2F - (double)this.yOffset), MathHelper.floor_double(this.posZ));
			if(var3 > 0) {
				StepSound var4 = Block.blocksList[var3].stepSound;
				this.worldObj.playSoundAtEntity(this, var4.stepSoundDir(), var4.getVolume() * 0.5F, var4.getPitch() * (12.0F / 16.0F));
			}
		}

	}

	public void moveEntityWithHeading(float var1, float var2) {
		double var3;
		if(this.isInWater()) {
			var3 = this.posY;
			this.moveFlying(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.8F;
			this.motionY *= (double)0.8F;
			this.motionZ *= (double)0.8F;
			this.motionY -= 0.02D;
			if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6F - this.posY + var3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else if(this.handleLavaMovement()) {
			var3 = this.posY;
			this.moveFlying(var1, var2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
			this.motionY -= 0.02D;
			if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6F - this.posY + var3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else {
			float var8 = 0.91F;
			if(this.onGround) {
				var8 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var4 > 0) {
					var8 = Block.blocksList[var4].slipperiness * 0.91F;
				}
			}

			float var9 = 0.16277136F / (var8 * var8 * var8);
			float var5 = this.onGround ? this.landMovementFactor * var9 : this.jumpMovementFactor;
			this.moveFlying(var1, var2, var5);
			var8 = 0.91F;
			if(this.onGround) {
				var8 = 546.0F * 0.1F * 0.1F * 0.1F;
				int var6 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(var6 > 0) {
					var8 = Block.blocksList[var6].slipperiness * 0.91F;
				}
			}

			if(this.isOnLadder()) {
				float var11 = 0.15F;
				if(this.motionX < (double)(-var11)) {
					this.motionX = (double)(-var11);
				}

				if(this.motionX > (double)var11) {
					this.motionX = (double)var11;
				}

				if(this.motionZ < (double)(-var11)) {
					this.motionZ = (double)(-var11);
				}

				if(this.motionZ > (double)var11) {
					this.motionZ = (double)var11;
				}

				this.fallDistance = 0.0F;
				if(this.motionY < -0.15D) {
					this.motionY = -0.15D;
				}

				if(this.isSneaking() && this.motionY < 0.0D) {
					this.motionY = 0.0D;
				}
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			if(this.isCollidedHorizontally && this.isOnLadder()) {
				this.motionY = 0.2D;
			}

			this.motionY -= 0.08D;
			this.motionY *= (double)0.98F;
			this.motionX *= (double)var8;
			this.motionZ *= (double)var8;
		}

		this.field_9142_bc = this.field_9141_bd;
		var3 = this.posX - this.prevPosX;
		double var10 = this.posZ - this.prevPosZ;
		float var7 = MathHelper.sqrt_double(var3 * var3 + var10 * var10) * 4.0F;
		if(var7 > 1.0F) {
			var7 = 1.0F;
		}

		this.field_9141_bd += (var7 - this.field_9141_bd) * 0.4F;
		this.field_386_ba += this.field_9141_bd;
	}

	public boolean isOnLadder() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		return this.worldObj.getBlockId(var1, var2, var3) == Block.ladder.blockID;
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("Health", (short)this.health);
		var1.setShort("HurtTime", (short)this.hurtTime);
		var1.setShort("DeathTime", (short)this.deathTime);
		var1.setShort("AttackTime", (short)this.attackTime);
		if(!this.activePotionsMap.isEmpty()) {
			NBTTagList var2 = new NBTTagList();
			Iterator var3 = this.activePotionsMap.values().iterator();

			while(var3.hasNext()) {
				PotionEffect var4 = (PotionEffect)var3.next();
				NBTTagCompound var5 = new NBTTagCompound();
				var5.setByte("Id", (byte)var4.getPotionID());
				var5.setByte("Amplifier", (byte)var4.getAmplifier());
				var5.setInteger("Duration", var4.getDuration());
				var2.setTag(var5);
			}

			var1.setTag("ActiveEffects", var2);
		}

	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.health = var1.getShort("Health");
		if(!var1.hasKey("Health")) {
			this.health = this.getMaxHealth();
		}

		this.hurtTime = var1.getShort("HurtTime");
		this.deathTime = var1.getShort("DeathTime");
		this.attackTime = var1.getShort("AttackTime");
		if(var1.hasKey("ActiveEffects")) {
			NBTTagList var2 = var1.getTagList("ActiveEffects");

			for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
				NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
				byte var5 = var4.getByte("Id");
				byte var6 = var4.getByte("Amplifier");
				int var7 = var4.getInteger("Duration");
				this.activePotionsMap.put(Integer.valueOf(var5), new PotionEffect(var5, var7, var6));
			}
		}

	}

	public boolean isEntityAlive() {
		return !this.isDead && this.health > 0;
	}

	public boolean canBreatheUnderwater() {
		return false;
	}

	public void func_46017_d(float var1) {
		this.moveForward = var1;
	}

	public void func_46014_e(boolean var1) {
		this.isJumping = var1;
	}

	public float func_46016_ar() {
		return this.moveSpeed;
	}

	public void onLivingUpdate() {
		if(this.field_39004_d > 0) {
			--this.field_39004_d;
		}

		if(this.newPosRotationIncrements > 0) {
			double var1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
			double var3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
			double var5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;

			double var7;
			for(var7 = this.newRotationYaw - (double)this.rotationYaw; var7 < -180.0D; var7 += 360.0D) {
			}

			while(var7 >= 180.0D) {
				var7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.newPosRotationIncrements);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
			--this.newPosRotationIncrements;
			this.setPosition(var1, var3, var5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
			List var9 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(1.0D / 32.0D, 0.0D, 1.0D / 32.0D));
			if(var9.size() > 0) {
				double var10 = 0.0D;

				for(int var12 = 0; var12 < var9.size(); ++var12) {
					AxisAlignedBB var13 = (AxisAlignedBB)var9.get(var12);
					if(var13.maxY > var10) {
						var10 = var13.maxY;
					}
				}

				var3 += var10 - this.boundingBox.minY;
				this.setPosition(var1, var3, var5);
			}
		}

		Profiler.startSection("ai");
		if(this.isMovementBlocked()) {
			this.isJumping = false;
			this.moveStrafing = 0.0F;
			this.moveForward = 0.0F;
			this.randomYawVelocity = 0.0F;
		} else if(this.func_44006_ak()) {
			if(this.func_46022_as()) {
				this.func_46015_av();
			} else {
				this.updateEntityActionState();
				this.field_46027_X = this.rotationYaw;
			}
		}

		Profiler.endSection();
		boolean var14 = this.isInWater();
		boolean var2 = this.handleLavaMovement();
		if(this.isJumping) {
			if(var14) {
				this.motionY += (double)0.04F;
			} else if(var2) {
				this.motionY += (double)0.04F;
			} else if(this.onGround && this.field_39004_d == 0) {
				this.jump();
				this.field_39004_d = 10;
			}
		} else {
			this.field_39004_d = 0;
		}

		this.moveStrafing *= 0.98F;
		this.moveForward *= 0.98F;
		this.randomYawVelocity *= 0.9F;
		float var15 = this.landMovementFactor;
		this.landMovementFactor *= this.getPotionSpeedMultiplier();
		this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
		this.landMovementFactor = var15;
		Profiler.startSection("push");
		List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
		if(var4 != null && var4.size() > 0) {
			for(int var16 = 0; var16 < var4.size(); ++var16) {
				Entity var6 = (Entity)var4.get(var16);
				if(var6.canBePushed()) {
					var6.applyEntityCollision(this);
				}
			}
		}

		Profiler.endSection();
	}

	protected boolean func_46022_as() {
		return false;
	}

	protected boolean func_44006_ak() {
		return !this.worldObj.singleplayerWorld;
	}

	protected boolean isMovementBlocked() {
		return this.health <= 0;
	}

	public boolean isBlocking() {
		return false;
	}

	protected void jump() {
		this.motionY = (double)0.42F;
		if(this.isPotionActive(Potion.jump)) {
			this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
		}

		if(this.isSprinting()) {
			float var1 = this.rotationYaw * ((float)Math.PI / 180.0F);
			this.motionX -= (double)(MathHelper.sin(var1) * 0.2F);
			this.motionZ += (double)(MathHelper.cos(var1) * 0.2F);
		}

		this.isAirBorne = true;
	}

	protected boolean canDespawn() {
		return true;
	}

	protected void despawnEntity() {
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
		if(var1 != null) {
			double var2 = var1.posX - this.posX;
			double var4 = var1.posY - this.posY;
			double var6 = var1.posZ - this.posZ;
			double var8 = var2 * var2 + var4 * var4 + var6 * var6;
			if(this.canDespawn() && var8 > 16384.0D) {
				this.setEntityDead();
			}

			if(this.entityAge > 600 && this.rand.nextInt(800) == 0 && var8 > 1024.0D && this.canDespawn()) {
				this.setEntityDead();
			} else if(var8 < 1024.0D) {
				this.entityAge = 0;
			}
		}

	}

	protected void func_46015_av() {
		++this.entityAge;
		this.despawnEntity();
		if(this.field_46024_aI != null && !this.field_46024_aI.isEntityAlive()) {
			this.field_46024_aI = null;
		}

		this.field_46025_aM.func_46133_a();
		this.field_46029_g.func_46032_a();
		this.field_46031_e.func_46072_a();
		this.field_46030_d.func_46059_a();
		this.field_46028_f.func_46116_b();
	}

	protected void updateEntityActionState() {
		++this.entityAge;
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
		this.despawnEntity();
		this.moveStrafing = 0.0F;
		this.moveForward = 0.0F;
		float var2 = 8.0F;
		if(this.rand.nextFloat() < 0.02F) {
			var1 = this.worldObj.getClosestPlayerToEntity(this, (double)var2);
			if(var1 != null) {
				this.currentTarget = var1;
				this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
			} else {
				this.randomYawVelocity = (this.rand.nextFloat() - 0.5F) * 20.0F;
			}
		}

		if(this.currentTarget != null) {
			this.faceEntity(this.currentTarget, 10.0F, (float)this.getVerticalFaceSpeed());
			if(this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > (double)(var2 * var2)) {
				this.currentTarget = null;
			}
		} else {
			if(this.rand.nextFloat() < 0.05F) {
				this.randomYawVelocity = (this.rand.nextFloat() - 0.5F) * 20.0F;
			}

			this.rotationYaw += this.randomYawVelocity;
			this.rotationPitch = this.defaultPitch;
		}

		boolean var3 = this.isInWater();
		boolean var4 = this.handleLavaMovement();
		if(var3 || var4) {
			this.isJumping = this.rand.nextFloat() < 0.8F;
		}

	}

	public int getVerticalFaceSpeed() {
		return 40;
	}

	public void faceEntity(Entity var1, float var2, float var3) {
		double var4 = var1.posX - this.posX;
		double var8 = var1.posZ - this.posZ;
		double var6;
		if(var1 instanceof EntityLiving) {
			EntityLiving var10 = (EntityLiving)var1;
			var6 = this.posY + (double)this.getEyeHeight() - (var10.posY + (double)var10.getEyeHeight());
		} else {
			var6 = (var1.boundingBox.minY + var1.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
		}

		double var14 = (double)MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float)(Math.atan2(var8, var4) * 180.0D / (double)((float)Math.PI)) - 90.0F;
		float var13 = (float)(-(Math.atan2(var6, var14) * 180.0D / (double)((float)Math.PI)));
		this.rotationPitch = -this.updateRotation(this.rotationPitch, var13, var3);
		this.rotationYaw = this.updateRotation(this.rotationYaw, var12, var2);
	}

	public boolean hasCurrentTarget() {
		return this.currentTarget != null;
	}

	public Entity getCurrentTarget() {
		return this.currentTarget;
	}

	private float updateRotation(float var1, float var2, float var3) {
		float var4;
		for(var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F) {
		}

		while(var4 >= 180.0F) {
			var4 -= 360.0F;
		}

		if(var4 > var3) {
			var4 = var3;
		}

		if(var4 < -var3) {
			var4 = -var3;
		}

		return var1 + var4;
	}

	public void onEntityDeath() {
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	protected void kill() {
		this.attackEntityFrom(DamageSource.outOfWorld, 4);
	}

	public Vec3D getLookVec() {
		return this.getLook(1.0F);
	}

	public Vec3D getLook(float var1) {
		float var2;
		float var3;
		float var4;
		float var5;
		if(var1 == 1.0F) {
			var2 = MathHelper.cos(-this.rotationYaw * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var3 = MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var4 = -MathHelper.cos(-this.rotationPitch * ((float)Math.PI / 180.0F));
			var5 = MathHelper.sin(-this.rotationPitch * ((float)Math.PI / 180.0F));
			return Vec3D.createVector((double)(var3 * var4), (double)var5, (double)(var2 * var4));
		} else {
			var2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * var1;
			var3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * var1;
			var4 = MathHelper.cos(-var3 * ((float)Math.PI / 180.0F) - (float)Math.PI);
			var5 = MathHelper.sin(-var3 * ((float)Math.PI / 180.0F) - (float)Math.PI);
			float var6 = -MathHelper.cos(-var2 * ((float)Math.PI / 180.0F));
			float var7 = MathHelper.sin(-var2 * ((float)Math.PI / 180.0F));
			return Vec3D.createVector((double)(var5 * var6), (double)var7, (double)(var4 * var6));
		}
	}

	public int getMaxSpawnedInChunk() {
		return 4;
	}

	public boolean isPlayerSleeping() {
		return false;
	}

	protected void updatePotionEffects() {
		Iterator var1 = this.activePotionsMap.keySet().iterator();

		while(var1.hasNext()) {
			Integer var2 = (Integer)var1.next();
			PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
			if(!var3.onUpdate(this) && !this.worldObj.singleplayerWorld) {
				var1.remove();
				this.onFinishedPotionEffect(var3);
			}
		}

		int var9;
		if(this.field_39002_b) {
			if(!this.worldObj.singleplayerWorld) {
				if(!this.activePotionsMap.isEmpty()) {
					var9 = PotionHelper.func_40553_a(this.activePotionsMap.values());
					this.dataWatcher.updateObject(8, Integer.valueOf(var9));
				} else {
					this.dataWatcher.updateObject(8, Integer.valueOf(0));
				}
			}

			this.field_39002_b = false;
		}

		if(this.rand.nextBoolean()) {
			var9 = this.dataWatcher.getWatchableObjectInt(8);
			if(var9 > 0) {
				double var10 = (double)(var9 >> 16 & 255) / 255.0D;
				double var5 = (double)(var9 >> 8 & 255) / 255.0D;
				double var7 = (double)(var9 >> 0 & 255) / 255.0D;
				this.worldObj.spawnParticle("mobSpell", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - (double)this.yOffset, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, var10, var5, var7);
			}
		}

	}

	public void func_40089_ar() {
		Iterator var1 = this.activePotionsMap.keySet().iterator();

		while(var1.hasNext()) {
			Integer var2 = (Integer)var1.next();
			PotionEffect var3 = (PotionEffect)this.activePotionsMap.get(var2);
			if(!this.worldObj.singleplayerWorld) {
				var1.remove();
				this.onFinishedPotionEffect(var3);
			}
		}

	}

	public Collection func_35183_ak() {
		return this.activePotionsMap.values();
	}

	public boolean isPotionActive(Potion var1) {
		return this.activePotionsMap.containsKey(Integer.valueOf(var1.id));
	}

	public PotionEffect getActivePotionEffect(Potion var1) {
		return (PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.id));
	}

	public void addPotionEffect(PotionEffect var1) {
		if(this.func_40096_a(var1)) {
			if(this.activePotionsMap.containsKey(Integer.valueOf(var1.getPotionID()))) {
				((PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.getPotionID()))).combine(var1);
				this.onChangedPotionEffect((PotionEffect)this.activePotionsMap.get(Integer.valueOf(var1.getPotionID())));
			} else {
				this.activePotionsMap.put(Integer.valueOf(var1.getPotionID()), var1);
				this.onNewPotionEffect(var1);
			}

		}
	}

	public boolean func_40096_a(PotionEffect var1) {
		if(this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			int var2 = var1.getPotionID();
			if(var2 == Potion.regeneration.id || var2 == Potion.poison.id) {
				return false;
			}
		}

		return true;
	}

	public boolean isEntityUndead() {
		return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}

	protected void onNewPotionEffect(PotionEffect var1) {
		this.field_39002_b = true;
	}

	protected void onChangedPotionEffect(PotionEffect var1) {
		this.field_39002_b = true;
	}

	protected void onFinishedPotionEffect(PotionEffect var1) {
		this.field_39002_b = true;
	}

	protected float getPotionSpeedMultiplier() {
		float var1 = 1.0F;
		if(this.isPotionActive(Potion.moveSpeed)) {
			var1 *= 1.0F + 0.2F * (float)(this.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}

		if(this.isPotionActive(Potion.moveSlowdown)) {
			var1 *= 1.0F - 0.15F * (float)(this.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
		}

		return var1;
	}

	public void setPositionAndUpdate(double var1, double var3, double var5) {
		this.setLocationAndAngles(var1, var3, var5, this.rotationYaw, this.rotationPitch);
	}

	public boolean isChild() {
		return false;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	public void func_41030_c(ItemStack var1) {
		this.worldObj.playSoundAtEntity(this, "random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);

		for(int var2 = 0; var2 < 5; ++var2) {
			Vec3D var3 = Vec3D.createVector(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			var3.rotateAroundX(-this.rotationPitch * (float)Math.PI / 180.0F);
			var3.rotateAroundY(-this.rotationYaw * (float)Math.PI / 180.0F);
			Vec3D var4 = Vec3D.createVector(((double)this.rand.nextFloat() - 0.5D) * 0.3D, (double)(-this.rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
			var4.rotateAroundX(-this.rotationPitch * (float)Math.PI / 180.0F);
			var4.rotateAroundY(-this.rotationYaw * (float)Math.PI / 180.0F);
			var4 = var4.addVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
			this.worldObj.spawnParticle("iconcrack_" + var1.getItem().shiftedIndex, var4.xCoord, var4.yCoord, var4.zCoord, var3.xCoord, var3.yCoord + 0.05D, var3.zCoord);
		}

	}
}
