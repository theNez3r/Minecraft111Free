package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;

public class SaveFormatOld implements ISaveFormat {
	protected final File savesDirectory;

	public SaveFormatOld(File var1) {
		if(!var1.exists()) {
			var1.mkdirs();
		}

		this.savesDirectory = var1;
	}

	public WorldInfo getWorldInfo(String var1) {
		File var2 = new File(this.savesDirectory, var1);
		if(!var2.exists()) {
			return null;
		} else {
			File var3 = new File(var2, "level.dat");
			NBTTagCompound var4;
			NBTTagCompound var5;
			if(var3.exists()) {
				try {
					var4 = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(var3));
					var5 = var4.getCompoundTag("Data");
					return new WorldInfo(var5);
				} catch (Exception var7) {
					var7.printStackTrace();
				}
			}

			var3 = new File(var2, "level.dat_old");
			if(var3.exists()) {
				try {
					var4 = CompressedStreamTools.loadGzippedCompoundFromOutputStream(new FileInputStream(var3));
					var5 = var4.getCompoundTag("Data");
					return new WorldInfo(var5);
				} catch (Exception var6) {
					var6.printStackTrace();
				}
			}

			return null;
		}
	}

	protected static void deleteFiles(File[] var0) {
		for(int var1 = 0; var1 < var0.length; ++var1) {
			if(var0[var1].isDirectory()) {
				System.out.println("Deleting " + var0[var1]);
				deleteFiles(var0[var1].listFiles());
			}

			var0[var1].delete();
		}

	}

	public ISaveHandler getSaveLoader(String var1, boolean var2) {
		return new PlayerNBTManager(this.savesDirectory, var1, var2);
	}

	public boolean isOldSaveType(String var1) {
		return false;
	}

	public boolean convertMapFormat(String var1, IProgressUpdate var2) {
		return false;
	}
}
