package site.hellishmods.digitality.init;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import site.hellishmods.digitality.digitality;
import site.hellishmods.digitality.util.ExceptionHandler;

// Init the pack directory
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = digitality.MOD_ID)
public class CommonPackInit {
    public static Path tmpDir; // /tmp/digitality
    public static File assetDir; // /tmp/digitality/assets
    public static File dataDir; // /tmp/digitality/data

    // Generate the files
    public static void generate() {
        try {
            // Create digitality dir in tmp
            tmpDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve(digitality.MOD_ID);
            tmpDir.toFile().mkdirs();
            digitality.LOGGER.info("Created virtual pack: "+tmpDir);

            // Create pack.mcmeta
            File pack = new File(tmpDir.toFile(), "pack.mcmeta");
            pack.createNewFile(); // Create the file
            FileWriter packwriter = new FileWriter(pack);
            packwriter.write("{\"pack\": {\"pack_format\": 6, \"description\": \"DigitalityAPI's auto-loaded resources\"}}"); // Write the json (I decided not to bother with the templates so it's done with a string)
            packwriter.close(); // Close file writer

            // Set asset and data dirs
            assetDir = tmpDir.resolve("assets").toFile();
            dataDir = tmpDir.resolve("data").toFile();
            assetDir.mkdir();
            dataDir.mkdir();
            
            // Delete digitality dir on minecraft shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    FileUtils.deleteDirectory(tmpDir.toFile());
                } catch (IOException err) {new ExceptionHandler(err);}
            }));
        } catch (IOException err) {new ExceptionHandler(err);}
    }

    @SubscribeEvent
    static void onConstruct(final FMLConstructModEvent e) {
        if (tmpDir==null) generate(); // Generate if not already
        ResourcePackInit.load(); // Call the resource pack method when the event comes
    }
}
