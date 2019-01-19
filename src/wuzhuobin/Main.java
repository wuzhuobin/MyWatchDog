package wuzhuobin;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.opencv.core.Core;

import wuzhuobin.camera.CameraSingleton;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	static public void main(String[] args) throws InterruptedException {
	
		CameraSingleton camera = CameraSingleton.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMddyyyyHHmmss");
		camera.start("record_" + dateFormat.format(new Date()) + ".avi");
		Scanner scanner = new Scanner(System.in);
		System.out.println("waiting input.");
		while(scanner.hasNext()) {
			System.out.println("waiting input.");
			String input = scanner.next();
			if(input.charAt(0) == 'q' && input.length() == 1) {
				scanner.close();
				camera.stop();
				break;
			}
			System.out.println("input 'q' to exit. ");
		}
		
//		VideoCapture videoCapture = new VideoCapture(0);
//		Mat oneFrame = new Mat();
//		videoCapture.read(oneFrame);	
//		VideoWriter videoWriter = new VideoWriter("output.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'), 5,
//				oneFrame.size());
//
//		final int NUM = 10;
//		class Counter{
//			public int data = 0;
//		};
//		final Counter counter = new Counter();
//		ScheduledExecutorService scheduledExecutorService = 
//			Executors.newSingleThreadScheduledExecutor();
//		Runnable capture = new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				videoWriter.write(oneFrame);
//				videoCapture.read(oneFrame);
//				System.err.println("counter:" + counter.data);
//				if(++counter.data > NUM) {
//					scheduledExecutorService.shutdown();
//					try {
//						scheduledExecutorService.awaitTermination(100, TimeUnit.MILLISECONDS);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		};
//		
//		scheduledExecutorService.scheduleAtFixedRate(capture, 0, 50, TimeUnit.MILLISECONDS);
//		System.err.println("hello");
//		for(;!(counter.data > NUM);) {}
//		videoWriter.release();
//		videoCapture.release();
	}
};