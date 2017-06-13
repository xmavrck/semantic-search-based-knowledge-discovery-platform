package com.sarah.web_crawler.dto;

public class Bindings
{
    private Subclass subclass;
    private S s;

    private P p;

    private O o;

    public S getS ()
    {
        return s;
    }

    public void setS (S s)
    {
        this.s = s;
    }

    public P getP ()
    {
        return p;
    }

    public void setP (P p)
    {
        this.p = p;
    }

    public O getO ()
    {
        return o;
    }

    public void setO (O o)
    {
        this.o = o;
    }

    
    public Subclass getSubclass ()
    {
        return subclass;
    }

    public void setSubclass (Subclass subclass)
    {
        this.subclass = subclass;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [subclass = "+subclass+"]";
    }
}