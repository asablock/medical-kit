package io.github.asablock.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsPumpkinMixin {
    @Inject(method = "register(Lnet/minecraft/block/Block;Lnet/minecraft/item/ItemGroup;)Lnet/minecraft/item/Item;",
            at = @At("HEAD"))
    private static void register(Block block, ItemGroup group, CallbackInfoReturnable<Item> cir) {
        if (block == Blocks.PUMPKIN) {
            cir.setReturnValue(new BlockItem(block, new Item.Settings()
                    .group(group)
                    .food(new FoodComponent.Builder()
                            .hunger(5)
                            .saturationModifier(0.6F)
                            .build())));
        }
    }
}
