package com.radex392.temporalrelativity.entity;

import com.radex392.temporalrelativity.TemporalRelativity;
import com.radex392.temporalrelativity.reference.Names;
import com.radex392.temporalrelativity.reference.Textures;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

import javax.swing.text.html.parser.Entity;

/**
 * Created by OEM on 25/02/2015.
 */
public class EntityInitialiserTR {

    public static void init() {
        registerEntities();
    }

    public static void registerEntities() {
        createEntity( TimeChicken.class, Textures.RESOURCE_PREFIX + Names.Entities.TIME_CHICKEN, 0x3B00FF, 0xA700FF, 64, 1, true, 2, 0, 1, EnumCreatureType.creature, BiomeGenBase.forest, BiomeGenBase.plains );
    }

    public static void createEntity( Class entityClass, String entityName, int solidColor, int spotColor, int trackingRange, int updateFreguency, boolean sendsVelocityUpdates,
                                    int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes ){
        int randomID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID( entityClass, entityName, randomID );

        EntityRegistry.registerModEntity(entityClass, entityName, randomID, TemporalRelativity.instance, trackingRange, updateFreguency, sendsVelocityUpdates );
        EntityRegistry.addSpawn( entityClass, weightedProb, min, max, typeOfCreature, biomes );

        createEgg( randomID, solidColor, spotColor );
    }

    private static void createEgg( int randomID, int solidColor, int spotColor ) {
        EntityList.entityEggs.put( Integer.valueOf( randomID ), new EntityList.EntityEggInfo( randomID, solidColor, spotColor )  );
    }
}
