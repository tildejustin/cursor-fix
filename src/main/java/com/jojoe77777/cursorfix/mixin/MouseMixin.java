package com.jojoe77777.cursorfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.*;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private boolean hasResolutionChanged;

    @Shadow
    public abstract boolean isCursorLocked();

    @Inject(method = "onCursorPos", at = @At("HEAD"))
    private void onCursorPosHook(long window, double x, double y, CallbackInfo ci) {
        if (window == MinecraftClient.getInstance().getWindow().getHandle()) {
            System.out.printf("x: %f, y: %f, hasResolutionChanged: %b, changingDelta: %b"/* + "\n"*/, x, y, this.hasResolutionChanged, this.isCursorLocked() && this.client.isWindowFocused());
        }
    }

    @WrapOperation(method = "unlockCursor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/InputUtil;setCursorParameters(JIDD)V"))
    private void unlockCursor(long handler, int mode, double x, double y, Operation<Void> original) {
        // GLFW resets cursor pos to what it was when mode was last CURSOR_NORMAL, so this setCursorPos does nothing. That's ICM.
        // GLFW.glfwSetCursorPos(handler, x, y);
        GLFW.glfwSetInputMode(handler, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
    }

    @WrapOperation(method = "lockCursor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/InputUtil;setCursorParameters(JIDD)V"))
    private void lockCursor(long handler, int mode, double x, double y, Operation<Void> original) {
        // if this setCursorPos fails or is removed, that's UCD. Now why *does* it apparently fail on Windows sometimes?
        GLFW.glfwSetCursorPos(handler, x, y);
        double[] currX = new double[1];
        double[] currY = new double[1];
        GLFW.glfwGetCursorPos(handler, currX, currY);
        System.out.println(currX[0] + " " + currY[0]);
        GLFW.glfwSetInputMode(this.client.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }
}
