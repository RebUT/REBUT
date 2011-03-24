package com;


import java.io.IOException;
import java.io.OutputStream;


public class SerialWriter implements Runnable 
{
    OutputStream out;
    Window w ; 
    
    public SerialWriter ( OutputStream out, Window w)
    {
        this.out = out;
    }
    
    public void run ()
    {
        try
        {                
            int c = 0;
            while ( ( c = System.in.read()) > -1 )
            {
                this.out.write(c);
            }  
            
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            System.exit(-1);
        }            
    }
}

