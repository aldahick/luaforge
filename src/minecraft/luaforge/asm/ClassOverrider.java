package luaforge.asm;

import java.io.InputStream;

public class ClassOverrider {
	public static InputStream override(String name) {
		return override(name, new byte[0]);
	}

	public static InputStream override(String className, byte[] bytes) {

		try {
			return ClassOverrider.class.getResourceAsStream("/classes/" + className.replace('.', '/') + ".class");
		} catch (Exception e) {
			throw new RuntimeException("Error overriding class: " + className, e);
		}

	}
}
