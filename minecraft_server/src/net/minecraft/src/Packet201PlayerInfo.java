package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet201PlayerInfo extends Packet {
	public String name;
	public boolean isConnected;
	public int ping;

	public Packet201PlayerInfo() {
	}

	public Packet201PlayerInfo(String var1, boolean var2, int var3) {
		this.name = var1;
		this.isConnected = var2;
		this.ping = var3;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.name = readString(var1, 16);
		this.isConnected = var1.readByte() != 0;
		this.ping = var1.readShort();
	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		writeString(this.name, var1);
		var1.writeByte(this.isConnected ? 1 : 0);
		var1.writeShort(this.ping);
	}

	public void processPacket(NetHandler var1) {
		var1.handlePlayerInfo(this);
	}

	public int getPacketSize() {
		return this.name.length() + 2 + 1 + 2;
	}
}
