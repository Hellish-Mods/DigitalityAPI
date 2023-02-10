package site.hellishmods.digitality.init;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import site.hellishmods.digitality.digitality;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = digitality.MOD_ID)
public class DataPackInit {
    @SubscribeEvent
    static void onServerStart(final FMLServerStartedEvent e) { // When server starts/a world is launched
        ResourcePackList packs = e.getServer().getPackRepository(); // Get loaded packs

        packs.addPackFinder(new FolderPackFinder(CommonPackInit.tmpDir.getParent().toFile(), IPackNameDecorator.DEFAULT)); // Add pack finder
        packs.reload(); // Reload visible packs
        packs.setSelected(ImmutableSet.<String>builder() // Add virtual pack to selected packs
            .addAll(packs.getSelectedIds())
            .add("file/"+CommonPackInit.tmpDir.toFile().getName())
            .build()
        );
        e.getServer().reloadResources(packs.getSelectedIds()); // Reload loaded packs
    }
}
