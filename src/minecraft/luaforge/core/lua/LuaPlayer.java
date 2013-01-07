package luaforge.core.lua;

import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.lib.OneArgFunction;
import luaforge.luaj.vm2.lib.ZeroArgFunction;
import net.minecraft.entity.player.EntityPlayer;

public class LuaPlayer {
    
    public static LuaValue getLuaPlayerObject(EntityPlayer player) {
        LuaValue t = LuaValue.tableOf();
        
        t.set("username", LuaValue.valueOf(player.username));
        t.set("health", LuaValue.valueOf(player.getHealth()));
        t.set("experience_total", LuaValue.valueOf(player.experienceTotal));
        t.set("experience_level", LuaValue.valueOf(player.experienceLevel));
        t.set("isSleeping", LuaValue.valueOf(player.isPlayerSleeping()));
        t.set("isBurning", LuaValue.valueOf(player.isBurning()));
        
        t.set("setOnFire", new setOnFire(player));
        t.set("setDead", new setDead(player));
        
        return t;
    }
    
    private static class setOnFire extends OneArgFunction {

        private EntityPlayer player;
        
        public setOnFire(EntityPlayer p) {
            player = p;
        }
        
        @Override
        public LuaValue call(LuaValue arg) {
            player.setFire(arg.arg1().checkint());
            return LuaValue.NONE;
        }
        
    }
    
    private static class setDead extends ZeroArgFunction {

        private EntityPlayer player;
        
        public setDead(EntityPlayer p) {
            player = p;
        }
        
        @Override
        public LuaValue call() {
            player.setDead();
            return LuaValue.NONE;
        }
        
    }
    
}
