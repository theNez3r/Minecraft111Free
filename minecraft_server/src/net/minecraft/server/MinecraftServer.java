package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ConsoleCommandHandler;
import net.minecraft.src.ConsoleLogManager;
import net.minecraft.src.ConvertProgressUpdater;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.EnumWorldType;
import net.minecraft.src.ICommandListener;
import net.minecraft.src.IProgressUpdate;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.IServer;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PropertyManager;
import net.minecraft.src.RConConsoleSource;
import net.minecraft.src.RConThreadMain;
import net.minecraft.src.RConThreadQuery;
import net.minecraft.src.SaveConverterMcRegion;
import net.minecraft.src.SaveOldDir;
import net.minecraft.src.ServerCommand;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.ServerGUI;
import net.minecraft.src.StatList;
import net.minecraft.src.ThreadCommandReader;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.ThreadSleepForever;
import net.minecraft.src.Vec3D;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldServer;
import net.minecraft.src.WorldServerMulti;
import net.minecraft.src.WorldSettings;

public class MinecraftServer implements Runnable, ICommandListener, IServer {
	public static Logger logger = Logger.getLogger("Minecraft");
	public static HashMap field_6037_b = new HashMap();
	private String hostname;
	private int serverPort;
	public NetworkListenThread networkServer;
	public PropertyManager propertyManagerObj;
	public WorldServer[] worldMngr;
	public long[] field_40027_f = new long[100];
	public long[][] field_40028_g;
	public ServerConfigurationManager configManager;
	private ConsoleCommandHandler commandHandler;
	private boolean serverRunning = true;
	public boolean serverStopped = false;
	int deathTime = 0;
	public String currentTask;
	public int percentDone;
	private List playersOnline = new ArrayList();
	private List commands = Collections.synchronizedList(new ArrayList());
	public EntityTracker[] entityTracker = new EntityTracker[3];
	public boolean onlineMode;
	public boolean spawnPeacefulMobs;
	public boolean field_44002_p;
	public boolean pvpOn;
	public boolean allowFlight;
	public String motd;
	private RConThreadQuery rconQueryThread;
	private RConThreadMain rconMainThread;

	public MinecraftServer() {
		new ThreadSleepForever(this);
	}

	private boolean startServer() throws UnknownHostException {
		this.commandHandler = new ConsoleCommandHandler(this);
		ThreadCommandReader var1 = new ThreadCommandReader(this);
		var1.setDaemon(true);
		var1.start();
		ConsoleLogManager.init();
		logger.info("Starting minecraft server version 1.1");
		if(Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
			logger.warning("**** NOT ENOUGH RAM!");
			logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
		}

		logger.info("Loading properties");
		this.propertyManagerObj = new PropertyManager(new File("server.properties"));
		this.hostname = this.propertyManagerObj.getStringProperty("server-ip", "");
		this.onlineMode = this.propertyManagerObj.getBooleanProperty("online-mode", true);
		this.spawnPeacefulMobs = this.propertyManagerObj.getBooleanProperty("spawn-animals", true);
		this.field_44002_p = this.propertyManagerObj.getBooleanProperty("spawn-npcs", true);
		this.pvpOn = this.propertyManagerObj.getBooleanProperty("pvp", true);
		this.allowFlight = this.propertyManagerObj.getBooleanProperty("allow-flight", false);
		this.motd = this.propertyManagerObj.getStringProperty("motd", "A Minecraft Server");
		this.motd.replace('\u00a7', '$');
		InetAddress var2 = null;
		if(this.hostname.length() > 0) {
			var2 = InetAddress.getByName(this.hostname);
		}

		this.serverPort = this.propertyManagerObj.getIntProperty("server-port", 25565);
		logger.info("Starting Minecraft server on " + (this.hostname.length() == 0 ? "*" : this.hostname) + ":" + this.serverPort);

		try {
			this.networkServer = new NetworkListenThread(this, var2, this.serverPort);
		} catch (IOException var13) {
			logger.warning("**** FAILED TO BIND TO PORT!");
			logger.log(Level.WARNING, "The exception was: " + var13.toString());
			logger.warning("Perhaps a server is already running on that port?");
			return false;
		}

		if(!this.onlineMode) {
			logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
			logger.warning("The server will make no attempt to authenticate usernames. Beware.");
			logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
			logger.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
		}

		this.configManager = new ServerConfigurationManager(this);
		this.entityTracker[0] = new EntityTracker(this, 0);
		this.entityTracker[1] = new EntityTracker(this, -1);
		this.entityTracker[2] = new EntityTracker(this, 1);
		long var3 = System.nanoTime();
		String var5 = this.propertyManagerObj.getStringProperty("level-name", "world");
		String var6 = this.propertyManagerObj.getStringProperty("level-seed", "");
		String var7 = this.propertyManagerObj.getStringProperty("level-type", "DEFAULT");
		long var8 = (new Random()).nextLong();
		if(var6.length() > 0) {
			try {
				long var10 = Long.parseLong(var6);
				if(var10 != 0L) {
					var8 = var10;
				}
			} catch (NumberFormatException var12) {
				var8 = (long)var6.hashCode();
			}
		}

		EnumWorldType var14 = EnumWorldType.func_46049_a(var7);
		if(var14 == null) {
			var14 = EnumWorldType.DEFAULT;
		}

		logger.info("Preparing level \"" + var5 + "\"");
		this.initWorld(new SaveConverterMcRegion(new File(".")), var5, var8, var14);
		logger.info("Done (" + (System.nanoTime() - var3) + "ns)! For help, type \"help\" or \"?\"");
		if(this.propertyManagerObj.getBooleanProperty("enable-query", false)) {
			logger.info("Starting GS4 status listener");
			this.rconQueryThread = new RConThreadQuery(this);
			this.rconQueryThread.startThread();
		}

		if(this.propertyManagerObj.getBooleanProperty("enable-rcon", false)) {
			logger.info("Starting remote control listener");
			this.rconMainThread = new RConThreadMain(this);
			this.rconMainThread.startThread();
		}

		return true;
	}

