package jp.gr.java_conf.shinexer.stepcounter.parser;

public class JavaParser extends BaseParser {

	public JavaParser() {
		super((line, inMultiLineComment) -> {
			if (inMultiLineComment) {
				if (line.matches(".*\\*/[ \\t]*$")) {
					return Judgement.COMMENT_MULTI_LINE_END;
				} else if (line.matches(".*\\*/[^ \\t]*$")) {
					return Judgement.STEP;
				}
				return Judgement.COMMENT_MULTI_LINE;
			}
			if (line.matches("[ \\t]*|[ \\t]*\\{|[ \\t]*\\}")) {
				return Judgement.BLANK;
			}
			if (line.matches("[ \\t]*//.*")) {
				return Judgement.COMMENT_SINGLE_LINE;
			}
			if (line.matches(".*/\\*(([ \\t]*)|!(\\*/))$")) {
				return Judgement.COMMENT_MULTI_LINE_START;
			}
			return Judgement.STEP;
		});
	}
}
