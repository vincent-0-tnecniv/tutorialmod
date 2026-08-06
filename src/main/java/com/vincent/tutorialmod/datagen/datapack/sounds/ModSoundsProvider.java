package com.vincent.tutorialmod.datagen.datapack.sounds;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.List;
import java.util.function.Supplier;

public class ModSoundsProvider extends SoundDefinitionsProvider {
    public ModSoundsProvider(PackOutput output) {
        super(output, TutorialMod.MOD_ID);
    }

    @Override
    public void registerSounds() {
        addSound(ModSounds.VALUABLES_FOUND, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "valuables_found"));
        addSound(ModSounds.VALUABLES_NOT_FOUND, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "valuables_not_found"));
        addSound(ModSounds.BAR_BRAWL, Identifier.fromNamespaceAndPath(TutorialMod.MOD_ID, "bar_brawl"));
    }

    protected void addSound(SoundEvent sound, Identifier location) {
        add(sound, definition().subtitle(Util.makeDescriptionId("sounds", location))
        .with(sound(location)));
    }

    protected void addSound(Supplier<SoundEvent> sound, Identifier location) {
        addSound(sound.get(), location);
    }

    protected void addSound(SoundEvent sound, Identifier soundSubtitle, List<Identifier> locations) {
        for(Identifier location : locations) {
            add(sound, definition().subtitle(Util.makeDescriptionId("sounds", soundSubtitle))
                    .with(sound(location)));
        }
    }

    protected void addSound(Supplier<SoundEvent> sound, Identifier soundSubtitle, List<Identifier> locations) {
        addSound(sound.get(), soundSubtitle, locations);
    }

    protected void addJukeboxSong(SoundEvent sound, Identifier location) {
        add(sound, definition().subtitle(Util.makeDescriptionId("sounds", location))
                .with(sound(location).stream()));
    }
    protected void addJukeboxSong(Supplier<SoundEvent> sound, Identifier location) {
        addJukeboxSong(sound.get(), location);
    }

    protected void addJukeboxSong(SoundEvent sound, Identifier soundSubtitle, List<Identifier> locations) {
        for(Identifier location : locations) {
            add(sound, definition().subtitle(Util.makeDescriptionId("sounds", soundSubtitle))
                    .with(sound(location).stream()));
        }
    }

    protected void addJukeboxSong(Supplier<SoundEvent> sound, Identifier soundSubtitle, List<Identifier> locations) {
        addJukeboxSong(sound.get(), soundSubtitle, locations);
    }

}
