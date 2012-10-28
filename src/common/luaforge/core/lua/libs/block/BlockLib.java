package luaforge.core.lua.libs.block;

import java.io.File;

import net.minecraft.src.Material;

import org.luaj.vm2.Varargs;

import luaforge.core.api.LuaMethod;

public class BlockLib {
    
    @LuaMethod (name = "block")
    public static Varargs createBlock(Varargs args) {
    	int numberOfArgs = args.narg();
    	Object[] arg = new Object[] {};
    	for (int i=0; i<numberOfArgs; i++) {
    		arg[i] = (Object) args.arg(i);
    	}
    	int id = (Integer) arg[0];
    	int iconIndex = (Integer) arg[1];
    	String mString = arg[2].toString();
    	Material material = null;
    	Material[] materials = new Material[] {
    		Material.air,
    		Material.cactus,
    		Material.cake,
    		Material.circuits,
    		Material.clay,
    		Material.cloth,
    		Material.craftedSnow,
    		Material.dragonEgg,
    		Material.fire,
    		Material.glass,
    		Material.grass,
    		Material.ground,
    		Material.ice,
    		Material.iron,
    		Material.lava,
    		Material.leaves,
    		Material.piston,
    		Material.plants,
    		Material.portal,
    		Material.pumpkin,
    		Material.redstoneLight,
    		Material.rock,
    		Material.sand,
    		Material.snow,
    		Material.sponge,
    		Material.tnt,
    		Material.vine,
    		Material.water,
    		Material.web,
    		Material.wood
    	};
    	for (int i=0; i<materials.length; i++) {
    		if (mString.equals(materials[i].toString())) {
    			material = materials[i];
    		}
    	}
    	String name = arg[3].toString();
    	String textureFileString = arg[4].toString();
    	String[] otherArgs = new String[] {
    		name,
    		textureFileString
    	};
    	new BlockTemplate(id, iconIndex, material, otherArgs);
		return args;
    }
    
}
