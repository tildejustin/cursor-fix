package com.jojoe77777.cursorfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InputUtil.class)
public class MixinInputUtil {
    @WrapOperation(method = "setCursorParameters", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetCursorPos(JDD)V"))
    private static void repeatSetCursorPos(long window, double xpos, double ypos, Operation<Void> original) {
        original.call(window, xpos, ypos);
        original.call(window, xpos + 1, ypos);
        original.call(window, xpos, ypos);
    }
}