	private void initWorld(ISaveFormat var1, String var2, long var3, EnumWorldType var5) {
		if(var1.isOldSaveType(var2)) {
			logger.info("Converting map!");
			var1.convertMapFormat(var2, new ConvertProgressUpdater(this));
		}

		this.worldMngr = new WorldServer[3];
		this.field_40028_g = new long[this.worldMngr.length][100];
		int var6 = this.propertyManagerObj.getIntProperty("gamemode", 0);
		var6 = WorldSettings.validGameType(var6);
		logger.info("Default game type: " + var6);
		boolean var7 = this.propertyManagerObj.getBooleanProperty("generate-structures", true);
		WorldSettings var8 = new WorldSettings(var3, var6, var7, false, var5);
		SaveOldDir var9 = new SaveOldDir(new File("."), var2, true);

		for(int var10 = 0; var10 < this.worldMngr.length; ++var10) {
			byte var11 = 0;
			if(var10 == 1) {
				var11 = -1;
			}

			if(var10 == 2) {
				var11 = 1;
			}

			if(var10 == 0) {
				this.worldMngr[var10] = new WorldServer(this, var9, var2, var11, var8);
			} else {
				this.worldMngr[var10] = new WorldServerMulti(this, var9, var2, var11, var8, this.worldMngr[0]);
			}

			this.worldMngr[var10].addWorldAccess(new WorldManager(this, this.worldMngr[var10]));
			this.worldMngr[var10].difficultySetting = this.propertyManagerObj.getIntProperty("difficulty", 1);
			this.worldMngr[var10].setAllowedSpawnTypes(this.propertyManagerObj.getBooleanProperty("spawn-monsters", true), this.spawnPeacefulMobs);
			this.worldMngr[var10].getWorldInfo().setGameType(var6);
			this.configManager.setPlayerManager(this.worldMngr);
		}

		short var22 = 196;
		long var23 = System.currentTimeMillis();

		for(int var13 = 0; var13 < 1; ++var13) {
			logger.info("Preparing start region for level " + var13);
			WorldServer var14 = this.worldMngr[var13];
			ChunkCoordinates var15 = var14.getSpawnPoint();

			for(int var16 = -var22; var16 <= var22 && this.serverRunning; var16 += 16) {
				for(int var17 = -var22; var17 <= var22 && this.serverRunning; var17 += 16) {
					long var18 = System.currentTimeMillis();
					if(var18 < var23) {
						var23 = var18;
					}

					if(var18 > var23 + 1000L) {
						int var20 = (var22 * 2 + 1) * (var22 * 2 + 1);
						int var21 = (var16 + var22) * (var22 * 2 + 1) + var17 + 1;
						this.outputPercentRemaining("Preparing spawn area", var21 * 100 / var20);
						var23 = var18;
					}

					var14.chunkProviderServer.loadChunk(var15.posX + var16 >> 4, var15.posZ + var17 >> 4);

					while(var14.updatingLighting() && this.serverRunning) {
					}
				}
			}
		}

		this.clearCurrentTask();
	}

