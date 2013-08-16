package scriptforge.asm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Vector;

import net.minecraft.launchwrapper.LaunchClassLoader;
import scriptforge.Scriptforge;
import scriptforge.api.LuaLib;
import tiin57.lib.luaj.vm2.LuaValue;
import tiin57.lib.luaj.vm2.lib.LibFunction;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModClassLoader;

public class AnnotationDiscoverer {
	public static HashMap<String, LuaValue> findLibs() {
		HashMap<String, LuaValue> funcs = new HashMap<String, LuaValue>();
		try {
			Vector<Class> classes = new Vector<Class>();
			LaunchClassLoader cl;
			ModClassLoader mcl;
			Field mclf = Loader.class.getDeclaredField("modClassLoader");
			mclf.setAccessible(true);
			mcl = (ModClassLoader)mclf.get(Loader.instance());
			loadLibs(mcl);
			Field clf = ModClassLoader.class.getDeclaredField("mainClassLoader");
			clf.setAccessible(true);
			cl = (LaunchClassLoader)clf.get(mcl);
			Field f = ClassLoader.class.getDeclaredField("classes");
			f.setAccessible(true);
			classes = (Vector<Class>)f.get(cl);
			if (classes.isEmpty()) {
				return funcs;
			}
			for (int i=0; i<classes.size(); i++) {
				Class c = classes.get(i);
				if (c == null || c.getPackage()==null || c.getPackage().getName()==null) {
					continue;
				}
				
				if (c.isAnnotationPresent(LuaLib.class)) {
					Object obj = c.newInstance();
					if (obj instanceof LuaValue) {
						LuaValue lf = (LuaValue) obj;
						LuaLib ref = (LuaLib)c.getAnnotation(LuaLib.class);
						funcs.put(ref.name(), lf);
					}
				}
				
			}
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		}
		return funcs;
	}
	private static void loadLibs(ModClassLoader mcl) throws ClassNotFoundException {
		for (String c : Scriptforge.rawlibnames) {
			mcl.loadClass(c);
		}
	}
}
