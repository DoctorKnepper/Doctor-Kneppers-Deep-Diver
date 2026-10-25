package net.deepdiver;

import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public final class ModItems {
	public static final TagKey<Item> REPAIRS_DIVING_SUIT = TagKey.create(Registries.ITEM, DeepDiver.id("repairs_diving_suit"));
	public static final ResourceKey<EquipmentAsset> DIVING_SUIT_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, DeepDiver.id("diving_suit"));
	public static final ArmorMaterial DIVING_SUIT = new ArmorMaterial(
		18,
		Map.of(ArmorType.HELMET, 2, ArmorType.CHESTPLATE, 6, ArmorType.LEGGINGS, 5, ArmorType.BOOTS, 2),
		12,
		SoundEvents.ARMOR_EQUIP_IRON,
		0.5F,
		0.1F,
		REPAIRS_DIVING_SUIT,
		DIVING_SUIT_ASSET
	);
	public static final Item DIVING_HELMET = register(
		"diving_helmet",
		ArmorType.HELMET,
		mods -> mods.withModifierAdded(
			Attributes.SUBMERGED_MINING_SPEED, new AttributeModifier(DeepDiver.id("diving_helmet_mining"), 0.8, Operation.ADD_VALUE), EquipmentSlotGroup.HEAD
		)
	);
	public static final Item DIVING_CHESTPLATE = register("diving_chestplate", ArmorType.CHESTPLATE, mods -> mods);
	public static final Item DIVING_LEGGINGS = register("diving_leggings", ArmorType.LEGGINGS, mods -> mods);
	public static final Item DIVING_BOOTS = register(
		"diving_boots",
		ArmorType.BOOTS,
		mods -> mods.withModifierAdded(
			Attributes.WATER_MOVEMENT_EFFICIENCY, new AttributeModifier(DeepDiver.id("diving_boots_movement"), 1.0, Operation.ADD_VALUE), EquipmentSlotGroup.FEET
		)
	);

	private ModItems() {
	}

	private static Item register(String name, ArmorType type, ModItems.ModifierTweak tweak) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, DeepDiver.id(name));
		Properties props = new Properties().humanoidArmor(DIVING_SUIT, type).attributes(tweak.apply(DIVING_SUIT.createAttributes(type))).setId(key);
		return Registry.register(BuiltInRegistries.ITEM, key, new Item(props));
	}

	static void init() {
	}

	private interface ModifierTweak {
		ItemAttributeModifiers apply(ItemAttributeModifiers modifiers);
	}
}
