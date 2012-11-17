package luaforge.core.lua.libs;

import java.util.HashMap;
import luaforge.core.api.LuaTable;
import org.luaj.vm2.LuaValue;

public class TablesLib {

    @LuaTable
    public static HashMap<String, LuaValue> creativeTabs() {
        HashMap<String, LuaValue> table = new HashMap<String, LuaValue>();
        table.put("allSearch", LuaValue.valueOf("tabAllSearch"));
        table.put("block", LuaValue.valueOf("tabBlock"));
        table.put("brewing", LuaValue.valueOf("tabBrewing"));
        table.put("combat", LuaValue.valueOf("tabCombat"));
        table.put("decorations", LuaValue.valueOf("tabDecorations"));
        table.put("food", LuaValue.valueOf("tabFood"));
        table.put("inventory", LuaValue.valueOf("tabInventory"));
        table.put("materials", LuaValue.valueOf("tabMaterials"));
        table.put("misc", LuaValue.valueOf("tabMisc"));
        table.put("redstone", LuaValue.valueOf("tabRedstone"));
        table.put("tools", LuaValue.valueOf("tabTools"));
        table.put("transport", LuaValue.valueOf("tabTransport"));
        return table;
    }

    @LuaTable
    public static HashMap<String, LuaValue> material() {
        HashMap<String, LuaValue> table = new HashMap<String, LuaValue>();
        table.put("air", LuaValue.valueOf("air"));
        table.put("cactus", LuaValue.valueOf("cactus"));
        table.put("cake", LuaValue.valueOf("cake"));
        table.put("circuits", LuaValue.valueOf("circuits"));
        table.put("clay", LuaValue.valueOf("clay"));
        table.put("cloth", LuaValue.valueOf("cloth"));
        table.put("craftedSnow", LuaValue.valueOf("craftedSnow"));
        table.put("dragonEgg", LuaValue.valueOf("dragonEgg"));
        table.put("fire", LuaValue.valueOf("fire"));
        table.put("glass", LuaValue.valueOf("glass"));
        table.put("grass", LuaValue.valueOf("grass"));
        table.put("ground", LuaValue.valueOf("ground"));
        table.put("ice", LuaValue.valueOf("ice"));
        table.put("iron", LuaValue.valueOf("iron"));
        table.put("lava", LuaValue.valueOf("lava"));
        table.put("leaves", LuaValue.valueOf("leaves"));
        table.put("piston", LuaValue.valueOf("piston"));
        table.put("plants", LuaValue.valueOf("plants"));
        table.put("portal", LuaValue.valueOf("portal"));
        table.put("pumpkin", LuaValue.valueOf("pumpkin"));
        table.put("redstoneLight", LuaValue.valueOf("redstoneLight"));
        table.put("rock", LuaValue.valueOf("rock"));
        table.put("sand", LuaValue.valueOf("sand"));
        table.put("snow", LuaValue.valueOf("snow"));
        table.put("sponge", LuaValue.valueOf("sponge"));
        table.put("tnt", LuaValue.valueOf("tnt"));
        table.put("vine", LuaValue.valueOf("vine"));
        table.put("water", LuaValue.valueOf("water"));
        table.put("web", LuaValue.valueOf("web"));
        table.put("wood", LuaValue.valueOf("wood"));
        return table;
    }
    
    @LuaTable
    public static HashMap<String, LuaValue> stepSound() { // TODO: Document on the wiki
        HashMap<String, LuaValue> table = new HashMap<String, LuaValue>();
        table.put("powder", LuaValue.valueOf("soundPowderFootstep"));
        table.put("wood", LuaValue.valueOf("soundWoodFootstep"));
        table.put("gravel", LuaValue.valueOf("soundGravelFootstep"));
        table.put("grass", LuaValue.valueOf("soundGrassFootstep"));
        table.put("stone", LuaValue.valueOf("soundStoneFootstep"));
        table.put("metal", LuaValue.valueOf("soundMetalFootstep"));
        table.put("glass", LuaValue.valueOf("soundGlassFootstep"));
        table.put("cloth", LuaValue.valueOf("soundClothFootstep"));
        table.put("sand", LuaValue.valueOf("soundSandFootstep"));
        table.put("snow", LuaValue.valueOf("soundSnowFootstep"));
        table.put("ladder", LuaValue.valueOf("soundLadderFootstep"));
        table.put("anvil", LuaValue.valueOf("soundAnvilFootstep"));
        return table;
    }
}
