package luaforge.core;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.InjectedModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkModHandler;
import java.util.Arrays;
import luaforge.core.lua.LuaEnvironment;

public class LuaForgeModsContainer extends DummyModContainer {

    public LuaEnvironment env;
    public InjectedModContainer wrapped;
    
    public LuaForgeModsContainer(LuaEnvironment env) {
        super(new ModMetadata());
        this.env = env;
        ModMetadata meta = getMetadata();
        meta.modId = env.getModName();
        meta.name = env.getModName();
        meta.version = env.getVersion();
        meta.authorList = Arrays.asList(env.getAuthor());
        meta.credits = env.getCredits();
        meta.description = env.getDescription();
        meta.url = env.getURL();
        meta.updateUrl = env.getUpdateURL();
        meta.logoFile = env.getLogoFile();
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Override
    public boolean isNetworkMod() {
        return true;
    }

    @Subscribe
    public void constructionEvent(FMLConstructionEvent event) {
        FMLNetworkHandler.instance().registerNetworkMod(new NetworkModHandler(wrapped, env.getNetworkMod()));
    }
}