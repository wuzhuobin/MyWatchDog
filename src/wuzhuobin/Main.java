package wuzhuobin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.opencv.core.Core;

import wuzhuobin.camera.CameraRecorder;
import wuzhuobin.camera.CameraSingleton;
import wuzhuobin.camera.CameraSingleton2;
import wuzhuobin.camera.CameraWindow;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws InterruptedException {

		Option optionFps = new Option("f", "framePerSecond", true, "Seting the fps of the camera.");
		Option optionLengthOfFile = new Option("l", "lengthOfFile", true, "Seting the length of the video file in seconds.");
		Option optionOutput = new Option("o", "output", true, "Seting the output directory.");
		Option optionCli = new Option("c", "cli", false, "Using the command line mode without a window.");
		optionOutput.setRequired(true);
		
		Options options = new Options();
		options.addOption(optionFps);
		options.addOption(optionLengthOfFile);
		options.addOption(optionOutput);
		options.addOption(optionCli);
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine commandLine = parser.parse(options, args);
			int fps = Integer.parseInt(commandLine.getOptionValue(optionFps.getOpt(), "10"));
			int lengthOfFile = Integer.parseInt(commandLine.getOptionValue(optionLengthOfFile.getOpt(), "5000"));
			String output = commandLine.getOptionValue(optionOutput.getOpt(), ".");
			if(!new java.io.File(output).exists()) {
				throw new ParseException("The directory " + output + " does not exist.");
			}
			boolean cliFlag = commandLine.hasOption(optionCli.getOpt());
			facade(fps, lengthOfFile, output, cliFlag);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("OpenCvJavaSample", options);
		}
		
	}
	
	private static void facade(int fps, int lengthOfFile, String output, boolean cliFlag) {
		CameraSingleton2 camera = CameraSingleton2.getInstance();
		camera.open();
		CameraRecorder recorder = new CameraRecorder();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMyyyydd_HHmmss");
				String fileName = "record" + simpleDateFormat.format(new Date()) + ".avi";
				System.err.println(fileName);
				if (recorder.isOpened()) {
					recorder.release();
				}
				recorder.open(output + "/" + fileName, fps);
				
			}
		}, 10, lengthOfFile * 1000);
		if(cliFlag) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Input 'q' to exit.");
			while (scanner.hasNext()) {
				String input = scanner.next();
				if (input.charAt(0) == 'q' && input.length() == 1) {
					timer.cancel();
					timer.purge();
					recorder.release();
					camera.release();
					scanner.close();
					System.out.println("Exit!");
					break;
				}
				System.out.println("Input 'q' to exit.");
			}
		}
		else {
			CameraWindow window = new CameraWindow(CameraSingleton2.getInstance(), fps);
			window.addWindowClosingListener(()->{
				timer.cancel();
				timer.purge();
				recorder.release();
				camera.release();
				System.out.println("Exit!");
			});
		}
	}

	@SuppressWarnings("unused")
	private static void facade1(int fps, int lengthOfFile, String output) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMyyyydd_hhmmss");
			String fileName = "record" + simpleDateFormat.format(new Date()) + ".avi";
			CameraSingleton cameraSingleton = CameraSingleton.getInstance();
			cameraSingleton.setFps(fps);
			if(cameraSingleton.isRecording()) {
				cameraSingleton.stop();
			}
			cameraSingleton.start(output + "/" + fileName);
			}
		}, 0, lengthOfFile * 1000);
		
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