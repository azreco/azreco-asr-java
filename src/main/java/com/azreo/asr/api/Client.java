package com.azreo.asr.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class Client 
{
    public static void main( String[] args ) throws IOException
    {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addRequiredOption("a", "audio", true, "Audio file to be processed");
        options.addOption("o", "output", true, "Output filename (will print to terminal if not specified)");
        options.addRequiredOption("i", "id", true, "Your transcriber API ID");
        options.addRequiredOption("k", "token", true, "Your transcriber API Token");
        options.addRequiredOption("l", "lang", true, "Code of language to use (e.g., en-US, ru-RU, tr-TR)");
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        }
        catch(ParseException ex) {
            System.err.println("Parsing commandline arguments failed: " + ex.getMessage());
            return;
        }
        Transcriber transcriber = new Transcriber(cmd.getOptionValue("i"), 
                cmd.getOptionValue("k"), cmd.getOptionValue("l"));
        String result = transcriber.transcribe(cmd.getOptionValue("a"));
        if(result == null) {
            System.err.println("Transcibing process failed.");
            System.err.println("Press any key to exit...");
            System.in.read();
            return;
        }
        if(cmd.hasOption("o")) {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(new File(cmd.getOptionValue("o"))));
            writer.write(result);
            writer.close();
        } else {
            System.out.println(result);
        }
        System.err.println("Press any key to exit...");
        System.in.read();
    }
}
