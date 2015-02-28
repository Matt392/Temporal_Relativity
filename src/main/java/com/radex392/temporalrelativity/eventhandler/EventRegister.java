package com.radex392.temporalrelativity.eventhandler;

import net.minecraftforge.common.MinecraftForge;

/**
 * Created by OEM on 27/02/2015.
 */
public class EventRegister {
    public static void registerEventListeners() {
        // DEBUG
        System.out.println("Registering event listeners");

        MinecraftForge.EVENT_BUS.register(new MobDeathHandler());

    }
}
