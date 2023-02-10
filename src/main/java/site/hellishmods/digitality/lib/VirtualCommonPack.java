package site.hellishmods.digitality.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import site.hellishmods.digitality.init.CommonPackInit;
import site.hellishmods.digitality.util.ExceptionHandler;

// Abstract class for VirtualDataPack and VirtualResourcePack
public abstract class VirtualCommonPack {
    protected String modid; // Mod id
    protected Path dir; // The pack directory
    protected final Gson GSON = new Gson(); // Json loader

    // Constructor
    protected VirtualCommonPack(String id) {
        if(CommonPackInit.tmpDir==null) CommonPackInit.generate(); // If pack directory doesn't exist yet, create it
        modid = id; // Set the mod id
    }
    // Write json method
    protected void writeJson(File file, Object object) throws IOException {
        FileWriter writer = new FileWriter(file); // New file writer
        writer.write(GSON.toJson(object)); // Write stringified json to file
        writer.close(); // Close file writer
    }

    // Custom assets
    public void customAsset(File originalFile, String name, String... assetPath) {
        try {
            File path = Paths.get(dir.toString(), assetPath).resolve(name).toFile(); // Resolve path
            path.getParentFile().mkdirs(); // Create parent dirs
            path.delete(); // Override
            FileUtils.copyFile(originalFile, path); // Copy file
        } catch(IOException e) {new ExceptionHandler(e);}
    }
    public void customAsset(Path originalFile, String name, String... assetPath) {customAsset(originalFile.toFile(), name, assetPath);}
}
