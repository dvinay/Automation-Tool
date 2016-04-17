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
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.io.*;
import java.util.*;
import java.text.*;

public class RecordData implements NativeKeyListener
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
        GlobalScreen.getInstance().addNativeKeyListener(new RecordData());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) 
    {
        System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
            statements[count++]="t:"+NativeKeyEvent.getKeyText(e.getKeyCode());
        
        if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) 
        {
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
    public void nativeKeyReleased(NativeKeyEvent e) 
    {
	// TODO Auto-generated method stub	
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) 
    {
	// TODO Auto-generated method stub	
    }
}