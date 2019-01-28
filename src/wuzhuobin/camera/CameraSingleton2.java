package wuzhuobin.camera;

import org.opencv.videoio.VideoCapture;

public class CameraSingleton2 extends VideoCapture {

	public static CameraSingleton2 getInstance() {
		if (instance == null) {
			instance = new CameraSingleton2();
		}
		return instance;
	}
	
	public boolean open() {
		return super.open(0);
	}
	// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	@Deprecated
	@Override
	public boolean open(int index) {
		return this.open();
	}
	
	
	@Deprecated
	@Override
	public boolean open(int cameraNum, int apiPerference) {
		return this.open();
	}

	@Deprecated
	@Override
	public boolean open(String fileName) {
		return this.open();
	}
	
	@Deprecated
	@Override
	public boolean open(String fileName, int aptPerference) {
		return this.open();
	}
	// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	
	private CameraSingleton2() {
		super();
	}

	static private CameraSingleton2 instance = null;
}

class Test {
	static void test() {
		CameraSingleton2 camera = CameraSingleton2.getInstance();
	}
}