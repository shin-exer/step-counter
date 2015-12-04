package jp.gr.java_conf.shinexer.stepcounter.parser;


public interface Judge {
	public Judgement judge(String line, boolean inMultiLineComment);
}
