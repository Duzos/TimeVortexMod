package net.plaaasma.vortexmod.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.block.entity.ModBlockEntities;
import net.plaaasma.vortexmod.block.entity.TardisBlockEntity;
import net.plaaasma.vortexmod.block.entity.ThrottleBlockEntity;
import net.plaaasma.vortexmod.block.entity.VortexInterfaceBlockEntity;
import net.plaaasma.vortexmod.worldgen.dimension.ModDimensions;
import net.plaaasma.vortexmod.worldgen.portal.ModTeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class DoorBlock extends Block {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 32, 16);

    public DoorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftserver = serverLevel.getServer();
            ServerLevel overworldDimension = minecraftserver.getLevel(Level.OVERWORLD);
            Iterable<ServerLevel> serverLevels = minecraftserver.getAllLevels();
            ServerLevel tardisDimension = minecraftserver.getLevel(ModDimensions.tardisDIM_LEVEL_KEY);
            ServerLevel vortexDimension = minecraftserver.getLevel(ModDimensions.vortexDIM_LEVEL_KEY);

            Random random = new Random();

            if (serverLevel == tardisDimension) {
                for (int x = -100; x <= 100; x++) {
                    for (int y = -100; y <= 100; y++) {
                        for (int z = -100; z <= 100; z++) {
                            BlockPos currentPos = pPos.offset(x, y, z);
                            var blockEntity = pLevel.getBlockEntity(currentPos);
                            if (blockEntity instanceof VortexInterfaceBlockEntity vortexInterfaceBlockEntity) {
                                ServerLevel targetDimension = overworldDimension;

                                for (ServerLevel cLevel : serverLevels) {
                                    if (cLevel.dimension().location().getPath().hashCode() == vortexInterfaceBlockEntity.data.get(9)) {
                                        targetDimension = cLevel;
                                    }
                                }

                                BlockPos blockExitPos = new BlockPos(vortexInterfaceBlockEntity.data.get(6), vortexInterfaceBlockEntity.data.get(7), vortexInterfaceBlockEntity.data.get(8));
                                if (targetDimension.getBlockState(blockExitPos).getBlock() == ModBlocks.TARDIS_BLOCK.get()) {
                                    Vec3 exitPosition = new Vec3(vortexInterfaceBlockEntity.data.get(6) + 0.5, vortexInterfaceBlockEntity.data.get(7), vortexInterfaceBlockEntity.data.get(8) + 1.5);
                                    pPlayer.changeDimension(targetDimension, new ModTeleporter(exitPosition));
                                } else {
                                    pPlayer.changeDimension(vortexDimension, new ModTeleporter(new Vec3(random.nextInt(1000000) - 500000, -100, random.nextInt(1000000) - 500000)));
                                }

                                return InteractionResult.CONSUME;
                            }
                        }
                    }
                }
            } else {
                pPlayer.displayClientMessage(Component.literal("Door is not in the TARDIS dimension.").withStyle(ChatFormatting.DARK_RED), true);
            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.translatable("tooltip.vortexmod.door_block.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}