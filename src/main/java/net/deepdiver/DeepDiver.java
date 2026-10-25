package net.deepdiver;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeepDiver implements ModInitializer {
	public static final String MOD_ID = "deepdiver";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		ModItems.init();
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output -> {
			output.accept(ModItems.DIVING_HELMET);
			output.accept(ModItems.DIVING_CHESTPLATE);
			output.accept(ModItems.DIVING_LEGGINGS);
			output.accept(ModItems.DIVING_BOOTS);
		});
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			for (ServerPlayer player : server.getPlayerList().getPlayers()) {
				DivingSuit.tickVision(player);
			}
		});
		LOGGER.info("Deep Diver loaded - the ocean floor is now walkable.");
	}
}
