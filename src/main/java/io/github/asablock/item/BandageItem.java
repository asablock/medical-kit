package io.github.asablock.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BandageItem extends Item {
    private final int level;

    public BandageItem(Settings settings, int level) {
        super(settings);
        this.level = level;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.getHealth() < user.getMaxHealth()) {
            user.heal(2 * (level + 1));
            stack.damage(1, user.getRandom(), user instanceof ServerPlayerEntity ? (ServerPlayerEntity) user : null);
            user.getItemCooldownManager().set(this, 200 * (level + 1));
            return TypedActionResult.success(stack);
        } else {
            return TypedActionResult.fail(stack);
        }
    }
}
