package com.vincent.tutorialmod;

import com.mojang.logging.LogUtils;
import com.vincent.tutorialmod.block.ModBlocks;
import com.vincent.tutorialmod.data.ModDataComponents;
import com.vincent.tutorialmod.effect.ModEffects;
import com.vincent.tutorialmod.item.ModItems;
import com.vincent.tutorialmod.potion.ModPotions;
import com.vincent.tutorialmod.sound.ModSounds;
import com.vincent.tutorialmod.stat.ModStats;
import com.vincent.tutorialmod.tab.ModCreativeModeTabs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TutorialMod.MOD_ID)
public class TutorialMod {
    public static final String MOD_ID = "tutorialmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TutorialMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        TutorialMod.registerModFunctionalities(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static void registerModFunctionalities(IEventBus modEventBus) {
        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModStats.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {}

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        addItemToCreativeTab(event, CreativeModeTabs.INGREDIENTS, List.of(
                ModItems.AZURITE,
                ModItems.RAW_AZURITE
        ));
        addBlockToCreativeTab(event, CreativeModeTabs.BUILDING_BLOCKS, List.of(
                ModBlocks.AZURITE_BLOCK
        ));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    public void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                     ResourceKey<CreativeModeTab> tab, DeferredItem<Item> item) {
        if(event.getTabKey() == tab){
            event.accept(item);
        }
    }

    public void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                      List<ResourceKey<CreativeModeTab>> tabs, DeferredItem<Item> item) {
        for(ResourceKey<CreativeModeTab> tab : tabs){
            if(event.getTabKey() == tab){
                event.accept(item);
            }
        }
    }

    public void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                      ResourceKey<CreativeModeTab> tab, List<DeferredItem<Item>> items) {
        if(event.getTabKey() == tab){
            for(DeferredItem<Item> item : items){
                event.accept(item);
            }
        }
    }

    public void addItemToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                      List<ResourceKey<CreativeModeTab>> tabs, List<DeferredItem<Item>> items) {
        for(ResourceKey<CreativeModeTab> tab : tabs){
            if(event.getTabKey() == tab){
                for(DeferredItem<Item> item : items){
                    event.accept(item);
                }
            }
        }
    }

    public void addBlockToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                     ResourceKey<CreativeModeTab> tab, DeferredBlock<Block> block) {
        if(event.getTabKey() == tab){
            event.accept(block);
        }
    }

    public void addBlockToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                      List<ResourceKey<CreativeModeTab>> tabs, DeferredBlock<Block> block) {
        for(ResourceKey<CreativeModeTab> tab : tabs){
            if(event.getTabKey() == tab){
                event.accept(block);
            }
        }
    }

    public void addBlockToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                      ResourceKey<CreativeModeTab> tab, List<DeferredBlock<Block>> blocks) {
        if(event.getTabKey() == tab){
            for(DeferredBlock<Block> block : blocks){
                event.accept(block);
            }
        }
    }

    public void addBlockToCreativeTab(BuildCreativeModeTabContentsEvent event,
                                      List<ResourceKey<CreativeModeTab>> tabs, List<DeferredBlock<Block>> blocks) {
        for(ResourceKey<CreativeModeTab> tab : tabs){
            if(event.getTabKey() == tab){
                for(DeferredBlock<Block> block : blocks){
                    event.accept(block);
                }
            }
        }
    }


}
