package gg.melty.deviant;

import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/** Finds the player's own Detroit: Become Human install. Nothing from the game is shipped. UNTESTED. */
public final class DetroitLocator {
    private static final String FOLDER = "Detroit Become Human"; // TODO verify Steam folder name
    private static final Pattern PATH = Pattern.compile("\"path\"\\s+\"([^\"]+)\"");

    public static Optional<Path> find() {
        List<Path> roots = new ArrayList<>();
        for (String s : new String[]{"C:/Program Files (x86)/Steam", "C:/Program Files/Steam"}) roots.add(Path.of(s));
        // Steam lists extra library folders in steamapps/libraryfolders.vdf
        for (Path steam : new ArrayList<>(roots)) {
            Path vdf = steam.resolve("steamapps/libraryfolders.vdf");
            if (!Files.isRegularFile(vdf)) continue;
            try {
                Matcher m = PATH.matcher(Files.readString(vdf));
                while (m.find()) roots.add(Path.of(m.group(1).replace("\\\\", "/")));
            } catch (Exception ignored) {}
        }
        for (Path r : roots) {
            Path g = r.resolve("steamapps/common").resolve(FOLDER);
            if (Files.isDirectory(g)) return Optional.of(g);
        }
        return Optional.empty(); // caller shows an in-game "Detroit not installed" message
    }
}
