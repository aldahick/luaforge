package luaforge.asm;

import java.util.Arrays;

import luaforge.Luaforge;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class LuaforgeModContainer extends DummyModContainer {
	
	public LuaforgeModContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "Luaforge";
		meta.name = "Luaforge";
		meta.authorList = Arrays.asList(new String[] { "tiin57", "samrg472"});
		meta.description = "Luaforge: Create Minecraft mods in Lua!";
		meta.version = Luaforge.VERSION;
		meta.url = "http://luaforge.info";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
