package net.minecraft.src;

public class EntityMagmaCube extends EntitySlime {
	public EntityMagmaCube(World var1) {
		super(var1);
		this.texture = "/mob/lava.png";
		this.isImmuneToFire = true;
		this.landMovementFactor = 0.2F;
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.difficultySetting > 0 && this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	public int getTotalArmorValue() {
		return this.getSlimeSize() * 3;
	}

	public float getEntityBrightness(float var1) {
		return 1.0F;
	}

	protected String func_40120_w() {
		return "flame";
	}

	protected EntitySlime func_40114_y() {
		return new EntityMagmaCube(this.worldObj);
	}

	protected int getDropItemId() {
		return Item.magmaCream.shiftedIndex;
	}

	protected void dropFewItems(boolean var1, int var2) {
		int var3 = this.getDropItemId();
		if(var3 > 0 && this.getSlimeSize() > 1) {
			int var4 = this.rand.nextInt(4) - 2;
			if(var2 > 0) {
				var4 += this.rand.nextInt(var2 + 1);
			}

			for(int var5 = 0; var5 < var4; ++var5) {
				this.dropItem(var3, 1);
			}
		}

	}

	public boolean isBurning() {
		return false;
	}

	protected int func_40115_A() {
		return super.func_40115_A() * 4;
	}

	protected void func_40116_B() {
		this.field_40122_a *= 0.9F;
	}

	protected void jump() {
		this.motionY = (double)(0.42F + (float)this.getSlimeSize() * 0.1F);
		this.isAirBorne = true;
	}

	protected void fall(float var1) {
	}

	protected boolean func_40119_C() {
		return true;
	}

	protected int func_40113_D() {
		return super.func_40113_D() + 2;
	}

	protected String getHurtSound() {
		return "mob.slime";
	}

	protected String getDeathSound() {
		return "mob.slime";
	}

	protected String func_40118_E() {
		return this.getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
	}

	public boolean handleLavaMovement() {
		return false;
	}

	protected boolean func_40121_G() {
		return true;
	}
}
