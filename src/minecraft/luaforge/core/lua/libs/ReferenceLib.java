package luaforge.core.lua.libs;

import luaforge.luaj.vm2.LuaError;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ReferenceLib {

    public static ItemStack getItemStack(int id) {
        return getItemStack(id, 1, 0);
    }
    
    public static ItemStack getItemStack(int id, int amount) {
        return getItemStack(id, amount, 0);
    }

    public static ItemStack getItemStack(int id, int amount, int metadata) {
        if (id < (Block.blocksList.length - 1)) {
            if (Block.blocksList[id] != null) {
                if (Block.blocksList[id].getBlockName() != null) {
                    return new ItemStack(Block.blocksList[id], amount, metadata);
                } else {
                    if (checkItem(id, 0)) {
                        return new ItemStack(Item.itemsList[id], amount, metadata);
                    } else if (checkItem(id, 256)) {
                        return new ItemStack(Item.itemsList[256 + id], amount, metadata);
                    }
                }
            } else if (checkItem(id, 0)) {
                return new ItemStack(Item.itemsList[id], amount, metadata);
            } else if (checkItem(id, 256)) {
                return new ItemStack(Item.itemsList[256 + id], amount, metadata);
            }
        } else {
            if (checkItem(id, 0)) {
                return new ItemStack(Item.itemsList[id], amount, metadata);
            } else if (checkItem(id, 256)) {
                return new ItemStack(Item.itemsList[256 + id], amount, metadata);
            }
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
