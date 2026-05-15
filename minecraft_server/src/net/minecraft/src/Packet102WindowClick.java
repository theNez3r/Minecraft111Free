package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet102WindowClick extends Packet {
	public int window_Id;
	public int inventorySlot;
	public int mouseClick;
	public short action;
	public ItemStack itemStack;
	public boolean holdingShift;

	public void processPacket(NetHandler var1) {
		var1.handleWindowClick(this);
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.window_Id = var1.readByte();
		this.inventorySlot = var1.readShort();
		this.mouseClick = var1.readByte();
		this.action = var1.readShort();
		this.holdingShift = var1.readBoolean();
		this.itemStack = this.func_40262_b(var1);
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		var1.writeByte(this.window_Id);
		var1.writeShort(this.inventorySlot);
		var1.writeByte(this.mouseClick);
		var1.writeShort(this.action);
		var1.writeBoolean(this.holdingShift);
		this.writeItemStack(this.itemStack, var1);
	}

	public int getPacketSize() {
		return 11;
	}
}
