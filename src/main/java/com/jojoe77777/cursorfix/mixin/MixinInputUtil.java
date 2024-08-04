package com.jojoe77777.cursorfix.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InputUtil.class)
public class MixinInputUtil {
    @WrapWithCondition(method = "setCursorParameters", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetCursorPos(JDD)V"))
    private static boolean doNotSetCursorWhenClosingScreen(long window, double x, double y, long window_, int mode, double x_, double y_) {
        return mode != GLFW.GLFW_CURSOR_NORMAL;
    }
}
