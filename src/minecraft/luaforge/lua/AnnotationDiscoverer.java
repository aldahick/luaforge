package luaforge.lua;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import luaforge.api.LuaLib;
import tiin57.lib.luaj.vm2.lib.LibFunction;

public class AnnotationDiscoverer {
	public static HashMap<String, LibFunction> findLibs() {
		HashMap<String, LibFunction> funcs = new HashMap<String, LibFunction>();
		Vector<Class> classes = new Vector<Class>();
		ModClassLoader cl = ClassLoader.getSystemClassLoader();
		try {
			Field f = ModClassLoader.class.getDeclaredField("classes");
			f.setAccessible(true);
			classes = (Vector<Class>)f.get(cl);
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		for (Class c : classes) {
			System.out.println(c.toString());
			try {
				if (c.isAnnotationPresent(LuaLib.class)) {
					System.out.println("Found an annotated class!");
					Object obj = c.newInstance();
					LibFunction lf = (LibFunction) obj;
					LuaLib ref = (LuaLib)c.getAnnotation(LuaLib.class);
					funcs.put(ref.name(), lf);
				}
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return funcs;
	}
}
