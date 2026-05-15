package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler {
	public static boolean profilingEnabled = false;
	private static List sectionList = new ArrayList();
	private static List timestampList = new ArrayList();
	private static String profilingSection = "";
	private static Map profilingMap = new HashMap();

	public static void startSection(String var0) {
		if(profilingEnabled) {
			if(profilingSection.length() > 0) {
				profilingSection = profilingSection + ".";
			}

			profilingSection = profilingSection + var0;
			sectionList.add(profilingSection);
			timestampList.add(Long.valueOf(System.nanoTime()));
		}
	}

	public static void endSection() {
		if(profilingEnabled) {
			long var0 = System.nanoTime();
			long var2 = ((Long)timestampList.remove(timestampList.size() - 1)).longValue();
			sectionList.remove(sectionList.size() - 1);
			long var4 = var0 - var2;
			if(profilingMap.containsKey(profilingSection)) {
				profilingMap.put(profilingSection, Long.valueOf(((Long)profilingMap.get(profilingSection)).longValue() + var4));
			} else {
				profilingMap.put(profilingSection, Long.valueOf(var4));
			}

			profilingSection = sectionList.size() > 0 ? (String)sectionList.get(sectionList.size() - 1) : "";
		}
	}

	public static void endStartSection(String var0) {
		endSection();
		startSection(var0);
	}
}
