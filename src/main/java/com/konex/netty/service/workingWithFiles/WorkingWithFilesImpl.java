package com.konex.netty.service.workingWithFiles;

import jcifs.smb.*;
import org.springframework.stereotype.Service;
import java.io.*;


/**
 * Created by vitaliy on 18.07.2017.
 */
@Service
public class WorkingWithFilesImpl implements WorkingWithFiles{

    @Override
    public String getFileContent(String fName) throws IOException {
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("","Сервис", "Сервис");
        //SmbFile smbFile=new SmbFile("smb://192.200.100.210/input-output$/testJava/263_87.prs", auth);
        SmbFile smbFile=new SmbFile("smb://192.200.100.100/k1$/263_87.prs");

        String result="";
        if(smbFile.isDirectory()) {
            for(SmbFile f : smbFile.listFiles()) {
                if (f.isFile()) {
                    System.out.println(f.createTime());
                }
                result += f.getName() + "\r\n" + "<br>";
            }
        }
        else{
            System.out.println(smbFile.getName());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(smbFile), "Windows-1251"));
            try {
                String s="";
                String value="";
                while ((s=reader.readLine()) != null) {
                    System.out.println(s);
                    result+=s+"\r\n"+"<br>";;
                }
                reader.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                reader.close();
            }
        }
        //writeFile();
        return result;
    }

    @Override
    public String writeFile(String text) throws IOException {
        SmbFile smbFile=new SmbFile("smb://192.200.100.100/k1$/123.prs");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new SmbFileOutputStream(smbFile),  "windows-1252"));
        try{
            //for(int i=0;i<4;i++) {
                writer.write(text);
                writer.newLine();
            //}
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            writer.close();
        };
        return null;
    }
}
