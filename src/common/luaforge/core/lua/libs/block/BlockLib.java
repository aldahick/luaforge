package luaforge.core.lua.libs.block;

import java.io.File;
import java.util.HashMap;
import luaforge.core.Log;
import net.minecraft.src.Material;

import org.luaj.vm2.Varargs;

import luaforge.core.api.LuaMethod;
import luaforge.core.api.LuaTable;
import luaforge.core.lua.LuaEnvironment;
import net.minecraft.src.CreativeTabs;
import org.luaj.vm2.LuaValue;

public class BlockLib {

    public static HashMap<String, BlockTemplate> regularBlocks = new HashMap<String, BlockTemplate>();
    
    @LuaMethod(name = "block")
    public static Varargs createBlock(Varargs args, LuaEnvironment env) {
        if(args.narg() < 6) {
            return LuaValue.FALSE;
        }
        int id = args.arg1().toint();
        int iconIndex = args.arg(2).toint();
        String argMaterial = args.arg(3).tojstring();
        String textureFile = new File(env.getModPath(), args.arg(4).tojstring()).getPath();
        String visibleName = args.arg(5).tojstring();
        String blockName = args.arg(6).tojstring();

        Material material;
        if(argMaterial.equalsIgnoreCase("air")) { material = Material.air; }
        else if(argMaterial.equalsIgnoreCase("cactus")) { material = Material.cactus; }
        else if(argMaterial.equalsIgnoreCase("cake")) { material = Material.cake; }
        else if(argMaterial.equalsIgnoreCase("circuits")) { material = Material.circuits; }
        else if(argMaterial.equalsIgnoreCase("clay")) { material = Material.clay; }
        else if(argMaterial.equalsIgnoreCase("cloth")) { material = Material.cloth; }
        else if(argMaterial.equalsIgnoreCase("craftedSnow")) { material = Material.craftedSnow; }
        else if(argMaterial.equalsIgnoreCase("dragonEgg")) { material = Material.dragonEgg; }
        else if(argMaterial.equalsIgnoreCase("fire")) { material = Material.fire; }
        else if(argMaterial.equalsIgnoreCase("glass")) { material = Material.glass; }
        else if(argMaterial.equalsIgnoreCase("grass")) { material = Material.grass; }
        else if(argMaterial.equalsIgnoreCase("ground")) { material = Material.ground; }
        else if(argMaterial.equalsIgnoreCase("ice")) { material = Material.ice; }
        else if(argMaterial.equalsIgnoreCase("iron")) { material = Material.iron; }
        else if(argMaterial.equalsIgnoreCase("lava")) { material = Material.lava; }
        else if(argMaterial.equalsIgnoreCase("leaves")) { material = Material.leaves; }
        else if(argMaterial.equalsIgnoreCase("piston")) { material = Material.piston; }
        else if(argMaterial.equalsIgnoreCase("plants")) { material = Material.plants; }
        else if(argMaterial.equalsIgnoreCase("portal")) { material = Material.portal; }
        else if(argMaterial.equalsIgnoreCase("pumpkin")) { material = Material.pumpkin; }
        else if(argMaterial.equalsIgnoreCase("redstoneLight")) { material = Material.redstoneLight; }
        else if(argMaterial.equalsIgnoreCase("rock")) { material = Material.rock; }
        else if(argMaterial.equalsIgnoreCase("sand")) { material = Material.sand; }
        else if(argMaterial.equalsIgnoreCase("snow")) { material = Material.snow; }
        else if(argMaterial.equalsIgnoreCase("sponge")) { material = Material.sponge; }
        else if(argMaterial.equalsIgnoreCase("tnt")) { material = Material.tnt; }
        else if(argMaterial.equalsIgnoreCase("vine")) { material = Material.vine; }
        else if(argMaterial.equalsIgnoreCase("water")) { material = Material.water; }
        else if(argMaterial.equalsIgnoreCase("web")) { material = Material.web; }
        else if(argMaterial.equalsIgnoreCase("wood")) { material = Material.wood; }
        else { return LuaValue.FALSE; }

        regularBlocks.put(blockName, new BlockTemplate(id, iconIndex, material, new String[] {textureFile, visibleName, blockName}));
        return LuaValue.TRUE;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setCreativeTab(Varargs args, LuaEnvironment env) {
        final String blockName = args.arg1().tojstring();
        final String tabName = args.arg(2).tojstring();
        BlockTemplate bt = regularBlocks.get(blockName);
        if (bt != null) {
            if(bt.getHiddenName().equals(blockName)) {
                if(tabName.equalsIgnoreCase("tabAllSearch")) { bt.setCreativeTab(CreativeTabs.tabAllSearch); }
                else if(tabName.equalsIgnoreCase("tabBlock")) { bt.setCreativeTab(CreativeTabs.tabBlock); }
                else if(tabName.equalsIgnoreCase("tabBrewing")) { bt.setCreativeTab(CreativeTabs.tabBrewing); }
                else if(tabName.equalsIgnoreCase("tabCombat")) { bt.setCreativeTab(CreativeTabs.tabCombat); }
                else if(tabName.equalsIgnoreCase("tabDecorations")) { bt.setCreativeTab(CreativeTabs.tabDecorations); }
                else if(tabName.equalsIgnoreCase("tabFood")) { bt.setCreativeTab(CreativeTabs.tabFood); }
                else if(tabName.equalsIgnoreCase("tabInventory")) { bt.setCreativeTab(CreativeTabs.tabInventory); }
                else if(tabName.equalsIgnoreCase("tabMaterials")) { bt.setCreativeTab(CreativeTabs.tabMaterials); }
                else if(tabName.equalsIgnoreCase("tabMisc")) { bt.setCreativeTab(CreativeTabs.tabMisc); }
                else if(tabName.equalsIgnoreCase("tabRedstone")) { bt.setCreativeTab(CreativeTabs.tabRedstone); }
                else if(tabName.equalsIgnoreCase("tabTools")) { bt.setCreativeTab(CreativeTabs.tabTools); }
                else if(tabName.equalsIgnoreCase("tabTransport")) { bt.setCreativeTab(CreativeTabs.tabTransport); }
                else { Log.warning(env.getModName() + " contains an invalid tab name: " + tabName); }
            }
        } else {
            Log.warning(env.getModName() + " contains a reference to a block that doesnt exist: " + blockName);
        }
        return LuaValue.NONE;
    }
    
    @LuaTable
    public static HashMap<String, LuaValue> creativeTabs() { // TODO: Document on the wiki
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
    
}