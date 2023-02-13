package site.hellishmods.digitality.lib;

import java.io.File;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

import site.hellishmods.digitality.init.CommonPackInit;
import site.hellishmods.digitality.templates.CubeAllModelTemplate;
import site.hellishmods.digitality.templates.ItemModelTemplate;
import site.hellishmods.digitality.templates.SoundEventTemplate;
import site.hellishmods.digitality.templates.SoundTemplate;

public class VirtualResourcePack extends VirtualCommonPack {
    public VirtualResourcePack(String modid) {
        super(modid);
        dir = CommonPackInit.assetDir.toPath().resolve(modid); // Dir = assets/<mod_id>
    }

    // Add sounds
    public void sound(File ogg, String name, String category) {
        name = FilenameUtils.removeExtension(name); // Remove extensions from the name
        customAsset(ogg, name+".ogg", "sounds"); // Copy sound to assets/<mod_id>/sounds

        // Templates
        SoundTemplate soundObject = new SoundTemplate(); // Sound
        soundObject.name = String.format("%s:%s", modid, name);
        soundObject.steam = (category=="record") ? true : false;
        SoundEventTemplate soundEvent = new SoundEventTemplate(); // Sound event
        soundEvent.category = category;
        soundEvent.sounds.add(soundObject);

        addJsonKey(dir.resolve("sounds.json").toFile(), name, soundEvent); // Add key to sounds.json
    }
    public void sound(File ogg, String category) {sound(ogg, ogg.getName(), category);}
    public void sound(Path ogg, String name, String category) {sound(ogg.toFile(), name, category);}
    public void sound(Path ogg, String category) {sound(ogg.toFile(), ogg.toFile().getName(), category);}
    
    // Register textures
    public void texture(String type, File file, String name) {
        name = FilenameUtils.removeExtension(name); // Remove extension from name
        File mcemeta = file.toPath().resolveSibling(FilenameUtils.removeExtension(file.getName())+".png.mcmeta").toFile(); // Mcmeta file

        customAsset(file, name+".png", "textures", type); // Copy texture
        if(mcemeta.exists()) customAsset(mcemeta, name+".png.mcmeta", "textures", type); // Copy .mcmeta file (if exists)
    }
    public void texture(String type, File file) {texture(type, file, file.getName());}
    public void texture(String type, Path file, String name) {texture(type, file.toFile(), name);}
    public void texture(String type, Path file) {texture(type, file.toFile(), file.toFile().getName());}

    // Add item models
    public void itemModel(String name, String... layers) {
        ItemModelTemplate modelJson = new ItemModelTemplate(); // Make a new template
        for (int i=0; i<layers.length; i++) modelJson.textures.addProperty("layer"+i, String.format("%s:item/%s", modid, layers[i])); // Add each layer

        newJson(dir.resolve("models").resolve("item").resolve(name+".json").toFile(), modelJson); // Write json
    }
    public void blockAllModel(String name, String texture) {
        newJson(dir.resolve("models").resolve("block").resolve(name+".json").toFile(), new CubeAllModelTemplate(String.format("%s:block/%s", modid, texture))); // Write model
    }

    // Add lang
    public void lang(String key, String value, String lang) {
        addJsonKey(dir.resolve("lang").resolve(FilenameUtils.removeExtension(lang)+".json").toFile(), key, value); // Add key to lang file
    }
    public void lang(String key, String value) {lang(key, value, "en_us");} // Default lang - en_us
}
