package com.radex392.temporalrelativity.entity;

import com.radex392.temporalrelativity.reference.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by OEM on 25/02/2015.
 */
public class RenderTimeChicken extends RenderLiving {

    private static final ResourceLocation mobTextures = new ResourceLocation( Reference.MOD_ID + ":textures/entity/TimeChicken.png");

    public RenderTimeChicken( ModelBase modelBase, float par2 ){
        super(modelBase, par2);
    }

    protected ResourceLocation getEntityTexture( TimeChicken chicken ){
         return mobTextures;
    }

    protected ResourceLocation getEntityTexture( Entity entity ) {
        return this.getEntityTexture((TimeChicken)entity);
    }
}
