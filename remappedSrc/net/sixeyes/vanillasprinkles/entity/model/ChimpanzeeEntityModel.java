package net.sixeyes.vanillasprinkles.entity.model;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.sixeyes.vanillasprinkles.VanillaSprinkles;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ChimpanzeeEntityModel extends GeoModel {

    private final Identifier model = Identifier.of(VanillaSprinkles.MOD_ID, "geo/chimpanzee/chimpanzee.geo.json");
    private final Identifier texture = Identifier.of(VanillaSprinkles.MOD_ID, "textures/entity/chimpanzee/texture.png");
    private final Identifier animations = Identifier.of(VanillaSprinkles.MOD_ID, "animations/chimpanzee.animation.json");

    @Override
    public Identifier getModelResource(GeoAnimatable animatable) {
        return this.model;
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable animatable) {
        return this.texture;
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable animatable) {
        return this.animations;
    }

    @Override
    public void setCustomAnimations(GeoAnimatable animatable, long instanceId, AnimationState animationState) {

        GeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
