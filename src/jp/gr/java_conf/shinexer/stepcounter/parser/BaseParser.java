package jp.gr.java_conf.shinexer.stepcounter.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.gr.java_conf.shinexer.stepcounter.bean.Result;

public class BaseParser implements Parser {

	protected Judge _judge;

	public BaseParser(Judge judge) {
		_judge = judge;
	}

	@Override
	public Result parse(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		int step = 0;
		int comment = 0;
		int blank = 0;
		boolean isMultiLineComment = false;

		for (String line = null; (line = reader.readLine()) != null;) {
			Judgement judgement = _judge.judge(line, isMultiLineComment);

			switch (judgement) {
			case STEP:
				step++;
				isMultiLineComment = false;
				break;
			case BLANK:
				blank ++;
				break;
			case COMMENT_SINGLE_LINE:
				comment++;
				isMultiLineComment = false;
				break;
			case COMMENT_MULTI_LINE_START:
				comment++;
				isMultiLineComment = true;
				break;
			case COMMENT_MULTI_LINE:
				comment++;
				break;
			case COMMENT_MULTI_LINE_END:
				comment++;
				isMultiLineComment = false;
				break;
			}
		}

		return new Result(step, comment, blank);
	}
}
