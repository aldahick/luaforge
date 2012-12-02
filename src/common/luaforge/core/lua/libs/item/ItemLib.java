package luaforge.core.lua.libs.item;

import java.io.File;
import java.util.HashMap;
import luaforge.core.Log;
import luaforge.core.api.LuaMethod;
import luaforge.core.lua.LuaEnvironment;
import luaforge.luaj.vm2.LuaError;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumAction;

public class ItemLib {
    
    public static HashMap<String, ItemTemplate> regularItems = new HashMap<String, ItemTemplate>();
    
    @LuaMethod (name = "item")
    public static Varargs createItem (Varargs args, LuaEnvironment env) {
        if (args.narg() < 6) {
            return LuaValue.FALSE;
        }
        int id = args.arg1().toint();
        int iconIndex = args.arg(2).toint();
        int maxStackSize = args.arg(3).toint();
        String givenTexturePath = args.arg(4).checkjstring();
        String textureFile = "/" + (new File(env.getModPath()).getName()) + ((givenTexturePath.startsWith("/")) ? "" : "/") + givenTexturePath;
        String visibleName = args.arg(5).tojstring();
        String hiddenName = args.arg(6).tojstring();
        regularItems.put(hiddenName, new ItemTemplate(id, iconIndex, new Object[] {
            maxStackSize,
            textureFile,
            visibleName,
            hiddenName
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
    
    @LuaMethod (name = "item")
    public static Varargs setAction(Varargs args) { // TODO: Document item.setAction & create a table for EnumAction constants
        String name = args.arg1().checkjstring();
        String action = args.arg(2).checkjstring();
        
        ItemTemplate it = regularItems.get(name);
        if (action.equals("none")) { it.action = EnumAction.none; }
        else if (action.equals("eat")) { it.action = EnumAction.eat; }
        else if (action.equals("drink")) { it.action = EnumAction.drink; }
        else if (action.equals("block")) { it.action = EnumAction.block; }
        else if (action.equals("bow")) { it.action = EnumAction.bow; }
        else { throw new LuaError("Action is undefined"); }
        
        return LuaValue.NONE;
    }
    
    @LuaMethod (name = "item")
    public static Varargs setHealAmount(Varargs args) { // TODO: Document setHealAmount on the wiki
        ItemTemplate it = regularItems.get(args.arg1().checkjstring());
        it.setHealAmount(args.arg(2).checkint());
        return LuaValue.NONE;
    }
    
    @LuaMethod (name = "item")
    public static Varargs setSaturationAmount(Varargs args) {
        ItemTemplate it = regularItems.get(args.arg1().checkjstring());
        args.arg(2).checkint();
        it.setSaturation(args.arg(2).tofloat());
        return LuaValue.NONE;
    }
    
    @LuaMethod (name = "item")
    public static Varargs setAlwaysEdible(Varargs args) {
        regularItems.get(args.arg1().checkjstring()).setAlwaysEdible();
        return LuaValue.NONE;
    }
}
