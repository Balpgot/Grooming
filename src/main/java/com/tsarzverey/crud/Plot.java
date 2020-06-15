package com.tsarzverey.crud;

import java.util.List;

public class Plot{
    private String[] x;
    private long[] y;
    private final String type = "scatter";
    private String name;

    public Plot(String[] x, long[] y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Plot(List<String> x, List<Long> y, String name) {
        this.x = x.toArray(String[]::new);
        this.y = new long[y.size()];
        for(int i = 0; i<y.size(); i++){
            this.y[i] = y.get(i);
        }
        this.name = name;
    }

    public String[] getX() {
        return x;
    }
    
    public long[] getY() { return y;}

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}