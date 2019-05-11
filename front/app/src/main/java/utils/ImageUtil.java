package utils;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil
{
    DataInputStream din;
    static DataOutputStream dout;
    static FileInputStream fis;

    public static byte[] getImage(String path)
    {
        byte[] data=null;
        try
        {
            BufferedInputStream in=new BufferedInputStream(new FileInputStream(path));
            ByteArrayOutputStream out=new ByteArrayOutputStream(1024);
            byte[] temp=new byte[1024];
            int size=0;
            while((size=in.read(temp))!=-1)
            {
                out.write(temp,0,size);
            }
            data=out.toByteArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }
    public static void savePic(byte[] data,String path) throws IOException
    {

        File file= new File(path);
        FileOutputStream fos = new FileOutputStream(file);

        fos.write(data);
        fos.flush();
        fos.close();
    }
}

