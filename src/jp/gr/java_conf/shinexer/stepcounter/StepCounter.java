package jp.gr.java_conf.shinexer.stepcounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.shinexer.stepcounter.bean.Result;
import jp.gr.java_conf.shinexer.stepcounter.parser.JavaParser;
import jp.gr.java_conf.shinexer.stepcounter.util.BeanUtil;

public class StepCounter {
	public static void main(String[] args) {

		if (args.length == 0) {
			args = new String[] { "Test.java" };
		}

		List<Result> results = new ArrayList<>(args.length);
		for (String path : args) {
			doCount(new File(path), results);
		}

		System.out.println(BeanUtil.toJSONString(results));
	}

	private static void doCount(File f, List<Result> results) {
		if (f.isDirectory()) {
			for (File child : f.listFiles()) {
				doCount(child, results);
			}
		} else {
			if (f.getName().endsWith(".java")) {
				try (FileInputStream in = new FileInputStream(f)) {
					Result result = new JavaParser().parse(in);
					result.setPath(f.getPath().replaceAll("\\\\", "\\\\\\\\"));
					results.add(result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
