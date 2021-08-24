package io.github.moulberry.bedwarshearts.mixins;

import io.github.moulberry.bedwarshearts.BedwarsHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngameForge {

    @Redirect(method="renderHealth",
            at=@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/storage/WorldInfo;isHardcoreModeEnabled()Z"
            )
    )
    public boolean renderPlayerStats_isHardcoreModeEnabled(WorldInfo info) {
        if(BedwarsHearts.useHardcoreHearts) return true;
        return info.isHardcoreModeEnabled();
    }

}
