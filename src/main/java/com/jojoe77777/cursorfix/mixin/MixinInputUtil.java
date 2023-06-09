package com.jojoe77777.cursorfix.mixin;

import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InputUtil.class)
public class MixinInputUtil {

    /**
     * @author jojoe77777
     * @reason Swapping the order of these lines fixes cursor position not always updating
     */
    @Overwrite
    public static void setCursorParameters(long handler, int i, double d, double e) {
        GLFW.glfwSetInputMode(handler, 208897, i);
        GLFW.glfwSetCursorPos(handler, d, e);
    }

}
