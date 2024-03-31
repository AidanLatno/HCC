package me.testedpugtato.kingdomcraftplugin.util;


// This class's whole purpose is to encapsulate the MathUtils.levelInter() function so it is shorter and easier to write.
public class lvl {
    public static float i(float low, float high, int level)
    {
        return MathUtils.levelInter(low,high,level);
    }
    public static float i(double low, double high, int level)
    {
        return MathUtils.levelInter(low,high,level);
    }
}
