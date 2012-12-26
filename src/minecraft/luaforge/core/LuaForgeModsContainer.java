package luaforge.core;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import java.util.Arrays;

public class LuaForgeModsContainer extends DummyModContainer {

        public LuaForgeModsContainer(String... info) {
            super(new ModMetadata());
            ModMetadata meta = getMetadata();
            meta.modId = info[0];
            meta.name = info[0];
            meta.version = info[1];
            meta.credits = "";
            meta.authorList = Arrays.asList(info[2]);
            meta.description = "";
            meta.url = "";
            meta.updateUrl = "";
            meta.logoFile = "";
        }

        @Override
        public boolean registerBus(EventBus bus, LoadController controller) {
            return true;
        }
        
        @Override
        public boolean isImmutable() {
            return true;
        }
    }