	private void outputPercentRemaining(String var1, int var2) {
		this.currentTask = var1;
		this.percentDone = var2;
		logger.info(var1 + ": " + var2 + "%");
	}

	private void clearCurrentTask() {
		this.currentTask = null;
		this.percentDone = 0;
	}

	private void saveServerWorld() {
		logger.info("Saving chunks");

		for(int var1 = 0; var1 < this.worldMngr.length; ++var1) {
			WorldServer var2 = this.worldMngr[var1];
			var2.saveWorld(true, (IProgressUpdate)null);
			var2.func_30006_w();
		}

	}

	private void stopServer() {
		logger.info("Stopping server");
		if(this.configManager != null) {
			this.configManager.savePlayerStates();
		}

		for(int var1 = 0; var1 < this.worldMngr.length; ++var1) {
			WorldServer var2 = this.worldMngr[var1];
			if(var2 != null) {
				this.saveServerWorld();
			}
		}

	}

	public void initiateShutdown() {
		this.serverRunning = false;
	}

	public void run() {
		try {
			if(this.startServer()) {
				long var1 = System.currentTimeMillis();

				for(long var3 = 0L; this.serverRunning; Thread.sleep(1L)) {
					long var5 = System.currentTimeMillis();
					long var7 = var5 - var1;
					if(var7 > 2000L) {
						logger.warning("Can\'t keep up! Did the system time change, or is the server overloaded?");
						var7 = 2000L;
					}

					if(var7 < 0L) {
						logger.warning("Time ran backwards! Did the system time change?");
						var7 = 0L;
					}

					var3 += var7;
					var1 = var5;
					if(this.worldMngr[0].isAllPlayersFullyAsleep()) {
						this.doTick();
						var3 = 0L;
					} else {
						while(var3 > 50L) {
							var3 -= 50L;
							this.doTick();
						}
					}
				}
			} else {
				while(this.serverRunning) {
					this.commandLineParser();

					try {
						Thread.sleep(10L);
					} catch (InterruptedException var57) {
						var57.printStackTrace();
					}
				}
			}
		} catch (Throwable var58) {
			var58.printStackTrace();
			logger.log(Level.SEVERE, "Unexpected exception", var58);

			while(this.serverRunning) {
				this.commandLineParser();

				try {
					Thread.sleep(10L);
				} catch (InterruptedException var56) {
					var56.printStackTrace();
				}
			}
		} finally {
			try {
				this.stopServer();
				this.serverStopped = true;
			} catch (Throwable var54) {
				var54.printStackTrace();
			} finally {
				System.exit(0);
			}

		}

	}

	private void doTick() {
		long var1 = System.nanoTime();
		ArrayList var3 = new ArrayList();
		Iterator var4 = field_6037_b.keySet().iterator();

		while(var4.hasNext()) {
			String var5 = (String)var4.next();
			int var6 = ((Integer)field_6037_b.get(var5)).intValue();
			if(var6 > 0) {
				field_6037_b.put(var5, Integer.valueOf(var6 - 1));
			} else {
				var3.add(var5);
			}
		}

		int var9;
		for(var9 = 0; var9 < var3.size(); ++var9) {
			field_6037_b.remove(var3.get(var9));
		}

		AxisAlignedBB.clearBoundingBoxPool();
		Vec3D.initialize();
		++this.deathTime;

		for(var9 = 0; var9 < this.worldMngr.length; ++var9) {
			long var10 = System.nanoTime();
			if(var9 == 0 || this.propertyManagerObj.getBooleanProperty("allow-nether", true)) {
				WorldServer var7 = this.worldMngr[var9];
				if(this.deathTime % 20 == 0) {
					this.configManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(var7.getWorldTime()), var7.worldProvider.worldType);
				}

				var7.tick();

				while(true) {
					if(!var7.updatingLighting()) {
						var7.updateEntities();
						break;
					}
				}
			}

			this.field_40028_g[var9][this.deathTime % 100] = System.nanoTime() - var10;
		}

		this.networkServer.handleNetworkListenThread();
		this.configManager.onTick();

