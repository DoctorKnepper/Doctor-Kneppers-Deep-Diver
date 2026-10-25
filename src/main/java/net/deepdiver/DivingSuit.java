package net.deepdiver;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class DivingSuit {
	public static final double ASCENT_THRUST = 0.22;
	public static final double MAX_SINK_SPEED = -0.5;
	private static final int VISION_DURATION = 260;
	private static final int VISION_REFRESH_BELOW = 220;

	private DivingSuit() {
	}

	public static boolean hasHelmet(LivingEntity entity) {
		return entity.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.DIVING_HELMET);
	}

	public static boolean hasFullSuit(LivingEntity entity) {
		return hasHelmet(entity)
			&& entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.DIVING_CHESTPLATE)
			&& entity.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.DIVING_LEGGINGS)
			&& entity.getItemBySlot(EquipmentSlot.FEET).is(ModItems.DIVING_BOOTS);
	}

	public static boolean walksOnSeaFloor(LivingEntity entity) {
		if (!(entity instanceof Player player)) {
			return false;
		}

		if (player.isSwimming() || player.getAbilities().flying || player.isPassenger()) {
			return false;
		}

		return hasFullSuit(player);
	}

	static void tickVision(ServerPlayer player) {
		boolean submerged = hasHelmet(player) && player.isEyeInFluid(FluidTags.WATER);
		MobEffectInstance current = player.getEffect(MobEffects.NIGHT_VISION);
		boolean ours = current != null && current.isAmbient() && !current.isVisible() && current.getDuration() <= VISION_DURATION;
		if (submerged) {
			if (current == null || ours && current.getDuration() < VISION_REFRESH_BELOW) {
				player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, VISION_DURATION, 0, true, false, false));
			}
		} else if (ours) {
			player.removeEffect(MobEffects.NIGHT_VISION);
		}
	}
}
