package com.vincent.tutorialmod.event;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.util.SlotGetHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getEntity() instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " just hit a sheep with an End Rod. Where did it go?"));
                if(!player.isCreative()) {
                    player.getMainHandItem().shrink(1);
                }
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 5));
            }
        }

    }
    @SubscribeEvent
    public static void livingEntityTakeDamage(LivingIncomingDamageEvent event) {
        // added by vincent00tencniv: adding a flat 50% damage reduction if wearing the full azurite armor
        if(event.getEntity() instanceof Player player && hasFullAzurite(player)) {
            event.setAmount(event.getAmount() * 0.5f);
        }
        if(event.getEntity() instanceof Player player && isHoldingSword(player) && isSwordNotAtCooldown(player)) {
            // player is holding with a sword - a parry
            event.setCanceled(true);
            player.getCooldowns().addCooldown(player.getActiveItem(), 100);
            player.getMainHandItem().hurtAndBreak(15, player, player.getUsedItemHand());

        }
    }

    private static boolean isSwordNotAtCooldown(Player player) {
        return !player.getCooldowns().isOnCooldown(player.getMainHandItem());
    }

    private static boolean hasFullAzurite(Player player){
        return SlotGetHelper.isBoots(player, ModItems.AZURITE_BOOTS) &&
                SlotGetHelper.isHelmet(player, ModItems.AZURITE_HELMET) &&
                SlotGetHelper.isChestplate(player, ModItems.AZURITE_CHESTPLATE) &&
                SlotGetHelper.isLeggings(player, ModItems.AZURITE_LEGGINGS);
    }

    private static boolean isHoldingSword(Player player) {
        return player.getMainHandItem().getItem().builtInRegistryHolder().is(ItemTags.SWORDS);
    }
}
