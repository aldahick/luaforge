package luaforge.core.lua.libs;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.HashMap;
import luaforge.core.api.LuaMethod;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class CraftingHandler {

    @LuaMethod(name = "craftingHandler")
    public static Varargs addShapelessRecipe(Varargs args) { // TODO: Document on the wiki
        if (args.narg() < 2) {
            throw new LuaError("Requires at least 2 paramters");
        }
        Object[] recipes = new Object[args.narg() - 1];
        for (int i = 2; i <= args.narg(); i++) {
            recipes[i - 2] = new ItemStack(Block.blocksList[args.arg(i).checkint()], 1);
        }
        GameRegistry.addShapelessRecipe(new ItemStack(Block.blocksList[args.arg1().checkint()]), recipes);
        return LuaValue.NONE;
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs addShapedRecipe(Varargs args) { // TODO: Document on the wiki
        Object[] recipe = new Object[args.narg() - 1];
        if (args.narg() % 2 == 0) {
            for (int i = 5; i <= args.narg(); i++) {
                if (i % 2 == 1) {
                    recipe[i - 2] = args.arg(i).checkjstring().charAt(0);
                } else {
                    recipe[i - 2] = new ItemStack(Block.blocksList[args.arg(i).checkint()], 1);
                }
            }
            recipe[2] = args.arg(4).checkjstring();
        } else {
            for (int i = 4; i <= args.narg(); i++) {
                if (i % 2 == 0) {
                    recipe[i - 2] = args.arg(i).checkjstring().charAt(0);
                } else {
                    recipe[i - 2] = new ItemStack(Block.blocksList[args.arg(i).checkint()], 1);
                }
            }

        }
        recipe[0] = args.arg(2).checkjstring();
        recipe[1] = args.arg(3).checkjstring();
        GameRegistry.addRecipe(new ItemStack(Block.blocksList[args.arg1().checkint()], 1), recipe);
        return LuaValue.NONE;
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs add1xXRecipe(Varargs args) { // TODO: Document on the wiki
        Object[] recipe = new Object[args.narg() - 1];
        for (int i = 3; i <= args.narg(); i++) {
            if (i % 2 == 1) {
                recipe[i - 2] = args.arg(i).checkjstring().charAt(0);
            } else {
                recipe[i - 2] = new ItemStack(Block.blocksList[args.arg(i).checkint()], 1);
            }
        }
        recipe[0] = args.arg(2).checkjstring();
        GameRegistry.addRecipe(new ItemStack(Block.blocksList[args.arg1().checkint()], 1), recipe);
        return LuaValue.NONE;
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs addRecipe(Varargs args) { // TODO: Document on the wiki
        return addShapedRecipe(args);
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs addSmeltingRecipe(Varargs args) { // TODO: Document on the wiki
        GameRegistry.addSmelting(args.arg1().checkint(), new ItemStack(Block.blocksList[args.arg(2).checkint()]), args.arg(3).optint(0));
        return LuaValue.NONE;
    }
}
