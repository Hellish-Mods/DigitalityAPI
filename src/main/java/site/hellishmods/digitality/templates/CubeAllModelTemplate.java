package site.hellishmods.digitality.templates;

// Template for block/cube_all model jsons
public class CubeAllModelTemplate extends ModelTemplate {
    public CubeAllModelTemplate(String texture) {
        parent = "minecraft:block/cube_all";
        textures.addProperty("all", texture);
    }
}
