package jp.gr.java_conf.shinexer.stepcounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.shinexer.stepcounter.bean.Result;
import jp.gr.java_conf.shinexer.stepcounter.parser.Parser;
import jp.gr.java_conf.shinexer.stepcounter.parser.ParserFactory;
import jp.gr.java_conf.shinexer.stepcounter.util.BeanUtil;

public class StepCounter {

	private ParserFactory _parserFactory = null;
	private List<Result> _results = null;

	public StepCounter() {
		_parserFactory = ParserFactory.getInstance();
		_results = new ArrayList<>();
	}

	public StepCounter(int fileCount) {
		_parserFactory = ParserFactory.getInstance();
		_results = new ArrayList<>(fileCount);
	}

	public static void main(String[] args) {

		if (args.length == 0) {
			args = new String[] { "Test.java" };
		}

		StepCounter stepCounter = new StepCounter(args.length);
		for (String path : args) {
			stepCounter.doCount(new File(path), stepCounter.getResults());
		}

		System.out.println(BeanUtil.toJSONString(stepCounter.getResults()));
	}

	private void doCount(File f, List<Result> results) {
		if (f.isDirectory()) {
			for (File child : f.listFiles()) {
				doCount(child, results);
			}
		} else {
			Parser parser = _parserFactory.get(f.getName());
			try (FileInputStream in = new FileInputStream(f)) {
				Result result = parser.parse(in);
				result.setPath(f.getPath().replaceAll("\\\\", "\\\\\\\\"));
				results.add(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Result> getResults() {
		return _results;
	}
}
