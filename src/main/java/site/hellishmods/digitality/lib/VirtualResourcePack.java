package site.hellishmods.digitality.lib;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import site.hellishmods.digitality.init.CommonPackInit;
import site.hellishmods.digitality.templates.CubeAllModelTemplate;
import site.hellishmods.digitality.templates.ItemModelTemplate;
import site.hellishmods.digitality.templates.SoundEventTemplate;
import site.hellishmods.digitality.templates.SoundTemplate;
import site.hellishmods.digitality.util.ExceptionHandler;

public class VirtualResourcePack extends VirtualCommonPack {
    public VirtualResourcePack(String modid) {
        super(modid);
        dir = CommonPackInit.assetDir.toPath().resolve(modid); // Dir = assets/<mod_id>
    }

    // Add sounds
    public void sound(File ogg, String name, String category) {
        try {
            name = FilenameUtils.removeExtension(name); // Remove extensions from the name

            customAsset(ogg, name+".ogg", "sounds"); // Copy sound to assets/<mod_id>/sounds

            File soundsJson = dir.resolve("sounds.json").toFile(); // Get sounds.json file

            JsonObject sounds; // Sounds.json in an object form
            if(soundsJson.exists()) sounds = new JsonParser().parse(new JsonReader(new FileReader(soundsJson))).getAsJsonObject(); // If sounds.json exists, load it
            else { // If not
                sounds = new JsonObject(); // Create a new object
                soundsJson.createNewFile(); // Create a new file
            }

            // Templates
            SoundTemplate soundObject = new SoundTemplate(); // Sound
            soundObject.name = name;
            soundObject.steam = (category=="record") ? true : false;
            SoundEventTemplate soundEvent = new SoundEventTemplate(); // Sound event
            soundEvent.category = category;
            soundEvent.sounds.add(soundObject);

            // Add the sound and write json
            sounds.add(name, GSON.toJsonTree(soundEvent));
            writeJson(soundsJson, sounds);
        } catch (IOException e) {new ExceptionHandler(e);}
    }
    public void sound(File ogg, String category) {sound(ogg, ogg.getName(), category);}
    public void sound(Path ogg, String name, String category) {sound(ogg.toFile(), name, category);}
    public void sound(Path ogg, String category) {sound(ogg.toFile(), ogg.toFile().getName(), category);}
    
    // Register texture via customAsset method
    public void texture(String type, File file, String name) {customAsset(file, FilenameUtils.removeExtension(name)+".png", "textures", type);}
    public void texture(String type, File file) {texture(type, file, file.getName());}
    public void texture(String type, Path file, String name) {texture(type, file.toFile(), name);}
    public void texture(String type, Path file) {texture(type, file.toFile(), file.toFile().getName());}

    // Private method for creating models
    private File createModel(String name, String type) throws IOException {
        File model = dir.resolve("models").resolve(type).resolve(name+".json").toFile(); // Get model path
        model.getParentFile().mkdirs(); // Create parent dir
        model.delete(); // Override the file if it already exists
        model.createNewFile(); // Create a new file

        return model; // Return the file
    }
    // Add item models
    public void itemModel(String name, String... layers) {
        try {
            File model = createModel(name, "item"); // Create new model

            ItemModelTemplate modelJson = new ItemModelTemplate(); // Make a new template
            for (int i=0; i<layers.length; i++) modelJson.textures.addProperty("layer"+i, String.format("%s:item/%s", modid, layers[i])); // Add each layer

            writeJson(model, modelJson); // Write json
        } catch (IOException e) {new ExceptionHandler(e);}
    }
    public void blockAllModel(String name, String texture) {
        try {
            File model = createModel(name, "block"); // Create model
            writeJson(model, new CubeAllModelTemplate(String.format("%s:block/%s", modid, texture))); // Write model
        } catch (IOException e) {new ExceptionHandler(e);}
    }
}
