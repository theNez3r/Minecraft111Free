package net.minecraft.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.MinecraftServer;

public class GuiStatsComponent extends JComponent {
	private static final DecimalFormat field_40573_a = new DecimalFormat("########0.000");
	private int[] memoryUse = new int[256];
	private int updateCounter = 0;
	private String[] displayStrings = new String[10];
	private final MinecraftServer field_40572_e;

	public GuiStatsComponent(MinecraftServer var1) {
		this.field_40572_e = var1;
		this.setPreferredSize(new Dimension(356, 226));
		this.setMinimumSize(new Dimension(356, 226));
		this.setMaximumSize(new Dimension(356, 226));
		(new Timer(500, new GuiStatsListener(this))).start();
		this.setBackground(Color.BLACK);
	}

	private void updateStats() {
		long var1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.gc();
		this.displayStrings[0] = "Memory use: " + var1 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
		this.displayStrings[1] = "Threads: " + NetworkManager.numReadThreads + " + " + NetworkManager.numWriteThreads;
		this.displayStrings[2] = "Avg tick: " + field_40573_a.format(this.func_40571_a(this.field_40572_e.field_40027_f) * 1.0E-6D) + " ms";

		for(int var3 = 0; var3 < this.field_40572_e.worldMngr.length; ++var3) {
			this.displayStrings[3 + var3] = "Lvl " + var3 + " tick: " + field_40573_a.format(this.func_40571_a(this.field_40572_e.field_40028_g[var3]) * 1.0E-6D) + " ms";
			if(this.field_40572_e.worldMngr[var3] != null && this.field_40572_e.worldMngr[var3].chunkProviderServer != null) {
				this.displayStrings[3 + var3] = this.displayStrings[3 + var3] + ", " + this.field_40572_e.worldMngr[var3].chunkProviderServer.func_46040_d();
			}
		}

		this.memoryUse[this.updateCounter++ & 255] = (int)(var1 * 100L / Runtime.getRuntime().maxMemory());
		this.repaint();
	}

	private double func_40571_a(long[] var1) {
		long var2 = 0L;

		for(int var4 = 0; var4 < var1.length; ++var4) {
			var2 += var1[var4];
		}

		return (double)var2 / (double)var1.length;
	}

	public void paint(Graphics var1) {
		var1.setColor(new Color(16777215));
		var1.fillRect(0, 0, 356, 226);

		int var2;
		for(var2 = 0; var2 < 256; ++var2) {
			int var3 = this.memoryUse[var2 + this.updateCounter & 255];
			var1.setColor(new Color(var3 + 28 << 16));
			var1.fillRect(var2, 100 - var3, 1, var3);
		}

		var1.setColor(Color.BLACK);

		for(var2 = 0; var2 < this.displayStrings.length; ++var2) {
			String var4 = this.displayStrings[var2];
			if(var4 != null) {
				var1.drawString(var4, 32, 116 + var2 * 16);
			}
		}

	}

	static void update(GuiStatsComponent var0) {
		var0.updateStats();
	}
}
