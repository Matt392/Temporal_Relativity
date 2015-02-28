package com.radex392.temporalrelativity.inventory;

import com.radex392.temporalrelativity.reference.Textures;
import com.radex392.temporalrelativity.tileEntitiy.TileEntityTemporalInfusor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUITemporalInfusor extends GuiContainer
{
	private TileEntityTemporalInfusor tileEntityTemporalInfusor;

	public GUITemporalInfusor(InventoryPlayer inventoryPlayer, TileEntityTemporalInfusor tileEntityTemporalInfusor)
	{
		super(new ContainerTemporalInfusor(inventoryPlayer, tileEntityTemporalInfusor));
		ySize = 176;
		this.tileEntityTemporalInfusor = tileEntityTemporalInfusor;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		// NOOP
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(Textures.Gui.TEMPORAL_INFUSOR);

		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		int scaleAdjustment;

		if (this.tileEntityTemporalInfusor.getState() == 1)
		{
			scaleAdjustment = this.tileEntityTemporalInfusor.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(xStart + 57, yStart + 26 + 23 - scaleAdjustment, 176, 12 - scaleAdjustment, 14, scaleAdjustment + 2);
		}

		scaleAdjustment = this.tileEntityTemporalInfusor.getCookProgressScaled(24);
		this.drawTexturedModalRect(xStart + 79, yStart + 35, 176, 14, scaleAdjustment + 1, 16);
	}
}
