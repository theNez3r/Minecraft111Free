package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet250CustomPayload extends Packet {
	public String field_44005_a;
	public int field_44003_b;
	public byte[] field_44004_c;

	public void readPacketData(DataInputStream var1) throws IOException {
		this.field_44005_a = readString(var1, 16);
		this.field_44003_b = var1.readShort();
		if(this.field_44003_b > 0 && this.field_44003_b < Short.MAX_VALUE) {
			this.field_44004_c = new byte[this.field_44003_b];
			var1.read(this.field_44004_c);
		}

	}

	public void writePacketData(DataOutputStream var1) throws IOException {
		writeString(this.field_44005_a, var1);
		var1.writeShort((short)this.field_44003_b);
		if(this.field_44004_c != null) {
			var1.write(this.field_44004_c);
		}

	}

	public void processPacket(NetHandler var1) {
		var1.func_44001_a(this);
	}

	public int getPacketSize() {
		return 2 + this.field_44005_a.length() * 2 + 2 + this.field_44003_b;
	}
}
