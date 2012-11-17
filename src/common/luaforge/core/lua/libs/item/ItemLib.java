package luaforge.core.lua.libs.item;

import java.io.File;
import java.util.HashMap;
import luaforge.core.Log;
import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import net.minecraft.src.CreativeTabs;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class ItemLib {
    
    public static HashMap<String, ItemTemplate> regularItems = new HashMap<String, ItemTemplate>();
    
    @LuaMethod (name = "item")
    public static Varargs createItem (Varargs args, LuaEnvironment env) {
        if (args.narg() < 6) {
            return LuaValue.FALSE;
        }
        int id = args.arg1().toint();
        int iconIndex = args.arg(2).toint();
        String textureFile = new File(env.getModPath(), args.arg(3).tojstring()).getPath();
        String visibleName = args.arg(4).tojstring();
        String itemName = args.arg(5).tojstring();
        int maxStackSize = args.arg(6).toint();
        regularItems.put(itemName, new ItemTemplate(id, iconIndex, new Object[] {
            visibleName,
            itemName,
            textureFile,
            maxStackSize
        }));
        return LuaValue.TRUE;
    }
    @LuaMethod (name = "item")
    public static Varargs setCreativeTab (Varargs args, LuaEnvironment env) {
        final String itemName = args.arg1().tojstring();
        final String tabName = args.arg(2).tojstring();
        ItemTemplate it = regularItems.get(itemName);
        if (it != null) {
            if(it.getHiddenName().equals(itemName)) {
                if(tabName.equalsIgnoreCase("tabAllSearch")) { it.setCreativeTab(CreativeTabs.tabAllSearch); }
                else if(tabName.equalsIgnoreCase("tabBlock")) { it.setCreativeTab(CreativeTabs.tabBlock); }
                else if(tabName.equalsIgnoreCase("tabBrewing")) { it.setCreativeTab(CreativeTabs.tabBrewing); }
                else if(tabName.equalsIgnoreCase("tabCombat")) { it.setCreativeTab(CreativeTabs.tabCombat); }
                else if(tabName.equalsIgnoreCase("tabDecorations")) { it.setCreativeTab(CreativeTabs.tabDecorations); }
                else if(tabName.equalsIgnoreCase("tabFood")) { it.setCreativeTab(CreativeTabs.tabFood); }
                else if(tabName.equalsIgnoreCase("tabInventory")) { it.setCreativeTab(CreativeTabs.tabInventory); }
                else if(tabName.equalsIgnoreCase("tabMaterials")) { it.setCreativeTab(CreativeTabs.tabMaterials); }
                else if(tabName.equalsIgnoreCase("tabMisc")) { it.setCreativeTab(CreativeTabs.tabMisc); }
                else if(tabName.equalsIgnoreCase("tabMisc")) { it.setCreativeTab(CreativeTabs.tabRedstone); }
                else if(tabName.equalsIgnoreCase("tabTools")) { it.setCreativeTab(CreativeTabs.tabTools); }
                else if(tabName.equalsIgnoreCase("tabTransport")) { it.setCreativeTab(CreativeTabs.tabTransport); }
                else { Log.warning(env.getModName() + " contains an invalid tab name: " + tabName); }
            }
        }
        else {
            Log.warning(env.getModName() + " contains a reference to an item that doesnt exist: " + itemName);
        }
        return LuaValue.TRUE;
    }
}
