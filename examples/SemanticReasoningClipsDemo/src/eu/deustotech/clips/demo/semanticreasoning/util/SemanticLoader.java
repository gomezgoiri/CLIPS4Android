package eu.deustotech.clips.demo.semanticreasoning.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import es.deusto.deustotech.rio.clips.CLPFormat;

public class SemanticLoader {
	
	private static void checkIfOk(URL source) throws IOException {
		HttpURLConnection conn=(HttpURLConnection) source.openConnection();
		if( !String.valueOf(conn.getResponseCode()).startsWith("2") )
			throw new IOException( "Incorrect response code " + conn.getResponseCode() + " Message: " + conn.getResponseMessage() );
	}
	
	public static String readURL(URL source) throws IOException, RDFParseException, RDFHandlerException {
		//String source = "files/wgs84_pos.owl";
		
		checkIfOk(source);
		
		final RDFFormat sourceFormat = RDFParserRegistry.getInstance().getFileFormatForFileName(source.toString(), RDFFormat.RDFXML);
		//RDFParserRegistry.getInstance().getFileFormatForFileName(destination, RDFFormat.NTRIPLES);
		final RDFFormat destinationFormat = CLPFormat.CLP;
		//Lod.d(" (" + sourceFormat.getName() + " to " + destinationFormat.getName() + ")");
		
		// Other useful OutputStreams: System.out, ByteArrayOutputStream, FileOutputStream
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final RDFWriter writer = RDFWriterRegistry.getInstance().get(destinationFormat).getWriter(out);
		
		//final RDFParser parser = RDFParserRegistry.getInstance().get(sourceFormat).getParser();
		final RDFParser parser = new RDFXMLParser();
		parser.setRDFHandler(writer);
		
		// Other util InputStreams: System.in, ByteArrayInputStream or new FileInputStream(source);
		final InputStream in =  source.openStream();
		parser.parse( in, "unknown:namespace" );
		
		out.flush();
		in.close();
		
		return out.toString();
	}
	
	public static String readFile(URL source) throws IOException, RDFParseException, RDFHandlerException {	
		final RDFFormat sourceFormat = RDFParserRegistry.getInstance().getFileFormatForFileName(source.toString(), RDFFormat.RDFXML);
		//RDFParserRegistry.getInstance().getFileFormatForFileName(destination, RDFFormat.NTRIPLES);
		final RDFFormat destinationFormat = CLPFormat.CLP;
		//Lod.d(" (" + sourceFormat.getName() + " to " + destinationFormat.getName() + ")");
		
		// Other useful OutputStreams: System.out, ByteArrayOutputStream, FileOutputStream
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final RDFWriter writer = RDFWriterRegistry.getInstance().get(destinationFormat).getWriter(out);
		
		final RDFParser parser = RDFParserRegistry.getInstance().get(sourceFormat).getParser();
		//final RDFParser parser = new RDFXMLParser();
		parser.setRDFHandler(writer);
		
		// Other util InputStreams: System.in, ByteArrayInputStream or new FileInputStream(source);
		final InputStream in =  source.openStream();
		parser.parse( in, "unknown:namespace" );
		
		out.flush();
		in.close();
		
		return out.toString();
	}
}
