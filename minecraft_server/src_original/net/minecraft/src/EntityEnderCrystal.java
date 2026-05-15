package net.minecraft.src;

public class EntityEnderCrystal extends Entity {
	public int field_41023_a = 0;
	public int field_41022_b;

	public EntityEnderCrystal(World var1) {
		super(var1);
		this.preventEntitySpawning = true;
		this.setSize(2.0F, 2.0F);
		this.yOffset = this.height / 2.0F;
		this.field_41022_b = 5;
		this.field_41023_a = this.rand.nextInt(100000);
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
		this.dataWatcher.addObject(8, Integer.valueOf(this.field_41022_b));
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		++this.field_41023_a;
		this.dataWatcher.updateObject(8, Integer.valueOf(this.field_41022_b));
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.posY);
		int var3 = MathHelper.floor_double(this.posZ);
		if(this.worldObj.getBlockId(var1, var2, var3) != Block.fire.blockID) {
			this.worldObj.setBlockWithNotify(var1, var2, var3, Block.fire.blockID);
		}

	}

	protected void writeEntityToNBT(NBTTagCompound var1) {
	}

	protected void readEntityFromNBT(NBTTagCompound var1) {
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource var1, int var2) {
		if(!this.isDead && !this.worldObj.singleplayerWorld) {
			this.field_41022_b = 0;
			if(this.field_41022_b <= 0) {
				if(!this.worldObj.singleplayerWorld) {
					this.setEntityDead();
					this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 6.0F);
				} else {
					this.setEntityDead();
				}
			}
		}

		return true;
	}
}
