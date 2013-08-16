package scriptforge.asm;

import java.util.Arrays;

import scriptforge.Scriptforge;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class ScriptforgeModContainer extends DummyModContainer {
	
	public ScriptforgeModContainer() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = Scriptforge.MODID;
		meta.name = Scriptforge.NAME;
		meta.authorList = Arrays.asList(new String[] { "tiin57" });
		meta.description = "Scriptforge: Create Minecraft mods in Lua!";
		meta.version = Scriptforge.VERSION;
		meta.url = "http://scriptforge.info";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
