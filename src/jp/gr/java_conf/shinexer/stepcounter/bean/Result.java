package jp.gr.java_conf.shinexer.stepcounter.bean;

public class Result {
	private String path;
	private int step;
	private int comment;
	private int blank;

	public Result() {
		step = 0;
		comment = 0;
		blank = 0;
	}

	public Result(String path, int step, int comment, int blank) {
		this.path = path;
		this.step = step;
		this.comment = comment;
		this.blank = blank;
	}

	public Result(int step, int comment, int blank) {
		this(null, step, comment, blank);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getBlank() {
		return blank;
	}

	public void setBlank(int blank) {
		this.blank = blank;
	}
}
