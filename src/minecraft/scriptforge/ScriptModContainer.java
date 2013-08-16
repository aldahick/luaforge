package scriptforge;

import java.util.Arrays;

import scriptforge.api.IScriptEnvironment;
import scriptforge.script.LuaEnvironment;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.InjectedModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkModHandler;

public class ScriptModContainer extends DummyModContainer {
	public IScriptEnvironment env;
	public InjectedModContainer wrapped;
	
	public ScriptModContainer(IScriptEnvironment env) {
		super(new ModMetadata());
		
		this.env = env;
		
		ModMetadata meta = getMetadata();
		meta.modId = env.modid();
		meta.name = env.modname();
		meta.version = env.version();
		meta.authorList = Arrays.asList(env.authors());
		meta.credits = env.credits();
		meta.description = env.description();
		meta.url = env.url();
		meta.updateUrl = env.updateurl();
		meta.logoFile = env.logofile();
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
	
	public void registerNetworkMod() {
		if (wrapped != null) {
			FMLNetworkHandler.instance().registerNetworkMod(new NetworkModHandler(this, env.networkmod()));
		}
	}
	@Override
	public boolean isNetworkMod() {
		return env.isNetworkMod();
	}
}
