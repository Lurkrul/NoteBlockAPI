package com.xxmicloxx.NoteBlockAPI.model.playmode;

import com.xxmicloxx.NoteBlockAPI.model.*;
import com.xxmicloxx.NoteBlockAPI.utils.CompatibilityUtils;
import com.xxmicloxx.NoteBlockAPI.utils.InstrumentUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Ignores panning of {@link Note} and {@link Layer} from nbs format and plays mono {@link Note} as fake stereo at fixed offset from {@link Player} head.
 */
public class MonoStereoMode extends ChannelMode{

    private float distance = 2;

    @Override
    public void play(Player player, Location location, Song song, Layer layer, Note note, SoundCategory soundCategory, float volume, float pitch) {
        if (InstrumentUtils.isCustomInstrument(note.getInstrument())) {
            CustomInstrument instrument = song.getCustomInstruments()[note.getInstrument() - InstrumentUtils.getCustomInstrumentFirstIndex()];

            if (instrument.getSound() != null) {
                CompatibilityUtils.playSound(player, location, instrument.getSound(), soundCategory, volume, pitch, distance);
                CompatibilityUtils.playSound(player, location, instrument.getSound(), soundCategory, volume, pitch, -distance);
            } else {
                CompatibilityUtils.playSound(player, location, instrument.getSoundFileName(), soundCategory, volume, pitch, distance);
                CompatibilityUtils.playSound(player, location, instrument.getSoundFileName(), soundCategory, volume, pitch, -distance);
            }
        } else {
            CompatibilityUtils.playSound(player, location, InstrumentUtils.getInstrument(note.getInstrument()), soundCategory, volume, pitch, distance);
            CompatibilityUtils.playSound(player, location, InstrumentUtils.getInstrument(note.getInstrument()), soundCategory, volume, pitch, -distance);
        }
    }

    /**
     * Returns distance of {@link Note} from {@link Player}'s head.
     * @return
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Sets distance of {@link Note} from {@link Player}'s head.
     * @param distance
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
