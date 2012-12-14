package luaforge.core.lua.libs;

import luaforge.luaj.vm2.LuaError;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class ReferenceLib {    
    
    public static ItemStack getItemStack(int id) {
        return getItemStack(id, 1);
    }

    public static ItemStack getItemStack(int id, int amount) {
        if (Block.blocksList[id] != null) {
            if (Block.blocksList[id].getBlockName() != null) {
                return new ItemStack(Block.blocksList[id], amount);
            } else {
                if (checkItem(id, 0)) {
                    return new ItemStack(Item.itemsList[id], amount);
                } else if (checkItem(id, 256)) {
                    return new ItemStack(Item.itemsList[256 + id], amount);
                }
            }
        } else if (checkItem(id, 0)) {
            return new ItemStack(Item.itemsList[id], amount);
        } else if (checkItem(id, 256)) {
            return new ItemStack(Item.itemsList[256 + id], amount);
        }
        throw new LuaError("Specified ID is invalid: " + id);
    }

    public static boolean checkItem(int id, int shifted) {
        id += shifted;
        if (Item.itemsList[id] != null) {
            if (Item.itemsList[id].getItemName() != null) {
                return true;
            }
        }
        return false;
    }
    
}
