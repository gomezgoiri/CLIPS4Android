package eu.deustotech.clips.demo.semanticreasoning.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFParserRegistry;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.RDFWriterRegistry;

import android.util.Log;

import es.deusto.deustotech.rio.clips.CLPFormat;
import eu.deustotech.clips.demo.semanticreasoning.MainActivity;

public class SemanticWriter {
	
	public static void write(ByteArrayInputStream clpSerialization, OutputStream out, RDFFormat destinationFormat) throws IOException, RDFParseException, RDFHandlerException {
		final RDFFormat sourceFormat = CLPFormat.CLP;
		
		// Other useful OutputStreams: System.out, ByteArrayOutputStream, FileOutputStream
		final RDFWriter writer = RDFWriterRegistry.getInstance().get(destinationFormat).getWriter(out);
		final RDFParser parser = RDFParserRegistry.getInstance().get(sourceFormat).getParser();
		parser.setRDFHandler(writer);
		
		// Other util InputStreams: System.in, ByteArrayInputStream or new FileInputStream(source);
		//final InputStream in = new ByteArrayInputStream(clpSerialization.getBytes());
		parser.parse( clpSerialization, "unknown:namespace" );
		
		out.flush();
		clpSerialization.close();
	}
	
	public static void writeFile(ByteArrayInputStream clpSerialization, String filepath, RDFFormat destinationFormat) throws IOException, RDFParseException, RDFHandlerException {
		final File file = new File(filepath);
		if (!file.exists()) {
			Log.d(MainActivity.logLabel, "Creating output file");
			file.createNewFile();
		} 
		final OutputStream os = new FileOutputStream( file, false );
		SemanticWriter.write(clpSerialization, os, destinationFormat);
	}
	
	public static void writeAnything(String clpSerialization, OutputStream out) throws IOException {
		final OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(clpSerialization);
		out.flush();
		writer.close();
	}
}
