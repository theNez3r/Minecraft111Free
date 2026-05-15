package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet26EntityExpOrb extends Packet {
	public int xpOrbId;
	public int posX;
	public int posY;
	public int posZ;
	public int xpOrbValue;

	public Packet26EntityExpOrb() {
	}

	public Packet26EntityExpOrb(EntityXPOrb var1) {
		this.xpOrbId = var1.entityId;
		this.posX = MathHelper.floor_double(var1.posX * 32.0D);
		this.posY = MathHelper.floor_double(var1.posY * 32.0D);
		this.posZ = MathHelper.floor_double(var1.posZ * 32.0D);
		this.xpOrbValue = var1.getXpValue();
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.xpOrbId = var1.readInt();
		this.posX = var1.readInt();
		this.posY = var1.readInt();
		this.posZ = var1.readInt();
		this.xpOrbValue = var1.readShort();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeInt(this.xpOrbId);
		var1.writeInt(this.posX);
		var1.writeInt(this.posY);
		var1.writeInt(this.posZ);
		var1.writeShort(this.xpOrbValue);
	}

	public void processPacket(NetHandler var1) {
		var1.handleEntityExpOrb(this);
	}

	public int getPacketSize() {
		return 18;
	}
}
