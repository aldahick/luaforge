package luaforge.asm;

import java.util.HashMap;

public class ObfuscationMappings {
	private static HashMap<String, String> classMappings = new HashMap<String, String>();
	private static HashMap<String, HashMap<String, String>> methodMappings = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, HashMap<String, String>> descriptorMappings = new HashMap<String, HashMap<String, String>>();
	public static final boolean isObfuscated = isObfuscated();

	public static void initialize() {
		classMappings.put("cpw.mods.fml.common.Loader",
				"cpw.mods.fml.common.Loader");

		HashMap<String,String> loaderMappings = new HashMap<String,String>();
		
		loaderMappings.put("identifyMods", "identifyMods");
		
		methodMappings.put("cpw.mods.fml.common.Loader", loaderMappings);

		HashMap<String,String> loaderDescriptorMappings = new HashMap<String,String>();
		
		loaderDescriptorMappings.put("identifyMods", "()Lcpw/mods/fml/common/discovery/ModDiscoverer;");
		
		descriptorMappings.put("cpw.mods.fml.common.Loader", loaderDescriptorMappings);
	}

	public static String getClassName(String name) {
		return (isObfuscated) ? classMappings.get(name) : name;
	}

	public static String getMethodName(String className, String name) {
		return (isObfuscated) ? methodMappings.get(className).get(name) : name;
	}

	public static String getDescriptor(String className, String name) {
		return descriptorMappings.get(className).get(name);
	}

	private static boolean isObfuscated() {
		return (ClassLoader.getSystemResourceAsStream("net/minecraft/src/") == null) ? true : false;
	}
}
