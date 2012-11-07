package luaforge.core.asm;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import java.util.Arrays;
import com.google.common.eventbus.EventBus;

public class LuaForgeModContainer extends DummyModContainer {
    
    public LuaForgeModContainer() {
        super(new ModMetadata());
        ModMetadata myMeta = super.getMetadata();
        myMeta.modId = "LuaForgeCore";
        myMeta.name = "LuaForgeCore";
        myMeta.authorList = Arrays.asList(new String[]{"tiin57", "samrg472"});
        myMeta.description = "LuaForge Core - Needed by LuaForge";
        myMeta.version = "1.0.0";
        myMeta.url = "http://luaforge.tk";
        
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
    
}
