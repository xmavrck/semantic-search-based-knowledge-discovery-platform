package com.sarah.web_crawler.dto;

public class Results
{
    private Bindings[] bindings;

    public Bindings[] getBindings ()
    {
        return bindings;
    }

    public void setBindings (Bindings[] bindings)
    {
        this.bindings = bindings;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bindings = "+bindings+"]";
    }
}
	
