package net.minecraft.src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import net.minecraft.server.MinecraftServer;

class NetworkAcceptThread extends Thread {
	final MinecraftServer mcServer;
	final NetworkListenThread netWorkListener;

	NetworkAcceptThread(NetworkListenThread var1, String var2, MinecraftServer var3) {
		super(var2);
		this.netWorkListener = var1;
		this.mcServer = var3;
	}

	public void run() {
		while(this.netWorkListener.isListening) {
			try {
				Socket var1 = NetworkListenThread.getServerSocket(this.netWorkListener).accept();
				if(var1 != null) {
					HashMap var2 = NetworkListenThread.func_35504_b(this.netWorkListener);
					synchronized(var2) {
						InetAddress var3 = var1.getInetAddress();
						if(NetworkListenThread.func_35504_b(this.netWorkListener).containsKey(var3) && System.currentTimeMillis() - ((Long)NetworkListenThread.func_35504_b(this.netWorkListener).get(var3)).longValue() < 5000L) {
							NetworkListenThread.func_35504_b(this.netWorkListener).put(var3, Long.valueOf(System.currentTimeMillis()));
							var1.close();
							continue;
						}

						NetworkListenThread.func_35504_b(this.netWorkListener).put(var3, Long.valueOf(System.currentTimeMillis()));
					}

					NetLoginHandler var7 = new NetLoginHandler(this.mcServer, var1, "Connection #" + NetworkListenThread.func_712_b(this.netWorkListener));
					NetworkListenThread.func_716_a(this.netWorkListener, var7);
				}
			} catch (IOException var6) {
				var6.printStackTrace();
			}
		}

	}
}
