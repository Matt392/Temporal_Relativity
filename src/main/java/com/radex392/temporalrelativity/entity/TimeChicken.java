package com.radex392.temporalrelativity.entity;

import com.radex392.temporalrelativity.reference.Reference;
import cpw.mods.fml.common.Mod;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * Created by OEM on 25/02/2015.
 */
public class TimeChicken extends EntityAnimal{

    public float field_70886_e;
    public float destPos;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    public TimeChicken( World world ) {
        super( world );

        setSize(0.3f, 0.7f);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 1.4d));
        tasks.addTask(2, new EntityAIMate(this, 1.0d));
        tasks.addTask(3, new EntityAITempt(this, 1.0d, Items.ender_pearl, false));
        tasks.addTask(4, new EntityAIFollowParent(this, 1.1d));
        tasks.addTask(5, new EntityAIWander(this, 1.0d));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        tasks.addTask(7, new EntityAILookIdle(this));

    }

    public boolean isAIEnabled(){
        return true;
    }

    protected void applyEntityAttributes(){
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0d);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }


    @Override
    public EntityAgeable createChild( EntityAgeable entity ) {

        return new TimeChicken( worldObj ) ;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return Reference.RESOURCE_PREFIX + "mob.timeChicken.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return Reference.RESOURCE_PREFIX + "mob.timeChicken.hurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return Reference.RESOURCE_PREFIX + "mob.timeChicken.hurt";
    }

    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        playSound("mob.chicken.step", 0.15F, 1.0F);
    }

    protected Item getDropItem()
    {
        return Items.ender_pearl;
    }

    /**
     * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
     * par2 - Level of Looting used to kill this mob.
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);

        for (int k = 0; k < j; ++k)
        {
            dropItem(Items.ender_pearl, 1);
        }

        /*if (this.isBurning())
        {
            this.dropItem(Items.cooked_chicken, 1);
        }
        else
        {
            this.dropItem(Items.chicken, 1);
        }*/
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();

        field_70888_h = field_70886_e;
        field_70884_g = destPos;
        destPos = (float)((double)destPos + (double)(onGround ? -1 : 4) * 0.3D);

        if (destPos < 0.0F)
        {
            destPos = 0.0F;
        }

        if (destPos > 1.0F)
        {
            destPos = 1.0F;
        }

        if (!onGround && field_70889_i < 1.0F)
        {
            field_70889_i = 1.0F;
        }

        field_70889_i = (float)((double)field_70889_i * 0.9D);

        if (!onGround && motionY < 0.0D)
        {
            motionY *= 0.6D;
        }

        field_70886_e += field_70889_i * 2.0F;
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float p_70069_1_) {}

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack item)
    {
        return item != null && item.getItem() instanceof ItemEnderPearl;
    }

}
