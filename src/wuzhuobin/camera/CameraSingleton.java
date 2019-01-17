package wuzhuobin.camera;

import org.opencv.videoio.VideoCapture;

public class CameraSingleton{
	public static VideoCapture getInstance() {
		if(CameraSingleton.videoCapture == null) {
			CameraSingleton.videoCapture = new VideoCapture(0);
		}
		if(!CameraSingleton.videoCapture.isOpened()) {
			CameraSingleton.videoCapture = null;
		}
		return CameraSingleton.videoCapture;
	}
	public static void release() {
		if(CameraSingleton.videoCapture != null) {
			CameraSingleton.videoCapture.release();
		}
	}
	private static VideoCapture videoCapture = null;
};