package com.vincent.tutorialmod.datagen.sounds;

import com.vincent.tutorialmod.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModJukeboxSongs {

    public static void boostrap(BootstrapContext<JukeboxSong> context) {
        register(context, ModSounds.BAR_BRAWL_KEY, ModSounds.BAR_BRAWL, 162, 15);
    }

    protected static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key,
                                   DeferredHolder<SoundEvent, SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent.getDelegate(),
                Component.translatable(Util.makeDescriptionId("jukebox_song", key.identifier())), lengthInSeconds, comparatorOutput));
    }
}
