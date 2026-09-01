package com.vincent.tutorialmod.event;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.keymapping.ModKeyMappings;
import com.vincent.tutorialmod.networking.ClientPayloadHandler;
import com.vincent.tutorialmod.networking.packet.TestPacketC2S;
import com.vincent.tutorialmod.stat.ModStats;
import com.vincent.tutorialmod.util.SlotGetHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

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
            player.getCooldowns().addCooldown(player.getWeaponItem(), 100);
            player.getMainHandItem().hurtAndBreak(15, player, player.getUsedItemHand());
            player.awardStat(ModStats.TIMES_PARRIED.get(), 1);

        }
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        registrar.playToServer(TestPacketC2S.TYPE, TestPacketC2S.STREAM_CODEC, ClientPayloadHandler::handleTestPacket);
        // any packets, whether from the server or client, MUST be registered here!
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        // either if loops or while loops can be pressed
        // if loops make the key a single click interaction
        // while loops make the key a hold-down click interaction
        while(ModKeyMappings.PRESS_KAUPEN.get().consumeClick()) {
            // client side!
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if(localPlayer == null) {
                // for some reason the client has noone controlling - just return!
                return;
            }
            localPlayer.sendSystemMessage(Component.literal("I just pressed the Kaupen Key!"));
            ClientPacketDistributor.sendToServer(new TestPacketC2S("Kaupenjoe", 67));
        }
        // When sending client packets, send here!
        // Otherwise, send from a server tick event!
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
