package io.github.asablock.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            user.heal(3.7F * (level + 1));
            damage(stack, user, hand);
            user.getItemCooldownManager().set(this, 200 * (level + 1));
            return TypedActionResult.success(stack);
        } else {
            return TypedActionResult.fail(stack);
        }
    }

    private void damage(ItemStack stack, PlayerEntity user, Hand hand) {
        stack.damage(1, user, u -> u.sendToolBreakStatus(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("bandage.cooldown", 10 * (level + 1)));
        tooltip.add(new TranslatableText("bandage.heal", 4 * (level + 1)));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        entity.heal(3.7F * (level + 1));
        damage(stack, user, hand);
        user.getItemCooldownManager().set(this, 200 * (level + 1));
        return ActionResult.SUCCESS;
    }
}
