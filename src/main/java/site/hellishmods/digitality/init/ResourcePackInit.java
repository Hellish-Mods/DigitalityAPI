package site.hellishmods.digitality.init;

import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import site.hellishmods.digitality.digitality;

public class ResourcePackInit implements IPackFinder {
    @Override
    public void loadPacks(Consumer<ResourcePackInfo> consumer, IFactory factory) {
        consumer.accept(ResourcePackInfo.create(digitality.MOD_ID, true, () -> new FolderPack(CommonPackInit.tmpDir.toFile()), factory, ResourcePackInfo.Priority.TOP, IPackNameDecorator.DEFAULT)); // Load virtual resource pack when resource packs load
    }

    public static void load() {
        if (FMLEnvironment.dist==Dist.DEDICATED_SERVER) return; // If on server, don't care
        Minecraft.getInstance().getResourcePackRepository().addPackFinder(new ResourcePackInit()); // Add the pack to selected packs
    }
}
