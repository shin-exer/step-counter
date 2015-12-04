package jp.gr.java_conf.shinexer.stepcounter.parser;

import java.io.IOException;
import java.io.InputStream;

import jp.gr.java_conf.shinexer.stepcounter.bean.Result;

public interface Parser {
	public Result parse(InputStream in) throws IOException;
}
