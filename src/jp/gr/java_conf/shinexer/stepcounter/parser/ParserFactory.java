package jp.gr.java_conf.shinexer.stepcounter.parser;

import java.util.HashMap;
import java.util.Map;

public class ParserFactory {
	private Map<String, Parser> _parserHolder = new HashMap<>();

	private static ParserFactory _singleton = new ParserFactory();

	private Object lock = new Object();

	public static ParserFactory getInstance() {
		return _singleton;
	}

	public Parser get(String fileName) {

		String extension = fileName.substring(fileName.lastIndexOf("."));
		return getParser(extension);
	}

	private Parser getParser(String extension) {
		synchronized (lock) {
			Parser parser = _parserHolder.get(extension);
			if (parser == null) {
				if ("java".equals(extension) || "js".equals(extension)) {
					parser = new JavaParser();
					_parserHolder.put(extension, parser);
				} else {
					// default parser
					parser = new JavaParser();
					_parserHolder.put(extension, new JavaParser());
				}
			}
			return parser;
		}
	}
}
