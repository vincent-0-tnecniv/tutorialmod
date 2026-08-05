package com.vincent.tutorialmod.datagen;

import com.vincent.tutorialmod.TutorialMod;
import com.vincent.tutorialmod.sound.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
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
    }

    protected void addSound(SoundEvent sound, Identifier location) {
        add(sound, definition().subtitle("sounds." + location.getNamespace() + "." + location.getPath())
        .with(sound(location)));
    }

    protected void addSound(Supplier<SoundEvent> sound, Identifier location) {
        addSound(sound.get(), location);
    }

    protected void addSound(SoundEvent sound, Identifier soundSubtitle, List<Identifier> locations) {
        for(Identifier location : locations) {
            add(sound, definition().subtitle("sounds." + soundSubtitle.getNamespace() + "." + soundSubtitle.getPath())
                    .with(sound(location)));
        }
    }

    protected void addSound(Supplier<SoundEvent> sound, Identifier soundSubtitle, List<Identifier> locations) {
        addSound(sound.get(), soundSubtitle, locations);
    }
}
