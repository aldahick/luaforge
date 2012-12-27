package luaforge.core;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import java.util.Arrays;

public class LuaForgeModsContainer extends DummyModContainer {

        public LuaForgeModsContainer(String[] authors, String... info) {
            super(new ModMetadata());
            ModMetadata meta = getMetadata();
            meta.modId = info[0];
            meta.name = info[0];
            meta.version = info[1];
            meta.authorList = Arrays.asList(authors);
            meta.credits = info[2];
            meta.description = info[3];
            meta.url = info[4];
            meta.updateUrl = info[5];
            meta.logoFile = info[6];
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