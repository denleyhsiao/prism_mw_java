package Prism.extensions.port.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import Prism.core.Event;

/**
 * This class provide a utility for compression of outgoing event and
 * decompression of incoming event.
 * 
 * @version 2.0
 * @author USC Soft. Arch. Group. Contact: Sam Malek <A
 *         HREF="mailto:malek@usc.edu"> malek@usc.edu </A>
 */
public class Compression extends AbstractCompression {

	/**
	 * An empty constructor.
	 */
	public Compression() {
	}

	/**
	 * This method is called from the ExtensiblePort. In this implementation,
	 * this method doesn't do anything.
	 */
	public void start() {
	}

	/**
	 * This method is called to compress or decomperss an event depending on the
	 * direction of the event.
	 * 
	 * @param eventObj
	 *            event to be processed
	 * @param direction
	 *            "IN" for decompression, "OUT" for compression
	 */
	public Object processEvent(Object eventObj, String direction) {
		if (direction.equals("IN")) {
			return this.decompress(eventObj);
		} else {
			return this.compress(eventObj);
		}
	}

	private byte[] compress(Object obj) {
		ByteArrayOutputStream tempbyte = null;
		try {

			tempbyte = new ByteArrayOutputStream();
			GZIPOutputStream tempgzip = new GZIPOutputStream(tempbyte);
			ObjectOutputStream tempout = new ObjectOutputStream(tempgzip);
			tempout.writeObject(obj);
			tempgzip.finish();

			tempgzip.close();
			tempout.close();
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}

		return tempbyte.toByteArray();

	}

	private Event decompress(Object obj) {
		Event e = null;
		try {
			ByteArrayInputStream tempbyte = new ByteArrayInputStream(
					(byte[]) obj);
			GZIPInputStream tempgzip = new GZIPInputStream(tempbyte);
			ObjectInputStream tempir = new ObjectInputStream(tempgzip);
			e = (Event) tempir.readObject();

			tempbyte.close();
			tempgzip.close();
			tempir.close();
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}

		return e;
	}

}
