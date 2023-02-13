package site.hellishmods.digitality.lib;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

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
    
    // Json methods
    private void writeJson(File file, Object object) throws IOException {
        file.getParentFile().mkdirs(); // Create parent dir
        if (!file.exists()) file.createNewFile(); // Create a new file

        FileWriter writer = new FileWriter(file); // New file writer
        writer.write(GSON.toJson(object)); // Write stringified json to file
        writer.close(); // Close file writer
    }
    protected void newJson(File file, Object object) { // New json (override)
        try {
            file.delete(); // Override the file if it already exists
            writeJson(file, object); // Write json
        } catch (IOException e) {new ExceptionHandler(e);}
    }
    protected void addJsonKey(File file, String key, Object value) { // Edit json
        try {
            JsonObject json; // JsonObject

            if (file.exists()) json = new JsonParser().parse(new JsonReader(new FileReader(file))).getAsJsonObject(); // Load if file exists
            else json = new JsonObject(); // New if it doesn't

            json.add(key, GSON.toJsonTree(value)); // Add value
            writeJson(file, json); // Save json
        } catch (IOException e) {new ExceptionHandler(e);}
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
    public void customAsset(Object obj, String name, String... assetPath) {newJson(Paths.get(dir.toString(), assetPath).resolve(name).toFile(), obj);}
}
