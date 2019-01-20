package wuzhuobin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.opencv.core.Core;

import wuzhuobin.camera.CameraSingleton;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	static public void main(String[] args) throws InterruptedException {

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMyyyydd_hhmmss");
			String fileName = "record" + simpleDateFormat.format(new Date()) + ".avi";
			CameraSingleton cameraSingleton = CameraSingleton.getInstance();
			if(cameraSingleton.isRecording()) {
				cameraSingleton.stop();
			}
			cameraSingleton.start(fileName);
			}
		}, 0, 5000);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("waiting input.");
		while (scanner.hasNext()) {
			String input = scanner.next();
			if (input.charAt(0) == 'q' && input.length() == 1) {
				CameraSingleton cameraSingleton = CameraSingleton.getInstance();
				timer.cancel();
				timer.purge();
				if (cameraSingleton.isRecording()) {
					cameraSingleton.stop();
				}
				scanner.close();
				break;
			}
			System.out.println("input 'q' to exit. ");
			System.out.println("waiting input.");
		}

	}
};