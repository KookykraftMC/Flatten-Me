package abused_master.FlattenMe.worldgen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FlatWorldProvider extends WorldProvider {


    public static World worldObject;

    public FlatWorldProvider(){
        this.worldObject = this.worldObj;
    }

    @Override
    public BlockPos getSpawnPoint(){
        net.minecraft.world.storage.WorldInfo info = worldObj.getWorldInfo();
        BlockPos spawn = this.worldObj.getWorldType() == WorldType.FLAT ? new BlockPos(0, 51, 0) : true ? new BlockPos(0,51,0) : new BlockPos(info.getSpawnX(), info.getSpawnY(), info.getSpawnZ());

        return spawn;

    }

    @Override
    protected void createBiomeProvider(){
        super.createBiomeProvider();
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        String generatorOptions = "";
        return new ChunkProviderFlat(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
    }

    @Override
    public boolean getHasNoSky(){

        return this.worldObj.getWorldType() == WorldType.FLAT ? true : this.hasNoSky;
    }

    @Override
    public DimensionType getDimensionType() {
        return DimensionType.OVERWORLD;
    }

    /**
     * Called to determine if the chunk at the given chunk coordinates within the provider's world can be dropped. Used
     * in WorldProviderSurface to prevent spawn chunks from being unloaded.
     */
    @Override
    public boolean canDropChunk(int x, int z){
        return !this.worldObj.isSpawnChunk(x, z) || !this.worldObj.provider.getDimensionType().shouldLoadSpawn();
    }

    /**
     * Creates the light to brightness table
     */
    @Override
    protected void generateLightBrightnessTable(){
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F;
        }
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    @Override
    public boolean isSurfaceWorld(){
        return true;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    @Override
    public boolean canCoordinateBeSpawn(int x, int z){
        return true;
    }

    @Override
    public boolean isDaytime()
    {
        return worldObj.getSkylightSubtracted() < 4;
    }

    @Override
    public float getSunBrightnessFactor(float par1)
    {
        return worldObj.getSunBrightnessFactor(par1);
    }

    @Override
    public int getMoonPhase(long worldTime)
    {
        return (int)(worldTime / 24000L % 8L + 8L) % 8;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return true;
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks){
        int i = (int)(worldTime % 24000L);
        float f = ((float)i + partialTicks) / 24000.0F - 0.25F;

        if (f < 0.0F)
        {
            ++f;
        }

        if (f > 1.0F)
        {
            --f;
        }

        float f1 = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) / 2.0D);
        f = f + (f1 - f) / 3.0F;

        return this.worldObj.getWorldType() == WorldType.FLAT ? 0.50F : true ? 0.50F : f;
    }

    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    @Override
    public boolean canRespawnHere(){
        return true;
    }

    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    @SideOnly(Side.CLIENT)
    public boolean doesXZShowFog(int x, int z){
        return false;
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    public String getDimensionName(){
        return "Flatten Me World";
    }
    @Override
    public int getHeight(){
        return this.worldObj.getWorldType() == WorldType.FLAT ? 128 : 256;
    }

    @Override
    public int getActualHeight(){
        return hasNoSky ? 128 : true ? 128 : 256;
    }


}
