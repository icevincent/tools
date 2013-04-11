package org.universAAL.ucc.webconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.universAAL.ucc.api.IInstaller;
import org.universAAL.ucc.frontend.api.IFrontend;
import org.universAAL.ucc.frontend.api.impl.FrontendImpl;

/**
 * Connects and registers the ucc to the uStore.
 * 
 * @author merkle
 * 
 */

public class WebConnector {
	public static final int SINGLE_INSTANCE_NETWORK_SOCKET = 9988;
	public static final String URL_SEARCH_TAG = "url";
	private static WebConnector instance;
	private ServerSocket socket;
	private IFrontend front;

	private WebConnector() {
		front = new FrontendImpl();
		try {
			socket = new ServerSocket(SINGLE_INSTANCE_NETWORK_SOCKET, 10,
					InetAddress.getByAddress(new byte[] { 127, 0, 0, 1 }));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WebConnector getInstance() {
		if (instance == null) {
			instance = new WebConnector();
		}
		return instance;
	}

	public void startListening() {
		socketListenerThread.start();
	}

	public void stopListening() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// this is obsolete - do not suport from MW2.0
	public void onEventCatched(String post) {
		String url = parseURL(post);
		System.err.println("IM WEBCONNECTOR " + url);

		if (url != null) {
			front.installService("", url);
			// PackageDownloader downloader=new PackageDownloader();
			// String fileOnHardDrive=downloader.download(url);
			// if(new File(fileOnHardDrive).exists()){
			// String appDir;
			// try {
			// // appDir = installer.installApplication(fileOnHardDrive);
			// } catch (Exception e) {
			// e.printStackTrace();
			// return;
			// }
			//
			// Activator.getMainWindow().installApp(appDir); */
			//
			// }
			//
			// }

		}
	}

	Thread socketListenerThread = new Thread(new Runnable() {
		public void run() {
			boolean socketClosed = false;

			while (!socketClosed) {
				if (socket.isClosed()) {
					socketClosed = true;
				} else {
					try {
						Socket client = socket.accept();
						BufferedReader in = new BufferedReader(
								new InputStreamReader(client.getInputStream(),
										Charset.forName("UTF-8")));
						DataOutputStream out = new DataOutputStream(
								client.getOutputStream());

						String message = in.readLine();
						char[] cbuf = new char[4096];

						in.read(cbuf);
						message = String.valueOf(cbuf);
						out.write(("HTTP/1.1 204 No Content").getBytes());
						// out.writeUTF("200");
						onEventCatched(message);

						in.close();
						client.close();
					} catch (IOException e) {
						socketClosed = true;
						e.printStackTrace();
					}
				}
			}
		}
	});

	private String parseURL(String post) {
		String url = "";
		String[] lines = post.split("\n");
		String[] content = lines[lines.length - 1].split("&");
		for (int i = 0; i < content.length; i++) {
			if (content[i].startsWith(URL_SEARCH_TAG + "=")) {
				url = content[i].substring(URL_SEARCH_TAG.length() + 1);
				try {
					url = URLDecoder.decode(url, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return url;
			}
		}
		return null;
	}

}