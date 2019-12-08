package com.company;
import java.net.*;
import java.io.*;

public class URLConnectionReader implements  Runnable  {

    String[] args1;
    String[] args2;

    public URLConnectionReader(String[] w1,String[] w2)
    {
        args1 = w1;
        args2 = w2;
    }
    // this function reads the response and compares them
    public void ReadURL() throws Exception {
        for(int i =1;i<=args1.length ;++i)
        {
            if(args1[i] == null || args2[i] == null) break;
            System.out.println(args1[i] + "," + args2[i]);
            URL url1 = new URL(args1[i]);
            URL url2  = new URL(args2[i]);
            HttpURLConnection yc1 = (HttpURLConnection)url1.openConnection();
            yc1.addRequestProperty("User-Agent", "Mozilla/4.0");
            yc1.setRequestMethod("GET");
            yc1.setRequestProperty("Content-Type", "application/json");
            HttpURLConnection yc2 = (HttpURLConnection)url2.openConnection();
            yc2.setRequestMethod("GET");
            yc2.setRequestProperty("Content-Type", "application/json");
            yc2.addRequestProperty("User-Agent", "Mozilla/4.0");
            BufferedReader in1 = null;
            BufferedReader in2 = null;
            try  {
                in1 = new BufferedReader(
                        new InputStreamReader(
                                yc1.getInputStream()));
                in2 = new BufferedReader(
                        new InputStreamReader(
                                yc2.getInputStream()));
                String inputLine1;
                String inputLine2;
                String result = "";
                while ((inputLine1 = in1.readLine()) != null && (inputLine2 = in2.readLine()) != null) {
                    inputLine1 = inputLine1.trim();
                    inputLine2 = inputLine2.trim();
                    if(inputLine1.compareToIgnoreCase(inputLine2) != 0)
                    {
                        result = "not equals";
                        break;
                    }
                    System.out.println("1= " + inputLine1 + "," + "2=" + inputLine2);
                }
                in1.close();
                in2.close();

                if(result.length() == 0)
                    result = "equals";
                System.out.println(result);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally {
                in1.close();
                in2.close();

            }

        }
    }

    @Override
    public void run()
    {
        try {
            ReadURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}