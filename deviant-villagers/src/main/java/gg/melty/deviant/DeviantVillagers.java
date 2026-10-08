package gg.melty.deviant;

import net.fabricmc.api.ModInitializer;
import org.slf4j.*;
import java.nio.file.Path;
import java.util.Optional;

public class DeviantVillagers implements ModInitializer {
    public static final Logger LOG = LoggerFactory.getLogger("deviant_villagers");
    public static final RevolutionMeter METER = new RevolutionMeter();
    public static Optional<Path> detroit = Optional.empty();

    @Override public void onInitialize() {
        detroit = DetroitLocator.find();
        if (detroit.isEmpty()) LOG.warn("Detroit: Become Human not found; audio/text disabled, in-game notice will be shown.");
        // TODO per sheets/minecraft_hooks.json: villager spawn tagging, LED render, interaction screen, Detroit audio playback
    }
}
