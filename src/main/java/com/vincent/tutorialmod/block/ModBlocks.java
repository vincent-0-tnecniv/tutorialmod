package com.vincent.tutorialmod.block;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.block.custom.MagicBlock;
import com.vincent.tutorialmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(TutorialMod.MOD_ID);

    public static final DeferredBlock<Block> AZURITE_BLOCK = registerBlock("azurite_block",
            properties -> new Block(properties.strength(4.0F)
                    .requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> RAW_AZURITE_BLOCK = registerBlock("raw_azurite_block",
            properties -> new Block(properties.strength(4.0F)
                    .requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<Block> AZURITE_ORE =
            registerExperienceDroppingOre("azurite_ore", 2, 4,
                    4.0f);
    public static final DeferredBlock<Block> AZURITE_DEEPSLATE_ORE =
            registerExperienceDroppingOre("azurite_deepslate_ore", 3, 5,
                    4.0f, SoundType.DEEPSLATE);
    public static final DeferredBlock<Block> AZURITE_NETHER_ORE =
            registerExperienceDroppingOre("azurite_nether_ore", 2, 4,
                    3.0f, SoundType.NETHERRACK);
    public static final DeferredBlock<Block> AZURITE_END_ORE =
            registerExperienceDroppingOre("azurite_end_ore", 2, 4,
                    4.0f, SoundType.STONE);

    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock("magic_block",
            properties -> new MagicBlock(properties.strength(4.0F)
                    .requiresCorrectToolForDrops().sound(SoundType.DECORATED_POT)));

    private static DeferredBlock<Block> registerExperienceDroppingOre(String name, int minXp, int maxXp, float strength, SoundType soundType) {
        return registerBlock(name,
                properties -> new DropExperienceBlock(UniformInt.of(minXp, maxXp), properties.strength(strength)
                        .requiresCorrectToolForDrops().sound(soundType)));
    }

    private static DeferredBlock<Block> registerExperienceDroppingOre(String name, int minXp, int maxXp, float strength) {
        return registerBlock(name,
                properties -> new DropExperienceBlock(UniformInt.of(minXp, maxXp), properties.strength(strength)
                        .requiresCorrectToolForDrops().sound(SoundType.STONE)));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, function);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
