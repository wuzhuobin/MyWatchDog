package wuzhuobin.camera;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

public class CameraSingleton {
	
	public static CameraSingleton getInstance() {
		if(cameraSingleton == null) {
			cameraSingleton = new CameraSingleton();
		}
		return cameraSingleton;
	}

	private CameraSingleton() {	
		this.videoCapture = new VideoCapture();
		this.videoWriter = new VideoWriter();
		this.setImage(new Mat());
		this.executorService = null;
		this.fps = 10;
	}
	
	public void start(String filename) {
		System.out.println("Camera start!");
		Mat firstFrame = new Mat();
		this.videoCapture.open(0);
		if (!this.videoCapture.isOpened()) {
			System.err.println("Camera cannot open.");
			return;
		}
		this.videoCapture.read(firstFrame);
		this.videoWriter.open(filename, VideoWriter.fourcc('M', 'J', 'P', 'G'), this.fps, firstFrame.size());
		this.executorService = Executors.newSingleThreadScheduledExecutor();
		this.executorService.scheduleAtFixedRate(() -> {
			// TODO Auto-generated method stub
			Mat oneFrame = new Mat();
			this.videoCapture.read(oneFrame);
			videoWriter.write(oneFrame);
			this.setImage(oneFrame);
		}, 0, 1000 / this.fps, TimeUnit.MILLISECONDS);
	}
	
	public void stop() {
		System.out.println("Camera Stop!");
		if(this.executorService != null && !this.executorService.isShutdown()) {
			this.executorService.shutdown();
			try {
				this.executorService.awaitTermination(1000 / this.fps, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(this.videoCapture.isOpened()) {
			this.videoCapture.release();
			this.videoWriter.release();
		}
	}
	
	public boolean isRecording() {
		return this.videoCapture.isOpened();
	}
	
	public void setFps(int fps) {
		fps = Math.max(fps, 1);
		fps = Math.min(fps, 20);
		this.fps = fps;
	}
	
	public Mat getImage() {
		return image;
	}

	private void setImage(Mat image) {
		this.image = image;
	}

	private static CameraSingleton cameraSingleton = null;
	private VideoCapture videoCapture;
	private VideoWriter videoWriter;
	private Mat image;
	private ScheduledExecutorService executorService;
	private int fps;
};
