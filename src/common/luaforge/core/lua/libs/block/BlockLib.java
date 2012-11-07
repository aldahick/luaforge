package luaforge.core.lua.libs.block;

import java.io.File;
import java.util.HashMap;
import luaforge.core.Log;
import net.minecraft.src.Material;

import org.luaj.vm2.Varargs;

import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import net.minecraft.src.CreativeTabs;
import org.luaj.vm2.LuaError;
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
                else if(tabName.equalsIgnoreCase("tabMisc")) { bt.setCreativeTab(CreativeTabs.tabRedstone); }
                else if(tabName.equalsIgnoreCase("tabTools")) { bt.setCreativeTab(CreativeTabs.tabTools); }
                else if(tabName.equalsIgnoreCase("tabTransport")) { bt.setCreativeTab(CreativeTabs.tabTransport); }
                else { Log.warning(env.getModName() + " contains an invalid tab name: " + tabName); }
            }
        } else {
            Log.warning(env.getModName() + " contains a reference to a block that doesnt exist: " + blockName);
        }
        return LuaValue.NONE;
    }
    
    
    
}