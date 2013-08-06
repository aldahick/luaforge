package luaforge;

import java.util.Arrays;

import luaforge.lua.LuaEnvironment;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.InjectedModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;

public class LuaModContainer extends DummyModContainer {
	public LuaEnvironment env;
	public InjectedModContainer wrapped;
	
	public LuaModContainer(LuaEnvironment env) {
		super(new ModMetadata());
		
		this.env = env;
		
		ModMetadata meta = getMetadata();
		meta.modId = env.modid;
		meta.name = env.modname;
		meta.version = env.version;
		meta.authorList = Arrays.asList(env.authors);
		meta.credits = env.credits;
		meta.description = env.description;
		meta.url = env.url;
		meta.updateUrl = env.updateurl;
		meta.logoFile = env.logofile;
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		FMLNetworkHandler.instance().registerNetworkMod(new NetworkModHandler(wrapped, env.networkmod));
		return true;
	}
	
	@Override
	public boolean isNetworkMod() {
		return env.isNetworkMod;
	}
	
	@Subscribe
	public void constructionEvent(FMLConstructionEvent evt) {
		System.out.println("Registered networkmod.");
		
	}
}
