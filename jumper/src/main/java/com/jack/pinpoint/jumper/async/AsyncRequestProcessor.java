package com.jack.pinpoint.jumper.async;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.AsyncContext;

public class AsyncRequestProcessor implements Runnable {

	private AsyncContext asyncContext;
	private int secs;

	public AsyncRequestProcessor() {
	}

	public AsyncRequestProcessor(AsyncContext asyncCtx, int secs) {
		this.asyncContext = asyncCtx;
		this.secs = secs;
	}

	public void run() {
		System.out.println("Async Supported? "
				+ asyncContext.getRequest().isAsyncSupported());
		longProcessing(secs);

		new Exception("fengjiaqi: --------------------  in async process").printStackTrace();

		try {
			URL url = new URL("http://localhost:8099/echo/hello");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.connect();

			OutputStream outs = conn.getOutputStream();
			InputStream ins = conn.getInputStream();
		} catch (Exception e) {}

		try {
			PrintWriter out = asyncContext.getResponse().getWriter();
			out.write("Processing done for " + secs + " milliseconds!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//complete the processing
		asyncContext.complete();
	}

	private void longProcessing(int secs) {
		// wait for given time before finishing
		try {
			Thread.sleep(secs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
