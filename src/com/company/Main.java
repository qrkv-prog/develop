package com.company;
import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.Files;
import java.util.stream.Stream;


// the URL file is divided into multiples of 10 files , and then passed to the URLConnectionReader
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        // java Main file1 file 2
        //the above need to invoked
        ReaderThread r = new ReaderThread(args[0],args[1]);
        r.FileReaderThread();
    }
}
