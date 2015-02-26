package com.radex392.temporalrelativity.proxy;

import com.radex392.temporalrelativity.entity.RenderTimeChicken;
import com.radex392.temporalrelativity.entity.TimeChicken;
import com.radex392.temporalrelativity.entity.models.ModelTimeChicken;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderThings(){
        RenderingRegistry.registerEntityRenderingHandler(TimeChicken.class, new RenderTimeChicken( new ModelTimeChicken(), 0.3f ) );
    }
}
