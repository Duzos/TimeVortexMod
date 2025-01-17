package net.plaaasma.vortexmod.events;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import net.plaaasma.vortexmod.block.entity.renderer.AngelBlockEntityRenderer;
import net.plaaasma.vortexmod.block.entity.renderer.MonitorBlockEntityRenderer;
import net.plaaasma.vortexmod.entities.client.models.DalekModel;
import net.plaaasma.vortexmod.entities.client.models.LaserModel;
import net.plaaasma.vortexmod.entities.client.models.LostTravelerModel;
import net.plaaasma.vortexmod.entities.client.ModModelLayers;
import net.plaaasma.vortexmod.entities.client.models.TardisModel;
import net.plaaasma.vortexmod.network.PacketHandler;

import java.util.ArrayList;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = VortexMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.LOST_TRAVELER_LAYER, LostTravelerModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DALEK_LAYER, DalekModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.LASER_LAYER, LaserModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TARDIS_LAYER, TardisModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.MONITOR_BE.get(), MonitorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ANGEL_BE.get(), AngelBlockEntityRenderer::new);
    }
}
