package net.sixeyes.vanillasprinkles.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import net.sixeyes.vanillasprinkles.entity.ChimpanzeeEntity;
import net.sixeyes.vanillasprinkles.entity.model.ChimpanzeeEntityModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChimpanzeeEntityRenderer extends GeoEntityRenderer<ChimpanzeeEntity> {
    public ChimpanzeeEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ChimpanzeeEntityModel());
    }

    @Override
    public Identifier getTextureLocation(ChimpanzeeEntity animatable) {
        return Identifier.of(VanillaSprinkles.MOD_ID, "textures/entity/chimpanzee/texture.png");
    }

    @Override
    public void render(ChimpanzeeEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.7f, 0.7f, 0.7f);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
