package abused_master.FlattenMe;

import abused_master.FlattenMe.proxy.CommonProxy;
import abused_master.FlattenMe.worldgen.FlatWorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Info.MODID, version = Info.VERSION, name = Info.MODNAME)
public class FlattenMe {

    @SidedProxy(clientSide = "abused_master.FlattenMe.proxy.ClientProxy", serverSide = "abused_master.FlattenMe.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        this.proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        this.proxy.init(e);
        DimensionManager.unregisterDimension(0);
        DimensionManager.registerDimension(0, DimensionType.register("FlatWorld", "_flat", 0, FlatWorldProvider.class, true));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        this.proxy.postInit(e);
    }
}
