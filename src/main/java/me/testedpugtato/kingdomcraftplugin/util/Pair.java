package me.testedpugtato.kingdomcraftplugin.util;

public class Pair {
    int x,y;

    public Pair(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public static Pair pair(int x, int y)
    {
        return new Pair(x,y);
    }
}
