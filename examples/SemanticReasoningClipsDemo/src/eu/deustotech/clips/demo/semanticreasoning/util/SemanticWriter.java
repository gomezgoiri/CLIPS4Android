package eu.deustotech.clips.demo.semanticreasoning.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFParserRegistry;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.RDFWriterRegistry;

import es.deusto.deustotech.rio.clips.CLPFormat;

public class SemanticWriter {
	
	public static void write(String clpSerialization, OutputStream out, RDFFormat destinationFormat) throws IOException, RDFParseException, RDFHandlerException {
		final RDFFormat sourceFormat = CLPFormat.CLP;
		
		// Other useful OutputStreams: System.out, ByteArrayOutputStream, FileOutputStream
		final RDFWriter writer = RDFWriterRegistry.getInstance().get(destinationFormat).getWriter(out);
		final RDFParser parser = RDFParserRegistry.getInstance().get(sourceFormat).getParser();
		parser.setRDFHandler(writer);
		
		// Other util InputStreams: System.in, ByteArrayInputStream or new FileInputStream(source);
		final InputStream in = new ByteArrayInputStream(clpSerialization.getBytes());
		parser.parse( in, "unknown:namespace" );
		
		out.flush();
		in.close();
	}
	
	public static void writeAnything(String clpSerialization, OutputStream out) throws IOException {
		final OutputStreamWriter writer = new OutputStreamWriter(out);
		writer.write(clpSerialization);
		out.flush();
		writer.close();
	}
}
