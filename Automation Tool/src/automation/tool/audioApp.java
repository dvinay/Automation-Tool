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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class audioApp extends JFrame
{

  boolean stopaudioCapture = false;
  ByteArrayOutputStream byteOutputStream;
  AudioFormat adFormat;
  TargetDataLine targetDataLine;
  AudioInputStream InputStream;
  SourceDataLine sourceLine;

  /*public static void main(String args[])
  {
    new audioApp();
  }*/

  public audioApp()
  {
    final JButton capture = new JButton("Capture");
    final JButton stop = new JButton("Stop");
    final JButton play = new JButton("Playback");

    capture.setEnabled(true);
    stop.setEnabled(false);
    play.setEnabled(false);

    capture.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          capture.setEnabled(false);
          stop.setEnabled(true);
          play.setEnabled(false);
          captureAudio();
        }
      }
    );
    getContentPane().add(capture);

    stop.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          capture.setEnabled(true);
          stop.setEnabled(false);
          play.setEnabled(true);
          stopaudioCapture = true;
        }
      }
    );
    getContentPane().add(stop);

    play.addActionListener(
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
                playAudio();
        }
      }
    );
    getContentPane().add(play);

    getContentPane().setLayout(new FlowLayout());
    setTitle("Capture/Playback Demo");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(250,70);
    setVisible(true);
  }

  private void captureAudio()
  {
    try
    {
      adFormat = getAudioFormat();
      DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,adFormat);
      targetDataLine = (TargetDataLine)
      AudioSystem.getLine(dataLineInfo);
      targetDataLine.open(adFormat);
      targetDataLine.start();

      Thread captureThread =new Thread(new CaptureThread());
      captureThread.start();
    } 
    catch (Exception e) 
    {
      System.out.println(e);
      System.exit(0);
    }
  }

  private void playAudio() 
  {
    try
    {
      byte audioData[] = byteOutputStream.toByteArray();
      InputStream byteInputStream = new ByteArrayInputStream(audioData);
      AudioFormat adFormat = getAudioFormat();
      InputStream = new AudioInputStream(byteInputStream,adFormat,audioData.length/adFormat.getFrameSize());
      DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,adFormat);
      sourceLine = (SourceDataLine) 
      AudioSystem.getLine(dataLineInfo);
      sourceLine.open(adFormat);
      sourceLine.start();

      Thread playThread = new Thread(new PlayThread());
      playThread.start();
    } 
    catch (Exception e) 
    {
      System.out.println(e);
      System.exit(0);
    }
  }

  private AudioFormat getAudioFormat(){
    float sampleRate = 8000.0F;
    int sampleInbits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = false;
    return new AudioFormat(
                      sampleRate,
                      sampleInbits,
                      channels,
                      signed,
                      bigEndian);
  }

class CaptureThread extends Thread{
  byte tempBuffer[] = new byte[10000];
  public void run(){
    byteOutputStream = new ByteArrayOutputStream();
    stopaudioCapture = false;
    try
    {
              while(!stopaudioCapture){
                int cnt = targetDataLine.read(
                    tempBuffer,
                    0,
                    tempBuffer.length);
        if(cnt > 0){
                  byteOutputStream.write(
                   tempBuffer, 0, cnt);
        }
      }
      byteOutputStream.close();
    }catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }
}

class PlayThread extends Thread{
  byte tempBuffer[] = new byte[10000];

  public void run(){
    try{
      int cnt;
      while((cnt = InputStream.
        read(tempBuffer, 0,
            tempBuffer.length)) != -1){
        if(cnt > 0){
          sourceLine.write(
                   tempBuffer, 0, cnt);
        }
      }
     sourceLine.drain();
      sourceLine.close();
    }catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }
}
}

