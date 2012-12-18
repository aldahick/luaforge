package luaforge.core.lua.libs;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import luaforge.core.api.LuaMethod;
import luaforge.luaj.vm2.LuaValue;
import luaforge.luaj.vm2.Varargs;

public class CraftingHandler {

    @LuaMethod(name = "craftingHandler")
    public static Varargs addShapelessRecipe(Varargs args) {
        Object[] recipes = new Object[args.narg() - 2];
        for (int i = 3; i <= args.narg(); i++) {
            if (args.istable(i)) {
                recipes[i - 3] = ReferenceLib.getItemStack(args.arg(i).get("id").checkint(), 1, args.arg(i).get("metadata").checkint());
            } else {
                recipes[i - 3] = ReferenceLib.getItemStack(args.arg(i).checkint());
            }
        }
        if (args.istable(1)) {
            GameRegistry.addShapelessRecipe(ReferenceLib.getItemStack(args.arg1().get("id").checkint(), args.arg1().get("amount").checkint(), args.arg1().get("metadata").checkint()), recipes);
        } else {
            GameRegistry.addShapelessRecipe(ReferenceLib.getItemStack(args.arg1().checkint(), args.arg(2).checkint()), recipes);
        }
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
                    if (args.istable(i)) {
                        recipe.add(i - 5, ReferenceLib.getItemStack(args.arg(i).get("id").checkint(), 1, args.arg(i).get("metadata").checkint()));
                    } else {
                        recipe.add(i - 5, ReferenceLib.getItemStack(args.arg(i).checkint()));
                    }
                }
            } else if (args.arg(4).checkjstring().isEmpty()) {
                if (i % 2 == 0) {
                    recipe.add(i - 4, args.arg(i).checkjstring().charAt(0));
                } else {
                    if (args.istable(i)) {
                        recipe.add(i - 4, ReferenceLib.getItemStack(args.arg(i).get("id").checkint(), 1, args.arg(i).get("metadata").checkint()));
                    } else {
                        recipe.add(i - 4, ReferenceLib.getItemStack(args.arg(i).checkint()));
                    }
                }
            } else {
                if (i % 2 == 0) {
                    recipe.add(i - 3, args.arg(i).checkjstring().charAt(0));
                } else {
                    if (args.istable(i)) {
                        recipe.add(i - 3, ReferenceLib.getItemStack(args.arg(i).get("id").checkint(), 1, args.arg(i).get("metadata").checkint()));
                    } else {
                        recipe.add(i - 3, ReferenceLib.getItemStack(args.arg(i).checkint()));
                    }
                }
            }
        }
        if (args.arg1().istable()) {
            GameRegistry.addRecipe(ReferenceLib.getItemStack(args.arg1().get("id").checkint(), 1, args.arg1().get("metadata").checkint()), recipe.toArray());
        } else {
            GameRegistry.addRecipe(ReferenceLib.getItemStack(args.arg1().checkint(), args.arg(2).checkint()), recipe.toArray());
        }
        return LuaValue.NONE;
    }

    @LuaMethod(name = "craftingHandler")
    public static Varargs addSmeltingRecipe(Varargs args) {
        int i = args.arg(3).optint(0);
        if (i == 0) {
            if (args.arg(2).istable()) {
                GameRegistry.addSmelting(args.arg1().checkint(), ReferenceLib.getItemStack(args.arg(2).get("id").checkint(), 1, args.arg(2).get("metadata").checkint()), 0);
            } else {
                GameRegistry.addSmelting(args.arg1().checkint(), ReferenceLib.getItemStack(args.arg(2).checkint()), 0);
            }
        } else {
            if (args.arg(2).istable()) {
                GameRegistry.addSmelting(args.arg1().checkint(), ReferenceLib.getItemStack(args.arg(2).get("id").checkint(), 1, args.arg(2).get("metadata").checkint()), args.arg(3).tofloat());
            } else {
                GameRegistry.addSmelting(args.arg1().checkint(), ReferenceLib.getItemStack(args.arg(2).checkint()), args.arg(3).tofloat());
            }
        }
        return LuaValue.NONE;
    }
}