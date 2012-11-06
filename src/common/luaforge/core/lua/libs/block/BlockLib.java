package luaforge.core.lua.libs.block;

import java.io.File;
import java.util.ArrayList;
import luaforge.core.Log;
import net.minecraft.src.Material;

import org.luaj.vm2.Varargs;

import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import luaforge.core.proxies.CommonProxy;
import net.minecraft.src.CreativeTabs;
import org.luaj.vm2.LuaValue;

public class BlockLib {

    public static ArrayList<BlockTemplate> regularBlocks = new ArrayList<BlockTemplate>();
    
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

        regularBlocks.add(new BlockTemplate(id, iconIndex, material, new String[] {textureFile, visibleName, blockName}));
        return LuaValue.TRUE;
    }
    
    @LuaMethod(name = "block")
    public static Varargs setCreativeTab(Varargs args, LuaEnvironment env) { // TODO: Document on the wiki
        final String blockName = args.arg1().tojstring();
        final String tabName = args.arg(2).tojstring();
        for(BlockTemplate t : regularBlocks) { // TODO: Make this more efficient
            if(t.getHiddenName().equals(blockName)) {
                if(tabName.equalsIgnoreCase("tabAllSearch")) { t.setCreativeTab(CreativeTabs.tabAllSearch); }
                else if(tabName.equalsIgnoreCase("tabBlock")) { t.setCreativeTab(CreativeTabs.tabBlock); }
                else if(tabName.equalsIgnoreCase("tabBrewing")) { t.setCreativeTab(CreativeTabs.tabBrewing); }
                else if(tabName.equalsIgnoreCase("tabCombat")) { t.setCreativeTab(CreativeTabs.tabCombat); }
                else if(tabName.equalsIgnoreCase("tabDecorations")) { t.setCreativeTab(CreativeTabs.tabDecorations); }
                else if(tabName.equalsIgnoreCase("tabFood")) { t.setCreativeTab(CreativeTabs.tabFood); }
                else if(tabName.equalsIgnoreCase("tabInventory")) { t.setCreativeTab(CreativeTabs.tabInventory); }
                else if(tabName.equalsIgnoreCase("tabMaterials")) { t.setCreativeTab(CreativeTabs.tabMaterials); }
                else if(tabName.equalsIgnoreCase("tabMisc")) { t.setCreativeTab(CreativeTabs.tabMisc); }
                else if(tabName.equalsIgnoreCase("tabMisc")) { t.setCreativeTab(CreativeTabs.tabRedstone); }
                else if(tabName.equalsIgnoreCase("tabTools")) { t.setCreativeTab(CreativeTabs.tabTools); }
                else if(tabName.equalsIgnoreCase("tabTransport")) { t.setCreativeTab(CreativeTabs.tabTransport); }
                else { Log.warning(env.getModName() + " contains an invalid tab name"); }
                break;
            }
        }
        return LuaValue.NONE;
    }
    
    
    
}