package net.minecraft.src;

public interface IServer {
	int getIntProperty(String var1, int var2);

	String getStringProperty(String var1, String var2);

	void setProperty(String var1, Object var2);

	void saveProperties();

	String getSettingsFilename();

	String getHostname();

	int getPort();

	String getMotd();

	String getVersionString();

	int playersOnline();

	int getMaxPlayers();

	String[] getPlayerNamesAsList();

	String getWorldName();

	String getPlugin();

	void func_40010_o();

	String handleRConCommand(String var1);

	boolean isDebuggingEnabled();

	void log(String var1);

	void logWarning(String var1);

	void logSevere(String var1);

	void logIn(String var1);
}
