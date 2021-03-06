package li.cil.tis3d.client;

import li.cil.tis3d.api.API;
import li.cil.tis3d.api.ManualAPI;
import li.cil.tis3d.api.prefab.manual.ItemStackTabIconRenderer;
import li.cil.tis3d.api.prefab.manual.TextureTabIconRenderer;
import li.cil.tis3d.client.gui.GuiHandlerClient;
import li.cil.tis3d.client.manual.provider.BlockImageProvider;
import li.cil.tis3d.client.manual.provider.ItemImageProvider;
import li.cil.tis3d.client.manual.provider.OreDictImageProvider;
import li.cil.tis3d.client.manual.provider.TextureImageProvider;
import li.cil.tis3d.client.renderer.TextureLoader;
import li.cil.tis3d.client.renderer.tileentity.TileEntitySpecialRendererCasing;
import li.cil.tis3d.client.renderer.tileentity.TileEntitySpecialRendererController;
import li.cil.tis3d.common.Constants;
import li.cil.tis3d.common.ProxyCommon;
import li.cil.tis3d.common.TIS3D;
import li.cil.tis3d.common.init.Blocks;
import li.cil.tis3d.common.init.Items;
import li.cil.tis3d.common.tileentity.TileEntityCasing;
import li.cil.tis3d.common.tileentity.TileEntityController;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.function.Supplier;

/**
 * Takes care of client-side only setup.
 */
public final class ProxyClient extends ProxyCommon {
    @Override
    public void onPreInit(final FMLPreInitializationEvent event) {
        super.onPreInit(event);

        MinecraftForge.EVENT_BUS.register(TextureLoader.INSTANCE);
    }

    @Override
    public void onInit(final FMLInitializationEvent event) {
        super.onInit(event);

        // Set up tile entity renderer for dynamic module content.
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCasing.class, new TileEntitySpecialRendererCasing());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityController.class, new TileEntitySpecialRendererController());

        // Register GUI handler for fancy GUIs in our almost GUI-less mod!
        NetworkRegistry.INSTANCE.registerGuiHandler(TIS3D.instance, new GuiHandlerClient());

        // Add default manual providers for client side stuff.
        ManualAPI.addProvider("", new TextureImageProvider());
        ManualAPI.addProvider("item", new ItemImageProvider());
        ManualAPI.addProvider("block", new BlockImageProvider());
        ManualAPI.addProvider("oredict", new OreDictImageProvider());

        ManualAPI.addTab(new TextureTabIconRenderer(new ResourceLocation(API.MOD_ID, "textures/gui/manual_home.png")), "tis3d.manual.home", "%LANGUAGE%/index.md");
        ManualAPI.addTab(new ItemStackTabIconRenderer(new ItemStack(Blocks.controller)), "tis3d.manual.blocks", "%LANGUAGE%/block/index.md");
        ManualAPI.addTab(new ItemStackTabIconRenderer(new ItemStack(Items.modules.get(Constants.NAME_ITEM_MODULE_EXECUTION))), "tis3d.manual.items", "%LANGUAGE%/item/index.md");
        ManualAPI.addTab(new TextureTabIconRenderer(new ResourceLocation(API.MOD_ID, "textures/gui/manual_serial_protocols.png")), "tis3d.manual.serialProtocols", "%LANGUAGE%/serial_protocols.md");
    }

    // --------------------------------------------------------------------- //

    @Override
    public Block registerBlock(final String name, final Supplier<Block> constructor, final Class<? extends TileEntity> tileEntity) {
        final Block block = super.registerBlock(name, constructor, tileEntity);
        final Item item = Item.getItemFromBlock(block);
        assert item != null;
        setCustomItemModelResourceLocation(item);
        return block;
    }

    @Override
    public Item registerItem(final String name, final Supplier<Item> constructor) {
        final Item item = super.registerItem(name, constructor);
        setCustomItemModelResourceLocation(item);
        return item;
    }

    // --------------------------------------------------------------------- //

    private static void setCustomItemModelResourceLocation(final Item item) {
        final ResourceLocation registryName = item.getRegistryName();
        assert registryName != null;
        final ModelResourceLocation location = new ModelResourceLocation(registryName, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, location);
    }
}
