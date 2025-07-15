package net.sixeyes.vanillasprinkles.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;

public class StretchedNetBlock extends AbstractNetBlock {

    public static final IntProperty DISTANCE = IntProperty.of("distance",1,9);
    public StretchedNetBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(DISTANCE, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE);
    }
}
