package eu.deustotech.clips.demo.semanticreasoning.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFParserRegistry;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.RDFWriterRegistry;
import org.openrdf.rio.rdfxml.RDFXMLParser;

import android.util.Log;
import es.deusto.deustotech.rio.clips.CLPFormat;
import eu.deustotech.clips.demo.semanticreasoning.MainActivity;

public class SemanticLoader {
	
	private static void checkIfOk(URL source) throws IOException {
		HttpURLConnection conn=(HttpURLConnection) source.openConnection();
		if( !String.valueOf(conn.getResponseCode()).startsWith("2") )
			throw new IOException( "Incorrect response code " + conn.getResponseCode() + " Message: " + conn.getResponseMessage() );
	}
	
	private static OutputStream readGeneric(InputStream in, RDFFormat inFormat, RDFFormat outFormat) throws RDFParseException, RDFHandlerException, IOException {
		Log.d( MainActivity.logLabel, " (" + inFormat.getName() + " to " + outFormat.getName() + ")" );
		
		// Other useful OutputStreams: System.out, ByteArrayOutputStream, FileOutputStream
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final RDFWriter writer = RDFWriterRegistry.getInstance().get(outFormat).getWriter(out);
		
		//final RDFParser parser = RDFParserRegistry.getInstance().get(sourceFormat).getParser();
		final RDFParser parser = new RDFXMLParser();
		parser.setRDFHandler(writer);
		
		// Other util InputStreams: System.in, ByteArrayInputStream or new FileInputStream(source);
		parser.parse( in, "unknown:namespace" );
		
		out.flush();
		in.close();
		
		return out;
	}
	
	public static String readURL(URL source) throws IOException, RDFParseException, RDFHandlerException {
		//String source = "files/wgs84_pos.owl";
		checkIfOk(source);
		
		final RDFFormat sourceFormat = RDFParserRegistry.getInstance().getFileFormatForFileName(source.toString(), RDFFormat.RDFXML); // RDFFormat.NTRIPLES
		final RDFFormat destinationFormat = CLPFormat.CLP;
		
		return SemanticLoader.readGeneric( source.openStream(), sourceFormat, destinationFormat).toString();
	}
	
	public static String readFile(File file) throws IOException, RDFParseException, RDFHandlerException {	
		final RDFFormat sourceFormat = RDFParserRegistry.getInstance().getFileFormatForFileName(file.getName(), RDFFormat.RDFXML);
		final RDFFormat destinationFormat = CLPFormat.CLP;
		
		return SemanticLoader.readGeneric( new FileInputStream(file), sourceFormat, destinationFormat).toString();
	}
}
