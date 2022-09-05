package surreal.contentcreator.core;

import com.google.common.collect.Maps;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import java.util.Map;
import java.util.function.Consumer;

import static org.objectweb.asm.Opcodes.*;

public class CCClassTransformer implements IClassTransformer {
    private static final Map<String, Consumer<ClassNode>> MAP = Maps.newHashMap();
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (MAP.containsKey(transformedName)) {
            CCLoadingPlugin.LOGGER.info("Manipulating " + transformedName);
            return transform(basicClass, MAP.get(transformedName));
        }
        return basicClass;
    }

    private byte[] transform(byte[] classBeingTransformed, Consumer<ClassNode> consumer) {
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBeingTransformed);
            classReader.accept(classNode, 0);

            consumer.accept(classNode);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classBeingTransformed;
    }

    static {
        MAP.put("net.minecraft.client.renderer.RenderItem", clazz -> {
            for (MethodNode method : clazz.methods) {
                if (method.name.equals(CCLoadingPlugin.DEOBF ? "renderItemAndEffectIntoGUI" : "func_184391_a")) {
                    AbstractInsnNode node = null;
                    for (AbstractInsnNode n : method.instructions.toArray()) {
                        if (n.getOpcode() == PUTFIELD) {
                            node = n.getNext().getNext().getNext();
                            break;
                        }
                    }

                    if (node != null) {
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(ALOAD, 2));
                        list.add(new VarInsnNode(ILOAD, 3));
                        list.add(new VarInsnNode(ILOAD, 4));
                        list.add(new MethodInsnNode(INVOKESTATIC, "surreal/contentcreator/util/RenderUtil", "renderBackground", "(Lnet/minecraft/item/ItemStack;II)V", false));
                        method.instructions.insertBefore(node, list);
                    }
                }
            }
        });
    }
}