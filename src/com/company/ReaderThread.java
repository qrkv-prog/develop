package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReaderThread
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
    public void FileReaderThread() throws IOException, InterruptedException {
        BufferedReader reader1;
        BufferedReader reader2;
        // assumption both the files have same number of lines
        int count = (int) getLineCount(new File(file1));
        String[] worker1 = new String[101];
        String[] worker2 = new String[101];
        List<Thread> pool = new ArrayList<Thread>();
        String result  = "";
        try {
            int j  =0;
            // reading the 1st json file
            reader1 = new BufferedReader(new FileReader(file1));
            reader2 = new BufferedReader(new FileReader(file2));
            int threadCounter =1;
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            --count;
            while (line1 != null && line2 != null) {

                worker1[threadCounter] = new String(line1);
                worker2[threadCounter] = new String(line2);
                line1 = reader1.readLine();
                // reading the 2nd json file
                line2 = reader2.readLine();
                if(threadCounter % 100 == 0 || count == 0)
                {
                    URLConnectionReader url = new URLConnectionReader(worker1,worker2);
                    Thread t  = new Thread(url);
                    pool.add(t);
                    t.start();
                    ++j;
                }
                --count;
                ++threadCounter;

            }
            reader1.close();
            reader2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for(int i =0;i<pool.size();++i)
        {
            pool.get(i).join();

        }

    }

}
