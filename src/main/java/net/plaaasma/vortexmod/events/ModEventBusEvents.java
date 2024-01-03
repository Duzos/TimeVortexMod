package net.plaaasma.vortexmod.events;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.entities.ModEntities;
import net.plaaasma.vortexmod.entities.custom.LostTravelerEntity;

@Mod.EventBusSubscriber(modid = VortexMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLUE_TRADER.get(), LostTravelerEntity.createAttributes().build());
        event.put(ModEntities.ORANGE_TRADER.get(), LostTravelerEntity.createAttributes().build());
        event.put(ModEntities.PURPLE_TRADER.get(), LostTravelerEntity.createAttributes().build());
        event.put(ModEntities.BLACK_TRADER.get(), LostTravelerEntity.createAttributes().build());
    }
}