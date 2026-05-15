package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeMap;

public class StringTranslate {
	private static StringTranslate instance = new StringTranslate();
	private Properties translateTable = new Properties();
	private TreeMap field_44013_c;
	private String field_44012_d;
	private boolean field_46121_e;

	private StringTranslate() {
		this.func_44009_b();
		this.func_44010_a("en_US");
	}

	public static StringTranslate getInstance() {
		return instance;
	}

	private void func_44009_b() {
		TreeMap var1 = new TreeMap();

		try {
			BufferedReader var2 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/languages.txt"), "UTF-8"));

			for(String var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
				String[] var4 = var3.split("=");
				if(var4 != null && var4.length == 2) {
					var1.put(var4[0], var4[1]);
				}
			}
		} catch (IOException var5) {
			var5.printStackTrace();
			return;
		}

		this.field_44013_c = var1;
	}

	private void func_44011_a(Properties var1, String var2) throws IOException {
		BufferedReader var3 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/" + var2 + ".lang"), "UTF-8"));

		for(String var4 = var3.readLine(); var4 != null; var4 = var3.readLine()) {
			var4 = var4.trim();
			if(!var4.startsWith("#")) {
				String[] var5 = var4.split("=");
				if(var5 != null && var5.length == 2) {
					var1.setProperty(var5[0], var5[1]);
				}
			}
		}

	}

	public void func_44010_a(String var1) {
		if(!var1.equals(this.field_44012_d)) {
			Properties var2 = new Properties();

			try {
				this.func_44011_a(var2, "en_US");
			} catch (IOException var8) {
			}

			this.field_46121_e = false;
			if(!"en_US".equals(var1)) {
				try {
					this.func_44011_a(var2, var1);
					Enumeration var3 = var2.propertyNames();

					label47:
					while(true) {
						while(true) {
							Object var5;
							do {
								if(!var3.hasMoreElements() || this.field_46121_e) {
									break label47;
								}

								Object var4 = var3.nextElement();
								var5 = var2.get(var4);
							} while(var5 == null);

							String var6 = var5.toString();

							for(int var7 = 0; var7 < var6.length(); ++var7) {
								if(var6.charAt(var7) >= 256) {
									this.field_46121_e = true;
									break;
								}
							}
						}
					}
				} catch (IOException var9) {
					var9.printStackTrace();
					return;
				}
			}

			this.field_44012_d = var1;
			this.translateTable = var2;
		}
	}

	public String translateKey(String var1) {
		return this.translateTable.getProperty(var1, var1);
	}

	public String translateKeyFormat(String var1, Object... var2) {
		String var3 = this.translateTable.getProperty(var1, var1);
		return String.format(var3, var2);
	}
}
