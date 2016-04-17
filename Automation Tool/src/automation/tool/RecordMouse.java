/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automation.tool;

/**
 *
 * @author Administrator
 */
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import java.io.*;
import java.util.*;
import java.text.*;

public class RecordMouse implements NativeMouseInputListener
{
    PrintWriter writer=null;
    String statements[]=new String[20000];
    int count=0;
    
    public void getScreen()
    {
        try 
        {
            GlobalScreen.registerNativeHook();
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
        GlobalScreen.getInstance().addNativeMouseListener(new RecordMouse());
        GlobalScreen.getInstance().addNativeMouseMotionListener(new RecordMouse());
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) 
    {
            System.out.println("Mouse Clicked: " + e.getClickCount());
        }
        
        @Override
        public void nativeMousePressed(NativeMouseEvent e)
        {
            System.out.println("Mouse Pressed: " + e.getButton()+" "+ e.getX() + ", " + e.getY());
            statements[count++]="m:"+e.getX()+","+e.getY();
            System.out.println(statements[count-1]+" "+count);
        }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) 
    {
        System.out.println("Mouse Released: " + e.getButton());
        if(e.getButton()==1)
            statements[count++]="l:";
        else if(e.getButton()==2)
        {
            statements[count++]="r:";
            GlobalScreen.unregisterNativeHook();
            try
            {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                writer = new PrintWriter("record_"+timeStamp+".auto");
                System.out.println(writer);
                for(int i=0;i<count;i++)
                {
                    writer.println(statements[i]);
                }
                writer.close();
            }
            catch(Exception ex)
            {
                    ex.printStackTrace();
            }
        }   
    }
        
    @Override
    public void nativeMouseMoved(NativeMouseEvent e) 
    {
        // TODO Auto-generated method stub	
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) 
    {
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }
}