package li.cil.tis3d.common.integration.charsetwires;

import li.cil.tis3d.common.integration.ModProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public final class ProxyCharsetWires implements ModProxy {
    public static final String MOD_ID = "CharsetWires";

    @Override
    public boolean isAvailable() {
        return Loader.isModLoaded(MOD_ID);
    }

    @Override
    public void init(final FMLInitializationEvent event) {
        CharsetWiresCallbacks.register();
    }
}
