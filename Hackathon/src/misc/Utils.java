package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;

import main.Main;

public class Utils 
{
	public static float dist(float x1, float x2)
	{
		float dist = x2 - x1;
		
		if(dist < 0)
			dist *= -1;
		
		return dist;
	}
	
	public static float distance(Vector3f pos1, Vector3f pos2)
	{
		float dist = 0.0f;
		
		float x = (pos2.x - pos1.x) * (pos2.x - pos1.x);
		float y = (pos2.y - pos1.y) * (pos2.y - pos1.y);
		dist = (float)(Math.sqrt(x + y));
		
		return dist;
	}
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) 
	{
        ByteBuffer newBuffer = MemoryUtil.memAlloc(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        MemoryUtil.memFree(buffer);
        buffer = null;
        return newBuffer;
    }
	
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize)
	{
        ByteBuffer buffer = null;
        
        try
        {
        	Path path = Paths.get(resource);
            if (Files.isReadable(path)) 
            {
                try (SeekableByteChannel fc = Files.newByteChannel(path)) 
                {
                    buffer = MemoryUtil.memAlloc((int)fc.size() + 1);
                    while (fc.read(buffer) != -1) 
                    {
                        ;
                    }
                }
            } 
            else 
            {
                try 
                {
                    InputStream source = Utils.class.getClass().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source);
                    buffer = MemoryUtil.memAlloc(bufferSize);

                    while (true) 
                    {
                        int bytes = rbc.read(buffer);
                        if (bytes == -1) 
                        {
                            break;
                        }
                        if (buffer.remaining() == 0) 
                        {
                            buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                        }
                    }
                }
                catch(IOException err)
                {
                	System.err.println(err);
                }
            buffer.flip();
            }
        }catch(IOException err)
        {
        	System.err.println(err);
        }
        
        return buffer;
    }
	
	public static int getNanoVGImage(String fileName, int bufferSize)
	{
		ByteBuffer buffer = Utils.ioResourceToByteBuffer(fileName, bufferSize);
		int image = NanoVG.nvgCreateImageMem(Main.vg, NanoVG.NVG_IMAGE_GENERATE_MIPMAPS, buffer);
		MemoryUtil.memFree(buffer);
		return image;
	}
	
	public static List<String> readAllLines(String fileName) 
	{
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e)
		{
			System.err.println(e);
		}
        return list;
    }
	
	public static String loadFileAsText(String path)
	{
		String text = "";
		InputStream in = null;
		in = Utils.class.getResourceAsStream(path);
		InputStreamReader inr = null;
		try
		{
			inr = new InputStreamReader(in, "UTF-8");
		} catch (UnsupportedEncodingException e1)
		{
			System.err.println(e1);
		}
		BufferedReader br = new BufferedReader(inr);
		String line;
		
		try
		{
			while((line = br.readLine()) != null)
			{
				text += line;
				text += "\r\n";
			}
		} catch (IOException e)
		{
			System.err.println(e);
		}
		
		try
		{
			br.close();
			in.close();
		} catch (IOException e)
		{
			System.err.println(e);
		}
		
		return text;
	}
}
