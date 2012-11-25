package luaforge.core.lua.libs;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import luaforge.core.Log;
import luaforge.core.api.LuaMethod;
import luaforge.core.lua.libs.item.ItemLib;
import luaforge.core.lua.libs.item.ItemTemplate;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import luaforge.luaj.vm2.LuaError;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;
import net.minecraft.src.Item;

public class CraftingHandler {

    @LuaMethod(name = "craftingHandler")
    public static Varargs addShapelessRecipe(Varargs args) {
        Object[] recipes = new Object[args.narg() - 2];
        for (int i = 3; i <= args.narg(); i++) {
            recipes[i - 3] = getItemStack(args.arg(i).checkint(), 1);
        }
        GameRegistry.addShapelessRecipe(getItemStack(args.arg1().checkint(), args.arg(2).checkint()), recipes);
        return LuaValue.NONE;
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs addShapedRecipe(Varargs args) {
        ArrayList<Object> recipe = new ArrayList<Object>();
        recipe.add(0, args.arg(3).checkjstring());
        if (args.arg(4).checkjstring().isEmpty()) {
            // Don't do anything
        } else if (args.arg(5).checkjstring().isEmpty()) {
            recipe.add(1, args.arg(4).checkjstring());
        } else {
            recipe.add(1, args.arg(4).checkjstring());
            recipe.add(2, args.arg(5).checkjstring());
        }
        for (int i = 6; i <= args.narg(); i++) {
            if (args.arg(3).checkjstring().isEmpty()) {
                if (i % 2 == 0) {
                    recipe.add(i - 5, args.arg(i).checkjstring().charAt(0));
                } else {
                    recipe.add(i - 5, getItemStack(args.arg(i).checkint()));
                }
            } else if (args.arg(4).checkjstring().isEmpty()) {
                if (i % 2 == 0) {
                    recipe.add(i - 4, args.arg(i).checkjstring().charAt(0));
                } else {
                    recipe.add(i - 4, getItemStack(args.arg(i).checkint()));
                }
            } else {
                if (i % 2 == 0) {
                    recipe.add(i - 3, args.arg(i).checkjstring().charAt(0));
                } else {
                    recipe.add(i - 3, getItemStack(args.arg(i).checkint()));
                }
            }
        }
        GameRegistry.addRecipe(getItemStack(args.arg1().checkint(), args.arg(2).checkint()), recipe.toArray());
        return LuaValue.NONE;
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs addSmeltingRecipe(Varargs args) {
        GameRegistry.addSmelting(args.arg1().checkint(), getItemStack(args.arg(2).checkint()), args.arg(3).optint(0));
        return LuaValue.NONE;
    }

    private static ItemStack getItemStack(int id) {
        return getItemStack(id, 1);
    }

    private static ItemStack getItemStack(int id, int amount) {
        if (Block.blocksList[id] != null) {
            if (Block.blocksList[id].getBlockName() != null) {
                return new ItemStack(Block.blocksList[id], amount);
            } else {
                if (Item.itemsList[256 + id] != null) {
                    if (Item.itemsList[256 + id].getItemName() != null) {
                        return new ItemStack(Item.itemsList[256 + id], amount);
                    }
                }
            }
        } else if (Item.itemsList[256 + id] != null) {
            if (Item.itemsList[256 + id].getItemName() != null) {
                return new ItemStack(Item.itemsList[256 + id], amount);
            }
        }
        throw new LuaError("Specified ID is invalid");
    }
}
