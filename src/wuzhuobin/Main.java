package wuzhuobin;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import wuzhuobin.camera.CameraSingleton;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	static public void main(String[] args) {
		VideoCapture videoCapture = CameraSingleton.getInstance();
		if(videoCapture == null) {
			return;
		}
		Mat oneFrame = new Mat();
		videoCapture.read(oneFrame);
		VideoWriter videoWriter = new VideoWriter("output.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'), 2,
				oneFrame.size());
		int i = 0;
		for (;;) {
			if (oneFrame.empty()) {
				break;
			}
			videoWriter.write(oneFrame);
			videoCapture.read(oneFrame);
			if(i > 100) {
				break;
			}
			System.err.println(++i);
		}
		videoWriter.release();
		CameraSingleton.release();
	}
};