		for(var9 = 0; var9 < this.entityTracker.length; ++var9) {
			this.entityTracker[var9].updateTrackedEntities();
		}

		for(var9 = 0; var9 < this.playersOnline.size(); ++var9) {
			((IUpdatePlayerListBox)this.playersOnline.get(var9)).update();
		}

		try {
			this.commandLineParser();
		} catch (Exception var8) {
			logger.log(Level.WARNING, "Unexpected exception while parsing console command", var8);
		}

		this.field_40027_f[this.deathTime % 100] = System.nanoTime() - var1;
	}

	public void addCommand(String var1, ICommandListener var2) {
		this.commands.add(new ServerCommand(var1, var2));
	}

	public void commandLineParser() {
		while(this.commands.size() > 0) {
			ServerCommand var1 = (ServerCommand)this.commands.remove(0);
			this.commandHandler.handleCommand(var1);
		}

	}

	public void addToOnlinePlayerList(IUpdatePlayerListBox var1) {
		this.playersOnline.add(var1);
	}

	public static void main(String[] var0) {
		StatList.func_27092_a();

		try {
			MinecraftServer var1 = new MinecraftServer();
			if(!GraphicsEnvironment.isHeadless() && (var0.length <= 0 || !var0[0].equals("nogui"))) {
				ServerGUI.initGui(var1);
			}

			(new ThreadServerApplication("Server thread", var1)).start();
		} catch (Exception var2) {
			logger.log(Level.SEVERE, "Failed to start the minecraft server", var2);
		}

	}

	public File getFile(String var1) {
		return new File(var1);
	}

	public void log(String var1) {
		logger.info(var1);
	}

	public void logWarning(String var1) {
		logger.warning(var1);
	}

	public String getUsername() {
		return "CONSOLE";
	}

	public WorldServer getWorldManager(int var1) {
		return var1 == -1 ? this.worldMngr[1] : (var1 == 1 ? this.worldMngr[2] : this.worldMngr[0]);
	}

	public EntityTracker getEntityTracker(int var1) {
		return var1 == -1 ? this.entityTracker[1] : (var1 == 1 ? this.entityTracker[2] : this.entityTracker[0]);
	}

	public int getIntProperty(String var1, int var2) {
		return this.propertyManagerObj.getIntProperty(var1, var2);
	}

	public String getStringProperty(String var1, String var2) {
		return this.propertyManagerObj.getStringProperty(var1, var2);
	}

	public void setProperty(String var1, Object var2) {
		this.propertyManagerObj.setProperty(var1, var2);
	}

	public void saveProperties() {
		this.propertyManagerObj.saveProperties();
	}

	public String getSettingsFilename() {
		File var1 = this.propertyManagerObj.func_40656_c();
		return var1 != null ? var1.getAbsolutePath() : "No settings file";
	}

	public String getHostname() {
		return this.hostname;
	}

	public int getPort() {
		return this.serverPort;
	}

	public String getMotd() {
		return this.motd;
	}

	public String getVersionString() {
		return "1.1";
	}

	public int playersOnline() {
		return this.configManager.playersOnline();
	}

	public int getMaxPlayers() {
		return this.configManager.getMaxPlayers();
	}

	public String[] getPlayerNamesAsList() {
		return this.configManager.getPlayerNamesAsList();
	}

	public String getWorldName() {
		return this.propertyManagerObj.getStringProperty("level-name", "world");
	}

	public String getPlugin() {
		return "";
	}

	public void func_40010_o() {
	}

	public String handleRConCommand(String var1) {
		RConConsoleSource.instance.resetLog();
		this.commandHandler.handleCommand(new ServerCommand(var1, RConConsoleSource.instance));
		return RConConsoleSource.instance.getLogContents();
	}

	public boolean isDebuggingEnabled() {
		return false;
	}

	public void logSevere(String var1) {
		logger.log(Level.SEVERE, var1);
	}

	public void logIn(String var1) {
		if(this.isDebuggingEnabled()) {
			logger.log(Level.INFO, var1);
		}

	}

	public String[] getBannedIPsList() {
		return (String[])this.configManager.getBannedIPsList().toArray(new String[0]);
	}

	public String[] getBannedPlayersList() {
		return (String[])this.configManager.getBannedPlayersList().toArray(new String[0]);
	}

	public static boolean isServerRunning(MinecraftServer var0) {
		return var0.serverRunning;
	}
}
