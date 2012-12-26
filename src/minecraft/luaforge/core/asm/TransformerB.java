package luaforge.core.asm;

import cpw.mods.fml.relauncher.IClassTransformer;
import java.io.IOException;
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
        if (name.equals(ObfuscationMappings.getClassName("cpw.mods.fml.common.Loader"))) {
            return getModifications(bytes, name, "identifyMods");
        }
        return bytes;
    }

    private static byte[] getModifications(byte[] bytes, String name, String... methods) {
        try {
            Visitor visit = new Visitor(name, methods);
            ClassReader reader = new ClassReader(bytes);
            reader.accept(visit, 0);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            visit.accept(writer);
            return writer.toByteArray();
        } catch (Exception e) {
            Log.severe(e.getMessage());
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
            for (String s : overridableMethods) {
                String obfName = ObfuscationMappings.getMethodName(className, s);
                if (name.equals(obfName)) {
                    if (!ObfuscationMappings.isObfuscated) {
                        Log.info("Development environment detected, not overriding " + className + " with method " + name);
                        methods.add(mn);
                        return mn;
                    }
                     ClassReader reader = null;
                    try {
                        reader = new ClassReader(ClassOverrider.override(className));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } 
                    ClassNode node = new ClassNode();
                    reader.accept(node, 0);
                    for (MethodNode nodes : (List<MethodNode>) node.methods) {
                        if (nodes.name.equals(obfName)) {
                            String descriptor = ObfuscationMappings.getDescriptor(className, s);
                            if ((mn.desc.equals(descriptor)) && (nodes.desc.equals(descriptor))) {
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
