package luaforge.core.asm;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;
import java.util.List;
import luaforge.core.Log;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class TransformerB implements IClassTransformer {

    @Override
    public byte[] transform(String name, byte[] bytes) {
        try {
            if (FMLRelauncher.side().equals("CLIENT")) {
                if (name.equals(ObfuscationMappings.getClassName("net.minecraft.src.RenderEngine"))) {
                    Visitor visit = new Visitor("net.minecraft.src.RenderEngine", "getTexture");
                    ClassReader reader = new ClassReader(bytes);
                    reader.accept(visit, 0);
                    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                    visit.accept(writer);
                    bytes = writer.toByteArray();
                }
            }
        } catch (Exception e) {
            Log.severe(e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

    static class Visitor extends ClassNode {

        private String className;
        private String[] overridableMethods;

        public Visitor(String className, String... overridableMethods) {
            super(Opcodes.ASM4);
            this.className = className;
            this.overridableMethods = overridableMethods;
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
            MethodNode mn = new MethodNode(access, name, desc, signature, exceptions);
            if (!ObfuscationMappings.isObfuscated) {
                Log.info("Development environment detected, not overriding " + className + " with method " + name);
                methods.add(mn);
                return mn;
            }
            for (String s : overridableMethods) {
                String obfName = ObfuscationMappings.getMethodName(className, s);
                if (name.equals(obfName)) {
                    ClassNode node = new ClassNode();
                    ClassReader reader = new ClassReader(ClassOverrider.override(ObfuscationMappings.getClassName(className), new byte[1]));
                    reader.accept(node, 0);
                    for (MethodNode nodes : (List<MethodNode>) node.methods) {
                        if (nodes.name.equals(obfName)) {
                            if (nodes.desc.equals(mn.desc)) {
                                Log.info(ObfuscationMappings.getClassName(className) + "'s method " + obfName + " was overriden by " + LuaForgeLoader.location.getName());
                                mn = nodes;
                            }
                        }
                    }
                }
            }
            methods.add(mn);
            return mn;
        }
    }
}
