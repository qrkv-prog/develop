package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLConnection;

class URLConnectionReader  {

     String[] args1;
     String[] args2;

      public URLConnectionReader(String[] w1,String[] w2)
      {
          args1 = w1;
          args2 = w2;
      }
 // this function reads the response and compares them
    public void ReadURL() throws Exception {
        String response1 = new String();
        String response2  = new String();
        for(int i =0;i<args1.length;++i)
        {
            String result  = "";
        URL url1 = new URL(args1[i]);
        URL url2  = new URL(args2[i]);
        URLConnection yc1 = url1.openConnection();
            URLConnection yc2 = url2.openConnection();
            try (BufferedReader in1 = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()))) {
                 BufferedReader in2 = new BufferedReader(
                         new InputStreamReader(
                                 yc2.getInputStream()));
                 String inputLine1 = in1.readLine();
                 String inputLine2 = in2.readLine();
                while ( inputLine1 != null && inputLine2 != null) {
                    response1 =  inputLine1.readLine();
                    response2 = inputLine2.readLine();
                    if(response1.Equals(response2) == false)
                    {
                        result = "not equals";
                        break;
                         // write to file as "not equals"
                    }
                }

                if(result.length() == 0)
                    result = "equals";
                }
                in.close();
            }
        }
}
// the URL file is divided into multiples of 10 files , and then passed to the URLConnectionReader
class ReaderThread implements  Runnable
{
    String file1;
    String file2;
    public ReaderThread(String file1,String file2)
    {
        this.file1 = file1;
        this.file2 = file2;
    }
    public  long getLineCount(File file) throws IOException {

        try (Stream<String> lines = Files.lines(file.toPath())) {
            return lines.count();
        }
    }
    public void FileReaderThread(String file1,String file2)
    {
        BufferedReader reader1;
        BufferedReader reader2;
        // assumption both the files have same number of lines
        int count = getLineCount(new File(file1));
        String[] worker1 = new String[100];
        String[] worker2 = new String[100];
        String result  = "";
        try {

            // reading the 1st json file
            reader1 = new BufferedReader(new FileReader(file1));
            reader2 = new BufferedReader(new FileReader(file2));
            int threadCounter =1;
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            while (line1 != null && line2 != null) {
                // read next line
                line1 = reader1.readLine();
                // reading the 2nd json file
                line2 = reader2.readLine();
                worker1[threadCounter] = new String(line1);
                worker2[threadCounter] = new String(line2);
                ++threadCounter;
                --count;
                if(threadCounter % 100 == 0 || count == 0)
                {
                   new URLConnectionReader(worker1,worker2).ReadURL();
                }
            }
            reader1.close();
            reader2.close();
            } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
            FileReaderThread(file1,file2);
    }
}
public class Main {

    public static void main(String[] args) {
        // java Main file1 file 2
        //the above need to invoked
        new ReaderThread(args[1],args[2]);
    }
}
